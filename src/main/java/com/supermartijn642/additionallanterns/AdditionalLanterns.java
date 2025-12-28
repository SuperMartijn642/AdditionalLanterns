package com.supermartijn642.additionallanterns;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.supermartijn642.additionallanterns.data.*;
import com.supermartijn642.core.CommonUtils;
import com.supermartijn642.core.item.CreativeItemGroup;
import com.supermartijn642.core.registry.GeneratorRegistrationHandler;
import com.supermartijn642.core.registry.RegistrationHandler;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

/**
 * Created 7/7/2020 by SuperMartijn642
 */
@Mod("additionallanterns")
public class AdditionalLanterns {

    public static final CreativeItemGroup GROUP = CreativeItemGroup.create("additionallanterns", AdditionalLanterns::randomLantern)
        .filler(items -> {
            for(LanternMaterial material : LanternMaterial.MATERIALS){
                items.accept(material.getLanternBlock().asItem().getDefaultInstance());
                if(material.canBeColored){
                    for(LanternColor color : LanternColor.values())
                        items.accept(material.getLanternBlock(color).asItem().getDefaultInstance());
                }
                if(material.hasChains)
                    items.accept(material.getChainBlock().asItem().getDefaultInstance());
            }
        });
    public static final Logger LOGGER = CommonUtils.getLogger("additionallanterns");

    private static final Random RANDOM = new Random();

    private static ItemStack randomLantern(){
        LanternMaterial material = LanternMaterial.MATERIALS.get(RANDOM.nextInt(LanternMaterial.MATERIALS.size()));
        if(!material.canBeColored)
            return material.getLanternBlock().asItem().getDefaultInstance();
        int colorIndex = RANDOM.nextInt(LanternColor.values().length + 1);
        LanternColor color = LanternColor.colorsAndNull().get(colorIndex);
        return material.getLanternBlock(color).asItem().getDefaultInstance();
    }

    public AdditionalLanterns(FMLJavaModLoadingContext context){
        VanillaLanternEvents.registerEventHandlers();
        FMLCommonSetupEvent.getBus(context.getModBusGroup()).addListener(AdditionalLanterns::init);

        register();
        if(CommonUtils.getEnvironmentSide().isClient())
            AdditionalLanternsClient.register();
        registerGenerators();
    }

    private static void register(){
        RegistrationHandler handler = RegistrationHandler.get("additionallanterns");
        for(LanternMaterial material : LanternMaterial.MATERIALS){
            handler.registerBlockCallback(material::registerBlocks);
            handler.registerItemCallback(material::registerItems);
        }
    }

    public static void init(FMLCommonSetupEvent e){
        e.enqueueWork(() -> {
            try{
                // Waxing
                Field delegateField = HoneycombItem.WAXABLES.getClass().getDeclaredField("delegate");
                delegateField.setAccessible(true);
                //noinspection unchecked
                Supplier<BiMap<Block,Block>> oldWaxables = (Supplier<BiMap<Block,Block>>)delegateField.get(HoneycombItem.WAXABLES);
                delegateField.set(HoneycombItem.WAXABLES, Suppliers.memoize(() -> {
                    ImmutableBiMap.Builder<Block,Block> builder = ImmutableBiMap.builder();
                    for(Map.Entry<LanternMaterial,LanternMaterial> entry : LanternMaterial.WAXING_MAPPINGS.entrySet()){
                        for(LanternColor color : LanternColor.colorsAndNull()){
                            builder.put(entry.getKey().getLanternBlock(color), entry.getValue().getLanternBlock(color));
                        }
                    }
                    builder.putAll(oldWaxables.get());
                    return builder.build();
                }));

                // Weathering
                delegateField = WeatheringCopper.NEXT_BY_BLOCK.getClass().getDeclaredField("delegate");
                delegateField.setAccessible(true);
                //noinspection unchecked
                Supplier<BiMap<Block,Block>> oldWeathering = (Supplier<BiMap<Block,Block>>)delegateField.get(WeatheringCopper.NEXT_BY_BLOCK);
                delegateField.set(WeatheringCopper.NEXT_BY_BLOCK, Suppliers.memoize(() -> {
                    ImmutableBiMap.Builder<Block,Block> builder = ImmutableBiMap.builder();
                    for(Map.Entry<LanternMaterial,LanternMaterial> entry : LanternMaterial.OXIDATION_MAPPINGS.entrySet()){
                        for(LanternColor color : LanternColor.colorsAndNull()){
                            builder.put(entry.getKey().getLanternBlock(color), entry.getValue().getLanternBlock(color));
                        }
                    }
                    builder.putAll(oldWeathering.get());
                    return builder.build();
                }));
            }catch(Exception exception){
                LOGGER.error("Failed to replace waxing and weathering maps! Copper lanterns will not be waxable or oxidize!", exception);
            }
        });
    }

    private static void registerGenerators(){
        GeneratorRegistrationHandler handler = GeneratorRegistrationHandler.get("additionallanterns");
        handler.addGenerator(LanternBlockModelGenerator::new);
        handler.addGenerator(LanternItemInfoGenerator::new);
        handler.addGenerator(LanternItemModelGenerator::new);
        handler.addGenerator(LanternBlockStateGenerator::new);
        handler.addGenerator(LanternLanguageGenerator::new);
        handler.addGenerator(LanternLootTableGenerator::new);
        handler.addGenerator(LanternTagGenerator::new);
        handler.addGenerator(LanternRecipeGenerator::new);
    }
}

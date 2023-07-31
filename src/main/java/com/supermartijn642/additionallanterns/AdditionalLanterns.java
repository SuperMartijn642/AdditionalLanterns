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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.util.function.Supplier;

/**
 * Created 7/7/2020 by SuperMartijn642
 */
@Mod("additionallanterns")
public class AdditionalLanterns {

    public static final CreativeItemGroup GROUP = CreativeItemGroup.create("additionallanterns", () -> LanternMaterial.NORMAL.getLanternBlock().asItem());
    public static final Logger LOGGER = CommonUtils.getLogger("additionallanterns");

    public AdditionalLanterns(){
        VanillaLanternEvents.registerEventHandlers();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(AdditionalLanterns::init);

        register();
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> AdditionalLanternsClient::register);
        registerGenerators();
    }

    private static void register(){
        RegistrationHandler handler = RegistrationHandler.get("additionallanterns");
        for(LanternMaterial material : LanternMaterial.values()){
            handler.registerBlockCallback(material::registerBlocks);
            handler.registerItemCallback(material::registerItems);
        }
    }

    public static void init(FMLCommonSetupEvent e){
        e.enqueueWork(() -> {
            try{
                // Waxing
                System.out.println("Class: " + HoneycombItem.WAXABLES.getClass());
                Field delegateField = HoneycombItem.WAXABLES.getClass().getDeclaredField("delegate");
                delegateField.setAccessible(true);
                //noinspection unchecked
                Supplier<BiMap<Block,Block>> oldWaxables = (Supplier<BiMap<Block,Block>>)delegateField.get(HoneycombItem.WAXABLES);
                delegateField.set(HoneycombItem.WAXABLES, Suppliers.memoize(() -> {
                    ImmutableBiMap.Builder<Block,Block> builder = ImmutableBiMap.builder();
                    builder.put(LanternMaterial.COPPER.getLanternBlock(), LanternMaterial.WAXED_COPPER.getLanternBlock());
                    builder.put(LanternMaterial.COPPER.getChainBlock(), LanternMaterial.WAXED_COPPER.getChainBlock());
                    builder.put(LanternMaterial.EXPOSED_COPPER.getLanternBlock(), LanternMaterial.WAXED_EXPOSED_COPPER.getLanternBlock());
                    builder.put(LanternMaterial.EXPOSED_COPPER.getChainBlock(), LanternMaterial.WAXED_EXPOSED_COPPER.getChainBlock());
                    builder.put(LanternMaterial.WEATHERED_COPPER.getLanternBlock(), LanternMaterial.WAXED_WEATHERED_COPPER.getLanternBlock());
                    builder.put(LanternMaterial.WEATHERED_COPPER.getChainBlock(), LanternMaterial.WAXED_WEATHERED_COPPER.getChainBlock());
                    builder.put(LanternMaterial.OXIDIZED_COPPER.getLanternBlock(), LanternMaterial.WAXED_OXIDIZED_COPPER.getLanternBlock());
                    builder.put(LanternMaterial.OXIDIZED_COPPER.getChainBlock(), LanternMaterial.WAXED_OXIDIZED_COPPER.getChainBlock());
                    for(LanternColor color : LanternColor.values()){
                        builder.put(LanternMaterial.COPPER.getLanternBlock(color), LanternMaterial.WAXED_COPPER.getLanternBlock(color));
                        builder.put(LanternMaterial.EXPOSED_COPPER.getLanternBlock(color), LanternMaterial.WAXED_EXPOSED_COPPER.getLanternBlock(color));
                        builder.put(LanternMaterial.WEATHERED_COPPER.getLanternBlock(color), LanternMaterial.WAXED_WEATHERED_COPPER.getLanternBlock(color));
                        builder.put(LanternMaterial.OXIDIZED_COPPER.getLanternBlock(color), LanternMaterial.WAXED_OXIDIZED_COPPER.getLanternBlock(color));
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
                    builder.put(LanternMaterial.COPPER.getLanternBlock(), LanternMaterial.EXPOSED_COPPER.getLanternBlock());
                    builder.put(LanternMaterial.EXPOSED_COPPER.getLanternBlock(), LanternMaterial.WEATHERED_COPPER.getLanternBlock());
                    builder.put(LanternMaterial.WEATHERED_COPPER.getLanternBlock(), LanternMaterial.OXIDIZED_COPPER.getLanternBlock());
                    builder.put(LanternMaterial.COPPER.getChainBlock(), LanternMaterial.EXPOSED_COPPER.getChainBlock());
                    builder.put(LanternMaterial.EXPOSED_COPPER.getChainBlock(), LanternMaterial.WEATHERED_COPPER.getChainBlock());
                    builder.put(LanternMaterial.WEATHERED_COPPER.getChainBlock(), LanternMaterial.OXIDIZED_COPPER.getChainBlock());
                    for(LanternColor color : LanternColor.values()){
                        builder.put(LanternMaterial.COPPER.getLanternBlock(color), LanternMaterial.EXPOSED_COPPER.getLanternBlock(color));
                        builder.put(LanternMaterial.EXPOSED_COPPER.getLanternBlock(color), LanternMaterial.WEATHERED_COPPER.getLanternBlock(color));
                        builder.put(LanternMaterial.WEATHERED_COPPER.getLanternBlock(color), LanternMaterial.OXIDIZED_COPPER.getLanternBlock(color));
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
        handler.addGenerator(LanternItemModelGenerator::new);
        handler.addGenerator(LanternBlockStateGenerator::new);
        handler.addGenerator(LanternLanguageGenerator::new);
        handler.addGenerator(LanternLootTableGenerator::new);
        handler.addGenerator(LanternTagGenerator::new);
        handler.addGenerator(LanternRecipeGenerator::new);
    }
}

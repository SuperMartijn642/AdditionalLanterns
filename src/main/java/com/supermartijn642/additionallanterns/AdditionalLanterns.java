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
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

import java.util.function.Supplier;

/**
 * Created 7/7/2020 by SuperMartijn642
 */
@Mod("additionallanterns")
public class AdditionalLanterns {

    public static final CreativeItemGroup GROUP = CreativeItemGroup.create("additionallanterns", () -> LanternMaterial.NORMAL.getLanternBlock().asItem())
        .filler(items -> {
            for(LanternMaterial material : LanternMaterial.values()){
                items.accept(material.getLanternBlock().asItem().getDefaultInstance());
                for(LanternColor color : LanternColor.values())
                    items.accept(material.getLanternBlock(color).asItem().getDefaultInstance());
                if(material.hasChains)
                    items.accept(material.getChainBlock().asItem().getDefaultInstance());
            }
        });
    public static final Logger LOGGER = CommonUtils.getLogger("additionallanterns");

    public AdditionalLanterns(IEventBus eventBus){
        VanillaLanternEvents.registerEventHandlers();
        eventBus.addListener(AdditionalLanterns::init);

        register();
        if(CommonUtils.getEnvironmentSide().isClient())
            AdditionalLanternsClient.register();
        registerGenerators();
    }

    private static void register(){
        RegistrationHandler handler = RegistrationHandler.get("additionallanterns");
        for(LanternMaterial material : LanternMaterial.values()){
            handler.registerBlockCallback(material::registerBlocks);
            handler.registerItemCallback(material::registerItems);
        }
        handler.registerBlockCallback(helper -> {
            // Weathering
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
            WeatheringLanternBlock.WEATHERING_MAP = builder.build();
        });
    }

    public static void init(FMLCommonSetupEvent e){
        e.enqueueWork(() -> {
            try{
                // Waxing
                Supplier<BiMap<Block,Block>> oldWaxables = HoneycombItem.WAXABLES;
                HoneycombItem.WAXABLES = Suppliers.memoize(() -> {
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
                });
            }catch(Exception exception){
                LOGGER.error("Failed to replace waxing map! Copper lanterns will not be waxable!", exception);
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

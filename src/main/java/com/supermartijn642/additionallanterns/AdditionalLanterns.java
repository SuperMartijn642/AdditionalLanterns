package com.supermartijn642.additionallanterns;

import com.supermartijn642.additionallanterns.data.*;
import com.supermartijn642.core.item.CreativeItemGroup;
import com.supermartijn642.core.registry.GeneratorRegistrationHandler;
import com.supermartijn642.core.registry.RegistrationHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;

/**
 * Created 7/7/2020 by SuperMartijn642
 */
public class AdditionalLanterns implements ModInitializer {

    public static final CreativeItemGroup GROUP = CreativeItemGroup.create("additionallanterns", () -> LanternMaterial.NORMAL.getLanternBlock().asItem());

    @Override
    public void onInitialize(){
        VanillaLanternEvents.registerEventHandlers();

        register();
        registerGenerators();
    }

    private static void register(){
        RegistrationHandler handler = RegistrationHandler.get("additionallanterns");
        for(LanternMaterial material : LanternMaterial.values()){
            handler.registerBlockCallback(material::registerBlocks);
            handler.registerItemCallback(material::registerItems);
        }
        handler.registerBlockCallback(helper -> {
            // Waxing
            OxidizableBlocksRegistry.registerWaxableBlockPair(LanternMaterial.COPPER.getLanternBlock(), LanternMaterial.WAXED_COPPER.getLanternBlock());
            OxidizableBlocksRegistry.registerWaxableBlockPair(LanternMaterial.COPPER.getChainBlock(), LanternMaterial.WAXED_COPPER.getChainBlock());
            OxidizableBlocksRegistry.registerWaxableBlockPair(LanternMaterial.EXPOSED_COPPER.getLanternBlock(), LanternMaterial.WAXED_EXPOSED_COPPER.getLanternBlock());
            OxidizableBlocksRegistry.registerWaxableBlockPair(LanternMaterial.EXPOSED_COPPER.getChainBlock(), LanternMaterial.WAXED_EXPOSED_COPPER.getChainBlock());
            OxidizableBlocksRegistry.registerWaxableBlockPair(LanternMaterial.WEATHERED_COPPER.getLanternBlock(), LanternMaterial.WAXED_WEATHERED_COPPER.getLanternBlock());
            OxidizableBlocksRegistry.registerWaxableBlockPair(LanternMaterial.WEATHERED_COPPER.getChainBlock(), LanternMaterial.WAXED_WEATHERED_COPPER.getChainBlock());
            OxidizableBlocksRegistry.registerWaxableBlockPair(LanternMaterial.OXIDIZED_COPPER.getLanternBlock(), LanternMaterial.WAXED_OXIDIZED_COPPER.getLanternBlock());
            OxidizableBlocksRegistry.registerWaxableBlockPair(LanternMaterial.OXIDIZED_COPPER.getChainBlock(), LanternMaterial.WAXED_OXIDIZED_COPPER.getChainBlock());
            for(LanternColor color : LanternColor.values()){
                OxidizableBlocksRegistry.registerWaxableBlockPair(LanternMaterial.COPPER.getLanternBlock(color), LanternMaterial.WAXED_COPPER.getLanternBlock(color));
                OxidizableBlocksRegistry.registerWaxableBlockPair(LanternMaterial.EXPOSED_COPPER.getLanternBlock(color), LanternMaterial.WAXED_EXPOSED_COPPER.getLanternBlock(color));
                OxidizableBlocksRegistry.registerWaxableBlockPair(LanternMaterial.WEATHERED_COPPER.getLanternBlock(color), LanternMaterial.WAXED_WEATHERED_COPPER.getLanternBlock(color));
                OxidizableBlocksRegistry.registerWaxableBlockPair(LanternMaterial.OXIDIZED_COPPER.getLanternBlock(color), LanternMaterial.WAXED_OXIDIZED_COPPER.getLanternBlock(color));
            }

            // Weathering
            OxidizableBlocksRegistry.registerOxidizableBlockPair(LanternMaterial.COPPER.getLanternBlock(), LanternMaterial.EXPOSED_COPPER.getLanternBlock());
            OxidizableBlocksRegistry.registerOxidizableBlockPair(LanternMaterial.EXPOSED_COPPER.getLanternBlock(), LanternMaterial.WEATHERED_COPPER.getLanternBlock());
            OxidizableBlocksRegistry.registerOxidizableBlockPair(LanternMaterial.WEATHERED_COPPER.getLanternBlock(), LanternMaterial.OXIDIZED_COPPER.getLanternBlock());
            OxidizableBlocksRegistry.registerOxidizableBlockPair(LanternMaterial.COPPER.getChainBlock(), LanternMaterial.EXPOSED_COPPER.getChainBlock());
            OxidizableBlocksRegistry.registerOxidizableBlockPair(LanternMaterial.EXPOSED_COPPER.getChainBlock(), LanternMaterial.WEATHERED_COPPER.getChainBlock());
            OxidizableBlocksRegistry.registerOxidizableBlockPair(LanternMaterial.WEATHERED_COPPER.getChainBlock(), LanternMaterial.OXIDIZED_COPPER.getChainBlock());
            for(LanternColor color : LanternColor.values()){
                OxidizableBlocksRegistry.registerOxidizableBlockPair(LanternMaterial.COPPER.getLanternBlock(color), LanternMaterial.EXPOSED_COPPER.getLanternBlock(color));
                OxidizableBlocksRegistry.registerOxidizableBlockPair(LanternMaterial.EXPOSED_COPPER.getLanternBlock(color), LanternMaterial.WEATHERED_COPPER.getLanternBlock(color));
                OxidizableBlocksRegistry.registerOxidizableBlockPair(LanternMaterial.WEATHERED_COPPER.getLanternBlock(color), LanternMaterial.OXIDIZED_COPPER.getLanternBlock(color));
                OxidizableBlocksRegistry.registerOxidizableBlockPair(LanternMaterial.COPPER.getChainBlock(), LanternMaterial.EXPOSED_COPPER.getChainBlock());
                OxidizableBlocksRegistry.registerOxidizableBlockPair(LanternMaterial.EXPOSED_COPPER.getChainBlock(), LanternMaterial.WEATHERED_COPPER.getChainBlock());
                OxidizableBlocksRegistry.registerOxidizableBlockPair(LanternMaterial.WEATHERED_COPPER.getChainBlock(), LanternMaterial.OXIDIZED_COPPER.getChainBlock());
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

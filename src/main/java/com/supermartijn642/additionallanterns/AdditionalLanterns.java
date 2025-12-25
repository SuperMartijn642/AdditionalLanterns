package com.supermartijn642.additionallanterns;

import com.supermartijn642.additionallanterns.data.*;
import com.supermartijn642.core.item.CreativeItemGroup;
import com.supermartijn642.core.registry.GeneratorRegistrationHandler;
import com.supermartijn642.core.registry.RegistrationHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.minecraft.world.item.ItemStack;

import java.util.Map;
import java.util.Random;

/**
 * Created 7/7/2020 by SuperMartijn642
 */
public class AdditionalLanterns implements ModInitializer {

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

    private static final Random RANDOM = new Random();

    private static ItemStack randomLantern(){
        LanternMaterial material = LanternMaterial.MATERIALS.get(RANDOM.nextInt(LanternMaterial.MATERIALS.size()));
        if(!material.canBeColored)
            return material.getLanternBlock().asItem().getDefaultInstance();
        int colorIndex = RANDOM.nextInt(LanternColor.values().length + 1);
        LanternColor color = LanternColor.colorsAndNull().get(colorIndex);
        return material.getLanternBlock(color).asItem().getDefaultInstance();
    }

    @Override
    public void onInitialize(){
        VanillaLanternEvents.registerEventHandlers();

        register();
        registerGenerators();
    }

    private static void register(){
        RegistrationHandler handler = RegistrationHandler.get("additionallanterns");
        for(LanternMaterial material : LanternMaterial.MATERIALS){
            handler.registerBlockCallback(material::registerBlocks);
            handler.registerItemCallback(material::registerItems);
        }
        handler.registerBlockCallback(helper -> {
            // Waxing
            for(Map.Entry<LanternMaterial,LanternMaterial> entry : LanternMaterial.WAXING_MAPPINGS.entrySet()){
                for(LanternColor color : LanternColor.colorsAndNull()){
                    OxidizableBlocksRegistry.registerWaxableBlockPair(entry.getKey().getLanternBlock(color), entry.getValue().getLanternBlock(color));
                }
            }
            // Weathering
            for(Map.Entry<LanternMaterial,LanternMaterial> entry : LanternMaterial.OXIDATION_MAPPINGS.entrySet()){
                for(LanternColor color : LanternColor.colorsAndNull()){
                    OxidizableBlocksRegistry.registerOxidizableBlockPair(entry.getKey().getLanternBlock(color), entry.getValue().getLanternBlock(color));
                }
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

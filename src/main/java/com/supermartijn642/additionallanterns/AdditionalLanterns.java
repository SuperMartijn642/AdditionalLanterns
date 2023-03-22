package com.supermartijn642.additionallanterns;

import com.supermartijn642.additionallanterns.data.*;
import com.supermartijn642.core.item.CreativeItemGroup;
import com.supermartijn642.core.registry.GeneratorRegistrationHandler;
import com.supermartijn642.core.registry.RegistrationHandler;
import net.fabricmc.api.ModInitializer;

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

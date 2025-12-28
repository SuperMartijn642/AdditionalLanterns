package com.supermartijn642.additionallanterns;

import com.supermartijn642.additionallanterns.data.*;
import com.supermartijn642.core.CommonUtils;
import com.supermartijn642.core.item.CreativeItemGroup;
import com.supermartijn642.core.registry.GeneratorRegistrationHandler;
import com.supermartijn642.core.registry.RegistrationHandler;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Random;
import java.util.function.Consumer;

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

    private static final Random RANDOM = new Random();

    private static ItemStack randomLantern(){
        LanternMaterial material = LanternMaterial.MATERIALS.get(RANDOM.nextInt(LanternMaterial.MATERIALS.size()));
        if(!material.canBeColored)
            return material.getLanternBlock().asItem().getDefaultInstance();
        int colorIndex = RANDOM.nextInt(LanternColor.values().length + 1);
        LanternColor color = LanternColor.colorsAndNull().get(colorIndex);
        return material.getLanternBlock(color).asItem().getDefaultInstance();
    }

    public AdditionalLanterns(IEventBus eventBus){
        VanillaLanternEvents.registerEventHandlers();

        register();
        if(CommonUtils.getEnvironmentSide().isClient())
            AdditionalLanternsClient.register();
        registerGenerators(eventBus);
    }

    private static void register(){
        RegistrationHandler handler = RegistrationHandler.get("additionallanterns");
        for(LanternMaterial material : LanternMaterial.MATERIALS){
            handler.registerBlockCallback(material::registerBlocks);
            handler.registerItemCallback(material::registerItems);
        }
    }

    private static void registerGenerators(IEventBus eventBus){
        GeneratorRegistrationHandler handler = GeneratorRegistrationHandler.get("additionallanterns");
        handler.addGenerator(LanternBlockModelGenerator::new);
        handler.addGenerator(LanternItemInfoGenerator::new);
        handler.addGenerator(LanternItemModelGenerator::new);
        handler.addGenerator(LanternBlockStateGenerator::new);
        handler.addGenerator(LanternLanguageGenerator::new);
        handler.addGenerator(LanternLootTableGenerator::new);
        handler.addGenerator(LanternTagGenerator::new);
        handler.addGenerator(LanternRecipeGenerator::new);
        eventBus.addListener((Consumer<GatherDataEvent.Client>)e -> e.createProvider(LanternDataMapDataProvider::new));
    }
}

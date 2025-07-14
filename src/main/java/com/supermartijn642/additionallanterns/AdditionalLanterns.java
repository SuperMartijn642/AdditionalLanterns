package com.supermartijn642.additionallanterns;

import com.supermartijn642.additionallanterns.data.*;
import com.supermartijn642.core.CommonUtils;
import com.supermartijn642.core.item.CreativeItemGroup;
import com.supermartijn642.core.registry.GeneratorRegistrationHandler;
import com.supermartijn642.core.registry.RegistrationHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.Logger;

import java.util.Random;

/**
 * Created 7/7/2020 by SuperMartijn642
 */
@Mod("additionallanterns")
public class AdditionalLanterns {

    public static final CreativeItemGroup GROUP = CreativeItemGroup.create("additionallanterns", AdditionalLanterns::randomLantern)
        .filler(items -> {
            for(LanternMaterial material : LanternMaterial.values()){
                if(material == LanternMaterial.NORMAL)
                    items.accept(Items.LANTERN.getDefaultInstance());
                else
                    items.accept(material.getLanternBlock().asItem().getDefaultInstance());
                for(LanternColor color : LanternColor.values())
                    items.accept(material.getLanternBlock(color).asItem().getDefaultInstance());
                if(material.hasChains)
                    items.accept(material.getChainBlock().asItem().getDefaultInstance());
            }
        });
    public static final Logger LOGGER = CommonUtils.getLogger("additionallanterns");

    private static final Random RANDOM = new Random();

    private static ItemStack randomLantern(){
        LanternMaterial material = LanternMaterial.values()[RANDOM.nextInt(LanternMaterial.values().length)];
        int colorIndex = RANDOM.nextInt(LanternColor.values().length + 1);
        LanternColor color = colorIndex < LanternColor.values().length ? LanternColor.values()[colorIndex] : null;
        Item item = material == LanternMaterial.NORMAL && color == null ? Items.LANTERN : material.getLanternBlock(color).asItem();
        return item.getDefaultInstance();
    }

    public AdditionalLanterns(){
        VanillaLanternEvents.registerEventHandlers();

        register();
        DistExecutor.runWhenOn(Dist.CLIENT, () -> AdditionalLanternsClient::register);
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

package com.supermartijn642.additionallanterns;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created 7/7/2020 by SuperMartijn642
 */
@Mod(modid = AdditionalLanterns.MODID, name = AdditionalLanterns.NAME, version = AdditionalLanterns.VERSION, dependencies = AdditionalLanterns.DEPENDENCIES)
public class AdditionalLanterns {

    public static final String MODID = "additionallanterns";
    public static final String NAME = "Additional Lanterns";
    public static final String VERSION = "1.0.0";
    public static final String DEPENDENCIES = "required-after:supermartijn642corelib@[1.0.9,)";

    public static final CreativeTabs GROUP = new CreativeTabs("additionallanterns") {
        @Override
        public ItemStack getTabIconItem(){
            return new ItemStack(LanternMaterial.NORMAL.getLanternBlock());
        }
    };

    public AdditionalLanterns(){
    }

    @Mod.EventBusSubscriber
    public static class ModEvents {

        @SubscribeEvent
        public static void onBlockRegistry(RegistryEvent.Register<Block> e){
            for(LanternMaterial material : LanternMaterial.values())
                material.registerBlocks(e.getRegistry());
        }

        @SubscribeEvent
        public static void onItemRegistry(RegistryEvent.Register<Item> e){
            for(LanternMaterial material : LanternMaterial.values())
                material.registerItems(e.getRegistry());
        }
    }

}

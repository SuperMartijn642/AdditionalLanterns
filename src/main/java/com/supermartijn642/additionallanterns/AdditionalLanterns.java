package com.supermartijn642.additionallanterns;

import com.supermartijn642.additionallanterns.data.*;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

/**
 * Created 7/7/2020 by SuperMartijn642
 */
@Mod("additionallanterns")
public class AdditionalLanterns {

    public static final CreativeModeTab GROUP = new CreativeModeTab("additionallanterns") {
        @Override
        public ItemStack makeIcon(){
            return new ItemStack(LanternMaterial.NORMAL.getLanternBlock());
        }
    };

    public AdditionalLanterns(){
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
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

        @SubscribeEvent
        public static void onGatherData(GatherDataEvent e){
            e.getGenerator().addProvider(new LanternBlockModelProvider(e));
            e.getGenerator().addProvider(new LanternItemModelProvider(e));
            e.getGenerator().addProvider(new LanternBlockStateProvider(e));
            e.getGenerator().addProvider(new LanternLanguageProvider(e));
            e.getGenerator().addProvider(new LanternLootTableProvider(e));
            e.getGenerator().addProvider(new LanternTagsProvider(e));
            e.getGenerator().addProvider(new LanternBlockTagsProvider(e));
            e.getGenerator().addProvider(new LanternRecipeProvider(e));
        }
    }

}

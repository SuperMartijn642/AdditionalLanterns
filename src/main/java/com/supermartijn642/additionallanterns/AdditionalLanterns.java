package com.supermartijn642.additionallanterns;

import com.supermartijn642.additionallanterns.data.*;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegisterEvent;

import java.util.Objects;

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
        public static void onRegisterEvent(RegisterEvent e){
            if(e.getRegistryKey().equals(ForgeRegistries.Keys.BLOCKS))
                onBlockRegistry(Objects.requireNonNull(e.getForgeRegistry()));
            else if(e.getRegistryKey().equals(ForgeRegistries.Keys.ITEMS))
                onItemRegistry(Objects.requireNonNull(e.getForgeRegistry()));
        }

        public static void onBlockRegistry(IForgeRegistry<Block> registry){
            for(LanternMaterial material : LanternMaterial.values())
                material.registerBlocks(registry);
        }

        public static void onItemRegistry(IForgeRegistry<Item> registry){
            for(LanternMaterial material : LanternMaterial.values())
                material.registerItems(registry);
        }

        @SubscribeEvent
        public static void onGatherData(GatherDataEvent e){
            e.getGenerator().addProvider(e.includeClient(), new LanternBlockModelProvider(e));
            e.getGenerator().addProvider(e.includeClient(), new LanternItemModelProvider(e));
            e.getGenerator().addProvider(e.includeClient(), new LanternBlockStateProvider(e));
            e.getGenerator().addProvider(e.includeClient(), new LanternLanguageProvider(e));
            e.getGenerator().addProvider(e.includeServer(), new LanternLootTableProvider(e));
            e.getGenerator().addProvider(e.includeServer(), new LanternTagsProvider(e));
            e.getGenerator().addProvider(e.includeServer(), new LanternBlockTagsProvider(e));
            e.getGenerator().addProvider(e.includeServer(), new LanternRecipeProvider(e));
        }
    }

}

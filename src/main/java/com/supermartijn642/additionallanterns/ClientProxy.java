package com.supermartijn642.additionallanterns;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created 7/1/2021 by SuperMartijn642
 */
@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy {

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent e){
        for(LanternMaterial material : LanternMaterial.values()){
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(material.getLanternBlock()), 0, new ModelResourceLocation(material.getLanternBlock().getRegistryName(), "inventory"));
            if(material.canBeColored){
                for(LanternColor color : LanternColor.values())
                    ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(material.getLanternBlock(color)), 0, new ModelResourceLocation(material.getLanternBlock(color).getRegistryName(), "inventory"));
            }
            if(material.hasChains)
                ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(material.getChainBlock()), 0, new ModelResourceLocation(material.getChainBlock().getRegistryName(), "inventory"));
        }
    }
}

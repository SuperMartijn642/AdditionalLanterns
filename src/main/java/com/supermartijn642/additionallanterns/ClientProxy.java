package com.supermartijn642.additionallanterns;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * Created 7/1/2021 by SuperMartijn642
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientProxy {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent e){
        for(LanternMaterial material : LanternMaterial.values()){
            ItemBlockRenderTypes.setRenderLayer(material.getLanternBlock(), RenderType.cutout());
            if(material.canBeColored){
                for(LanternColor color : LanternColor.values())
                    ItemBlockRenderTypes.setRenderLayer(material.getLanternBlock(color), RenderType.cutout());
            }
            if(material.hasChains)
                ItemBlockRenderTypes.setRenderLayer(material.getChainBlock(), RenderType.cutout());
        }
    }
}

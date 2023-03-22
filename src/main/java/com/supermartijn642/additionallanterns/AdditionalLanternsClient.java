package com.supermartijn642.additionallanterns;

import com.supermartijn642.core.registry.ClientRegistrationHandler;
import net.fabricmc.api.ClientModInitializer;

/**
 * Created 7/1/2021 by SuperMartijn642
 */
public class AdditionalLanternsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient(){
        ClientRegistrationHandler handler = ClientRegistrationHandler.get("additionallanterns");
        for(LanternMaterial material : LanternMaterial.values()){
            handler.registerBlockModelCutoutRenderType(material::getLanternBlock);
            if(material.canBeColored){
                for(LanternColor color : LanternColor.values())
                    handler.registerBlockModelCutoutRenderType(() -> material.getLanternBlock(color));
            }
            if(material.hasChains)
                handler.registerBlockModelCutoutRenderType(material::getChainBlock);
        }
    }
}

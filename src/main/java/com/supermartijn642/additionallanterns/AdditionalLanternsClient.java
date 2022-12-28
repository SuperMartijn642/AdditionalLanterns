package com.supermartijn642.additionallanterns;

import com.supermartijn642.core.registry.ClientRegistrationHandler;

/**
 * Created 7/1/2021 by SuperMartijn642
 */
public class AdditionalLanternsClient {

    public static void register(){
        ClientRegistrationHandler handler = ClientRegistrationHandler.get("additionallanterns");
        for(LanternMaterial material : LanternMaterial.values()){
            handler.registerBlockModelCutoutRenderType(material::getLanternBlock);
            if(material.canBeColored){
                for(LanternColor color : LanternColor.values())
                    handler.registerBlockModelCutoutRenderType(() -> material.getLanternBlock(color));
            }
        }
    }
}

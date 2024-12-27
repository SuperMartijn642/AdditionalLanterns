package com.supermartijn642.additionallanterns.data;

import com.supermartijn642.additionallanterns.LanternColor;
import com.supermartijn642.additionallanterns.LanternMaterial;
import com.supermartijn642.core.generator.ItemInfoGenerator;
import com.supermartijn642.core.generator.ResourceCache;

/**
 * Created 26/12/2024 by SuperMartijn642
 */
public class LanternItemInfoGenerator extends ItemInfoGenerator {

    public LanternItemInfoGenerator(ResourceCache cache){
        super("additionallanterns", cache);
    }

    @Override
    public void generate(){
        for(LanternMaterial material : LanternMaterial.values()){
            this.info(material.getLanternBlock()).model(this.model(LanternItemModelGenerator.getModelLocation(material, null)));
            if(material.canBeColored){
                for(LanternColor color : LanternColor.values())
                    this.info(material.getLanternBlock(color)).model(this.model(LanternItemModelGenerator.getModelLocation(material, color)));
            }
            if(material.hasChains)
                this.info(material.getChainBlock()).model(this.model(LanternItemModelGenerator.getChainModelLocation(material)));
        }
    }
}

package com.supermartijn642.additionallanterns.data;

import com.supermartijn642.additionallanterns.LanternColor;
import com.supermartijn642.additionallanterns.LanternMaterial;
import com.supermartijn642.core.generator.LootTableGenerator;
import com.supermartijn642.core.generator.ResourceCache;

/**
 * Created 7/6/2021 by SuperMartijn642
 */
public class LanternLootTableGenerator extends LootTableGenerator {

    public LanternLootTableGenerator(ResourceCache cache){
        super("additionallanterns", cache);
    }

    @Override
    public void generate(){
        for(LanternMaterial material : LanternMaterial.values()){
            this.dropSelf(material.getLanternBlock());

            if(material.canBeColored){
                for(LanternColor color : LanternColor.values())
                    this.dropSelf(material.getLanternBlock(color));
            }

            if(material.hasChains)
                this.dropSelf(material.getChainBlock());
        }
    }
}

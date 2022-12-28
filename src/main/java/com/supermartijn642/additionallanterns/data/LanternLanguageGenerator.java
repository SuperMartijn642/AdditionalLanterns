package com.supermartijn642.additionallanterns.data;

import com.supermartijn642.additionallanterns.AdditionalLanterns;
import com.supermartijn642.additionallanterns.LanternColor;
import com.supermartijn642.additionallanterns.LanternMaterial;
import com.supermartijn642.core.generator.LanguageGenerator;
import com.supermartijn642.core.generator.ResourceCache;

/**
 * Created 7/6/2021 by SuperMartijn642
 */
public class LanternLanguageGenerator extends LanguageGenerator {

    public LanternLanguageGenerator(ResourceCache cache){
        super("additionallanterns", cache, "en_us");
    }

    @Override
    public void generate(){
        this.itemGroup(AdditionalLanterns.GROUP, "Additional Lanterns");
        for(LanternMaterial material : LanternMaterial.values()){
            this.block(material.getLanternBlock(), material == LanternMaterial.NORMAL ? "Lantern" : material.englishTranslation + " Lantern");
            if(material.canBeColored){
                for(LanternColor color : LanternColor.values())
                    this.block(material.getLanternBlock(color), material == LanternMaterial.NORMAL ? color.englishTranslation + " Lantern" : color.englishTranslation + " " + material.englishTranslation + " Lantern");
            }
            if(material.hasChains)
                this.block(material.getChainBlock(), material.englishTranslation + " Chain");
        }
    }
}

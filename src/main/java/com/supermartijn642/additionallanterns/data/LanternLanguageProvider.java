package com.supermartijn642.additionallanterns.data;

import com.supermartijn642.additionallanterns.LanternColor;
import com.supermartijn642.additionallanterns.LanternMaterial;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

/**
 * Created 7/6/2021 by SuperMartijn642
 */
public class LanternLanguageProvider extends LanguageProvider {

    public LanternLanguageProvider(GatherDataEvent e){
        super(e.getGenerator(), "additionallanterns", "en_us");
    }

    @Override
    protected void addTranslations(){
        this.add("itemGroup.additionallanterns", "Additional Lanterns");
        for(LanternMaterial material : LanternMaterial.values()){
            this.add(material.getLanternBlock(), material == LanternMaterial.NORMAL ? "Lantern" : material.englishTranslation + " Lantern");
            if(material.canBeColored){
                for(LanternColor color : LanternColor.values())
                    this.add(material.getLanternBlock(color), material == LanternMaterial.NORMAL ? color.englishTranslation + " Lantern" : color.englishTranslation + " " + material.englishTranslation + " Lantern");
            }
            if(material.hasChains)
                this.add(material.getChainBlock(), material.englishTranslation + " Chain");
        }
    }
}

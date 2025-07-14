package com.supermartijn642.additionallanterns.data;

import com.supermartijn642.additionallanterns.LanternColor;
import com.supermartijn642.additionallanterns.LanternMaterial;
import com.supermartijn642.core.generator.ResourceCache;
import com.supermartijn642.core.generator.TagGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

/**
 * Created 8/5/2021 by SuperMartijn642
 */
public class LanternTagGenerator extends TagGenerator {

    public LanternTagGenerator(ResourceCache cache){
        super("additionallanterns", cache);
    }

    @Override
    public void generate(){
        // Tags for recipes
        for(LanternMaterial material : LanternMaterial.values())
            if(material.canBeColored)
                this.addMaterialTag(material);
    }

    private void addMaterialTag(LanternMaterial material){
        TagBuilder<Item> tag = this.itemTag(material.getSuffix() + "_lanterns");
        if(material == LanternMaterial.NORMAL)
            tag.add(Items.LANTERN);
        else
            tag.add(material.getLanternBlock().asItem());
        if(material.canBeColored){
            for(LanternColor color : LanternColor.values())
                tag.add(material.getLanternBlock(color).asItem());
        }
    }
}

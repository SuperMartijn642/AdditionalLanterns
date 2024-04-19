package com.supermartijn642.additionallanterns.data;

import com.supermartijn642.additionallanterns.LanternColor;
import com.supermartijn642.additionallanterns.LanternMaterial;
import com.supermartijn642.core.generator.ResourceCache;
import com.supermartijn642.core.generator.TagGenerator;
import net.minecraft.world.item.Item;

/**
 * Created 8/5/2021 by SuperMartijn642
 */
public class LanternTagGenerator extends TagGenerator {

    public LanternTagGenerator(ResourceCache cache){
        super("additionallanterns", cache);
    }

    @Override
    public void generate(){
        // Mineable tags
        for(LanternMaterial material : LanternMaterial.values()){
            this.blockMineableWithPickaxe().add(material.getLanternBlock());
            if(material.canBeColored){
                for(LanternColor color : LanternColor.values())
                    this.blockMineableWithPickaxe().add(material.getLanternBlock(color));
            }
            if(material.hasChains)
                this.blockMineableWithPickaxe().add(material.getChainBlock());
        }

        // Tags for recipes
        for(LanternMaterial material : LanternMaterial.values())
            if(material.canBeColored)
                this.addMaterialTag(material);

        // Compatibility tags for Dynamic Lights mod
        for(LanternMaterial material : LanternMaterial.values()){
            this.itemTag("dynamiclights", "light_level/15").add(material.getLanternBlock().asItem());
            if(material.canBeColored){
                for(LanternColor color : LanternColor.values())
                    this.itemTag("dynamiclights", "light_level/15").add(material.getLanternBlock(color).asItem());
            }
        }
    }

    private void addMaterialTag(LanternMaterial material){
        TagBuilder<Item> tag = this.itemTag(material.getSuffix() + "_lanterns");
        tag.add(material.getLanternBlock().asItem());
        if(material.canBeColored){
            for(LanternColor color : LanternColor.values())
                tag.add(material.getLanternBlock(color).asItem());
        }
    }
}

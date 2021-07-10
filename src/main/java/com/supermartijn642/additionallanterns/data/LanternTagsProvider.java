package com.supermartijn642.additionallanterns.data;

import com.supermartijn642.additionallanterns.LanternColor;
import com.supermartijn642.additionallanterns.LanternMaterial;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.data.TagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

import java.util.HashSet;
import java.util.Set;

/**
 * Created 7/6/2021 by SuperMartijn642
 */
public class LanternTagsProvider extends ItemTagsProvider {

    public LanternTagsProvider(GatherDataEvent e){
        super(e.getGenerator(), new BlockTagsProvider(e.getGenerator()), "additionallanterns", e.getExistingFileHelper());
    }

    @Override
    protected void addTags(){
        for(LanternMaterial material : LanternMaterial.values())
            if(material.canBeColored)
                this.addMaterialTag(material);
    }

    private void addMaterialTag(LanternMaterial material){
        Set<Item> lanterns = new HashSet<>();
        lanterns.add(Item.byBlock(material.getLanternBlock()));
        if(material.canBeColored){
            for(LanternColor color : LanternColor.values())
                lanterns.add(Item.byBlock(material.getLanternBlock(color)));
        }
        TagsProvider.Builder<Item> builder = this.tag(ItemTags.bind("additionallanterns:" + material.getSuffix() + "_lanterns"));
        lanterns.forEach(builder::add);
    }
}

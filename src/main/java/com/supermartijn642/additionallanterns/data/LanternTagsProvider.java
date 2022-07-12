package com.supermartijn642.additionallanterns.data;

import com.supermartijn642.additionallanterns.LanternColor;
import com.supermartijn642.additionallanterns.LanternMaterial;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraftforge.data.event.GatherDataEvent;

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
        TagsProvider.TagAppender<Item> builder = this.tag(ItemTags.create(new ResourceLocation("additionallanterns", material.getSuffix() + "_lanterns")));
        lanterns.forEach(builder::add);
    }
}

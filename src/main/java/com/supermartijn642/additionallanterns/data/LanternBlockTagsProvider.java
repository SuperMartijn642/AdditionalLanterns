package com.supermartijn642.additionallanterns.data;

import com.supermartijn642.additionallanterns.LanternColor;
import com.supermartijn642.additionallanterns.LanternMaterial;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.data.event.GatherDataEvent;

/**
 * Created 8/5/2021 by SuperMartijn642
 */
public class LanternBlockTagsProvider extends BlockTagsProvider {

    public LanternBlockTagsProvider(GatherDataEvent e){
        super(e.getGenerator(), "additionallanterns", e.getExistingFileHelper());
    }

    @Override
    protected void addTags(){
        TagsProvider.TagAppender<Block> pickaxeTag = this.tag(BlockTags.MINEABLE_WITH_PICKAXE);
        for(LanternMaterial material : LanternMaterial.values()){
            pickaxeTag.add(material.getLanternBlock());
            if(material.canBeColored){
                for(LanternColor color : LanternColor.values())
                    pickaxeTag.add(material.getLanternBlock(color));
            }
            if(material.hasChains)
                pickaxeTag.add(material.getChainBlock());
        }
    }
}

package com.supermartijn642.additionallanterns.data;

import com.supermartijn642.additionallanterns.LanternColor;
import com.supermartijn642.additionallanterns.LanternMaterial;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

/**
 * Created 7/5/2021 by SuperMartijn642
 */
public class LanternItemModelProvider extends ItemModelProvider {

    public LanternItemModelProvider(GatherDataEvent e){
        super(e.getGenerator(), "additionallanterns", e.getExistingFileHelper());
    }

    @Override
    protected void registerModels(){
        for(LanternMaterial material : LanternMaterial.values())
            this.addModels(material);
    }

    public void addModels(LanternMaterial material){
        this.addModel(material, null);
        if(material.canBeColored){
            for(LanternColor color : LanternColor.values())
                this.addModel(material, color);
        }
        if(material == LanternMaterial.NORMAL || material.hasChains)
            this.addChainModel(material);
    }

    public void addModel(LanternMaterial material, LanternColor color){
        this.withExistingParent(getModelLocation(material, color), getParentModelLocation(material, color));
    }

    public void addChainModel(LanternMaterial material){
        this.withExistingParent(getChainModelLocation(material), getChainParentModelLocation(material));
    }

    public static String getModelLocation(LanternMaterial material, LanternColor color){
        if(material == LanternMaterial.NORMAL && color == null)
            return "minecraft:item/lantern";
        return color == null ?
            "item/" + material.getSuffix() + "_lantern" :
            "item/" + color.getSuffix() + "_" + material.getSuffix() + "_lantern";
    }

    public static ResourceLocation getParentModelLocation(LanternMaterial material, LanternColor color){
        return color == null ?
            new ResourceLocation("additionallanterns", "block/" + material.getSuffix() + "_lantern") :
            new ResourceLocation("additionallanterns", "block/" + color.getSuffix() + "_" + material.getSuffix() + "_lantern");
    }

    public static String getChainModelLocation(LanternMaterial material){
        if(material == LanternMaterial.NORMAL)
            return "minecraft:item/chain";
        return "item/" + material.getSuffix() + "_chain";
    }

    public static ResourceLocation getChainParentModelLocation(LanternMaterial material){
        return new ResourceLocation("additionallanterns", "block/" + material.getSuffix() + "_chain");
    }
}

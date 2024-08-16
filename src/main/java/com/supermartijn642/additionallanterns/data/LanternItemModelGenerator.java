package com.supermartijn642.additionallanterns.data;

import com.supermartijn642.additionallanterns.LanternColor;
import com.supermartijn642.additionallanterns.LanternMaterial;
import com.supermartijn642.core.generator.ModelGenerator;
import com.supermartijn642.core.generator.ResourceCache;
import net.minecraft.resources.ResourceLocation;

/**
 * Created 7/5/2021 by SuperMartijn642
 */
public class LanternItemModelGenerator extends ModelGenerator {

    public LanternItemModelGenerator(ResourceCache cache){
        super("additionallanterns", cache);
    }

    @Override
    public void generate(){
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
        this.model(getModelLocation(material, color)).parent(getParentModelLocation(material, color));
    }

    public void addChainModel(LanternMaterial material){
        this.model(getChainModelLocation(material)).parent(getChainParentModelLocation(material));
    }

    public static ResourceLocation getModelLocation(LanternMaterial material, LanternColor color){
        if(material == LanternMaterial.NORMAL && color == null)
            return ResourceLocation.withDefaultNamespace("item/lantern");
        return ResourceLocation.fromNamespaceAndPath("additionallanterns", color == null ?
            "item/" + material.getSuffix() + "_lantern" :
            "item/" + color.getSuffix() + "_" + material.getSuffix() + "_lantern");
    }

    public static ResourceLocation getParentModelLocation(LanternMaterial material, LanternColor color){
        return color == null ?
            ResourceLocation.fromNamespaceAndPath("additionallanterns", "block/" + material.getSuffix() + "_lantern") :
            ResourceLocation.fromNamespaceAndPath("additionallanterns", "block/" + color.getSuffix() + "_" + material.getSuffix() + "_lantern");
    }

    public static ResourceLocation getChainModelLocation(LanternMaterial material){
        if(material == LanternMaterial.NORMAL)
            return ResourceLocation.withDefaultNamespace("item/chain");
        return ResourceLocation.fromNamespaceAndPath("additionallanterns", "item/" + material.getSuffix() + "_chain");
    }

    public static ResourceLocation getChainParentModelLocation(LanternMaterial material){
        return ResourceLocation.fromNamespaceAndPath("additionallanterns", "block/" + material.getSuffix() + "_chain");
    }

    @Override
    public String getName(){
        return this.modName + " Item Model Generator";
    }
}

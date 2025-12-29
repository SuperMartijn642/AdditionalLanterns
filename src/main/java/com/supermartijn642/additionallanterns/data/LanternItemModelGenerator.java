package com.supermartijn642.additionallanterns.data;

import com.supermartijn642.additionallanterns.LanternColor;
import com.supermartijn642.additionallanterns.LanternMaterial;
import com.supermartijn642.core.generator.ModelGenerator;
import com.supermartijn642.core.generator.ResourceCache;
import net.minecraft.resources.Identifier;

/**
 * Created 7/5/2021 by SuperMartijn642
 */
public class LanternItemModelGenerator extends ModelGenerator {

    public LanternItemModelGenerator(ResourceCache cache){
        super("additionallanterns", cache);
    }

    @Override
    public void generate(){
        for(LanternMaterial material : LanternMaterial.MATERIALS)
            this.addModels(material);
    }

    public void addModels(LanternMaterial material){
        this.addModel(material, null);
        if(material.canBeColored){
            for(LanternColor color : LanternColor.values())
                this.addModel(material, color);
        }
        if(material.hasChains)
            this.addChainModel(material);
    }

    public void addModel(LanternMaterial material, LanternColor color){
        this.model(getModelLocation(material, color)).parent(getParentModelLocation(material, color));
    }

    public void addChainModel(LanternMaterial material){
        this.model(getChainModelLocation(material)).parent(getChainParentModelLocation(material));
    }

    public static Identifier getModelLocation(LanternMaterial material, LanternColor color){
        return Identifier.fromNamespaceAndPath("additionallanterns", color == null ?
            "item/" + material.getSuffix() + "_lantern" :
            "item/" + color.getSuffix() + "_" + material.getSuffix() + "_lantern");
    }

    public static Identifier getParentModelLocation(LanternMaterial material, LanternColor color){
        return color == null ?
            Identifier.fromNamespaceAndPath("additionallanterns", "block/" + material.getSuffix() + "_lantern") :
            Identifier.fromNamespaceAndPath("additionallanterns", "block/" + color.getSuffix() + "_" + material.getSuffix() + "_lantern");
    }

    public static Identifier getChainModelLocation(LanternMaterial material){
        return Identifier.fromNamespaceAndPath("additionallanterns", "item/" + material.getSuffix() + "_chain");
    }

    public static Identifier getChainParentModelLocation(LanternMaterial material){
        return Identifier.fromNamespaceAndPath("additionallanterns", "block/" + material.getSuffix() + "_chain");
    }

    @Override
    public String getName(){
        return this.modName + " Item Model Generator";
    }
}

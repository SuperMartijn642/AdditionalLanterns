package com.supermartijn642.additionallanterns.data;

import com.supermartijn642.additionallanterns.LanternColor;
import com.supermartijn642.additionallanterns.LanternMaterial;
import com.supermartijn642.core.generator.ModelGenerator;
import com.supermartijn642.core.generator.ResourceCache;
import net.minecraft.resources.Identifier;

/**
 * Created 7/5/2021 by SuperMartijn642
 */
public class LanternBlockModelGenerator extends ModelGenerator {

    public LanternBlockModelGenerator(ResourceCache cache){
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
        if(material.isVanilla || material.hasChains)
            this.addChainModel(material);
    }

    public void addModel(LanternMaterial material, LanternColor color){
        this.model(getModelLocation(material, color, false, false))
            .parent(getModelLocation(false))
            .texture("material", getMaterialTexture(material))
            .texture("color", getColorTexture(material, color, false));
        this.model(getModelLocation(material, color, false, true))
            .parent(getModelLocation(false))
            .texture("material", getMaterialTexture(material))
            .texture("color", getColorTexture(material, color, true));
        this.model(getModelLocation(material, color, true, false))
            .parent(getModelLocation(true))
            .texture("material", getMaterialTexture(material))
            .texture("color", getColorTexture(material, color, false));
        this.model(getModelLocation(material, color, true, true))
            .parent(getModelLocation(true))
            .texture("material", getMaterialTexture(material))
            .texture("color", getColorTexture(material, color, true));
    }

    public void addChainModel(LanternMaterial material){
        this.model(getChainModelLocation(material))
            .parent(getChainModelLocation())
            .texture("chain", getChainMaterialTexture(material));
    }

    public static String getModelLocation(LanternMaterial material, LanternColor color, boolean hanging, boolean on){
        return color == null ?
            hanging ?
                on ?
                    "block/" + material.getSuffix() + "_hanging_lantern" :
                    "block/" + material.getSuffix() + "_hanging_lantern_off" :
                on ?
                    "block/" + material.getSuffix() + "_lantern" :
                    "block/" + material.getSuffix() + "_lantern_off" :
            hanging ?
                on ?
                    "block/" + color.getSuffix() + "_" + material.getSuffix() + "_hanging_lantern" :
                    "block/" + color.getSuffix() + "_" + material.getSuffix() + "_hanging_lantern_off" :
                on ?
                    "block/" + color.getSuffix() + "_" + material.getSuffix() + "_lantern" :
                    "block/" + color.getSuffix() + "_" + material.getSuffix() + "_lantern_off";
    }

    public static Identifier getModelLocation(boolean hanging){
        return hanging ?
            Identifier.fromNamespaceAndPath("additionallanterns", "block/hanging_lantern") :
            Identifier.fromNamespaceAndPath("additionallanterns", "block/lantern");
    }

    public static Identifier getMaterialTexture(LanternMaterial material){
        if(material.lanternTexture != null)
            return material.lanternTexture;
        return Identifier.fromNamespaceAndPath("additionallanterns", "block/materials/" + material.getSuffix() + "_lantern");
    }

    public static Identifier getColorTexture(LanternMaterial material, LanternColor color, boolean on){
        return color == null ?
            on ?
                material.lanternTexture == null ? Identifier.fromNamespaceAndPath("minecraft", "block/lantern") : material.lanternTexture :
                material.lanternOffTexture == null ? Identifier.fromNamespaceAndPath("additionallanterns", "block/lantern_off") : material.lanternOffTexture :
            on ?
                Identifier.fromNamespaceAndPath("additionallanterns", "block/colors/" + color.getSuffix() + "_lantern") :
                Identifier.fromNamespaceAndPath("additionallanterns", "block/colors/" + color.getSuffix() + "_lantern_off");
    }

    public static String getChainModelLocation(LanternMaterial material){
        return "block/" + material.getSuffix() + "_chain";
    }

    public static Identifier getChainModelLocation(){
        return Identifier.fromNamespaceAndPath("additionallanterns", "block/chain");
    }

    public static Identifier getChainMaterialTexture(LanternMaterial material){
        if(material.chainTexture != null)
            return material.chainTexture;
        return Identifier.fromNamespaceAndPath("additionallanterns", "block/materials/" + material.getSuffix() + "_chain");
    }

    @Override
    public String getName(){
        return this.modName + " Block Model Generator";
    }
}

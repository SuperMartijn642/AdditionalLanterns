package com.supermartijn642.additionallanterns.data;

import com.supermartijn642.additionallanterns.LanternColor;
import com.supermartijn642.additionallanterns.LanternMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;

import java.util.function.BiFunction;

/**
 * Created 7/5/2021 by SuperMartijn642
 */
public class LanternBlockModelProvider {

    private final BiFunction<String,String,BlockModelBuilder> stringToBuilder;
    private final BiFunction<String,ResourceLocation,BlockModelBuilder> locationToBuilder;

    public LanternBlockModelProvider(BiFunction<String,String,BlockModelBuilder> stringToBuilder, BiFunction<String,ResourceLocation,BlockModelBuilder> locationToBuilder){
        this.stringToBuilder = stringToBuilder;
        this.locationToBuilder = locationToBuilder;
    }

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
        this.withExistingParent(getModelLocation(material, color, false, false), getModelLocation(false))
            .texture("material", getMaterialTexture(material))
            .texture("color", getColorTexture(color, false));
        this.withExistingParent(getModelLocation(material, color, false, true), getModelLocation(false))
            .texture("material", getMaterialTexture(material))
            .texture("color", getColorTexture(color, true));
        this.withExistingParent(getModelLocation(material, color, true, false), getModelLocation(true))
            .texture("material", getMaterialTexture(material))
            .texture("color", getColorTexture(color, false));
        this.withExistingParent(getModelLocation(material, color, true, true), getModelLocation(true))
            .texture("material", getMaterialTexture(material))
            .texture("color", getColorTexture(color, true));
    }

    public void addChainModel(LanternMaterial material){
        this.withExistingParent(getChainModelLocation(material), getChainModelLocation())
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

    public static ResourceLocation getModelLocation(boolean hanging){
        return hanging ?
            new ResourceLocation("additionallanterns", "block/hanging_lantern") :
            new ResourceLocation("additionallanterns", "block/lantern");
    }

    public static ResourceLocation getMaterialTexture(LanternMaterial material){
        return new ResourceLocation("additionallanterns", "block/materials/" + material.getSuffix() + "_lantern");
    }

    public static ResourceLocation getColorTexture(LanternColor color, boolean on){
        return color == null ?
            on ?
                new ResourceLocation("minecraft", "block/lantern") :
                new ResourceLocation("additionallanterns", "block/lantern_off") :
            on ?
                new ResourceLocation("additionallanterns", "block/colors/" + color.getSuffix() + "_lantern") :
                new ResourceLocation("additionallanterns", "block/colors/" + color.getSuffix() + "_lantern_off");
    }

    public static String getChainModelLocation(LanternMaterial material){
        return "block/" + material.getSuffix() + "_chain";
    }

    public static ResourceLocation getChainModelLocation(){
        return new ResourceLocation("additionallanterns", "block/chain");
    }

    public static ResourceLocation getChainMaterialTexture(LanternMaterial material){
        return new ResourceLocation("additionallanterns", "block/materials/" + material.getSuffix() + "_chain");
    }

    private BlockModelBuilder withExistingParent(String name, String parent){
        return this.stringToBuilder.apply(name, parent);
    }

    private BlockModelBuilder withExistingParent(String name, ResourceLocation parent){
        return this.locationToBuilder.apply(name, parent);
    }
}

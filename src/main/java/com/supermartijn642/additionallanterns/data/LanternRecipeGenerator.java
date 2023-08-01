package com.supermartijn642.additionallanterns.data;

import com.supermartijn642.additionallanterns.LanternColor;
import com.supermartijn642.additionallanterns.LanternMaterial;
import com.supermartijn642.core.generator.RecipeGenerator;
import com.supermartijn642.core.generator.ResourceCache;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

/**
 * Created 7/6/2021 by SuperMartijn642
 */
public class LanternRecipeGenerator extends RecipeGenerator {

    public LanternRecipeGenerator(ResourceCache cache){
        super("additionallanterns", cache);
    }

    @Override
    public void generate(){
        for(LanternMaterial material : LanternMaterial.values())
            this.addMaterialRecipes(material);
    }

    private void addMaterialRecipes(LanternMaterial material){
        this.addLanternRecipe(material);
        if(material.canBeColored){
            this.addColorRecipe(material, null);
            for(LanternColor color : LanternColor.values())
                this.addColorRecipe(material, color);
        }
        if(material.hasChains)
            this.addChainRecipe(material);
    }

    private void addLanternRecipe(LanternMaterial material){
        if(material.primaryLanternIngredient == null && material.secondaryLanternIngredient == null)
            return;

        if(material.primaryLanternIngredient == null)
            this.shaped(Item.getItemFromBlock(material.getLanternBlock()), material.lanternRecipeCount)
                .pattern("B B").pattern(" C ").pattern("B B")
                .input('B', material.secondaryLanternIngredient.get())
                .input('C', Item.getItemFromBlock(Blocks.TORCH))
                .unlockedBy(Item.getItemFromBlock(Blocks.TORCH));
        else if(material.secondaryLanternIngredient == null)
            this.shaped(Item.getItemFromBlock(material.getLanternBlock()), material.lanternRecipeCount)
                .pattern(" A ").pattern("ACA").pattern(" A ")
                .input('A', material.primaryLanternIngredient.get())
                .input('C', Item.getItemFromBlock(Blocks.TORCH))
                .unlockedBy(Item.getItemFromBlock(Blocks.TORCH));
        else
            this.shaped(Item.getItemFromBlock(material.getLanternBlock()), material.lanternRecipeCount)
                .pattern("BAB").pattern("ACA").pattern("BAB")
                .input('A', material.primaryLanternIngredient.get())
                .input('B', material.secondaryLanternIngredient.get())
                .input('C', Item.getItemFromBlock(Blocks.TORCH))
                .unlockedBy(Item.getItemFromBlock(Blocks.TORCH));
    }

    private void addColorRecipe(LanternMaterial material, LanternColor color){
        if(color == null)
            this.shapeless(new ResourceLocation("additionallanterns", material.getSuffix() + "_lantern_colorless"), Item.getItemFromBlock(material.getLanternBlock()))
                .input(getMaterialLanternTag(material))
                .unlockedByOreDict(getMaterialLanternTag(material));
        else
            this.shapeless(new ResourceLocation("additionallanterns", material.getSuffix() + "_lantern_" + color.getSuffix()), Item.getItemFromBlock(material.getLanternBlock(color)))
                .input(getMaterialLanternTag(material))
                .input(getColorDyeTag(color))
                .unlockedByOreDict(getMaterialLanternTag(material));
    }

    private static String getMaterialLanternTag(LanternMaterial material){
        return material.getSuffix() + "_lanterns";
    }

    private static String getColorDyeTag(LanternColor color){
        return color == LanternColor.LIGHT_GRAY ?
            "dyeLightGray" :
            "dye" + color.dyeColor.getUnlocalizedName().substring(0, 1).toUpperCase() + color.dyeColor.getUnlocalizedName().substring(1);
    }

    private void addChainRecipe(LanternMaterial material){
        if(material.primaryChainIngredient == null && material.secondaryChainIngredient == null)
            return;

        if(material.primaryChainIngredient == null)
            this.shaped(Item.getItemFromBlock(material.getChainBlock()), material.chainRecipeCount)
                .pattern("B").pattern(" ").pattern("B")
                .input('B', material.secondaryChainIngredient.get())
                .unlockedBy(material.secondaryChainIngredient.get());
        else if(material.secondaryChainIngredient == null)
            this.shaped(Item.getItemFromBlock(material.getChainBlock()), material.chainRecipeCount)
                .pattern("A")
                .input('A', material.primaryChainIngredient.get())
                .unlockedBy(material.primaryChainIngredient.get());
        else
            this.shaped(Item.getItemFromBlock(material.getChainBlock()), material.chainRecipeCount)
                .pattern("B").pattern("A").pattern("B")
                .input('A', material.primaryChainIngredient.get())
                .input('B', material.secondaryChainIngredient.get())
                .unlockedBy(material.primaryChainIngredient.get());
    }
}

package com.supermartijn642.additionallanterns.data;

import com.supermartijn642.additionallanterns.LanternColor;
import com.supermartijn642.additionallanterns.LanternMaterial;
import com.supermartijn642.core.generator.RecipeGenerator;
import com.supermartijn642.core.generator.ResourceCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

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
            addColorRecipe(material, null);
            for(LanternColor color : LanternColor.values())
                addColorRecipe(material, color);
        }
        if(material.hasChains)
            addChainRecipe(material);
    }

    private void addLanternRecipe(LanternMaterial material){
        if(material.primaryLanternIngredient == null && material.secondaryLanternIngredient == null)
            return;

        if(material.primaryLanternIngredient == null)
            this.shaped(material.getLanternBlock(), material.lanternRecipeCount)
                .pattern("B B").pattern(" C ").pattern("B B")
                .input('B', material.secondaryLanternIngredient)
                .input('C', () -> Items.TORCH)
                .unlockedBy(Items.TORCH);
        else if(material.secondaryLanternIngredient == null)
            this.shaped(material.getLanternBlock(), material.lanternRecipeCount)
                .pattern(" A ").pattern("ACA").pattern(" A ")
                .input('A', material.primaryLanternIngredient)
                .input('C', () -> Items.TORCH)
                .unlockedBy(Items.TORCH);
        else
            this.shaped(material.getLanternBlock(), material.lanternRecipeCount)
                .pattern("BAB").pattern("ACA").pattern("BAB")
                .input('A', material.primaryLanternIngredient)
                .input('B', material.secondaryLanternIngredient)
                .input('C', () -> Items.TORCH)
                .unlockedBy(Items.TORCH);
    }

    private void addColorRecipe(LanternMaterial material, LanternColor color){
        if(color == null)
            this.shapeless(ResourceLocation.fromNamespaceAndPath("additionallanterns", material.getSuffix() + "_lantern_colorless"), material.getLanternBlock())
                .input(getMaterialLanternTag(material))
                .unlockedBy(getMaterialLanternTag(material));
        else
            this.shapeless(ResourceLocation.fromNamespaceAndPath("additionallanterns", material.getSuffix() + "_lantern_" + color.getSuffix()), material.getLanternBlock(color))
                .input(getMaterialLanternTag(material))
                .input(getColorDyeTag(color))
                .unlockedBy(getMaterialLanternTag(material));
    }

    private static TagKey<Item> getMaterialLanternTag(LanternMaterial material){
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath("additionallanterns", material.getSuffix() + "_lanterns"));
    }

    private static TagKey<Item> getColorDyeTag(LanternColor color){
        return color.dyeColor.getTag();
    }

    private void addChainRecipe(LanternMaterial material){
        if(material.primaryChainIngredient == null && material.secondaryChainIngredient == null)
            return;

        if(material.primaryChainIngredient == null)
            this.shaped(material.getChainBlock(), material.chainRecipeCount)
                .pattern("B").pattern(" ").pattern("B")
                .input('B', material.secondaryChainIngredient)
                .unlockedBy(material.secondaryChainIngredient);
        else if(material.secondaryChainIngredient == null)
            this.shaped(material.getChainBlock(), material.chainRecipeCount)
                .pattern("A")
                .input('A', material.primaryChainIngredient)
                .unlockedBy(material.primaryChainIngredient);
        else
            this.shaped(material.getChainBlock(), material.chainRecipeCount)
                .pattern("B").pattern("A").pattern("B")
                .input('A', material.primaryChainIngredient)
                .input('B', material.secondaryChainIngredient)
                .unlockedBy(material.primaryChainIngredient);
    }
}

package com.supermartijn642.additionallanterns.data;

import com.supermartijn642.additionallanterns.LanternColor;
import com.supermartijn642.additionallanterns.LanternMaterial;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

import java.util.function.Consumer;

/**
 * Created 7/6/2021 by SuperMartijn642
 */
public class LanternRecipeProvider extends RecipeProvider {

    public LanternRecipeProvider(GatherDataEvent e){
        super(e.getGenerator());
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> recipeConsumer){
        for(LanternMaterial material : LanternMaterial.values())
            addMaterialRecipes(material, recipeConsumer);
    }

    private static void addMaterialRecipes(LanternMaterial material, Consumer<FinishedRecipe> recipeConsumer){
        addLanternRecipe(material, recipeConsumer);
        if(material.canBeColored){
            addColorRecipe(material, null, recipeConsumer);
            for(LanternColor color : LanternColor.values())
                addColorRecipe(material, color, recipeConsumer);
        }
        if(material.hasChains)
            addChainRecipe(material, recipeConsumer);
    }

    private static void addLanternRecipe(LanternMaterial material, Consumer<FinishedRecipe> recipeConsumer){
        if(material.primaryLanternIngredient == null && material.secondaryLanternIngredient == null)
            return;

        if(material.primaryLanternIngredient == null)
            ShapedRecipeBuilder.shaped(material.getLanternBlock())
                .pattern("B B").pattern(" C ").pattern("B B")
                .define('B', material.secondaryLanternIngredient)
                .define('C', () -> Items.TORCH)
                .unlockedBy("has_torch", has(() -> Items.TORCH))
                .save(recipeConsumer);
        else if(material.secondaryLanternIngredient == null)
            ShapedRecipeBuilder.shaped(material.getLanternBlock())
                .pattern(" A ").pattern("ACA").pattern(" A ")
                .define('A', material.primaryLanternIngredient)
                .define('C', () -> Items.TORCH)
                .unlockedBy("has_torch", has(() -> Items.TORCH))
                .save(recipeConsumer);
        else
            ShapedRecipeBuilder.shaped(material.getLanternBlock())
                .pattern("BAB").pattern("ACA").pattern("BAB")
                .define('A', material.primaryLanternIngredient)
                .define('B', material.secondaryLanternIngredient)
                .define('C', () -> Items.TORCH)
                .unlockedBy("has_torch", has(() -> Items.TORCH))
                .save(recipeConsumer);
    }

    private static void addColorRecipe(LanternMaterial material, LanternColor color, Consumer<FinishedRecipe> recipeConsumer){
        if(color == null)
            ShapelessRecipeBuilder.shapeless(material.getLanternBlock())
                .requires(getMaterialLanternTag(material))
                .unlockedBy("has_lantern", has(getMaterialLanternTag(material)))
                .save(recipeConsumer, new ResourceLocation("additionallanterns", material.getSuffix() + "_lantern_colorless"));
        else
            ShapelessRecipeBuilder.shapeless(material.getLanternBlock(color))
                .requires(getMaterialLanternTag(material))
                .requires(getColorDyeTag(color))
                .unlockedBy("has_lantern", has(getMaterialLanternTag(material)))
                .save(recipeConsumer, new ResourceLocation("additionallanterns", material.getSuffix() + "_lantern_" + color.getSuffix()));
    }

    private static TagKey<Item> getMaterialLanternTag(LanternMaterial material){
        return ItemTags.create(new ResourceLocation("additionallanterns", material.getSuffix() + "_lanterns"));
    }

    private static TagKey<Item> getColorDyeTag(LanternColor color){
        return color.dyeColor.getTag();
    }

    private static void addChainRecipe(LanternMaterial material, Consumer<FinishedRecipe> recipeConsumer){
        if(material.primaryChainIngredient == null && material.secondaryChainIngredient == null)
            return;

        if(material.primaryChainIngredient == null)
            ShapedRecipeBuilder.shaped(material.getChainBlock(), material.chainRecipeCount)
                .pattern("B").pattern(" ").pattern("B")
                .define('B', material.secondaryChainIngredient)
                .unlockedBy("has_secondary", has(material.secondaryChainIngredient))
                .save(recipeConsumer);
        else if(material.secondaryChainIngredient == null)
            ShapedRecipeBuilder.shaped(material.getChainBlock(), material.chainRecipeCount)
                .pattern("A")
                .define('A', material.primaryChainIngredient)
                .unlockedBy("has_primary", has(material.primaryChainIngredient))
                .save(recipeConsumer);
        else
            ShapedRecipeBuilder.shaped(material.getChainBlock(), material.chainRecipeCount)
                .pattern("B").pattern("A").pattern("B")
                .define('A', material.primaryChainIngredient)
                .define('B', material.secondaryChainIngredient)
                .unlockedBy("has_primary", has(material.primaryChainIngredient))
                .save(recipeConsumer);
    }
}

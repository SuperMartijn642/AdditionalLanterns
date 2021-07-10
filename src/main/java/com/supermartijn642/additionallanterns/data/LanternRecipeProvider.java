package com.supermartijn642.additionallanterns.data;

import com.supermartijn642.additionallanterns.LanternColor;
import com.supermartijn642.additionallanterns.LanternMaterial;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created 7/6/2021 by SuperMartijn642
 */
public class LanternRecipeProvider extends RecipeProvider {

    public static final Map<LanternMaterial,Tag<Item>> LANTERNS_TAGS = new EnumMap<>(LanternMaterial.class);

    static{
        for(LanternMaterial material : LanternMaterial.values())
            LANTERNS_TAGS.put(material, new ItemTags.Wrapper(new ResourceLocation("additionallanterns", material.getSuffix() + "_lanterns")));
    }

    public LanternRecipeProvider(GatherDataEvent e){
        super(e.getGenerator());
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> recipeConsumer){
        for(LanternMaterial material : LanternMaterial.values())
            this.addMaterialRecipes(material, recipeConsumer);
    }

    private void addMaterialRecipes(LanternMaterial material, Consumer<IFinishedRecipe> recipeConsumer){
        this.addLanternRecipe(material, recipeConsumer);
        if(material.canBeColored){
            this.addColorRecipe(material, null, recipeConsumer);
            for(LanternColor color : LanternColor.values())
                this.addColorRecipe(material, color, recipeConsumer);
        }
        if(material.hasChains)
            this.addChainRecipe(material, recipeConsumer);
    }

    private void addLanternRecipe(LanternMaterial material, Consumer<IFinishedRecipe> recipeConsumer){
        if(material.primaryLanternIngredient == null && material.secondaryLanternIngredient == null)
            return;

        if(material.primaryLanternIngredient == null)
            ShapedRecipeBuilder.shaped(material.getLanternBlock())
                .pattern("B B").pattern(" C ").pattern("B B")
                .define('B', material.secondaryLanternIngredient)
                .define('C', () -> Items.TORCH)
                .unlocks("has_torch", this.has(() -> Items.TORCH))
                .save(recipeConsumer);
        else if(material.secondaryLanternIngredient == null)
            ShapedRecipeBuilder.shaped(material.getLanternBlock())
                .pattern(" A ").pattern("ACA").pattern(" A ")
                .define('A', material.primaryLanternIngredient)
                .define('C', () -> Items.TORCH)
                .unlocks("has_torch", this.has(() -> Items.TORCH))
                .save(recipeConsumer);
        else
            ShapedRecipeBuilder.shaped(material.getLanternBlock())
                .pattern("BAB").pattern("ACA").pattern("BAB")
                .define('A', material.primaryLanternIngredient)
                .define('B', material.secondaryLanternIngredient)
                .define('C', () -> Items.TORCH)
                .unlocks("has_torch", this.has(() -> Items.TORCH))
                .save(recipeConsumer);
    }

    private void addColorRecipe(LanternMaterial material, LanternColor color, Consumer<IFinishedRecipe> recipeConsumer){
        if(color == null)
            ShapelessRecipeBuilder.shapeless(material.getLanternBlock())
                .requires(getMaterialLanternTag(material))
                .unlocks("has_lantern", this.has(getMaterialLanternTag(material)))
                .save(recipeConsumer, new ResourceLocation("additionallanterns", material.getSuffix() + "_lantern_colorless"));
        else
            ShapelessRecipeBuilder.shapeless(material.getLanternBlock(color))
                .requires(getMaterialLanternTag(material))
                .requires(getColorDyeTag(color))
                .unlocks("has_lantern", this.has(getMaterialLanternTag(material)))
                .save(recipeConsumer, new ResourceLocation("additionallanterns", material.getSuffix() + "_lantern_" + color.getSuffix()));
    }

    private static Tag<Item> getMaterialLanternTag(LanternMaterial material){
        return LANTERNS_TAGS.get(material);
    }

    private static Tag<Item> getColorDyeTag(LanternColor color){
        return color.dyeColor.getTag();
    }

    private void addChainRecipe(LanternMaterial material, Consumer<IFinishedRecipe> recipeConsumer){
        if(material.primaryChainIngredient == null && material.secondaryChainIngredient == null)
            return;

        if(material.primaryChainIngredient == null)
            ShapedRecipeBuilder.shaped(material.getChainBlock(), material.chainRecipeCount)
                .pattern("B").pattern(" ").pattern("B")
                .define('B', material.secondaryChainIngredient)
                .unlocks("has_secondary", this.has(material.secondaryChainIngredient))
                .save(recipeConsumer);
        else if(material.secondaryChainIngredient == null)
            ShapedRecipeBuilder.shaped(material.getChainBlock(), material.chainRecipeCount)
                .pattern("A")
                .define('A', material.primaryChainIngredient)
                .unlocks("has_primary", this.has(material.primaryChainIngredient))
                .save(recipeConsumer);
        else
            ShapedRecipeBuilder.shaped(material.getChainBlock(), material.chainRecipeCount)
                .pattern("B").pattern("A").pattern("B")
                .define('A', material.primaryChainIngredient)
                .define('B', material.secondaryChainIngredient)
                .unlocks("has_primary", this.has(material.primaryChainIngredient))
                .save(recipeConsumer);
    }
}

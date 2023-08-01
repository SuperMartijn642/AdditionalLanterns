package com.supermartijn642.additionallanterns;

import com.supermartijn642.core.item.BaseBlockItem;
import com.supermartijn642.core.item.ItemProperties;
import com.supermartijn642.core.registry.RegistrationHandler;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created 7/5/2021 by SuperMartijn642
 */
public enum LanternMaterial {

    ANDESITE(true, true, "Andesite", () -> Items.ANDESITE, null, 12, () -> Items.ANDESITE, () -> Items.ANDESITE, 16),
    BONE(true, true, "Bone", () -> Items.BONE, () -> Items.BONE, 4, () -> Items.BONE_BLOCK, () -> Items.BONE, 8),
    BRICKS(true, true, "Brick", () -> Items.BRICK, null, 2, () -> Items.BRICKS, () -> Items.BRICK, 8),
    COBBLESTONE(true, true, "Cobblestone", () -> Items.COBBLESTONE, null, 12, () -> Items.COBBLESTONE, () -> Items.COBBLESTONE, 16),
    DARK_PRISMARINE(true, true, "Dark Prismarine", () -> Items.DARK_PRISMARINE, null, 12, () -> Items.DARK_PRISMARINE, () -> Items.PRISMARINE_SHARD, 8),
    DIAMOND(true, true, "Diamond", () -> Items.DIAMOND, null, 8, () -> Items.DIAMOND, () -> Items.DIAMOND, 4),
    DIORITE(true, true, "Diorite", () -> Items.DIORITE, null, 12, () -> Items.DIORITE, () -> Items.DIORITE, 16),
    EMERALD(true, true, "Emerald", () -> Items.EMERALD, null, 8, () -> Items.EMERALD, () -> Items.EMERALD, 4),
    END_STONE(true, true, "End Stone", () -> Items.END_STONE, null, 12, () -> Items.END_STONE, () -> Items.END_STONE, 16),
    GOLD(true, true, "Gold", () -> Items.GOLD_NUGGET, Items.GOLD_NUGGET, 1, () -> Items.GOLD_INGOT, () -> Items.GOLD_NUGGET, 1),
    GRANITE(true, true, "Granite", () -> Items.GRANITE, null, 12, () -> Items.GRANITE, () -> Items.GRANITE, 16),
    IRON(true, false, "Iron", () -> Items.IRON_INGOT, null, 4, null, null, 0),
    MOSSY_COBBLESTONE(true, true, "Mossy Cobblestone", () -> Items.MOSSY_COBBLESTONE, null, 12, () -> Items.MOSSY_COBBLESTONE, () -> Items.COBBLESTONE, 16),
    NORMAL(true, false, "", null, null, 0, null, null, 0),
    NORMAL_NETHER_BRICKS(true, true, "Nether Brick", () -> Items.NETHER_BRICK, null, 4, () -> Items.NETHER_BRICKS, () -> Items.NETHER_BRICK, 16),
    NORMAL_SANDSTONE(true, true, "Sandstone", () -> Items.SANDSTONE, null, 12, () -> Items.SANDSTONE, () -> Items.SANDSTONE, 16),
    OBSIDIAN(true, true, "Obsidian", () -> Items.OBSIDIAN, null, 12, () -> Items.OBSIDIAN, () -> Items.OBSIDIAN, 16),
    PRISMARINE(true, true, "Prismarine", () -> Items.PRISMARINE_SHARD, null, 4, () -> Items.PRISMARINE_BRICKS, () -> Items.PRISMARINE_SHARD, 8),
    PURPUR(true, true, "Purpur", () -> Items.POPPED_CHORUS_FRUIT, null, 1, () -> Items.PURPUR_BLOCK, () -> Items.POPPED_CHORUS_FRUIT, 8),
    QUARTZ(true, true, "Quartz", () -> Items.QUARTZ, () -> Items.QUARTZ, 4, () -> Items.QUARTZ_BLOCK, () -> Items.QUARTZ, 8),
    RED_NETHER_BRICKS(true, true, "Red Nether Brick", () -> Items.RED_NETHER_BRICKS, null, 12, () -> Items.RED_NETHER_BRICKS, () -> Items.NETHER_BRICK, 8),
    RED_SANDSTONE(true, true, "Red Sandstone", () -> Items.RED_SANDSTONE, null, 12, () -> Items.RED_SANDSTONE, () -> Items.RED_SANDSTONE, 16),
    SMOOTH_STONE(true, true, "Smooth Stone", () -> Items.SMOOTH_STONE, null, 12, () -> Items.SMOOTH_STONE, () -> Items.SMOOTH_STONE, 16),
    STONE(true, true, "Stone", () -> Items.STONE, null, 12, () -> Items.STONE, () -> Items.STONE, 16),
    STONE_BRICKS(true, true, "Stone Bricks", () -> Items.STONE_BRICKS, null, 12, () -> Items.STONE_BRICKS, () -> Items.STONE_BRICKS, 16);

    public final boolean canBeColored;
    public final boolean hasChains;
    private LanternBlock lanternBlock;
    private final Map<LanternColor,LanternBlock> coloredLanternBlocks = new EnumMap<>(LanternColor.class);
    private ChainBlock chainBlock;
    private Item lanternItem;
    private final Map<LanternColor,Item> coloredLanternItems = new EnumMap<>(LanternColor.class);
    private Item chainItem;
    public final String englishTranslation;
    public final IItemProvider primaryLanternIngredient, secondaryLanternIngredient;
    public final int lanternRecipeCount;
    public final IItemProvider primaryChainIngredient, secondaryChainIngredient;
    public final int chainRecipeCount;

    LanternMaterial(boolean canBeColored, boolean hasChains, String englishTranslation, IItemProvider primaryLanternIngredient, IItemProvider secondaryLanternIngredient, int lanternRecipeCount, IItemProvider primaryChainIngredient, IItemProvider secondaryChainIngredient, int chainRecipeCount){
        this.canBeColored = canBeColored;
        this.hasChains = hasChains;
        this.englishTranslation = englishTranslation;
        this.primaryLanternIngredient = primaryLanternIngredient;
        this.secondaryLanternIngredient = secondaryLanternIngredient;
        this.lanternRecipeCount = lanternRecipeCount;
        this.primaryChainIngredient = primaryChainIngredient;
        this.secondaryChainIngredient = secondaryChainIngredient;
        this.chainRecipeCount = chainRecipeCount;
    }

    public Block getLanternBlock(){
        return this.lanternBlock;
    }

    public Block getLanternBlock(LanternColor color){
        if(color == null)
            return this.getLanternBlock();
        return this.coloredLanternBlocks.get(color);
    }

    public ChainBlock getChainBlock(){
        return this.chainBlock;
    }

    public String getSuffix(){
        return this.name().toLowerCase(Locale.ROOT);
    }

    public Block.Properties getLanternBlockProperties(){
        return Block.Properties.copy(Blocks.LANTERN);
    }

    public Block.Properties getChainBlockProperties(){
        return Block.Properties.of(Material.METAL, MaterialColor.NONE).strength(5.0F, 6.0F).sound(SoundType.METAL).noOcclusion();
    }

    public void registerBlocks(RegistrationHandler.Helper<Block> helper){
        if(this.lanternBlock != null)
            throw new IllegalStateException("Blocks have already been registered!");

        this.lanternBlock = new LanternBlock(this, null);
        helper.register(this.getSuffix() + "_lantern", this.lanternBlock);
        if(this.canBeColored){
            for(LanternColor color : LanternColor.values()){
                LanternBlock block = new LanternBlock(this, color);
                this.coloredLanternBlocks.put(color, block);
                helper.register(color.getSuffix() + "_" + this.getSuffix() + "_lantern", block);
            }
        }

        if(this.hasChains){
            this.chainBlock = new ChainBlock(this);
            helper.register(this.getSuffix() + "_chain", this.chainBlock);
        }
    }

    public void registerItems(RegistrationHandler.Helper<Item> helper){
        if(this.lanternItem != null)
            throw new IllegalStateException("Items have already been registered!");
        if(this.lanternBlock == null)
            throw new IllegalStateException("Blocks must be registered before registering items!");

        this.lanternItem = new BaseBlockItem(this.lanternBlock, ItemProperties.create().group(AdditionalLanterns.GROUP));
        if(this == NORMAL)
            helper.registerOverride("minecraft", "lantern", this.lanternItem);
        else
            helper.register(this.getSuffix() + "_lantern", this.lanternItem);
        if(this.canBeColored){
            for(LanternColor color : LanternColor.values()){
                LanternBlock block = this.coloredLanternBlocks.get(color);
                BaseBlockItem item = new BaseBlockItem(block, ItemProperties.create().group(AdditionalLanterns.GROUP));
                this.coloredLanternItems.put(color, item);
                helper.register(color.getSuffix() + "_" + this.getSuffix() + "_lantern", item);
            }
        }

        if(this.hasChains){
            this.chainItem = new BaseBlockItem(this.chainBlock, ItemProperties.create().group(AdditionalLanterns.GROUP));
            helper.register(this.getSuffix() + "_chain", this.chainItem);
        }
    }
}

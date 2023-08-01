package com.supermartijn642.additionallanterns;

import com.supermartijn642.core.block.BlockProperties;
import com.supermartijn642.core.item.BaseBlockItem;
import com.supermartijn642.core.item.ItemProperties;
import com.supermartijn642.core.registry.RegistrationHandler;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Created 7/5/2021 by SuperMartijn642
 */
public enum LanternMaterial {

    ANDESITE(true, true, "Andesite", () -> new ItemStack(Blocks.STONE, 5), null, 12, () -> new ItemStack(Blocks.STONE, 5), () -> new ItemStack(Blocks.STONE, 5), 16),
    BONE(true, true, "Bone", () -> new ItemStack(Items.BONE), () -> new ItemStack(Items.BONE),4, () -> new ItemStack(Blocks.BONE_BLOCK), () -> new ItemStack(Items.BONE), 8),
    BRICKS(true, true, "Brick", () -> new ItemStack(Items.BRICK), null, 2, () -> new ItemStack(Blocks.BRICK_BLOCK), () -> new ItemStack(Items.BRICK), 8),
    COBBLESTONE(true, true, "Cobblestone", () -> new ItemStack(Blocks.COBBLESTONE), null, 12, () -> new ItemStack(Blocks.COBBLESTONE), () -> new ItemStack(Blocks.COBBLESTONE), 16),
    DARK_PRISMARINE(true, true, "Dark Prismarine", () -> new ItemStack(Blocks.PRISMARINE, 2), null, 12, () -> new ItemStack(Blocks.PRISMARINE, 2), () -> new ItemStack(Blocks.PRISMARINE, 2), 8),
    DIAMOND(true, true, "Diamond", () -> new ItemStack(Items.DIAMOND), null, 8, () -> new ItemStack(Items.DIAMOND), () -> new ItemStack(Items.DIAMOND), 4),
    DIORITE(true, true, "Diorite", () -> new ItemStack(Blocks.STONE, 3), null, 12, () -> new ItemStack(Blocks.STONE, 3), () -> new ItemStack(Blocks.STONE, 3), 16),
    EMERALD(true, true, "Emerald", () -> new ItemStack(Items.EMERALD), null, 8, () -> new ItemStack(Items.EMERALD), () -> new ItemStack(Items.EMERALD), 4),
    END_STONE(true, true, "End Stone", () -> new ItemStack(Blocks.END_STONE), null, 12, () -> new ItemStack(Blocks.END_STONE), () -> new ItemStack(Blocks.END_STONE), 16),
    GOLD(true, true, "Gold", () -> new ItemStack(Items.GOLD_NUGGET), () -> new ItemStack(Items.GOLD_NUGGET), 1, () -> new ItemStack(Items.GOLD_INGOT), () -> new ItemStack(Items.GOLD_NUGGET), 1),
    GRANITE(true, true, "Granite", () -> new ItemStack(Blocks.STONE, 1), null, 12, () -> new ItemStack(Blocks.STONE, 1), () -> new ItemStack(Blocks.STONE, 1), 16),
    IRON(true, false, "Iron", () -> new ItemStack(Items.IRON_INGOT), null, 4, null, null, 0),
    MOSSY_COBBLESTONE(true, true, "Mossy Cobblestone", () -> new ItemStack(Blocks.MOSSY_COBBLESTONE), null, 12, () -> new ItemStack(Blocks.MOSSY_COBBLESTONE), () -> new ItemStack(Blocks.COBBLESTONE), 16),
    NORMAL(true, true, "", () -> new ItemStack(Items.IRON_NUGGET), () -> new ItemStack(Items.IRON_NUGGET), 1, () -> new ItemStack(Items.IRON_INGOT), () -> new ItemStack(Items.IRON_NUGGET), 1),
    NORMAL_NETHER_BRICKS(true, true, "Nether Brick", () -> new ItemStack(Items.NETHERBRICK), null, 4, () -> new ItemStack(Blocks.NETHER_BRICK), () -> new ItemStack(Items.NETHERBRICK), 16),
    NORMAL_SANDSTONE(true, true, "Sandstone", () -> new ItemStack(Blocks.SANDSTONE), null, 12, () -> new ItemStack(Blocks.SANDSTONE), () -> new ItemStack(Blocks.SANDSTONE), 16),
    OBSIDIAN(true, true, "Obsidian", () -> new ItemStack(Blocks.OBSIDIAN), null, 12, () -> new ItemStack(Blocks.OBSIDIAN), () -> new ItemStack(Blocks.OBSIDIAN), 16),
    PRISMARINE(true, true, "Prismarine", () -> new ItemStack(Items.PRISMARINE_SHARD), null, 4, () -> new ItemStack(Blocks.PRISMARINE, 1), () -> new ItemStack(Items.PRISMARINE_SHARD), 8),
    PURPUR(true, true, "Purpur", () -> new ItemStack(Items.CHORUS_FRUIT_POPPED), null, 1, () -> new ItemStack(Blocks.PURPUR_BLOCK), () -> new ItemStack(Items.CHORUS_FRUIT_POPPED), 8),
    QUARTZ(true, true, "Quartz", () -> new ItemStack(Items.QUARTZ), () -> new ItemStack(Items.QUARTZ), 4, () -> new ItemStack(Blocks.QUARTZ_BLOCK), () -> new ItemStack(Items.QUARTZ), 8),
    RED_NETHER_BRICKS(true, true, "Red Nether Brick", () -> new ItemStack(Blocks.RED_NETHER_BRICK), null, 12, () -> new ItemStack(Blocks.RED_NETHER_BRICK), () -> new ItemStack(Items.NETHERBRICK), 8),
    RED_SANDSTONE(true, true, "Red Sandstone", () -> new ItemStack(Blocks.RED_SANDSTONE), null, 12, () -> new ItemStack(Blocks.RED_SANDSTONE), () -> new ItemStack(Blocks.RED_SANDSTONE), 16),
    SMOOTH_STONE(true, true, "Smooth Stone", () -> new ItemStack(Blocks.STONE), null, 12, () -> new ItemStack(Blocks.STONE), () -> new ItemStack(Blocks.STONE), 16),
    STONE(true, true, "Stone", () -> new ItemStack(Blocks.STONE, 0), null, 12, () -> new ItemStack(Blocks.STONE, 0), () -> new ItemStack(Blocks.STONE, 0), 16),
    STONE_BRICKS(true, true, "Stone Bricks", () -> new ItemStack(Blocks.STONEBRICK, 0), null, 12, () -> new ItemStack(Blocks.STONEBRICK, 0), () -> new ItemStack(Blocks.STONEBRICK, 0), 16);

    public final boolean canBeColored;
    public final boolean hasChains;
    private LanternBlock lanternBlock;
    private final Map<LanternColor,LanternBlock> coloredLanternBlocks = new EnumMap<>(LanternColor.class);
    private ChainBlock chainBlock;
    private Item lanternItem;
    private final Map<LanternColor,Item> coloredLanternItems = new EnumMap<>(LanternColor.class);
    private Item chainItem;
    public final String englishTranslation;
    public final Supplier<ItemStack> primaryLanternIngredient, secondaryLanternIngredient;
    public final int lanternRecipeCount;
    public final Supplier<ItemStack> primaryChainIngredient, secondaryChainIngredient;
    public final int chainRecipeCount;

    LanternMaterial(boolean canBeColored, boolean hasChains, String englishTranslation, Supplier<ItemStack> primaryLanternIngredient, Supplier<ItemStack> secondaryLanternIngredient, int lanternRecipeCount, Supplier<ItemStack> primaryChainIngredient, Supplier<ItemStack> secondaryChainIngredient, int chainRecipeCount){
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

    public BlockProperties getLanternBlockProperties(){
        return BlockProperties.create(Material.IRON).requiresCorrectTool().destroyTime(5).explosionResistance(6).sound(SoundType.METAL).lightLevel(state -> LanternBlock.emitsLight(state) ? 15 : 0);
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
        helper.register(this.getSuffix() + "_lantern", this.lanternItem);
        OreDictionary.registerOre(this.getSuffix() + "_lanterns", this.lanternItem);
        if(this.canBeColored){
            for(LanternColor color : LanternColor.values()){
                LanternBlock block = this.coloredLanternBlocks.get(color);
                BaseBlockItem item = new BaseBlockItem(block, ItemProperties.create().group(AdditionalLanterns.GROUP));
                this.coloredLanternItems.put(color, item);
                helper.register(color.getSuffix() + "_" + this.getSuffix() + "_lantern", item);
                OreDictionary.registerOre(this.getSuffix() + "_lanterns", item);
            }
        }

        if(this.hasChains){
            this.chainItem = new BaseBlockItem(this.chainBlock, ItemProperties.create().group(AdditionalLanterns.GROUP));
            helper.register(this.getSuffix() + "_chain", this.chainItem);
        }
    }
}

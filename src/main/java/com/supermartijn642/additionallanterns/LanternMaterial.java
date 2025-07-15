package com.supermartijn642.additionallanterns;

import com.google.common.collect.ImmutableMap;
import com.supermartijn642.core.item.BaseBlockItem;
import com.supermartijn642.core.item.ItemProperties;
import com.supermartijn642.core.registry.RegistrationHandler;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created 7/5/2021 by SuperMartijn642
 */
public enum LanternMaterial {

    AMETHYST(true, true, "Amethyst", () -> Items.AMETHYST_SHARD, null, 1, () -> Items.AMETHYST_BLOCK, () -> Items.AMETHYST_SHARD, 4),
    ANDESITE(true, true, "Andesite", () -> Items.ANDESITE, null, 4, () -> Items.ANDESITE, () -> Items.ANDESITE, 8),
    BASALT(true, true, "Basalt", () -> Items.BASALT, null, 4, () -> Items.BASALT, () -> Items.BASALT, 8),
    BLACKSTONE(true, true, "Blackstone", () -> Items.BLACKSTONE, null, 4, () -> Items.BLACKSTONE, () -> Items.BLACKSTONE, 8),
    BONE(true, true, "Bone", () -> Items.BONE, () -> Items.BONE, 1, () -> Items.BONE_BLOCK, () -> Items.BONE, 3),
    BRICKS(true, true, "Brick", () -> Items.BRICK, null, 1, () -> Items.BRICKS, () -> Items.BRICK, 4),
    COBBLESTONE(true, true, "Cobblestone", () -> Items.COBBLESTONE, null, 4, () -> Items.COBBLESTONE, () -> Items.COBBLESTONE, 8),
    COBBLED_DEEPSLATE(true, true, "Cobbled Deepslate", () -> Items.COBBLED_DEEPSLATE, null, 4, () -> Items.COBBLED_DEEPSLATE, () -> Items.COBBLED_DEEPSLATE, 8),
    COPPER(true, true, "Copper", () -> Items.COPPER_INGOT, null, 1, () -> Items.COPPER_BLOCK, () -> Items.COPPER_INGOT, 4),
    CRIMSON(true, true, "Crimson", () -> Items.CRIMSON_PLANKS, null, 4, () -> Items.CRIMSON_PLANKS, () -> Items.CRIMSON_PLANKS, 8),
    DARK_PRISMARINE(true, true, "Dark Prismarine", () -> Items.DARK_PRISMARINE, null, 1, () -> Items.DARK_PRISMARINE, () -> Items.PRISMARINE_SHARD, 4),
    DEEPSLATE_BRICKS(true, true, "Deepslate Bricks", () -> Items.DEEPSLATE_BRICKS, null, 4, () -> Items.DEEPSLATE_BRICKS, () -> Items.DEEPSLATE_BRICKS, 8),
    DIAMOND(true, true, "Diamond", () -> Items.DIAMOND, null, 1, () -> Items.DIAMOND, () -> Items.DIAMOND, 3),
    DIORITE(true, true, "Diorite", () -> Items.DIORITE, null, 4, () -> Items.DIORITE, () -> Items.DIORITE, 8),
    EMERALD(true, true, "Emerald", () -> Items.EMERALD, null, 1, () -> Items.EMERALD, () -> Items.EMERALD, 3),
    END_STONE(true, true, "End Stone", () -> Items.END_STONE, null, 4, () -> Items.END_STONE, () -> Items.END_STONE, 8),
    EXPOSED_COPPER(true, true, "Exposed Copper", null, null, 0, null, null, 0),
    GOLD(true, true, "Gold", () -> Items.GOLD_NUGGET, Items.GOLD_NUGGET, 1, () -> Items.GOLD_INGOT, () -> Items.GOLD_NUGGET, 1),
    GRANITE(true, true, "Granite", () -> Items.GRANITE, null, 4, () -> Items.GRANITE, () -> Items.GRANITE, 8),
    IRON(true, false, "Iron", () -> Items.IRON_INGOT, null, 1, null, null, 0),
    MOSSY_COBBLESTONE(true, true, "Mossy Cobblestone", () -> Items.MOSSY_COBBLESTONE, null, 4, () -> Items.MOSSY_COBBLESTONE, () -> Items.COBBLESTONE, 8),
    NETHERITE(true, true, "Netherite", () -> Items.NETHERITE_INGOT, null, 1, () -> Items.NETHERITE_INGOT, () -> Items.NETHERITE_INGOT, 3),
    NORMAL(true, false, "", null, null, 0, null, null, 0),
    NORMAL_NETHER_BRICKS(true, true, "Nether Brick", () -> Items.NETHER_BRICK, null, 1, () -> Items.NETHER_BRICKS, () -> Items.NETHER_BRICK, 3),
    NORMAL_SANDSTONE(true, true, "Sandstone", () -> Items.SANDSTONE, null, 4, () -> Items.SANDSTONE, () -> Items.SANDSTONE, 8),
    OBSIDIAN(true, true, "Obsidian", () -> Items.OBSIDIAN, null, 4, () -> Items.OBSIDIAN, () -> Items.OBSIDIAN, 8),
    OXIDIZED_COPPER(true, true, "Oxidized Copper", null, null, 0, null, null, 0),
    PRISMARINE(true, true, "Prismarine", () -> Items.PRISMARINE_SHARD, null, 1, () -> Items.PRISMARINE_BRICKS, () -> Items.PRISMARINE_SHARD, 4),
    PURPUR(true, true, "Purpur", () -> Items.POPPED_CHORUS_FRUIT, null, 1, () -> Items.PURPUR_BLOCK, () -> Items.POPPED_CHORUS_FRUIT, 4),
    QUARTZ(true, true, "Quartz", () -> Items.QUARTZ, () -> Items.QUARTZ, 1, () -> Items.QUARTZ_BLOCK, () -> Items.QUARTZ, 4),
    RED_NETHER_BRICKS(true, true, "Red Nether Brick", () -> Items.RED_NETHER_BRICKS, null, 4, () -> Items.RED_NETHER_BRICKS, () -> Items.NETHER_BRICK, 8),
    RED_SANDSTONE(true, true, "Red Sandstone", () -> Items.RED_SANDSTONE, null, 4, () -> Items.RED_SANDSTONE, () -> Items.RED_SANDSTONE, 8),
    SMOOTH_STONE(true, true, "Smooth Stone", () -> Items.SMOOTH_STONE, null, 4, () -> Items.SMOOTH_STONE, () -> Items.SMOOTH_STONE, 8),
    STONE(true, true, "Stone", () -> Items.STONE, null, 4, () -> Items.STONE, () -> Items.STONE, 8),
    STONE_BRICKS(true, true, "Stone Bricks", () -> Items.STONE_BRICKS, null, 4, () -> Items.STONE_BRICKS, () -> Items.STONE_BRICKS, 8),
    WARPED(true, true, "Warped", () -> Items.WARPED_PLANKS, null, 4, () -> Items.WARPED_PLANKS, () -> Items.WARPED_PLANKS, 8),
    WAXED_COPPER(true, true, "Waxed Copper", null, null, 0, null, null, 0),
    WAXED_EXPOSED_COPPER(true, true, "Waxed Exposed Copper", null, null, 0, null, null, 0),
    WAXED_OXIDIZED_COPPER(true, true, "Waxed Oxidized Copper", null, null, 0, null, null, 0),
    WAXED_WEATHERED_COPPER(true, true, "Waxed Weathered Copper", null, null, 0, null, null, 0),
    WEATHERED_COPPER(true, true, "Weathered Copper", null, null, 0, null, null, 0);

    private static final Map<LanternMaterial,WeatheringCopper.WeatherState> WEATHERING_BLOCKS = ImmutableMap.<LanternMaterial,WeatheringCopper.WeatherState>builder()
        .put(COPPER, WeatheringCopper.WeatherState.UNAFFECTED)
        .put(EXPOSED_COPPER, WeatheringCopper.WeatherState.EXPOSED)
        .put(WEATHERED_COPPER, WeatheringCopper.WeatherState.WEATHERED)
        .put(OXIDIZED_COPPER, WeatheringCopper.WeatherState.OXIDIZED)
        .build();

    public final boolean canBeColored;
    public final boolean hasChains;
    private LanternBlock lanternBlock;
    private final Map<LanternColor,LanternBlock> coloredLanternBlocks = new EnumMap<>(LanternColor.class);
    private ChainBlock chainBlock;
    private Item lanternItem;
    private final Map<LanternColor,Item> coloredLanternItems = new EnumMap<>(LanternColor.class);
    private Item chainItem;
    public final String englishTranslation;
    public final ItemLike primaryLanternIngredient, secondaryLanternIngredient;
    public final int lanternRecipeCount;
    public final ItemLike primaryChainIngredient, secondaryChainIngredient;
    public final int chainRecipeCount;

    LanternMaterial(boolean canBeColored, boolean hasChains, String englishTranslation, ItemLike primaryLanternIngredient, ItemLike secondaryLanternIngredient, int lanternRecipeCount, ItemLike primaryChainIngredient, ItemLike secondaryChainIngredient, int chainRecipeCount){
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

    private String getLanternIdentifier(LanternColor color){
        if(color == null)
            return this.getSuffix() + "_lantern";
        return color.getSuffix() + "_" + this.getSuffix() + "_lantern";
    }

    private String getChainIdentifier(){
        return this.getSuffix() + "_chain";
    }

    public BlockBehaviour.Properties getLanternBlockProperties(){
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN).lightLevel(state -> LanternBlock.emitsLight(state) ? 15 : 0);
        return WEATHERING_BLOCKS.get(this) == null ? properties : properties.randomTicks();
    }

    public BlockBehaviour.Properties getChainBlockProperties(){
        return BlockBehaviour.Properties.ofFullCopy(Blocks.CHAIN);
    }

    public void registerBlocks(RegistrationHandler.Helper<Block> helper){
        if(this.lanternBlock != null)
            throw new IllegalStateException("Blocks have already been registered!");

        WeatheringCopper.WeatherState weathering = WEATHERING_BLOCKS.get(this);
        this.lanternBlock = weathering == null ? new LanternBlock(this, null) : new WeatheringLanternBlock(this, null, weathering);
        helper.register(this.getLanternIdentifier(null), this.lanternBlock);
        if(this.canBeColored){
            for(LanternColor color : LanternColor.values()){
                LanternBlock block = weathering == null ? new LanternBlock(this, color) : new WeatheringLanternBlock(this, color, weathering);
                this.coloredLanternBlocks.put(color, block);
                helper.register(this.getLanternIdentifier(color), block);
            }
        }

        if(this.hasChains){
            this.chainBlock = weathering == null ? new ChainBlock(this) : new WeatheringChainBlock(this, weathering);
            helper.register(this.getChainIdentifier(), this.chainBlock);
        }
    }

    public void registerItems(RegistrationHandler.Helper<Item> helper){
        if(this.lanternItem != null)
            throw new IllegalStateException("Items have already been registered!");
        if(this.lanternBlock == null)
            throw new IllegalStateException("Blocks must be registered before registering items!");

        if(this != NORMAL){
            this.lanternItem = new BaseBlockItem(this.lanternBlock, ItemProperties.create().group(AdditionalLanterns.GROUP));
            helper.register(this.getLanternIdentifier(null), this.lanternItem);
        }
        if(this.canBeColored){
            for(LanternColor color : LanternColor.values()){
                LanternBlock block = this.coloredLanternBlocks.get(color);
                BlockItem item = new BaseBlockItem(block, ItemProperties.create().group(AdditionalLanterns.GROUP));
                this.coloredLanternItems.put(color, item);
                helper.register(this.getLanternIdentifier(color), item);
            }
        }

        if(this.hasChains){
            this.chainItem = new BaseBlockItem(this.chainBlock, ItemProperties.create().group(AdditionalLanterns.GROUP));
            helper.register(this.getChainIdentifier(), this.chainItem);
        }
    }
}

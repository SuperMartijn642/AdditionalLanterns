package com.supermartijn642.additionallanterns;

import com.google.common.collect.ImmutableMap;
import com.supermartijn642.core.item.BaseBlockItem;
import com.supermartijn642.core.item.ItemProperties;
import com.supermartijn642.core.registry.RegistrationHandler;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Created 7/5/2021 by SuperMartijn642
 */
public class LanternMaterial {

    public static final List<LanternMaterial> MATERIALS = new ArrayList<>();

    public static final LanternMaterial AMETHYST = createWithChain("amethyst", "Amethyst", () -> Items.AMETHYST_SHARD, null, 1, () -> Items.AMETHYST_BLOCK, () -> Items.AMETHYST_SHARD, 4);
    public static final LanternMaterial ANDESITE = createWithChain("andesite", "Andesite", () -> Items.ANDESITE, null, 4, () -> Items.ANDESITE, () -> Items.ANDESITE, 8);
    public static final LanternMaterial BASALT = createWithChain("basalt", "Basalt", () -> Items.BASALT, null, 4, () -> Items.BASALT, () -> Items.BASALT, 8);
    public static final LanternMaterial BLACKSTONE = createWithChain("blackstone", "Blackstone", () -> Items.BLACKSTONE, null, 4, () -> Items.BLACKSTONE, () -> Items.BLACKSTONE, 8);
    public static final LanternMaterial BONE = createWithChain("bone", "Bone", () -> Items.BONE, () -> Items.BONE, 1, () -> Items.BONE_BLOCK, () -> Items.BONE, 3);
    public static final LanternMaterial BRICKS = createWithChain("bricks", "Brick", () -> Items.BRICK, null, 1, () -> Items.BRICKS, () -> Items.BRICK, 4);
    public static final LanternMaterial COBBLESTONE = createWithChain("cobblestone", "Cobblestone", () -> Items.COBBLESTONE, null, 4, () -> Items.COBBLESTONE, () -> Items.COBBLESTONE, 8);
    public static final LanternMaterial COBBLED_DEEPSLATE = createWithChain("cobbled_deepslate", "Cobbled Deepslate", () -> Items.COBBLED_DEEPSLATE, null, 4, () -> Items.COBBLED_DEEPSLATE, () -> Items.COBBLED_DEEPSLATE, 8);
    public static final LanternMaterial COPPER = createWithoutChain("copper", "Ordinary Copper", () -> Items.COPPER_INGOT, null, 1);
    public static final LanternMaterial CRIMSON = createWithChain("crimson", "Crimson", () -> Items.CRIMSON_PLANKS, null, 4, () -> Items.CRIMSON_PLANKS, () -> Items.CRIMSON_PLANKS, 8);
    public static final LanternMaterial DARK_PRISMARINE = createWithChain("dark_prismarine", "Dark Prismarine", () -> Items.DARK_PRISMARINE, null, 1, () -> Items.DARK_PRISMARINE, () -> Items.PRISMARINE_SHARD, 4);
    public static final LanternMaterial DEEPSLATE_BRICKS = createWithChain("deepslate_bricks", "Deepslate Bricks", () -> Items.DEEPSLATE_BRICKS, null, 4, () -> Items.DEEPSLATE_BRICKS, () -> Items.DEEPSLATE_BRICKS, 8);
    public static final LanternMaterial DIAMOND = createWithChain("diamond", "Diamond", () -> Items.DIAMOND, null, 1, () -> Items.DIAMOND, () -> Items.DIAMOND, 3);
    public static final LanternMaterial DIORITE = createWithChain("diorite", "Diorite", () -> Items.DIORITE, null, 4, () -> Items.DIORITE, () -> Items.DIORITE, 8);
    public static final LanternMaterial EMERALD = createWithChain("emerald", "Emerald", () -> Items.EMERALD, null, 1, () -> Items.EMERALD, () -> Items.EMERALD, 3);
    public static final LanternMaterial END_STONE = createWithChain("end_stone", "End Stone", () -> Items.END_STONE, null, 4, () -> Items.END_STONE, () -> Items.END_STONE, 8);
    public static final LanternMaterial EXPOSED_COPPER = createWithoutChain("exposed_copper", "Exposed Ordinary Copper", null, null, 0);
    public static final LanternMaterial GOLD = createWithChain("gold", "Gold", () -> Items.GOLD_NUGGET, Items.GOLD_NUGGET, 1, () -> Items.GOLD_INGOT, () -> Items.GOLD_NUGGET, 1);
    public static final LanternMaterial GRANITE = createWithChain("granite", "Granite", () -> Items.GRANITE, null, 4, () -> Items.GRANITE, () -> Items.GRANITE, 8);
    public static final LanternMaterial IRON = createWithoutChain("iron", "Rough Iron", () -> Items.IRON_INGOT, null, 1);
    public static final LanternMaterial MOSSY_COBBLESTONE = createWithChain("mossy_cobblestone", "Mossy Cobblestone", () -> Items.MOSSY_COBBLESTONE, null, 4, () -> Items.MOSSY_COBBLESTONE, () -> Items.COBBLESTONE, 8);
    public static final LanternMaterial NETHERITE = createWithChain("netherite", "Netherite", () -> Items.NETHERITE_INGOT, null, 1, () -> Items.NETHERITE_INGOT, () -> Items.NETHERITE_INGOT, 3);
    public static final LanternMaterial NORMAL_NETHER_BRICKS = createWithChain("normal_nether_bricks", "Nether Brick", () -> Items.NETHER_BRICK, null, 1, () -> Items.NETHER_BRICKS, () -> Items.NETHER_BRICK, 3);
    public static final LanternMaterial NORMAL_SANDSTONE = createWithChain("normal_sandstone", "Sandstone", () -> Items.SANDSTONE, null, 4, () -> Items.SANDSTONE, () -> Items.SANDSTONE, 8);
    public static final LanternMaterial OBSIDIAN = createWithChain("obsidian", "Obsidian", () -> Items.OBSIDIAN, null, 4, () -> Items.OBSIDIAN, () -> Items.OBSIDIAN, 8);
    public static final LanternMaterial OXIDIZED_COPPER = createWithoutChain("oxidized_copper", "Oxidized Ordinary Copper", null, null, 0);
    public static final LanternMaterial PRISMARINE = createWithChain("prismarine", "Prismarine", () -> Items.PRISMARINE_SHARD, null, 1, () -> Items.PRISMARINE_BRICKS, () -> Items.PRISMARINE_SHARD, 4);
    public static final LanternMaterial PURPUR = createWithChain("purpur", "Purpur", () -> Items.POPPED_CHORUS_FRUIT, null, 1, () -> Items.PURPUR_BLOCK, () -> Items.POPPED_CHORUS_FRUIT, 4);
    public static final LanternMaterial QUARTZ = createWithChain("quartz", "Quartz", () -> Items.QUARTZ, () -> Items.QUARTZ, 1, () -> Items.QUARTZ_BLOCK, () -> Items.QUARTZ, 4);
    public static final LanternMaterial RED_NETHER_BRICKS = createWithChain("red_nether_bricks", "Red Nether Brick", () -> Items.RED_NETHER_BRICKS, null, 4, () -> Items.RED_NETHER_BRICKS, () -> Items.NETHER_BRICK, 8);
    public static final LanternMaterial RED_SANDSTONE = createWithChain("red_sandstone", "Red Sandstone", () -> Items.RED_SANDSTONE, null, 4, () -> Items.RED_SANDSTONE, () -> Items.RED_SANDSTONE, 8);
    public static final LanternMaterial SMOOTH_STONE = createWithChain("smooth_stone", "Smooth Stone", () -> Items.SMOOTH_STONE, null, 4, () -> Items.SMOOTH_STONE, () -> Items.SMOOTH_STONE, 8);
    public static final LanternMaterial STONE = createWithChain("stone", "Stone", () -> Items.STONE, null, 4, () -> Items.STONE, () -> Items.STONE, 8);
    public static final LanternMaterial STONE_BRICKS = createWithChain("stone_bricks", "Stone Bricks", () -> Items.STONE_BRICKS, null, 4, () -> Items.STONE_BRICKS, () -> Items.STONE_BRICKS, 8);
    public static final LanternMaterial VANILLA_IRON = createVanilla("vanilla_iron", "Iron", Blocks.LANTERN, Blocks.IRON_CHAIN, Identifier.withDefaultNamespace("block/lantern"), null, Identifier.withDefaultNamespace("block/iron_chain"));
    public static final LanternMaterial VANILLA_COPPER = createVanilla("vanilla_copper", "Copper", Blocks.COPPER_LANTERN.weathering().unaffected(), Blocks.COPPER_CHAIN.weathering().unaffected(), Identifier.withDefaultNamespace("block/copper_lantern"), Identifier.fromNamespaceAndPath("additionallanterns", "block/copper_lantern_off"), Identifier.withDefaultNamespace("block/copper_chain"));
    public static final LanternMaterial VANILLA_EXPOSED_COPPER = createVanilla("vanilla_exposed_copper", "Exposed Copper", Blocks.COPPER_LANTERN.weathering().exposed(), Blocks.COPPER_CHAIN.weathering().exposed(), Identifier.withDefaultNamespace("block/exposed_copper_lantern"), Identifier.fromNamespaceAndPath("additionallanterns", "block/exposed_copper_lantern_off"), Identifier.withDefaultNamespace("block/exposed_copper_chain"));
    public static final LanternMaterial VANILLA_OXIDIZED_COPPER = createVanilla("vanilla_oxidized_copper", "Oxidized Copper", Blocks.COPPER_LANTERN.weathering().oxidized(), Blocks.COPPER_CHAIN.weathering().oxidized(), Identifier.withDefaultNamespace("block/oxidized_copper_lantern"), Identifier.fromNamespaceAndPath("additionallanterns", "block/oxidized_copper_lantern_off"), Identifier.withDefaultNamespace("block/oxidized_copper_chain"));
    public static final LanternMaterial VANILLA_WEATHERED_COPPER = createVanilla("vanilla_weathered_copper", "Weathered Copper", Blocks.COPPER_LANTERN.weathering().weathered(), Blocks.COPPER_CHAIN.weathering().weathered(), Identifier.withDefaultNamespace("block/weathered_copper_lantern"), Identifier.fromNamespaceAndPath("additionallanterns", "block/weathered_copper_lantern_off"), Identifier.withDefaultNamespace("block/weathered_copper_chain"));
    public static final LanternMaterial VANILLA_WAXED_COPPER = createVanilla("vanilla_waxed_copper", "Waxed Copper", Blocks.COPPER_LANTERN.waxed().unaffected(), Blocks.COPPER_CHAIN.waxed().unaffected(), Identifier.withDefaultNamespace("block/copper_lantern"), Identifier.fromNamespaceAndPath("additionallanterns", "block/copper_lantern_off"), Identifier.withDefaultNamespace("block/copper_chain"));
    public static final LanternMaterial VANILLA_WAXED_EXPOSED_COPPER = createVanilla("vanilla_waxed_exposed_copper", "Waxed Exposed Copper", Blocks.COPPER_LANTERN.waxed().exposed(), Blocks.COPPER_CHAIN.waxed().exposed(), Identifier.withDefaultNamespace("block/exposed_copper_lantern"), Identifier.fromNamespaceAndPath("additionallanterns", "block/exposed_copper_lantern_off"), Identifier.withDefaultNamespace("block/exposed_copper_chain"));
    public static final LanternMaterial VANILLA_WAXED_OXIDIZED_COPPER = createVanilla("vanilla_waxed_oxidized_copper", "Waxed Oxidized Copper", Blocks.COPPER_LANTERN.waxed().oxidized(), Blocks.COPPER_CHAIN.waxed().oxidized(), Identifier.withDefaultNamespace("block/oxidized_copper_lantern"), Identifier.fromNamespaceAndPath("additionallanterns", "block/oxidized_copper_lantern_off"), Identifier.withDefaultNamespace("block/oxidized_copper_chain"));
    public static final LanternMaterial VANILLA_WAXED_WEATHERED_COPPER = createVanilla("vanilla_waxed_weathered_copper", "Waxed Weathered Copper", Blocks.COPPER_LANTERN.waxed().weathered(), Blocks.COPPER_CHAIN.waxed().weathered(), Identifier.withDefaultNamespace("block/weathered_copper_lantern"), Identifier.fromNamespaceAndPath("additionallanterns", "block/weathered_copper_lantern_off"), Identifier.withDefaultNamespace("block/weathered_copper_chain"));
    public static final LanternMaterial WARPED = createWithChain("warped", "Warped", () -> Items.WARPED_PLANKS, null, 4, () -> Items.WARPED_PLANKS, () -> Items.WARPED_PLANKS, 8);
    public static final LanternMaterial WAXED_COPPER = createWithoutChain("waxed_copper", "Waxed Ordinary Copper", null, null, 0);
    public static final LanternMaterial WAXED_EXPOSED_COPPER = createWithoutChain("waxed_exposed_copper", "Waxed Exposed Ordinary Copper", null, null, 0);
    public static final LanternMaterial WAXED_OXIDIZED_COPPER = createWithoutChain("waxed_oxidized_copper", "Waxed Oxidized Ordinary Copper", null, null, 0);
    public static final LanternMaterial WAXED_WEATHERED_COPPER = createWithoutChain("waxed_weathered_copper", "Waxed Weathered Ordinary Copper", null, null, 0);
    public static final LanternMaterial WEATHERED_COPPER = createWithoutChain("weathered_copper", "Weathered Ordinary Copper", null, null, 0);

    private static final Map<LanternMaterial,WeatheringCopper.WeatherState> WEATHERING_BLOCKS = ImmutableMap.<LanternMaterial,WeatheringCopper.WeatherState>builder()
        .put(VANILLA_COPPER, WeatheringCopper.WeatherState.UNAFFECTED)
        .put(VANILLA_EXPOSED_COPPER, WeatheringCopper.WeatherState.EXPOSED)
        .put(VANILLA_WEATHERED_COPPER, WeatheringCopper.WeatherState.WEATHERED)
        .put(VANILLA_OXIDIZED_COPPER, WeatheringCopper.WeatherState.OXIDIZED)
        .put(COPPER, WeatheringCopper.WeatherState.UNAFFECTED)
        .put(EXPOSED_COPPER, WeatheringCopper.WeatherState.EXPOSED)
        .put(WEATHERED_COPPER, WeatheringCopper.WeatherState.WEATHERED)
        .put(OXIDIZED_COPPER, WeatheringCopper.WeatherState.OXIDIZED)
        .build();
    public static final Map<LanternMaterial,LanternMaterial> WAXING_MAPPINGS = ImmutableMap.<LanternMaterial,LanternMaterial>builder()
        .put(VANILLA_COPPER, VANILLA_WAXED_COPPER)
        .put(VANILLA_EXPOSED_COPPER, VANILLA_WAXED_EXPOSED_COPPER)
        .put(VANILLA_WEATHERED_COPPER, VANILLA_WAXED_WEATHERED_COPPER)
        .put(VANILLA_OXIDIZED_COPPER, VANILLA_WAXED_OXIDIZED_COPPER)
        .put(COPPER, WAXED_COPPER)
        .put(EXPOSED_COPPER, WAXED_EXPOSED_COPPER)
        .put(WEATHERED_COPPER, WAXED_WEATHERED_COPPER)
        .put(OXIDIZED_COPPER, WAXED_OXIDIZED_COPPER)
        .build();
    public static final Map<LanternMaterial,LanternMaterial> OXIDATION_MAPPINGS = ImmutableMap.<LanternMaterial,LanternMaterial>builder()
        .put(VANILLA_COPPER, VANILLA_EXPOSED_COPPER)
        .put(VANILLA_EXPOSED_COPPER, VANILLA_WEATHERED_COPPER)
        .put(VANILLA_WEATHERED_COPPER, VANILLA_OXIDIZED_COPPER)
        .put(COPPER, EXPOSED_COPPER)
        .put(EXPOSED_COPPER, WEATHERED_COPPER)
        .put(WEATHERED_COPPER, OXIDIZED_COPPER)
        .build();

    public static final Map<Block,LanternMaterial> VANILLA_LANTERN_MAPPINGS;

    static{
        ImmutableMap.Builder<Block,LanternMaterial> builder = ImmutableMap.builder();
        for(LanternMaterial material : MATERIALS){
            if(material.isVanilla)
                builder.put(material.vanillaLanternBlock, material);
        }
        VANILLA_LANTERN_MAPPINGS = builder.build();
    }

    private static LanternMaterial createWithChain(String identifier, String englishTranslation, ItemLike primaryLanternIngredient, ItemLike secondaryLanternIngredient, int lanternRecipeCount, ItemLike primaryChainIngredient, ItemLike secondaryChainIngredient, int chainRecipeCount){
        LanternMaterial material = new LanternMaterial(identifier, false, null, null, true, true, englishTranslation, primaryLanternIngredient, secondaryLanternIngredient, lanternRecipeCount, primaryChainIngredient, secondaryChainIngredient, chainRecipeCount, null, null, null);
        MATERIALS.add(material);
        return material;
    }

    private static LanternMaterial createWithoutChain(String identifier, String englishTranslation, ItemLike primaryLanternIngredient, ItemLike secondaryLanternIngredient, int lanternRecipeCount){
        LanternMaterial material = new LanternMaterial(identifier, false, null, null, true, false, englishTranslation, primaryLanternIngredient, secondaryLanternIngredient, lanternRecipeCount, null, null, 0, null, null, null);
        MATERIALS.add(material);
        return material;
    }

    private static LanternMaterial createVanilla(String identifier, String englishTranslation, Block vanillaLanternBlock, Block vanillaChainBlock, Identifier lanternTexture, Identifier lanternOffTexture, Identifier vanillaChainTexture){
        LanternMaterial material = new LanternMaterial(identifier, true, vanillaLanternBlock, vanillaChainBlock, true, false, englishTranslation, null, null, 0, null, null, 0, lanternTexture, lanternOffTexture, vanillaChainTexture);
        MATERIALS.add(material);
        return material;
    }

    private final String identifier;
    public final boolean isVanilla;
    public final Block vanillaLanternBlock, vanillaChainBlock;
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
    public final Identifier lanternTexture, lanternOffTexture;
    public final Identifier chainTexture;

    LanternMaterial(String identifier, boolean isVanilla, Block vanillaLanternBlock, Block vanillaChainBlock, boolean canBeColored, boolean hasChains, String englishTranslation, ItemLike primaryLanternIngredient, ItemLike secondaryLanternIngredient, int lanternRecipeCount, ItemLike primaryChainIngredient, ItemLike secondaryChainIngredient, int chainRecipeCount, Identifier lanternTexture, Identifier lanternOffTexture, Identifier chainTexture){
        this.identifier = identifier;
        this.isVanilla = isVanilla;
        this.vanillaLanternBlock = vanillaLanternBlock;
        this.vanillaChainBlock = vanillaChainBlock;
        this.canBeColored = canBeColored;
        this.hasChains = hasChains;
        this.englishTranslation = englishTranslation;
        this.primaryLanternIngredient = primaryLanternIngredient;
        this.secondaryLanternIngredient = secondaryLanternIngredient;
        this.lanternRecipeCount = lanternRecipeCount;
        this.primaryChainIngredient = primaryChainIngredient;
        this.secondaryChainIngredient = secondaryChainIngredient;
        this.chainRecipeCount = chainRecipeCount;
        this.lanternTexture = lanternTexture;
        this.lanternOffTexture = lanternOffTexture;
        this.chainTexture = chainTexture;
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
        return this.identifier;
    }

    private String getLanternIdentifier(LanternColor color){
        if(color == null)
            return this.getSuffix() + "_lantern";
        return color.getSuffix() + "_" + this.getSuffix() + "_lantern";
    }

    private String getChainIdentifier(){
        return this.getSuffix() + "_chain";
    }

    public BlockBehaviour.Properties getLanternBlockProperties(LanternColor color){
        BlockBehaviour.Properties properties = this.isVanilla ?
            BlockBehaviour.Properties.ofFullCopy(this.vanillaLanternBlock) :
            BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN);
        properties.lightLevel(state -> LanternBlock.emitsLight(state) ? 15 : 0)
            .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("additionallanterns", this.getLanternIdentifier(color))));
        return WEATHERING_BLOCKS.get(this) == null ? properties : properties.randomTicks();
    }

    public BlockBehaviour.Properties getChainBlockProperties(){
        return BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_CHAIN).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("additionallanterns", this.getChainIdentifier())));
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

        if(this.isVanilla)
            Item.BY_BLOCK.put(this.lanternBlock, this.vanillaLanternBlock.asItem());
    }

    public void registerItems(RegistrationHandler.Helper<Item> helper){
        if(this.lanternItem != null)
            throw new IllegalStateException("Items have already been registered!");
        if(this.lanternBlock == null)
            throw new IllegalStateException("Blocks must be registered before registering items!");

        if(!this.isVanilla){
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

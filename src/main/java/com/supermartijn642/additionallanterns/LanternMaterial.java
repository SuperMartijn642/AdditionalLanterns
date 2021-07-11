package com.supermartijn642.additionallanterns;

import com.supermartijn642.core.block.BaseBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created 7/5/2021 by SuperMartijn642
 */
public enum LanternMaterial {

    NORMAL(true, true),
    OBSIDIAN(true, true),
    //    BASALT(true, true),
    ANDESITE(true, true),
    DIORITE(true, true),
    GRANITE(true, true),
    NORMAL_SANDSTONE(true, true),
    RED_SANDSTONE(true, true),
    SMOOTH_STONE(true, true),
    END_STONE(true, true),
    QUARTZ(true, true),
    PRISMARINE(true, true),
    DARK_PRISMARINE(true, true),
    //    BLACKSTONE(true, true),
    NORMAL_NETHER_BRICKS(true, true),
    RED_NETHER_BRICKS(true, true),
    //    CRIMSON(true, true),
    //    WARPED(true, true),
    PURPUR(true, true),
    BRICKS(true, true);

    public final boolean canBeColored;
    public final boolean hasChains;
    private LanternBlock lanternBlock;
    private final Map<LanternColor,LanternBlock> coloredLanternBlocks = new EnumMap<>(LanternColor.class);
    private ChainBlock chainBlock;
    private Item lanternItem;
    private final Map<LanternColor,Item> coloredLanternItems = new EnumMap<>(LanternColor.class);
    private Item chainItem;

    LanternMaterial(boolean canBeColored, boolean hasChains){
        this.canBeColored = canBeColored;
        this.hasChains = hasChains;
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

    public BaseBlock.Properties getLanternBlockProperties(){
        return BaseBlock.Properties.create(Material.IRON).setRequiresTool().hardnessAndResistance(3.5f).sound(SoundType.METAL).setLightLevel(state -> LanternBlock.emitsLight(state) ? 15 : 0);
    }

    public void registerBlocks(IForgeRegistry<Block> registry){
        if(this.lanternBlock != null)
            throw new IllegalStateException("Blocks have already been registered!");

        this.lanternBlock = new LanternBlock(this, null);
        if(this.canBeColored){
            for(LanternColor color : LanternColor.values())
                this.coloredLanternBlocks.put(color, new LanternBlock(this, color));
        }
        registry.register(this.lanternBlock);
        this.coloredLanternBlocks.values().forEach(registry::register);

        if(this.hasChains){
            this.chainBlock = new ChainBlock(this);
            registry.register(this.chainBlock);
        }
    }

    public void registerItems(IForgeRegistry<Item> registry){
        if(this.lanternItem != null)
            throw new IllegalStateException("Items have already been registered!");
        if(this.lanternBlock == null)
            throw new IllegalStateException("Blocks must be registered before registering items!");

        this.lanternItem = new ItemBlock(this.lanternBlock).setRegistryName(this.lanternBlock.getRegistryName());
        if(this.canBeColored){
            for(LanternColor color : LanternColor.values()){
                LanternBlock block = this.coloredLanternBlocks.get(color);
                this.coloredLanternItems.put(color, new ItemBlock(block).setRegistryName(block.getRegistryName()));
            }
        }
        registry.register(this.lanternItem);
        this.coloredLanternItems.values().forEach(registry::register);
        OreDictionary.registerOre(this.getSuffix() + "_lanterns", this.lanternItem);
        this.coloredLanternItems.values().forEach(item -> OreDictionary.registerOre(this.getSuffix() + "_lanterns", item));

        if(this.hasChains){
            this.chainItem = new ItemBlock(this.chainBlock).setRegistryName(this.chainBlock.getRegistryName());
            registry.register(this.chainItem);
        }
    }

}

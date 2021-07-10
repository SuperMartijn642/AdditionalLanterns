package com.supermartijn642.additionallanterns.data;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.supermartijn642.additionallanterns.LanternColor;
import com.supermartijn642.additionallanterns.LanternMaterial;
import net.minecraft.block.Block;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.*;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created 7/6/2021 by SuperMartijn642
 */
public class LanternLootTableProvider extends LootTableProvider {

    public LanternLootTableProvider(GatherDataEvent e){
        super(e.getGenerator());
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation,LootTable.Builder>>>,LootParameterSet>> getTables(){
        BlockLootTables lootTables = new BlockLootTables() {
            @Override
            protected Iterable<Block> getKnownBlocks(){
                return ForgeRegistries.BLOCKS.getValues().stream().filter(block -> block.getRegistryName().getNamespace().equals("additionallanterns")).collect(Collectors.toList());
            }

            @Override
            protected void addTables(){
                for(LanternMaterial material : LanternMaterial.values()){
                    if(material == LanternMaterial.NORMAL)
                        this.dropOther(material.getLanternBlock(), () -> Items.LANTERN);
                    else
                        this.dropSelf(material.getLanternBlock());

                    if(material.canBeColored){
                        for(LanternColor color : LanternColor.values())
                            this.dropSelf(material.getLanternBlock(color));
                    }

                    if(material.hasChains)
                        this.dropSelf(material.getChainBlock());
                }
            }
        };


        return ImmutableList.of(Pair.of(() -> lootTables, LootParameterSets.BLOCK));
    }

    @Override
    protected void validate(Map<ResourceLocation,LootTable> map, ValidationTracker validationtracker){
        map.forEach((a, b) -> LootTableManager.validate(validationtracker, a, b));
    }
}

package com.supermartijn642.additionallanterns.data;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.supermartijn642.additionallanterns.LanternColor;
import com.supermartijn642.additionallanterns.LanternMaterial;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.data.event.GatherDataEvent;
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
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation,LootTable.Builder>>>,LootContextParamSet>> getTables(){
        BlockLoot lootTables = new BlockLoot() {
            @Override
            protected Iterable<Block> getKnownBlocks(){
                return ForgeRegistries.BLOCKS.getEntries()
                    .stream()
                    .filter(entry -> entry.getKey().location().getNamespace().equals("additionallanterns"))
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());
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


        return ImmutableList.of(Pair.of(() -> lootTables, LootContextParamSets.BLOCK));
    }

    @Override
    protected void validate(Map<ResourceLocation,LootTable> map, ValidationContext validationtracker){
        map.forEach((a, b) -> LootTables.validate(validationtracker, a, b));
    }
}

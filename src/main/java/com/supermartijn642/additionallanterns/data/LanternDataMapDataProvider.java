package com.supermartijn642.additionallanterns.data;

import com.supermartijn642.additionallanterns.LanternColor;
import com.supermartijn642.additionallanterns.LanternMaterial;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.neoforged.neoforge.registries.datamaps.builtin.Oxidizable;
import net.neoforged.neoforge.registries.datamaps.builtin.Waxable;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Created 28/12/2025 by SuperMartijn642
 */
public class LanternDataMapDataProvider extends DataMapProvider {

    public LanternDataMapDataProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider){
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider){
        // Waxing
        for(Map.Entry<LanternMaterial,LanternMaterial> entry : LanternMaterial.WAXING_MAPPINGS.entrySet()){
            for(LanternColor color : LanternColor.colorsAndNull()){
                //noinspection deprecation
                this.builder(NeoForgeDataMaps.WAXABLES).add(entry.getKey().getLanternBlock(color).builtInRegistryHolder(), new Waxable(entry.getValue().getLanternBlock(color)), false);
            }
        }
        // Weathering
        for(Map.Entry<LanternMaterial,LanternMaterial> entry : LanternMaterial.OXIDATION_MAPPINGS.entrySet()){
            for(LanternColor color : LanternColor.colorsAndNull()){
                this.builder(NeoForgeDataMaps.OXIDIZABLES).add(entry.getKey().getLanternBlock(color).builtInRegistryHolder(), new Oxidizable(entry.getValue().getLanternBlock(color)), false);
            }
        }
    }
}

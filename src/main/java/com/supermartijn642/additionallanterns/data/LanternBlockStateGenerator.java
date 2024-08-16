package com.supermartijn642.additionallanterns.data;


import com.supermartijn642.additionallanterns.LanternBlock;
import com.supermartijn642.additionallanterns.LanternColor;
import com.supermartijn642.additionallanterns.LanternMaterial;
import com.supermartijn642.core.generator.BlockStateGenerator;
import com.supermartijn642.core.generator.ResourceCache;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

/**
 * Created 7/5/2021 by SuperMartijn642
 */
public class LanternBlockStateGenerator extends BlockStateGenerator {

    public LanternBlockStateGenerator(ResourceCache cache){
        super("additionallanterns", cache);
    }

    @Override
    public void generate(){
        for(LanternMaterial material : LanternMaterial.values())
            this.addStates(material);
    }

    public void addStates(LanternMaterial material){
        this.addState(material, null);
        if(material.canBeColored){
            for(LanternColor color : LanternColor.values())
                this.addState(material, color);
        }
        if(material.hasChains)
            this.addChainState(material);
    }

    public void addState(LanternMaterial material, LanternColor color){
        this.blockState(material.getLanternBlock(color)).variantsForAllExcept(
            (state, variant) -> variant.model(getModelLocation(material, color, state.get(BlockStateProperties.HANGING), state.get(LanternBlock.ON), state.get(LanternBlock.REDSTONE))),
            BlockStateProperties.WATERLOGGED
        );
    }

    public void addChainState(LanternMaterial material){
        this.blockState(material.getChainBlock()).variantsForAllExcept(
            (state, variant) -> {
                Direction.Axis axis = state.get(BlockStateProperties.AXIS);
                variant.model(getChainModelLocation(material), axis == Direction.Axis.X || axis == Direction.Axis.Z ? 90 : 0, axis == Direction.Axis.X ? 90 : 0, false);
            },
            BlockStateProperties.WATERLOGGED
        );
    }

    public static ResourceLocation getModelLocation(LanternMaterial material, LanternColor color, boolean hanging, boolean on, boolean redstone){
        boolean lit = on != redstone;
        return color == null ?
            hanging ?
                lit ?
                    ResourceLocation.fromNamespaceAndPath("additionallanterns", "block/" + material.getSuffix() + "_hanging_lantern") :
                    ResourceLocation.fromNamespaceAndPath("additionallanterns", "block/" + material.getSuffix() + "_hanging_lantern_off") :
                lit ?
                    ResourceLocation.fromNamespaceAndPath("additionallanterns", "block/" + material.getSuffix() + "_lantern") :
                    ResourceLocation.fromNamespaceAndPath("additionallanterns", "block/" + material.getSuffix() + "_lantern_off") :
            hanging ?
                lit ?
                    ResourceLocation.fromNamespaceAndPath("additionallanterns", "block/" + color.getSuffix() + "_" + material.getSuffix() + "_hanging_lantern") :
                    ResourceLocation.fromNamespaceAndPath("additionallanterns", "block/" + color.getSuffix() + "_" + material.getSuffix() + "_hanging_lantern_off") :
                lit ?
                    ResourceLocation.fromNamespaceAndPath("additionallanterns", "block/" + color.getSuffix() + "_" + material.getSuffix() + "_lantern") :
                    ResourceLocation.fromNamespaceAndPath("additionallanterns", "block/" + color.getSuffix() + "_" + material.getSuffix() + "_lantern_off");
    }

    public static ResourceLocation getChainModelLocation(LanternMaterial material){
        return ResourceLocation.fromNamespaceAndPath("additionallanterns", "block/" + material.getSuffix() + "_chain");
    }
}

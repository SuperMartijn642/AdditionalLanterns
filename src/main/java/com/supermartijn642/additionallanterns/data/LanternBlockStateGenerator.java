package com.supermartijn642.additionallanterns.data;


import com.supermartijn642.additionallanterns.ChainBlock;
import com.supermartijn642.additionallanterns.LanternBlock;
import com.supermartijn642.additionallanterns.LanternColor;
import com.supermartijn642.additionallanterns.LanternMaterial;
import com.supermartijn642.core.generator.BlockStateGenerator;
import com.supermartijn642.core.generator.ResourceCache;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

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
        this.blockState(material.getLanternBlock(color)).variantsForAll(
            (state, variant) -> variant.model(getModelLocation(material, color, state.get(LanternBlock.HANGING), state.get(LanternBlock.ON), state.get(LanternBlock.REDSTONE)))
        );
    }

    public void addChainState(LanternMaterial material){
        this.blockState(material.getChainBlock()).variantsForAll(
            (state, variant) -> {
                EnumFacing.Axis axis = state.get(ChainBlock.AXIS);
                variant.model(getChainModelLocation(material), axis == EnumFacing.Axis.X || axis == EnumFacing.Axis.Z ? 90 : 0, axis == EnumFacing.Axis.X ? 90 : 0, false);
            }
        );
    }

    public static ResourceLocation getModelLocation(LanternMaterial material, LanternColor color, boolean hanging, boolean on, boolean redstone){
        boolean lit = on != redstone;
        return color == null ?
            hanging ?
                lit ?
                    new ResourceLocation("additionallanterns", "block/" + material.getSuffix() + "_hanging_lantern") :
                    new ResourceLocation("additionallanterns", "block/" + material.getSuffix() + "_hanging_lantern_off") :
                lit ?
                    new ResourceLocation("additionallanterns", "block/" + material.getSuffix() + "_lantern") :
                    new ResourceLocation("additionallanterns", "block/" + material.getSuffix() + "_lantern_off") :
            hanging ?
                lit ?
                    new ResourceLocation("additionallanterns", "block/" + color.getSuffix() + "_" + material.getSuffix() + "_hanging_lantern") :
                    new ResourceLocation("additionallanterns", "block/" + color.getSuffix() + "_" + material.getSuffix() + "_hanging_lantern_off") :
                lit ?
                    new ResourceLocation("additionallanterns", "block/" + color.getSuffix() + "_" + material.getSuffix() + "_lantern") :
                    new ResourceLocation("additionallanterns", "block/" + color.getSuffix() + "_" + material.getSuffix() + "_lantern_off");
    }

    public static ResourceLocation getChainModelLocation(LanternMaterial material){
        return new ResourceLocation("additionallanterns", "block/" + material.getSuffix() + "_chain");
    }
}

package com.supermartijn642.additionallanterns.data;


import com.supermartijn642.additionallanterns.LanternBlock;
import com.supermartijn642.additionallanterns.LanternColor;
import com.supermartijn642.additionallanterns.LanternMaterial;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

/**
 * Created 7/5/2021 by SuperMartijn642
 */
public class LanternBlockStateProvider extends BlockStateProvider {

    public LanternBlockStateProvider(GatherDataEvent e){
        super(e.getGenerator(), "additionallanterns", e.getExistingFileHelper());
    }

    @Override
    protected void registerStatesAndModels(){
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
        this.getVariantBuilder(material.getLanternBlock(color)).forAllStatesExcept(
            state -> new ConfiguredModel[]{new ConfiguredModel(this.models().getExistingFile(getModelLocation(material, color, state.getValue(BlockStateProperties.HANGING), state.getValue(LanternBlock.ON), state.getValue(LanternBlock.REDSTONE))))},
            BlockStateProperties.WATERLOGGED
        );
    }

    public void addChainState(LanternMaterial material){
        this.getVariantBuilder(material.getChainBlock()).forAllStatesExcept(
            state -> {
                Direction.Axis axis = state.getValue(BlockStateProperties.AXIS);
                ConfiguredModel model = new ConfiguredModel(this.models().getExistingFile(getChainModelLocation(material)),
                    axis == Direction.Axis.X || axis == Direction.Axis.Z ? 90 : 0, axis == Direction.Axis.X ? 90 : 0, false);
                return new ConfiguredModel[]{model};
            },
            BlockStateProperties.WATERLOGGED
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

package com.supermartijn642.additionallanterns;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created 7/5/2021 by SuperMartijn642
 */
public class LanternBlock extends net.minecraft.block.LanternBlock implements IWaterLoggable {

    public static final BooleanProperty ON = BooleanProperty.create("on");
    public static final BooleanProperty REDSTONE = BooleanProperty.create("redstone");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public final LanternMaterial material;
    public final LanternColor color;

    public LanternBlock(LanternMaterial material, LanternColor color){
        super(material.getLanternBlockProperties());
        this.material = material;
        this.color = color;

        this.registerDefaultState(this.defaultBlockState().setValue(ON, true).setValue(REDSTONE, false).setValue(WATERLOGGED, false));
    }

    @Override
    public ActionResultType use(BlockState state, World level, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult){
        ItemStack stack = player.getItemInHand(hand);
        if(this.material.canBeColored && stack.getItem() instanceof DyeItem){
            LanternColor color = LanternColor.fromDyeColor(((DyeItem)stack.getItem()).getDyeColor());
            BlockState newState = this.material.getLanternBlock(color).defaultBlockState();
            newState = newState.setValue(WATERLOGGED, state.getValue(WATERLOGGED));
            newState = newState.setValue(HANGING, state.getValue(HANGING));
            newState = newState.setValue(ON, state.getValue(ON));
            newState = newState.setValue(REDSTONE, state.getValue(REDSTONE));
            level.setBlock(pos, newState, 1 | 2);
        }else if(this.material == LanternMaterial.NORMAL && this.color == null && !state.getValue(ON) && !state.getValue(REDSTONE) && !state.getValue(WATERLOGGED))
            level.setBlock(pos, Blocks.LANTERN.defaultBlockState().setValue(BlockStateProperties.HANGING, state.getValue(HANGING)), 1 | 2);
        else
            level.setBlock(pos, state.setValue(ON, !state.getValue(ON)), 1 | 2);
        return level.isClientSide ? ActionResultType.SUCCESS : ActionResultType.CONSUME;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block,BlockState> builder){
        super.createBlockStateDefinition(builder);
        builder.add(ON, REDSTONE, WATERLOGGED);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context){
        BlockState state = super.getStateForPlacement(context);
        IFluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return state == null ? null :
            state
                .setValue(REDSTONE, context.getLevel().hasNeighborSignal(context.getClickedPos()))
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState state2, IWorld level, BlockPos pos, BlockPos pos2){
        if(state.getValue(WATERLOGGED))
            level.getLiquidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        return super.updateShape(state, direction, state2, level, pos, pos2);
    }

    @Override
    public IFluidState getFluidState(BlockState state){
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public void neighborChanged(BlockState state, World level, BlockPos pos, Block block, BlockPos neighborPos, boolean p_220069_6_){
        if(!level.isClientSide){
            boolean redstone = level.hasNeighborSignal(pos);
            if(this.material == LanternMaterial.NORMAL && this.color == null && state.getValue(ON) && !redstone && !state.getValue(WATERLOGGED))
                level.setBlock(pos, Blocks.LANTERN.defaultBlockState().setValue(BlockStateProperties.HANGING, state.getValue(HANGING)), 1 | 2 | 4);
            else if(state.getValue(REDSTONE) != redstone)
                level.setBlock(pos, state.setValue(REDSTONE, redstone), 1 | 2 | 4);
        }
    }

    @Override
    public int getLightEmission(BlockState state){
        return state.getValue(REDSTONE) != state.getValue(ON) ? 15 : 0;
    }

    @Override
    public Fluid takeLiquid(IWorld level, BlockPos pos, BlockState state){
        if(state.getValue(ON) && !state.getValue(REDSTONE) && state.getValue(WATERLOGGED)){
            level.setBlock(pos, Blocks.LANTERN.defaultBlockState().setValue(BlockStateProperties.HANGING, state.getValue(HANGING)), 3);
            return Fluids.WATER;
        }
        return IWaterLoggable.super.takeLiquid(level, pos, state);
    }
}

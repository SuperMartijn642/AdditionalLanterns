package com.supermartijn642.additionallanterns;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created 7/5/2021 by SuperMartijn642
 */
public class LanternBlock extends net.minecraft.block.LanternBlock {

    public static final BooleanProperty ON = BooleanProperty.create("on");
    public static final BooleanProperty REDSTONE = BooleanProperty.create("redstone");

    public final LanternMaterial material;
    public final LanternColor color;

    public LanternBlock(LanternMaterial material, LanternColor color){
        super(material.getLanternBlockProperties());
        this.material = material;
        this.color = color;

        this.registerDefaultState(this.defaultBlockState().setValue(ON, true).setValue(REDSTONE, false));
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult){
        ItemStack stack = player.getItemInHand(hand);
        if(this.material.canBeColored && stack.getItem() instanceof DyeItem){
            LanternColor color = LanternColor.fromDyeColor(((DyeItem)stack.getItem()).getDyeColor());
            BlockState newState = this.material.getLanternBlock(color).defaultBlockState();
            newState = newState.setValue(WATERLOGGED, state.getValue(WATERLOGGED));
            newState = newState.setValue(HANGING, state.getValue(HANGING));
            newState = newState.setValue(ON, state.getValue(ON));
            newState = newState.setValue(REDSTONE, state.getValue(REDSTONE));
            world.setBlock(pos, newState, 1 | 2);
        }else if(this.material == LanternMaterial.NORMAL && this.color == null && !state.getValue(ON) && !state.getValue(REDSTONE))
            world.setBlock(pos, Blocks.LANTERN.defaultBlockState().setValue(BlockStateProperties.HANGING, state.getValue(HANGING)).setValue(BlockStateProperties.WATERLOGGED, state.getValue(WATERLOGGED)), 1 | 2);
        else
            world.setBlock(pos, state.setValue(ON, !state.getValue(ON)), 1 | 2);
        return ActionResultType.sidedSuccess(world.isClientSide);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block,BlockState> builder){
        super.createBlockStateDefinition(builder);
        builder.add(ON, REDSTONE);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context){
        BlockState state = super.getStateForPlacement(context);
        return state == null ? null : state.setValue(REDSTONE, context.getLevel().hasNeighborSignal(context.getClickedPos()));
    }

    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos neighborPos, boolean p_220069_6_){
        if(!world.isClientSide){
            boolean redstone = world.hasNeighborSignal(pos);
            if(this.material == LanternMaterial.NORMAL && this.color == null && state.getValue(ON) && !redstone)
                world.setBlock(pos, Blocks.LANTERN.defaultBlockState().setValue(BlockStateProperties.HANGING, state.getValue(HANGING)).setValue(BlockStateProperties.WATERLOGGED, state.getValue(WATERLOGGED)), 1 | 2 | 4);
            else if(state.getValue(REDSTONE) != redstone)
                world.setBlock(pos, state.setValue(REDSTONE, redstone), 1 | 2 | 4);
        }
    }

    public static boolean emitsLight(BlockState state){
        return state.getValue(REDSTONE) != state.getValue(ON);
    }
}

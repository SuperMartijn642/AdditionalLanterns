package com.supermartijn642.additionallanterns;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;


/**
 * Created 7/5/2021 by SuperMartijn642
 */
public class LanternBlock extends net.minecraft.world.level.block.LanternBlock {

    public static final BooleanProperty ON = BooleanProperty.create("on");
    public static final BooleanProperty REDSTONE = BooleanProperty.create("redstone");

    public final LanternMaterial material;
    public final LanternColor color;

    public LanternBlock(LanternMaterial material, LanternColor color){
        super(material.getLanternBlockProperties(color));
        this.material = material;
        this.color = color;

        this.registerDefaultState(this.defaultBlockState().setValue(ON, true).setValue(REDSTONE, false));
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult){
        if(this.material.canBeColored && stack.getItem() instanceof DyeItem){
            LanternColor color = LanternColor.fromDyeColor(((DyeItem)stack.getItem()).getDyeColor());
            BlockState newState = this.material.getLanternBlock(color).defaultBlockState();
            newState = newState.setValue(WATERLOGGED, state.getValue(WATERLOGGED));
            newState = newState.setValue(HANGING, state.getValue(HANGING));
            newState = newState.setValue(ON, state.getValue(ON));
            newState = newState.setValue(REDSTONE, state.getValue(REDSTONE));
            level.setBlock(pos, newState, 1 | 2);
        }else if(this.material == LanternMaterial.NORMAL && this.color == null && !state.getValue(ON) && !state.getValue(REDSTONE))
            level.setBlock(pos, Blocks.LANTERN.defaultBlockState().setValue(BlockStateProperties.HANGING, state.getValue(HANGING)).setValue(BlockStateProperties.WATERLOGGED, state.getValue(WATERLOGGED)), 1 | 2);
        else
            level.setBlock(pos, state.setValue(ON, !state.getValue(ON)), 1 | 2);
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block,BlockState> builder){
        super.createBlockStateDefinition(builder);
        builder.add(ON, REDSTONE);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context){
        BlockState state = super.getStateForPlacement(context);
        if(state == null)
            return null;
        boolean redstone = context.getLevel().hasNeighborSignal(context.getClickedPos());
        if(!redstone && this.material == LanternMaterial.NORMAL && this.color == null)
            return Blocks.LANTERN.defaultBlockState().setValue(BlockStateProperties.HANGING, state.getValue(HANGING)).setValue(BlockStateProperties.WATERLOGGED, state.getValue(WATERLOGGED));
        return state.setValue(REDSTONE, redstone);
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, @Nullable Orientation orientation, boolean isMoving){
        if(!level.isClientSide){
            boolean redstone = level.hasNeighborSignal(pos);
            if(this.material == LanternMaterial.NORMAL && this.color == null && state.getValue(ON) && !redstone)
                level.setBlock(pos, Blocks.LANTERN.defaultBlockState().setValue(BlockStateProperties.HANGING, state.getValue(HANGING)).setValue(BlockStateProperties.WATERLOGGED, state.getValue(WATERLOGGED)), 1 | 2 | 4);
            else if(state.getValue(REDSTONE) != redstone)
                level.setBlock(pos, state.setValue(REDSTONE, redstone), 1 | 2 | 4);
        }
    }

    public static boolean emitsLight(BlockState state){
        return state.getValue(REDSTONE) != state.getValue(ON);
    }
}

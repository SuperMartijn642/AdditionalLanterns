package com.supermartijn642.additionallanterns;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;

/**
 * Created 25/01/2023 by SuperMartijn642
 */
public class VanillaLanternEvents {

    public static void registerEventHandlers(){
        UseBlockCallback.EVENT.register(VanillaLanternEvents::handleInteractWithLantern);
    }

    private static InteractionResult handleInteractWithLantern(Player player, Level level, InteractionHand hand, BlockHitResult hitResult){
        // Replace the vanilla lantern with Additional Lantern's lantern when right-clicked
        BlockPos clickedPos = hitResult.getBlockPos();
        BlockState oldState = level.getBlockState(clickedPos);
        if(oldState.getBlock() == Blocks.LANTERN){
            if(!level.isClientSide){
                BlockState newState = LanternMaterial.NORMAL.getLanternBlock().defaultBlockState()
                    .setValue(LanternBlock.ON, false)
                    .setValue(LanternBlock.REDSTONE, level.hasNeighborSignal(clickedPos))
                    .setValue(LanternBlock.HANGING, oldState.getValue(BlockStateProperties.HANGING))
                    .setValue(LanternBlock.WATERLOGGED, oldState.getValue(BlockStateProperties.WATERLOGGED));
                level.setBlock(clickedPos, newState, 1 | 2);
            }
            return level.isClientSide ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    public static void handleLanternRedstone(Level level, BlockPos pos){
        BlockState oldState = level.getBlockState(pos);
        // Replace the vanilla lantern with Additional Lantern's lantern when it's powered
        if(oldState.getBlock() == Blocks.LANTERN && level.hasNeighborSignal(pos)){
            BlockState newState = LanternMaterial.NORMAL.getLanternBlock().defaultBlockState()
                .setValue(LanternBlock.REDSTONE, true)
                .setValue(LanternBlock.HANGING, oldState.getValue(BlockStateProperties.HANGING))
                .setValue(LanternBlock.WATERLOGGED, oldState.getValue(BlockStateProperties.WATERLOGGED));
            level.setBlock(pos, newState, 1 | 2);
        }
    }
}

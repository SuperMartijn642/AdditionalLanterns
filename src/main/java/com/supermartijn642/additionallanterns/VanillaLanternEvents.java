package com.supermartijn642.additionallanterns;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
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
        if(oldState.getBlock() instanceof net.minecraft.world.level.block.LanternBlock){
            LanternMaterial material = LanternMaterial.VANILLA_LANTERN_MAPPINGS.get(oldState.getBlock());
            if(material != null){
                if(!level.isClientSide()){
                    ItemStack stack = player.getItemInHand(hand);
                    LanternColor color = material.canBeColored && stack.getItem() instanceof DyeItem ? LanternColor.fromDyeColor(((DyeItem)stack.getItem()).getDyeColor()) : null;
                    BlockState newState = material.getLanternBlock(color).defaultBlockState()
                        .setValue(LanternBlock.WATERLOGGED, oldState.getValue(LanternBlock.WATERLOGGED))
                        .setValue(LanternBlock.HANGING, oldState.getValue(LanternBlock.HANGING))
                        .setValue(LanternBlock.ON, color != null)
                        .setValue(LanternBlock.REDSTONE, level.hasNeighborSignal(clickedPos));
                    level.setBlock(clickedPos, newState, 1 | 2);
                }
                return level.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
            }
        }
        return InteractionResult.PASS;
    }

    public static BlockState getLanternPlacement(LanternMaterial material, BlockPlaceContext context){
        Level level = context.getLevel();
        // Place Additional Lantern's lantern instead of vanilla lantern when the block position is powered
        if(!level.isClientSide() && level.hasNeighborSignal(context.getClickedPos()))
            return material.getLanternBlock().getStateForPlacement(context);
        return null;
    }

    public static void handleLanternRedstone(Level level, BlockPos pos){
        BlockState oldState = level.getBlockState(pos);
        // Replace the vanilla lantern with Additional Lantern's lantern when it's powered
        if(oldState.getBlock() instanceof net.minecraft.world.level.block.LanternBlock && level.hasNeighborSignal(pos)){
            LanternMaterial material = LanternMaterial.VANILLA_LANTERN_MAPPINGS.get(oldState.getBlock());
            if(material != null){
                BlockState newState = material.getLanternBlock().defaultBlockState()
                    .setValue(LanternBlock.REDSTONE, true)
                    .setValue(LanternBlock.HANGING, oldState.getValue(BlockStateProperties.HANGING))
                    .setValue(LanternBlock.WATERLOGGED, oldState.getValue(BlockStateProperties.WATERLOGGED));
                level.setBlock(pos, newState, 1 | 2);
            }
        }
    }
}

package com.supermartijn642.additionallanterns;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

/**
 * Created 25/01/2023 by SuperMartijn642
 */
public class VanillaLanternEvents {

    public static void registerEventHandlers(){
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, false, VanillaLanternEvents::handleInteractWithLantern);
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, false, VanillaLanternEvents::handleLanternPlacement);
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, false, VanillaLanternEvents::handleLanternRedstone);
    }

    private static void handleInteractWithLantern(PlayerInteractEvent.RightClickBlock e){
        Level level = e.getLevel();
        // Replace the vanilla lantern with Additional Lantern's lantern when right-clicked
        BlockPos clickedPos = e.getPos();
        BlockState oldState = level.getBlockState(clickedPos);
        if(oldState.getBlock() instanceof net.minecraft.world.level.block.LanternBlock){
            LanternMaterial material = LanternMaterial.VANILLA_LANTERN_MAPPINGS.get(oldState.getBlock());
            if(material != null){
                if(!level.isClientSide()){
                    ItemStack stack = e.getItemStack();
                    LanternColor color = material.canBeColored && stack.getItem() instanceof DyeItem ? LanternColor.fromDyeColor(((DyeItem)stack.getItem()).getDyeColor()) : null;
                    BlockState newState = material.getLanternBlock(color).defaultBlockState()
                        .setValue(LanternBlock.WATERLOGGED, oldState.getValue(LanternBlock.WATERLOGGED))
                        .setValue(LanternBlock.HANGING, oldState.getValue(LanternBlock.HANGING))
                        .setValue(LanternBlock.ON, color != null)
                        .setValue(LanternBlock.REDSTONE, level.hasNeighborSignal(clickedPos));
                    level.setBlock(clickedPos, newState, 1 | 2);
                }
                e.setCanceled(true);
                e.setCancellationResult(level.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.CONSUME);
            }
        }
    }

    private static void handleLanternPlacement(BlockEvent.EntityPlaceEvent e){
        LevelAccessor level = e.getLevel();
        // Replace Additional Lantern's lantern with the vanilla lantern when it's on and not powered
        if(!level.isClientSide()){
            BlockState oldState = e.getPlacedBlock();
            Block block = oldState.getBlock();
            if(block instanceof LanternBlock){
                LanternMaterial material = ((LanternBlock)block).material;
                if(material.isVanilla && oldState.getValue(LanternBlock.ON) && !e.getLevel().hasNeighborSignal(e.getPos())){
                    BlockState newState = material.vanillaLanternBlock.defaultBlockState()
                        .setValue(BlockStateProperties.HANGING, oldState.getValue(LanternBlock.HANGING))
                        .setValue(BlockStateProperties.WATERLOGGED, oldState.getValue(LanternBlock.WATERLOGGED));
                    level.setBlock(e.getPos(), newState, 1 | 2);
                }
            }else if(block instanceof net.minecraft.world.level.block.LanternBlock){
                LanternMaterial material = LanternMaterial.VANILLA_LANTERN_MAPPINGS.get(block);
                if(material != null && e.getLevel().hasNeighborSignal(e.getPos())){
                    BlockState newState = material.getLanternBlock().defaultBlockState()
                        .setValue(BlockStateProperties.HANGING, oldState.getValue(LanternBlock.HANGING))
                        .setValue(BlockStateProperties.WATERLOGGED, oldState.getValue(LanternBlock.WATERLOGGED))
                        .setValue(LanternBlock.REDSTONE, true);
                    level.setBlock(e.getPos(), newState, 1 | 2);
                }
            }
        }
    }

    private static void handleLanternRedstone(BlockEvent.NeighborNotifyEvent e){
        LevelAccessor level = e.getLevel();
        BlockPos originatingPos = e.getPos();
        for(Direction direction : e.getNotifiedSides()){
            BlockPos pos = originatingPos.relative(direction);
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
}

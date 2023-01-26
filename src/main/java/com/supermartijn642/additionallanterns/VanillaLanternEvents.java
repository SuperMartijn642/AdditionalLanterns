package com.supermartijn642.additionallanterns;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;

/**
 * Created 25/01/2023 by SuperMartijn642
 */
public class VanillaLanternEvents {

    public static void registerEventHandlers(){
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, false, VanillaLanternEvents::handleInteractWithLantern);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, false, VanillaLanternEvents::handleLanternPlacement);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, false, VanillaLanternEvents::handleLanternRedstone);
    }

    private static void handleInteractWithLantern(PlayerInteractEvent.RightClickBlock e){
        World level = e.getWorld();
        // Replace the vanilla lantern with Additional Lantern's lantern when right-clicked
        BlockPos clickedPos = e.getPos();
        BlockState oldState = level.getBlockState(clickedPos);
        if(oldState.getBlock() == Blocks.LANTERN){
            e.setCanceled(true);
            e.setCancellationResult(ActionResultType.SUCCESS);
            if(!level.isClientSide){
                BlockState newState = LanternMaterial.NORMAL.getLanternBlock().defaultBlockState()
                    .setValue(LanternBlock.ON, false)
                    .setValue(LanternBlock.REDSTONE, level.hasNeighborSignal(clickedPos))
                    .setValue(LanternBlock.HANGING, oldState.getValue(BlockStateProperties.HANGING));
                level.setBlock(clickedPos, newState, 1 | 2);
            }
        }else if(e.getItemStack().getItem() == Items.WATER_BUCKET && !(oldState.getBlock() instanceof ILiquidContainer && ((ILiquidContainer)oldState.getBlock()).canPlaceLiquid(level, clickedPos, oldState, Fluids.WATER))){
            BlockPos pos = clickedPos.relative(e.getFace());
            oldState = level.getBlockState(pos);
            if(oldState.getBlock() == Blocks.LANTERN){
                e.setCanceled(true);
                BlockState newState = LanternMaterial.NORMAL.getLanternBlock().defaultBlockState()
                    .setValue(LanternBlock.HANGING, oldState.getValue(BlockStateProperties.HANGING));
                level.setBlock(pos, newState, 1 | 2);
                ActionResult<ItemStack> result = e.getItemStack().use(level, e.getPlayer(), e.getHand());
                if(level.getBlockState(pos) == newState)
                    level.setBlock(pos, oldState, 1 | 2);
                e.setCancellationResult(result.getResult());
            }
        }
    }

    private static void handleLanternPlacement(BlockEvent.EntityPlaceEvent e){
        IWorld level = e.getWorld();
        // Replace Additional Lantern's lantern with the vanilla lantern when it's on and not powered
        if(!level.isClientSide()){
            BlockState oldState = e.getPlacedBlock();
            if(oldState.getBlock() == LanternMaterial.NORMAL.getLanternBlock() && oldState.getValue(LanternBlock.ON) && !oldState.getValue(LanternBlock.REDSTONE) && !oldState.getValue(LanternBlock.WATERLOGGED)){
                BlockState newState = Blocks.LANTERN.defaultBlockState()
                    .setValue(BlockStateProperties.HANGING, oldState.getValue(LanternBlock.HANGING));
                level.setBlock(e.getPos(), newState, 1 | 2);
            }
        }
    }

    private static void handleLanternRedstone(BlockEvent.NeighborNotifyEvent e){
        IWorld level = e.getWorld();
        BlockPos originatingPos = e.getPos();
        for(Direction direction : e.getNotifiedSides()){
            BlockPos pos = originatingPos.relative(direction);
            BlockState oldState = level.getBlockState(pos);
            // Replace the vanilla lantern with Additional Lantern's lantern when it's powered
            if(oldState.getBlock() == Blocks.LANTERN && level instanceof World && ((World)level).hasNeighborSignal(pos)){
                BlockState newState = LanternMaterial.NORMAL.getLanternBlock().defaultBlockState()
                    .setValue(LanternBlock.REDSTONE, true)
                    .setValue(LanternBlock.HANGING, oldState.getValue(BlockStateProperties.HANGING));
                level.setBlock(pos, newState, 1 | 2);
            }
        }
    }
}

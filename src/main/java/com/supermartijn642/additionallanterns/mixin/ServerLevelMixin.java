package com.supermartijn642.additionallanterns.mixin;

import com.supermartijn642.additionallanterns.VanillaLanternEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created 24/03/2023 by SuperMartijn642
 */
@Mixin(ServerLevel.class)
public class ServerLevelMixin {

    @Inject(
        method = "updateNeighborsAt(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Block;)V",
        at = @At("HEAD")
    )
    private void updateNeighborsAt(BlockPos pos, Block block, CallbackInfo ci){
        //noinspection DataFlowIssue
        ServerLevel level = (ServerLevel)(Object)this;
        for(Direction direction : Direction.values())
            VanillaLanternEvents.handleLanternRedstone(level, pos.relative(direction));
    }

    @Inject(
        method = "updateNeighborsAtExceptFromFacing(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Block;Lnet/minecraft/core/Direction;)V",
        at = @At("HEAD")
    )
    private void updateNeighborsAtExceptFromFacing(BlockPos pos, Block block, Direction ignoredDirection, CallbackInfo ci){
        //noinspection DataFlowIssue
        ServerLevel level = (ServerLevel)(Object)this;
        for(Direction direction : Direction.values())
            if(direction != ignoredDirection)
                VanillaLanternEvents.handleLanternRedstone(level, pos.relative(direction));
    }
}

package com.supermartijn642.additionallanterns.mixin;

import com.supermartijn642.additionallanterns.VanillaLanternEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created 22/03/2023 by SuperMartijn642
 */
@Mixin(Level.class)
public class LevelMixin {

    @Inject(
        method = "updateNeighborsAt(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Block;)V",
        at = @At("HEAD")
    )
    private void updateNeighborsAt(BlockPos pos, Block block, CallbackInfo ci){
        //noinspection DataFlowIssue
        Level level = (Level)(Object)this;
        for(Direction direction : Direction.values())
            VanillaLanternEvents.handleLanternRedstone(level, pos.relative(direction));
    }
}

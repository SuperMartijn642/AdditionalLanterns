package com.supermartijn642.additionallanterns.mixin;

import com.supermartijn642.additionallanterns.VanillaLanternEvents;
import net.minecraft.core.BlockPos;
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
        method = "neighborChanged(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Block;Lnet/minecraft/core/BlockPos;)V",
        at = @At("HEAD")
    )
    private void neighborChanged(BlockPos pos, Block originatingBlock, BlockPos originatingPos, CallbackInfo ci){
        //noinspection DataFlowIssue
        Level level = (Level)(Object)this;
        VanillaLanternEvents.handleLanternRedstone(level, pos);
    }
}

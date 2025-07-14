package com.supermartijn642.additionallanterns.mixin;

import com.supermartijn642.additionallanterns.VanillaLanternEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Created 15/07/2025 by SuperMartijn642
 */
@Mixin(BlockItem.class)
public class BlockItemMixin {

    @Final
    @Shadow
    private Block block;

    @Unique
    private boolean isLantern = false;

    @Inject(
        method = "<init>",
        at = @At("TAIL")
    )
    private void init(CallbackInfo ci){
        this.isLantern = this.block == Blocks.LANTERN;
    }

    @Inject(
        method = "getPlacementState",
        at = @At("HEAD"),
        cancellable = true
    )
    private void getPlacementState(BlockPlaceContext context, CallbackInfoReturnable<BlockState> ci){
        if(this.isLantern){
            BlockState state = VanillaLanternEvents.getLanternPlacement(context);
            if(state != null)
                ci.setReturnValue(state);
        }
    }
}

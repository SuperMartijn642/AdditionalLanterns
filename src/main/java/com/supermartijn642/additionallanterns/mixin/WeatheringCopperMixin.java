package com.supermartijn642.additionallanterns.mixin;

import com.supermartijn642.additionallanterns.WeatheringLanternBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

/**
 * Created 19/04/2024 by SuperMartijn642
 */
@Mixin(WeatheringCopper.class)
@SuppressWarnings("unused")
public interface WeatheringCopperMixin {

    @Inject(
        method = "getPrevious(Lnet/minecraft/world/level/block/Block;)Ljava/util/Optional;",
        at = @At("HEAD"),
        cancellable = true
    )
    private static void getPrevious(Block block, CallbackInfoReturnable<Optional<Block>> ci){
        if(WeatheringLanternBlock.WEATHERING_MAP != null){
            Block previous = WeatheringLanternBlock.WEATHERING_MAP.inverse().get(block);
            if(previous != null)
                ci.setReturnValue(Optional.of(previous));
        }
    }

    @Inject(
        method = "getFirst(Lnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/level/block/Block;",
        at = @At("HEAD"),
        cancellable = true
    )
    private static void getFirst(Block block, CallbackInfoReturnable<Block> ci){
        if(WeatheringLanternBlock.WEATHERING_MAP != null){
            Block first = block;
            Block previous;
            while((previous = WeatheringLanternBlock.WEATHERING_MAP.inverse().get(first)) != null)
                first = previous;
            if(first != block)
                ci.setReturnValue(first);
        }
    }

    @Inject(
        method = "getNext(Lnet/minecraft/world/level/block/Block;)Ljava/util/Optional;",
        at = @At("HEAD"),
        cancellable = true
    )
    private static void getNext(Block block, CallbackInfoReturnable<Optional<Block>> ci){
        if(WeatheringLanternBlock.WEATHERING_MAP != null){
            Block next = WeatheringLanternBlock.WEATHERING_MAP.get(block);
            if(next != null)
                ci.setReturnValue(Optional.of(next));
        }
    }
}

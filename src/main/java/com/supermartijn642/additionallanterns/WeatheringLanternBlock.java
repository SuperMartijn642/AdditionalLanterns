package com.supermartijn642.additionallanterns;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

/**
 * Created 7/5/2021 by SuperMartijn642
 */
public class WeatheringLanternBlock extends LanternBlock implements WeatheringCopper {

    private final WeatherState weatherState;

    public WeatheringLanternBlock(LanternMaterial material, LanternColor color, WeatheringCopper.WeatherState weatherState){
        super(material, color);
        this.weatherState = weatherState;
        this.registerDefaultState(this.defaultBlockState().setValue(ON, true).setValue(REDSTONE, false));
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, Random randomSource){
        if(randomSource.nextFloat() < 0.05688889f)
            this.applyChangeOverTime(blockState, serverLevel, blockPos, randomSource);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state){
        return WeatheringCopper.getNext(state.getBlock()).isPresent();
    }

    @Override
    public WeatherState getAge(){
        return this.weatherState;
    }
}
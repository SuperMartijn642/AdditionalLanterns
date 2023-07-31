package com.supermartijn642.additionallanterns;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

/**
 * Created 7/6/2021 by SuperMartijn642
 */
public class WeatheringChainBlock extends ChainBlock implements WeatheringCopper {

    private final WeatheringCopper.WeatherState weatherState;

    public WeatheringChainBlock(LanternMaterial material, WeatheringCopper.WeatherState weatherState){
        super(material);
        this.weatherState = weatherState;
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
    public WeatheringCopper.WeatherState getAge(){
        return this.weatherState;
    }
}

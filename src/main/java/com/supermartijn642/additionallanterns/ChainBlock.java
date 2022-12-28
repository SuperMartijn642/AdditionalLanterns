package com.supermartijn642.additionallanterns;

/**
 * Created 7/6/2021 by SuperMartijn642
 */
public class ChainBlock extends net.minecraft.block.ChainBlock {

    public final LanternMaterial material;

    public ChainBlock(LanternMaterial material){
        super(material.getChainBlockProperties());
        this.material = material;
    }
}

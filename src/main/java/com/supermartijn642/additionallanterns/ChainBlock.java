package com.supermartijn642.additionallanterns;

import com.supermartijn642.core.block.BlockShape;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

/**
 * Created 7/6/2021 by SuperMartijn642
 */
public class ChainBlock extends BlockRotatedPillar {

    protected static final BlockShape Y_AXIS_AABB = BlockShape.createBlockShape(6.5D, 0.0D, 6.5D, 9.5D, 16.0D, 9.5D);
    protected static final BlockShape Z_AXIS_AABB = BlockShape.createBlockShape(6.5D, 6.5D, 0.0D, 9.5D, 9.5D, 16.0D);
    protected static final BlockShape X_AXIS_AABB = BlockShape.createBlockShape(0.0D, 6.5D, 6.5D, 16.0D, 9.5D, 9.5D);

    public final LanternMaterial material;

    public ChainBlock(LanternMaterial material){
        super(Material.IRON, MapColor.AIR);
        this.material = material;

        this.setHardness(5);
        this.setResistance(6);
        this.setSoundType(SoundType.METAL);
        this.setCreativeTab(AdditionalLanterns.GROUP);

        this.setDefaultState(this.getDefaultState().withProperty(AXIS, EnumFacing.Axis.Y));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess level, BlockPos pos){
        return state.getValue(AXIS) == EnumFacing.Axis.X ? X_AXIS_AABB.simplify() :
            state.getValue(AXIS) == EnumFacing.Axis.Y ? Y_AXIS_AABB.simplify() :
                Z_AXIS_AABB.simplify();
    }

    @Nullable
    @Override
    public PathNodeType getAiPathNodeType(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EntityLiving entity){
        return PathNodeType.BLOCKED;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face){
        return face.getAxis() == state.getValue(AXIS) ? BlockFaceShape.CENTER_SMALL : BlockFaceShape.UNDEFINED;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer(){
        return BlockRenderLayer.CUTOUT;
    }

    public boolean isOpaqueCube(IBlockState state){
        return false;
    }

    public boolean isFullCube(IBlockState state){
        return false;
    }

    public String getLocalizedName(){
        return I18n.format(this.getUnlocalizedName()).trim();
    }

    public String getUnlocalizedName(){
        return this.getRegistryName().getResourceDomain() + ".block." + this.getRegistryName().getResourcePath();
    }
}

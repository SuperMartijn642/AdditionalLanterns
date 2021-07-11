package com.supermartijn642.additionallanterns;

import com.supermartijn642.core.block.BaseBlock;
import com.supermartijn642.core.block.BlockShape;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created 7/5/2021 by SuperMartijn642
 */
public class LanternBlock extends BaseBlock {

    protected static final BlockShape AABB = BlockShape.or(BlockShape.createBlockShape(5, 0, 5, 11, 7, 11), BlockShape.createBlockShape(6, 7, 6, 10, 9, 10));
    protected static final BlockShape HANGING_AABB = BlockShape.or(BlockShape.createBlockShape(5, 1, 5, 11, 8, 11), BlockShape.createBlockShape(6, 8, 6, 10, 10, 10));

    public static final PropertyBool ON = PropertyBool.create("on");
    public static final PropertyBool REDSTONE = PropertyBool.create("redstone");
    public static final PropertyBool HANGING = PropertyBool.create("hanging");

    public final LanternMaterial material;
    public final LanternColor color;

    public LanternBlock(LanternMaterial material, LanternColor color){
        super(color == null ? material.getSuffix() + "_lantern" : color.getSuffix() + "_" + material.getSuffix() + "_lantern", false, material.getLanternBlockProperties());
        this.material = material;
        this.color = color;

        this.setCreativeTab(AdditionalLanterns.GROUP);

        this.setDefaultState(this.getDefaultState().withProperty(ON, true).withProperty(REDSTONE, false).withProperty(HANGING, false));
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        ItemStack stack = player.getHeldItem(hand);
        if(this.material.canBeColored && stack.getItem() instanceof ItemDye){
            LanternColor color = LanternColor.fromDyeColor(EnumDyeColor.byDyeDamage(stack.getMetadata()));
            IBlockState newState = this.material.getLanternBlock(color).getDefaultState();
            newState = newState.withProperty(ON, state.getValue(ON));
            newState = newState.withProperty(REDSTONE, state.getValue(REDSTONE));
            newState = newState.withProperty(HANGING, state.getValue(HANGING));
            world.setBlockState(pos, newState, 1 | 2);
        }else
            world.setBlockState(pos, state.withProperty(ON, !state.getValue(ON)), 1 | 2);
        return true;
    }

    @Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, ON, REDSTONE, HANGING);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand){
        IBlockState state = this.getDefaultState()
            .withProperty(REDSTONE, world.isBlockPowered(pos))
            .withProperty(HANGING, facing == EnumFacing.UP);
        if(canSurvive(world, pos, state))
            return state;

        state = state.cycleProperty(HANGING);
        if(canSurvive(world, pos, state))
            return state;

        return Blocks.AIR.getDefaultState();
    }

    private static boolean canSurvive(World world, BlockPos pos, IBlockState state){
        EnumFacing direction = state.getValue(HANGING) ? EnumFacing.UP : EnumFacing.DOWN;
        BlockFaceShape shape = world.getBlockState(pos.offset(direction)).getBlockFaceShape(world, pos.offset(direction), direction.getOpposite());
        return shape == BlockFaceShape.CENTER || shape == BlockFaceShape.CENTER_BIG || shape == BlockFaceShape.CENTER_SMALL ||
            shape == BlockFaceShape.MIDDLE_POLE || shape == BlockFaceShape.MIDDLE_POLE_THICK || shape == BlockFaceShape.MIDDLE_POLE_THIN ||
            shape == BlockFaceShape.SOLID;
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos){
        return world.getBlockState(pos).getBlock().isReplaceable(world, pos) &&
            (canSurvive(world, pos, this.getDefaultState().withProperty(HANGING, false)) ||
                canSurvive(world, pos, this.getDefaultState().withProperty(HANGING, true)));
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state){
        if(state.getBlock() == this && !canSurvive(world, pos, state)){
            if(world.getBlockState(pos).getBlock() == this){
                this.dropBlockAsItem(world, pos, state, 0);
                world.setBlockToAir(pos);
            }
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos){
        if(state.getBlock() == this && !world.isRemote){
            if(!canSurvive(world, pos, state)){
                this.dropBlockAsItem(world, pos, state, 0);
                world.setBlockToAir(pos);
            }else{
                boolean redstone = state.getValue(REDSTONE);
                if(redstone != world.isBlockPowered(pos))
                    world.setBlockState(pos, state.withProperty(REDSTONE, !redstone), 1 | 2 | 4);
            }
        }
    }

    public static boolean emitsLight(IBlockState state){
        return state.getValue(REDSTONE) != state.getValue(ON);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
        return state.getValue(HANGING) ? HANGING_AABB.simplify() : AABB.simplify();
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face){
        return state.getValue(HANGING) ?
            face == EnumFacing.UP ? BlockFaceShape.MIDDLE_POLE_THIN : BlockFaceShape.UNDEFINED :
            face == EnumFacing.DOWN ? BlockFaceShape.CENTER : BlockFaceShape.UNDEFINED;
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

    @Override
    public int getMetaFromState(IBlockState state){
        int meta = 0;
        if(state.getValue(ON))
            meta |= 1;
        if(state.getValue(REDSTONE))
            meta |= 2;
        if(state.getValue(HANGING))
            meta |= 4;
        return meta;
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState()
            .withProperty(ON, (meta & 1) != 0)
            .withProperty(REDSTONE, (meta & 2) != 0)
            .withProperty(HANGING, (meta & 4) != 0);
    }
}

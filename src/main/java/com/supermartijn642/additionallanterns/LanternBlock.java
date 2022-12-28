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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

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
        super(false, material.getLanternBlockProperties());
        this.material = material;
        this.color = color;

        this.setDefaultState(this.getDefaultState().withProperty(ON, true).withProperty(REDSTONE, false).withProperty(HANGING, false));
    }

    @Override
    protected InteractionFeedback interact(IBlockState state, World level, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing hitSide, Vec3d hitLocation){
        ItemStack stack = player.getHeldItem(hand);
        if(this.material.canBeColored && stack.getItem() instanceof ItemDye){
            LanternColor color = LanternColor.fromDyeColor(EnumDyeColor.byDyeDamage(stack.getMetadata()));
            IBlockState newState = this.material.getLanternBlock(color).getDefaultState();
            newState = newState.withProperty(ON, state.getValue(ON));
            newState = newState.withProperty(REDSTONE, state.getValue(REDSTONE));
            newState = newState.withProperty(HANGING, state.getValue(HANGING));
            level.setBlockState(pos, newState, 1 | 2);
        }else
            level.setBlockState(pos, state.withProperty(ON, !state.getValue(ON)), 1 | 2);
        return InteractionFeedback.SUCCESS;
    }

    @Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, ON, REDSTONE, HANGING);
    }

    @Override
    public IBlockState getStateForPlacement(World level, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand){
        IBlockState state = this.getDefaultState()
            .withProperty(REDSTONE, level.isBlockPowered(pos))
            .withProperty(HANGING, facing == EnumFacing.UP);
        if(canSurvive(level, pos, state))
            return state;

        state = state.cycleProperty(HANGING);
        if(canSurvive(level, pos, state))
            return state;

        return Blocks.AIR.getDefaultState();
    }

    private static boolean canSurvive(World level, BlockPos pos, IBlockState state){
        EnumFacing direction = state.getValue(HANGING) ? EnumFacing.UP : EnumFacing.DOWN;
        BlockFaceShape shape = level.getBlockState(pos.offset(direction)).getBlockFaceShape(level, pos.offset(direction), direction.getOpposite());
        return shape == BlockFaceShape.CENTER || shape == BlockFaceShape.CENTER_BIG || shape == BlockFaceShape.CENTER_SMALL ||
            shape == BlockFaceShape.MIDDLE_POLE || shape == BlockFaceShape.MIDDLE_POLE_THICK || shape == BlockFaceShape.MIDDLE_POLE_THIN ||
            shape == BlockFaceShape.SOLID;
    }

    @Override
    public boolean canPlaceBlockAt(World level, BlockPos pos){
        return level.getBlockState(pos).getBlock().isReplaceable(level, pos) &&
            (canSurvive(level, pos, this.getDefaultState().withProperty(HANGING, false)) ||
                canSurvive(level, pos, this.getDefaultState().withProperty(HANGING, true)));
    }

    @Override
    public void onBlockAdded(World level, BlockPos pos, IBlockState state){
        if(state.getBlock() == this && !canSurvive(level, pos, state)){
            if(level.getBlockState(pos).getBlock() == this){
                this.dropBlockAsItem(level, pos, state, 0);
                level.setBlockToAir(pos);
            }
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World level, BlockPos pos, Block block, BlockPos fromPos){
        if(state.getBlock() == this && !level.isRemote){
            if(!canSurvive(level, pos, state)){
                this.dropBlockAsItem(level, pos, state, 0);
                level.setBlockToAir(pos);
            }else{
                boolean redstone = state.getValue(REDSTONE);
                if(redstone != level.isBlockPowered(pos))
                    level.setBlockState(pos, state.withProperty(REDSTONE, !redstone), 1 | 2 | 4);
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

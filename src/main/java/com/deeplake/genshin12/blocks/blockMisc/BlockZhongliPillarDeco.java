package com.deeplake.genshin12.blocks.blockMisc;

import com.deeplake.genshin12.blocks.BlockBase;
import com.deeplake.genshin12.blocks.tileEntity.genshin.TEZhongliPillar;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockZhongliPillarDeco extends BlockBase {

    static final float UNIT = 0.31f;
    public static final AxisAlignedBB AABB_Y = new AxisAlignedBB(UNIT, 0, UNIT, 1-UNIT, 1, 1-UNIT);

    public BlockZhongliPillarDeco(String name, Material material) {
        super(name, material);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean causesSuffocation(IBlockState state) {
        return false;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return AABB_Y;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB_Y;
    }

    @Override
    public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity) {
        return true;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        if (face.getAxis() == EnumFacing.Axis.Y)
        {
            return BlockFaceShape.MIDDLE_POLE_THICK;
        }
        else {
            return BlockFaceShape.UNDEFINED;
        }
    }
}

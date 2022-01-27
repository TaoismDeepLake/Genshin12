package com.deeplake.genshin12.blocks.blockMisc;

import com.deeplake.genshin12.blocks.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nullable;

public class BlockZhongliPillar extends BlockBase {

    static final float UNIT = 0.31f;
    public static final AxisAlignedBB AABB_Y = new AxisAlignedBB(UNIT, 0, UNIT, 1-UNIT, 1, 1-UNIT);

    public BlockZhongliPillar(String name, Material material) {
        super(name, material);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return super.isFullCube(state);
    }

    @Override
    public boolean causesSuffocation(IBlockState state) {
        return super.causesSuffocation(state);
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
}

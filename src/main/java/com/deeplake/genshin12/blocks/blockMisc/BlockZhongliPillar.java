package com.deeplake.genshin12.blocks.blockMisc;

import com.deeplake.genshin12.blocks.BlockBase;
import com.deeplake.genshin12.blocks.tileEntity.genshin.TEZhongliPillar;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockZhongliPillar extends BlockZhongliPillarDeco implements ITileEntityProvider {

    public BlockZhongliPillar(String name, Material material) {
        super(name, material);
    }
    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TEZhongliPillar();
    }
}

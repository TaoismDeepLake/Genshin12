package com.deeplake.genshin12.blocks.ore;

import com.deeplake.genshin12.blocks.BlockOreBase;
import com.deeplake.genshin12.item.ModItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockOreArtifact extends BlockOreBase {
    public BlockOreArtifact(String name) {
        super(name);
    }

    //ignored vanilla
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        Random rand = world instanceof World ? ((World)world).rand : RANDOM;

        int count = quantityDropped(state, fortune, rand);
        for (int i = 0; i < count; i++)
        {
            drops.add(ModItems.AR_MINER.getRandomBlankInstance());
        }
    }
}

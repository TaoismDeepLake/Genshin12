package com.deeplake.genshin12.worldgen;

import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.util.CommonDef;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class GenOreEmeraldLike implements IWorldGenerator {

    final IBlockState stateOre;
    final int minY, maxY;
    final int count;
    final int delta;

    //count genshin12:cor_lapis_ore
    public GenOreEmeraldLike(IBlockState state, int minY, int maxY, int count, int delta) {
        this.stateOre = state;
        this.minY = minY;
        this.maxY = maxY;
        this.count = count;
        this.delta = delta;
    }

    protected BlockPos getPosGenerate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        return new BlockPos(chunkX * CommonDef.CHUNK_SIZE + CommonDef.CHUNK_CENTER_INT, random.nextInt(maxY - minY) + minY, chunkZ * CommonDef.CHUNK_SIZE + CommonDef.CHUNK_CENTER_INT);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        generate(world, random, getPosGenerate(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider));
    }

    boolean generate(World worldIn, Random rand, BlockPos pos)
    {
        int thisCount = count + rand.nextInt(delta + 1);
        if (ModConfig.DEBUG_CONF.DEBUG_MODE)
        {
            thisCount = ModConfig.WorldGenConf.COR_LAPIS + rand.nextInt(ModConfig.WorldGenConf.COR_LAPIS_DELTA);
        }

        for (int i = 0; i < thisCount; i++)
        {
            int offset = net.minecraftforge.common.ForgeModContainer.fixVanillaCascading ? 8 : 0; // MC-114332
            BlockPos blockpos = pos.add(rand.nextInt(16) + offset, rand.nextInt(28) + 4, rand.nextInt(16) + offset);

            net.minecraft.block.state.IBlockState state = worldIn.getBlockState(blockpos);
            if (state.getBlock().isReplaceableOreGen(state, worldIn, blockpos, net.minecraft.block.state.pattern.BlockMatcher.forBlock(Blocks.STONE)))
            {
                worldIn.setBlockState(blockpos, stateOre, 16 | 2);
            }
        }
        return true;
    }

}

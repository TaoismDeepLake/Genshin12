package com.deeplake.genshin12.blocks;

import com.deeplake.genshin12.blocks.blockMisc.BlockZhongliPillar;
import com.deeplake.genshin12.init.ModCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	/*
	 * To add a block, put a line here,
	 * -Create a json at assets.eo.blockstates
	 * -Create a json at assets.eo.models.block
	 * -Create a json at assets.eo.models.item
	 * -Add corresponding texture png
	 */


	public static final Block GRID_BLOCK_1 = new BlockZhongliPillar("test_pillar", Material.ROCK).setCreativeTab(ModCreativeTab.IDL_MISC).setHardness(20f);
}

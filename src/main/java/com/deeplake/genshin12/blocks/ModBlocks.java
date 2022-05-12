package com.deeplake.genshin12.blocks;

import com.deeplake.genshin12.blocks.blockMisc.BlockZhongliPillar;
import com.deeplake.genshin12.blocks.blockMisc.BlockZhongliPillarDeco;
import com.deeplake.genshin12.blocks.ore.BlockOreArtifact;
import com.deeplake.genshin12.init.ModCreativeTab;
import com.deeplake.genshin12.item.ModItems;
import com.deeplake.genshin12.util.CommonDef;
import com.deeplake.genshin12.util.NBTStrDef.IDLNBTDef;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;

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


	public static final Block ZL_PILLAR = new BlockZhongliPillar("zl_pillar", Material.ROCK).setCreativeTab(ModCreativeTab.IDL_MISC).setHardness(20f);
	public static final Block ZL_PILLAR_DECO = new BlockZhongliPillarDeco("zl_pillar_deco", Material.ROCK).setCreativeTab(ModCreativeTab.IDL_MISC).setHardness(20f);

	public static final BlockOreBase COR_LAPIS_ORE = (BlockOreBase) new BlockOreBase("cor_lapis_ore").setHardness(10f);
	public static final Block COR_LAPIS_BLOCK =  new BlockOreBase("cor_lapis_block").setHardness(80f);

	public static final BlockOreArtifact ARTIFACT_ORE = (BlockOreArtifact) new BlockOreArtifact("artifact_ore").setHardness(10f).setLightLevel(0.1f);

	public static void init() {
		COR_LAPIS_ORE.setHarvestLevel(IDLNBTDef.TOOL_PICKAXE, 2);
		COR_LAPIS_ORE.setDrop(ModItems.COR_LAPIS);
	}

}

package com.deeplake.genshin12.blocks;

import com.deeplake.genshin12.Idealland;
import com.deeplake.genshin12.init.ModCreativeTab;
import com.deeplake.genshin12.item.ModItems;
import com.deeplake.genshin12.util.IHasModel;
import net.minecraft.block.BlockOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import java.util.Random;

public class BlockOreBase extends BlockOre implements IHasModel {
    Item drop = Items.APPLE;

    public BlockOreBase(String name)
    {
        super();
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(ModCreativeTab.IDL_MISC);

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));

        setHarvestLevel("pickaxe", 2);
        setHardness(5.0F);
        setLightOpacity(1);
    }

    public void setDrop(Item drop) {
        this.drop = drop;
    }

    @Override
    public void registerModels() {
        Idealland.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return drop;
    }
}

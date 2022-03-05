package com.deeplake.genshin12.init;

import com.deeplake.genshin12.entity.creatures.attribute.ModAttributes;
import com.deeplake.genshin12.item.ModItems;
import com.deeplake.genshin12.item.artifact.ArtifactUtil;
import com.deeplake.genshin12.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModCreativeTab {
	public static final CreativeTabs IDL_MISC = new CreativeTabs(CreativeTabs.getNextID(), "genshin12MiscTab")
    {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem()
        {
            return new ItemStack(ModItems.PRIMOGEM);
        }
    };

    public static final CreativeTabs ARTIFACTS = new CreativeTabs(CreativeTabs.getNextID(), "genshin12ArtifactTab")
    {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem()
        {
            ItemStack stack = new ItemStack(ModItems.AR_DEFAULT);
            IDLNBTUtil.SetInt(stack, ArtifactUtil.KEY_LEVEL, 0);
            IDLNBTUtil.SetInt(stack, ArtifactUtil.KEY_MAIN_ATTR, ModAttributes.EnumAttr.HP.id);
            IDLNBTUtil.SetInt(stack, ArtifactUtil.KEY_RARITY, 1);
            IDLNBTUtil.SetInt(stack, ArtifactUtil.KEY_SLOT, 2);

            IDLNBTUtil.SetInt(stack, ArtifactUtil.KEY_READY_ATTR, 0);

            return stack;
        }
    };
}

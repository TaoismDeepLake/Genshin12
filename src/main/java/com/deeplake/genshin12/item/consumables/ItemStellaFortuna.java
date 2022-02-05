package com.deeplake.genshin12.item.consumables;

import com.deeplake.genshin12.IdlFramework;
import com.deeplake.genshin12.item.IHasVariant;
import com.deeplake.genshin12.util.NBTStrDef.IDLNBTDef;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemStellaFortuna extends ItemParcel implements IHasVariant {
    protected int typeCount = 1;
    public ItemStellaFortuna(String name, int count) {
        super(name);
        if (count < 1)
        {
            IdlFramework.LogWarning("Item %s has less than 1 types.", name);
        }
        typeCount = count;
        this.setHasSubtypes(true);
    }

    @Override
    public void registerModels()
    {
        for (int i = 0; i < typeCount; i++)
        {
            IdlFramework.proxy.registerItemRenderer(this, i, IDLNBTDef.NAME_INVENTORY);
        }
    }

    public String getUnlocalizedName(ItemStack stack)
    {
        int i = stack.getMetadata();
        return super.getUnlocalizedName() + "_" + i;
    }

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab))
        {
            for (int i = 0; i < typeCount; ++i)
            {
                items.add(new ItemStack(this, 1, i));
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public String descGetKey(ItemStack stack, World world, boolean showFlavor)
    {
        return showFlavor ? (stack.getUnlocalizedName() + IDLNBTDef.FLAVOR_KEY)
                : String.format("%s%s", getUnlocalizedName(stack), IDLNBTDef.DESC_COMMON);
    }

    @Override
    public boolean give(World worldIn, EntityPlayer playerIn, ItemStack parcel) {
        try {
            EnumCharacter.values()[parcel.getMetadata()].giveSkills(playerIn);
        }
        catch (Exception e)
        {
            IdlFramework.LogWarning(e.toString());
            return false;
        }

        return true;
    }
}

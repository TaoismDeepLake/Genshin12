package com.deeplake.genshin12.item.misc;

import com.deeplake.genshin12.item.ItemBase;
import com.deeplake.genshin12.util.CommonFunctions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemNanoMender extends ItemBase {
    public ItemNanoMender(String name, int maxDmg) {
        super(name);
        setMaxDamage(maxDmg);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);

        if (!worldIn.isRemote)
        {
            EntityPlayer playerIn = (EntityPlayer) entityIn;
            for (EntityEquipmentSlot slot:
                    EntityEquipmentSlot.values()) {

                ItemStack itemstack1 = playerIn.getItemStackFromSlot(slot);
                if (!itemstack1.isEmpty() && itemstack1.isItemDamaged() && !(itemstack1.getItem() instanceof ItemNanoMender)) {
                    //Fix Dura
                    CommonFunctions.repairItem(itemstack1, 1);
                    stack.damageItem(1, playerIn);
                    break;
                }
            }
        }
    }

//    public EnumRarity getRarity(ItemStack stack)
//    {
//        return stack.isItemEnchanted() ? EnumRarity.RARE : EnumRarity.COMMON;
//    }
}

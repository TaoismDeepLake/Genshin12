package com.deeplake.genshin12.util;

import com.deeplake.genshin12.init.ModConfig;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class PlayerUtil {
    public static int FindItemInIvtrGeneralized(EntityPlayer player, Class<? extends Item> itemClass)
    {
        for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
            ItemStack itemstack = player.inventory.getStackInSlot(i);
            {
                //itemClass.getClass();
                if (itemClass.isAssignableFrom(itemstack.getItem().getClass()))
                {
                    return i;
                }
            }
        }
        return -1;
    }

    public static ItemStack FindStackInIvtrGeneralized(EntityPlayer player, Class<? extends Item> itemClass)
    {
        for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
            ItemStack itemstack = player.inventory.getStackInSlot(i);
            {
                //itemClass.getClass();
                if (itemClass.isAssignableFrom(itemstack.getItem().getClass()))
                {
                    return itemstack;
                }
            }
        }
        return ItemStack.EMPTY;
    }

    public static int FindItemInIvtr(EntityPlayer player, Item item)
    {
        for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
            ItemStack itemstack = player.inventory.getStackInSlot(i);
            {
                if (itemstack.getItem() == item)
                {
                    return i;
                }
            }
        }
        return -1;
    }

    public static ItemStack FindStackInIvtr(EntityPlayer player, Item item)
    {
        for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
            ItemStack itemstack = player.inventory.getStackInSlot(i);
            {
                if (itemstack.getItem() == item)
                {
                    return itemstack;
                }
            }
        }
        return ItemStack.EMPTY;
    }

    public static boolean isCreative(EntityPlayer player)
    {
        return player.capabilities.isCreativeMode;
    }

    public static boolean giveToPlayer(EntityPlayer player, ItemStack stack)
    {
        boolean result = player.addItemStackToInventory(stack);
        if (!result)
        {
            player.dropItem(stack, false);
        }
        return result;
    }

    public static void giveDrop(List<EntityItem> stacks, EntityPlayer player, EntityLivingBase livingBase, ItemStack stack) {
        if (ModConfig.GeneralConf.SAFE_DROP)
        {
            giveToPlayer(player, stack);
        }
        else {
            stacks.add(livingBase.entityDropItem(stack, 1f));
        }
    }
}

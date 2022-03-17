package com.deeplake.genshin12.util;

import com.deeplake.genshin12.Idealland;
import com.deeplake.genshin12.init.ModConfig;
import net.minecraft.advancements.Advancement;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.FoodStats;
import net.minecraft.util.ResourceLocation;

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


    public static void TryGrantAchv(EntityPlayer player, String key)
    {
        if (player instanceof EntityPlayerMP)
        {
            EntityPlayerMP playerMP = ((EntityPlayerMP) player);
            Advancement advancement = playerMP.getServerWorld().getAdvancementManager().getAdvancement(new ResourceLocation(Idealland.MODID, key));

            //String achvName = GetAchvName(key);
            //playerMP.getStatFile().unlockAchievement(this.gameController.player, statbase, k);
        }

        //todo
    }

    public static void setCoolDown(EntityPlayer player, EnumHand hand)
    {
        player.getCooldownTracker().setCooldown(player.getHeldItem(hand).getItem(), CommonDef.TICK_PER_SECOND);
    }

    //not intended to decrease
    public static boolean addFoodLevel(EntityPlayer player, int value)
    {
        FoodStats stats = player.getFoodStats();
        if (stats.needFood())
        {
            stats.setFoodLevel(stats.getFoodLevel() + value);
            return true;
        }
        else {
            return false;
        }
    }
}

package com.deeplake.genshin12.item.consumables;

import com.deeplake.genshin12.util.CommonFunctions;
import com.deeplake.genshin12.util.PlayerUtil;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.Random;

public class ItemPackageWeapon extends ItemParcel {
    public ItemPackageWeapon(String name) {
        super(name);
    }

    @Override
    public boolean give(World worldIn, EntityPlayer playerIn, ItemStack parcel) {
        ItemStack stack;
        Random random = playerIn.getRNG();
        int i = random.nextInt(5);
        switch (i)
        {
            case 0:
                stack = new ItemStack(Items.IRON_SWORD);
                break;
            case 1:
                stack = new ItemStack(Items.GOLDEN_SWORD);
                break;
            case 2:
                stack = new ItemStack(Items.IRON_PICKAXE);
                break;
            case 3:
                stack = new ItemStack(Items.DIAMOND_SWORD);
                break;
            case 4:
                stack = new ItemStack(Items.DIAMOND_PICKAXE);
                break;

            default:
                stack = new ItemStack(Items.IRON_AXE);
        }
        EnchantmentHelper.addRandomEnchantment(random, stack, random.nextInt(30), false);
        PlayerUtil.giveToPlayer(playerIn, stack);
        return true;
    }
}

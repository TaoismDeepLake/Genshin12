package com.deeplake.genshin12.item.consumables;

import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.item.ModItems;
import com.deeplake.genshin12.util.PlayerUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.Random;

public class ItemInterwinedFate extends ItemParcel {
    public ItemInterwinedFate(String name) {
        super(name);
    }

    @Override
    public boolean give(World worldIn, EntityPlayer playerIn, ItemStack parcel) {
        Random random = playerIn.getRNG();
        if (random.nextFloat() < ModConfig.GACHA_CONF.CHARA_CHANCE)
        {
            PlayerUtil.giveToPlayer(playerIn,
                    new ItemStack(ModItems.STELLA_FORTUNA, 1,
                            playerIn.getRNG().nextInt(ModItems.STELLA_FORTUNA.getTypeCount() + 1)));
        }
        else {
            PlayerUtil.giveToPlayer(playerIn, new ItemStack(ModItems.WEAPON_PACK));
        }

        return false;
    }

}

package com.deeplake.genshin12.item.consumables;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemInterwinedFate extends ItemConsumableBase {
    public ItemInterwinedFate(String name) {
        super(name);
    }

    @Override
    public ActionResult<ItemStack> onConsume(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        return null;
    }
}

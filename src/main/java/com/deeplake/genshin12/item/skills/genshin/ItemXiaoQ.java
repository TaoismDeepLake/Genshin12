package com.deeplake.genshin12.item.skills.genshin;

import com.deeplake.genshin12.item.IWIP;
import com.deeplake.genshin12.potion.ModPotions;
import com.deeplake.genshin12.util.CommonDef;
import com.deeplake.genshin12.util.EnumAmount;
import com.deeplake.genshin12.util.EnumElemental;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemXiaoQ extends ItemGenshinBurstBase {
    public ItemXiaoQ(String name) {
        super(name, 70, EnumElemental.ANEMO);
        setAmount(EnumAmount.SMALL);
        setCD(18f, 0f);
        setMaxLevel(14);
    }

    @Override
    public boolean applyCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot) {
        if (!worldIn.isRemote)
        {
            livingBase.addPotionEffect(new PotionEffect(ModPotions.YAKSHA_MASK, 15 * CommonDef.TICK_PER_SECOND, getLevel(stack)));
        }
        return super.applyCast(worldIn, livingBase, stack, slot);
    }
}

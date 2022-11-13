package com.deeplake.genshin12.item.skills.genshin;

import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.potion.ModPotions;
import com.deeplake.genshin12.util.EntityUtil;
import com.deeplake.genshin12.util.EnumElemental;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBarbaraE extends ItemGenshinSkillBase{
     public ItemBarbaraE(String name) {
        super(name, EnumElemental.HYDRO);
        setCD(32f, 0f);
        setMaxLevel(13);
        setDura(15,0);
    }

    @Override
    public boolean applyCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot) {
        int level = getLevel(stack);
        EntityUtil.ApplyBuff(livingBase, ModPotions.BUFF_BARBARA, level-1, 15f);
        return super.applyCast(worldIn, livingBase, stack, slot);
    }
}

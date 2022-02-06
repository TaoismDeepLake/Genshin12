package com.deeplake.genshin12.item.skills.genshin;

import com.deeplake.genshin12.entity.EntityGlacialWaltz;
import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.util.EnumAmount;
import com.deeplake.genshin12.util.EnumElemental;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemKaeyaQ extends ItemGenshinBurstBase {

    public ItemKaeyaQ(String name) {
        super(name, 60, EnumElemental.CYRO);
        setCD(15f,0f);
        initDamageRatio = new float[]{77.6f, 83.42f, 89.24f, 97f, 102.82f, 108.64f, 116.4f, 124.16f, 131.92f, 139.68f, 147.44f, 155.2f, 164.9f, 175};
        setAmount(EnumAmount.SMALL);
    }

    @Override
    public boolean applyCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot) {
        if (!worldIn.isRemote)
        {
            EntityGlacialWaltz waltz = new EntityGlacialWaltz(worldIn);
            waltz.setOwner(livingBase);
            waltz.setDamage(getInitDamage(getLevel(stack)) * ModConfig.GeneralConf.DMG_ATK_PERCENT_GENSHIN_TO_MC);
            worldIn.spawnEntity(waltz);
        }
        return super.applyCast(worldIn, livingBase, stack, slot);
    }
}

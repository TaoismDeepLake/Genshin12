package com.deeplake.genshin12.util;

import com.deeplake.genshin12.item.ModItems;
import com.deeplake.genshin12.item.skills.genshin.ItemGenshinSkillBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

import static com.deeplake.genshin12.item.ModItems.XIAO_Q;

public class GenshinUtil {
    public static boolean isGenshin(EntityLivingBase livingBase)
    {
        return livingBase != null && (isGenshin(livingBase.getHeldItemOffhand()) || isGenshin(livingBase.getHeldItemMainhand()));
    }

    public static boolean isGenshin(ItemStack stack)
    {
        Item item = stack.getItem();
        if (item instanceof ItemGenshinSkillBase)
        {
            return true;
        }

        //weapon goes here

        return false;
    }

    public static boolean isXiao(EntityLivingBase livingBase)
    {
        return livingBase != null && (isXiao(livingBase.getHeldItemOffhand()) || isXiao(livingBase.getHeldItemMainhand()));
    }

    public static boolean isXiao(ItemStack stack)
    {
        Item item = stack.getItem();
        return item == ModItems.XIAO_E || item == XIAO_Q;
    }

    //damage is final value
    //returns wether it hits a target.
    public static boolean dealAoEDamage(Vec3d pos, EntityLivingBase caster, float damage, float aoeRange, EnumElemental elemental, EnumAmount amount)
    {
        List<EntityLivingBase> list = EntityUtil.getEntitiesWithinAABB(caster.getEntityWorld(), EntityLiving.class, pos,aoeRange, null);

        boolean onHit = false;
        if (caster instanceof EntityPlayer)
        {
            for (EntityLivingBase target :
                    list) {
                ElementalUtil.applyElementalDamage((EntityPlayer) caster, target, damage, elemental, amount);
                onHit = true;
            }
        }

        return onHit;
    }

    public static boolean dealAoEDamagePhysical(Vec3d pos, EntityLivingBase caster, float damage, float aoeRange)
    {
        return dealAoEDamage(pos, caster, damage, aoeRange, EnumElemental.PHYSICAL, EnumAmount.NONE);
    }

    public static boolean dealAoEKnockBack(Vec3d pos, EntityLivingBase caster, float power, float aoeRange)
    {
        List<EntityLivingBase> list = EntityUtil.getEntitiesWithinAABB(caster.getEntityWorld(), EntityLiving.class, pos,aoeRange, null);

        boolean onHit = false;
        if (caster instanceof EntityPlayer)
        {
            for (EntityLivingBase target :
                    list) {
                EntityUtil.simpleKnockBack(power, caster, target);
                onHit = true;
            }
        }

        return onHit;
    }
}

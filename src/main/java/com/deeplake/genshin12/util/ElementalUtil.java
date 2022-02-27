package com.deeplake.genshin12.util;

import com.deeplake.genshin12.entity.creatures.attribute.HandleResistance;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

public class ElementalUtil {

    public static float getAmountFromBuff(PotionEffect effect)
    {
        if (effect == null)
        {
            return 0f;
        }
        switch (effect.getAmplifier())
        {
            case 0:
                return effect.getDuration() / EnumAmount.SMALL.decayRate;
            case 1:
                return effect.getDuration() / EnumAmount.MEDIUM.decayRate;
            case 2:
                return effect.getDuration() / EnumAmount.LARGE.decayRate;
            default:
                return 0f;//this should never happen
        }

    }

    public static float getElementalAmount(EntityLivingBase target, EnumElemental elemental)
    {
        PotionEffect effect = null;
        switch (elemental)
        {
            case PHYSICAL:
                break;
            case ANEMO:
            case GEO:
            case ELECTRO:
            case DENDRO:
            case HYDRO:
            case PYRO:
            case CYRO:
            case CHRONO:
                effect = target.getActivePotionEffect(elemental.potion);
                break;
        }

        if (effect != null)
        {
            return getAmountFromBuff(effect);
        }

        return 0;
    }

    public static void applyElementalDamage(EntityLivingBase player, EntityLivingBase target, double damage, EnumElemental elemental, EnumAmount amount)
    {
        applyElementalDamage(player, target, (float) damage, elemental, amount);
    }

    public static void applyElementalDamage(EntityLivingBase player, EntityLivingBase target, float damage, EnumElemental elemental, EnumAmount amount)
    {
        applyElemental(target, damage, elemental, amount);

        DamageSource source = player instanceof EntityPlayer ? DamageSource.causePlayerDamage((EntityPlayer) player) : DamageSource.causeMobDamage(player);
        if (elemental == EnumElemental.PYRO)
        {
            source.setFireDamage();
            source.setMagicDamage();
        }
        else if (elemental != EnumElemental.PHYSICAL)
        {
            source.setMagicDamage();
        }

        //physical damage is handled elsewhere
        if (elemental != EnumElemental.PHYSICAL)
        {
            damage *= HandleResistance.handleResistance(player, target, elemental);
        }

        target.attackEntityFrom(
                source,
                damage);
    }

    public static void applyElemental(EntityLivingBase target, float damage, EnumElemental elemental, EnumAmount amount)
    {
        if (target.getEntityWorld().isRemote)
        {
            return;
        }

        if (amount != EnumAmount.NONE)
        {
            switch (elemental)
            {
                case PHYSICAL:
                    break;
                case ANEMO:
                case GEO:
                    //trigger at once, don't last
                    break;

                case ELECTRO:
                case DENDRO:
                case HYDRO:
                case PYRO:
                case CYRO:
                    target.addPotionEffect(amount.getPotionEffect(elemental));
                    break;

                case CHRONO:
                    //??
                    break;
            }
        }
    }
}

package com.deeplake.genshin12.util;

import com.deeplake.genshin12.IdlFramework;
import com.deeplake.genshin12.designs.ElemTuple;
import com.deeplake.genshin12.designs.ReactionResult;
import com.deeplake.genshin12.entity.creatures.attribute.HandleResistance;
import com.deeplake.genshin12.init.ModConfig;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
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
        ReactionResult reactionResult = applyElemental(target, elemental, amount);

        if (ModConfig.DEBUG_CONF.DEBUG_MODE)
        {
            IdlFramework.Log("ElemDamage:(%s,%s), %s to %s",elemental,amount,player,target);
        }

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

        if (ModConfig.DEBUG_CONF.DEBUG_MODE)
        {
            IdlFramework.Log("Reaction: %s", reactionResult);
        }

        switch (reactionResult.reaction)
        {
            case NONE:
                break;
            case MELT_S:
            case MELT_W:
            case VAPORIZE_S:
            case VAPORIZE_W:
                damage *= reactionResult.factor;
                break;
            case OVERLOAD:
                break;
            case SUPERCONDUCT:
                break;
            case CRYSTALLIZE:
                break;
            case SWIRL:
                break;
            case ELECTRO_CHARGED:
                break;
            case FROZEN:
                break;
            case SHATTER:
                break;
        }

        target.attackEntityFrom(
                source,
                damage);
    }

    public static ReactionResult applyElemental(EntityLivingBase target, EnumElemental elemental, EnumAmount amount)
    {
        if (target.getEntityWorld().isRemote)
        {
            return ReactionResult.NONE;
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
                    ReactionResult reactionResult =
                            ElemTuple.reactionResult(new ElemTuple(target), new ElemTuple(elemental, amount)
                                    , EnumAmount.level(target), amount.level);

                    if (reactionResult.reaction != ElemTuple.EnumReaction.NONE)
                    {
                        updateGivenElement(target, reactionResult.enumElemental, reactionResult.amount, reactionResult.level);
                        updateGivenElement(target, reactionResult.enumElemental2, reactionResult.amount2, reactionResult.level2);
                    }
                    else {
                        //no reaction, just apply it
//                        ElemTuple.reactionResult(new ElemTuple(target), new ElemTuple(elemental, amount)
//                                , EnumAmount.level(target), amount.level);


//                        if (elemental != EnumElemental.PHYSICAL)
//                        {
                            PotionEffect effect = amount.getPotionEffect(elemental);
                            if (effect != null)
                            {
                                target.removePotionEffect(effect.getPotion());
                                target.addPotionEffect(effect);
                            }
//                        }
                    }

                    return reactionResult;

                case CHRONO:
                    //??
                    break;
            }
        }
        return ReactionResult.NONE;
    }

    public static void updateGivenElement(EntityLivingBase target, EnumElemental elemental, double amount, int level)
    {
        Potion potion = elemental.getPotion();
        if (potion != null)
        {
            if (target.isPotionActive(potion))
            {
                target.removePotionEffect(potion);
            }

            if (amount > 0)
            {
                PotionEffect effect = EnumAmount.getPotionEffect(elemental, EnumAmount.getTicks(amount, level), level);
                if (effect != null)
                {
                    target.addPotionEffect(effect);
                }
            }
        }
    }

    public static PotionEffect getPotionEffect1ByElemStatus(ReactionResult reactionResult) {
        return EnumAmount.getPotionEffect(reactionResult.enumElemental, EnumAmount.getTicks(reactionResult.amount, reactionResult.level), reactionResult.level);
    }
}

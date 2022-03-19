package com.deeplake.genshin12.util;

import com.deeplake.genshin12.Idealland;
import com.deeplake.genshin12.designs.ElemTuple;
import com.deeplake.genshin12.designs.ReactionResult;
import com.deeplake.genshin12.entity.creatures.attribute.HandleResistance;
import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.potion.ModPotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
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

    public static boolean applyElementalDamage(EntityLivingBase attacker, EntityLivingBase target, double damage, EnumElemental elemental, EnumAmount amount)
    {
        return applyElementalDamage(attacker, target, (float) damage, elemental, amount);
    }

    public static boolean applyElementalDamage(EntityLivingBase attacker, EntityLivingBase target, float damage, EnumElemental elemental, EnumAmount amount)
    {
        boolean result = false;

        ReactionResult reactionResult = applyElemental(target, elemental, amount);

        //todo: handle immnune

        if (ModConfig.DEBUG_CONF.DEBUG_MODE)
        {
            Idealland.Log("ElemDamage:(%s,%s), %s to %s",elemental,amount,attacker,target);
        }

        DamageSource source = attacker instanceof EntityPlayer ? DamageSource.causePlayerDamage((EntityPlayer) attacker) : DamageSource.causeMobDamage(attacker);
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
            damage *= HandleResistance.handleResistance(attacker, target, elemental);
        }

        if (ModConfig.DEBUG_CONF.DEBUG_MODE)
        {
            Idealland.Log("Reaction: %s", reactionResult);
        }

        switch (reactionResult.reaction)
        {
            case NONE:
                break;
            case MELT_S:
                damage *= ModConfig.ELEMCONF.DAMAGE_STRONG_MELT;
                break;
            case MELT_W:
                damage *= ModConfig.ELEMCONF.DAMAGE_WEAK_MELT;
                break;
            case VAPORIZE_S:
                damage *= ModConfig.ELEMCONF.DAMAGE_STRONG_VAPORIZE;
                break;
            case VAPORIZE_W:
                damage *= ModConfig.ELEMCONF.DAMAGE_WEAK_VAPORIZE;
                break;
            case OVERLOAD:
                target.getEntityWorld().createExplosion(attacker, target.posX, target.posY, target.posZ, 1f, ModConfig.ELEMCONF.OVERLOAD_EXPLOSION_GRIEF);
                break;
            case SUPERCONDUCT:
                //todo: AOE
//                Superconduct is the Elemental Reaction triggered by inflicting Electro on a target that is already affected by Cryo or vice versa. This reaction deals AoE Cryo DMG and reduces the physical resistance of all enemies in the AoE by 40% for 12 seconds.
//                    Superconduct can also occur in the environment when dealing Electro to water that was previously frozen by Cryo, shortening the frozen duration and causing the water underneath to be Electro-Charged. However, no AoE damage is dealt, nor does the defense of any nearby enemies decrease. Superconduct will not occur when dealing Cryo to Electro-Charged water. However, using Cryo on an Electro Crystal or Electro on a Mist Flower will also cause Superconduct and deal DMG to nearby enemies.
//
//                    Unlike Swirl, Superconduct cannot trigger further elemental reactions as it does not apply Cryo to targets hit.

                EntityUtil.ApplyBuff(target, ModPotions.SUPER_CONDUCT, 0, (float) ModConfig.ELEMCONF.SUPERCONDUCT_DURA);
                break;
            case CRYSTALLIZE:
                //todo: temp
                EntityUtil.ApplyBuff(target, MobEffects.RESISTANCE, 2, amount.defaultDura);
                break;
            case SWIRL:
//                Swirl is the Elemental Reaction triggered by inflicting Anemo on a target that is already affected by Pyro, Electro, Hydro, or Cryo. Swirl can also be triggered in the other direction by inflicting Pyro, Electro, Hydro, or Cryo to a target that is already affected by Anemo, but aside from enemies that apply long-lasting Anemo to themselves, Anemo applications do not linger, so Anemo is the only element that can trigger Swirl most of the time. This reaction deals elemental DMG of the non-Anemo element involved.
//                Unlike other elemental reactions, Swirl can spread elements (specifically, the non-Anemo element involved in the reaction) to nearby targets, usually in the direction of the Swirl reaction.
//
//                    Swirl damage should not be confused with the non-Anemo damage that comes from Elemental Absorption. Swirl damage is fixed damage based only on character level and elemental mastery, while additional elemental damage from Elemental Absorption is regular damage and is thus affected by the stats that normally affect damage, including attack, critical hit rate and damage, elemental damage bonuses, and the level of the talent with the potential for Elemental Absorption.
                break;
            case ELECTRO_CHARGED:
                //todo: temp
                EntityUtil.ApplyBuff(target, MobEffects.WITHER, 0, amount.defaultDura);
                break;
            case FROZEN:
                //todo: temporal duration
                EntityUtil.ApplyBuff(target, ModPotions.FREEZE, 0, amount.defaultDura);
                break;
            case SHATTER:
                //todo: deal damage and break ice.
                break;
        }

        return target.attackEntityFrom(
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

    //https://genshin-impact.fandom.com/wiki/Shields/Enemy
    public static void applyElemShieldMonster(EntityLivingBase target, EnumElemental elem, float amount)
    {
        float expectedAbsorption = target.getMaxHealth() * amount / ModConfig.DEBUG_CONF.ENEMY_SHIELD_DAMAGE_RATIO;
        if (target.getAbsorptionAmount() < expectedAbsorption)
        {
            target.setAbsorptionAmount(expectedAbsorption);
            //todo: elem buff that marks the shield type.
        }
    }

}

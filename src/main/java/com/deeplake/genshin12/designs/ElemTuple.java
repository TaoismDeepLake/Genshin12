package com.deeplake.genshin12.designs;

import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.util.CommonFunctions;
import com.deeplake.genshin12.util.EnumAmount;
import com.deeplake.genshin12.util.EnumElemental;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class ElemTuple {
    final EnumElemental enumElemental;
    final double amount;

    public static ElemTuple ZERO = new ElemTuple(EnumElemental.PHYSICAL, 0);

    public ElemTuple(EnumElemental enumElemental, double amount) {
        this.enumElemental = enumElemental;
        this.amount = amount;
    }

    public ElemTuple(EnumElemental enumElemental, EnumAmount amount) {
        this.enumElemental = enumElemental;
        this.amount = amount.gauge;
    }

    public ElemTuple(EntityLivingBase livingBase) {
        for (EnumElemental elem :
                EnumElemental.values()) {
            Potion potion = elem.getPotion();
            if (potion != null && livingBase.isPotionActive(potion))
            {
                PotionEffect effect = livingBase.getActivePotionEffect(potion);
                int level = effect.getAmplifier();
                level = CommonFunctions.clamp(level, 0, EnumAmount.MAX_LEVEL);
                this.enumElemental = elem;
                this.amount = EnumAmount.getValue(effect.getDuration(), level);
                return;
            }
        }

        this.enumElemental = EnumElemental.PHYSICAL;
        this.amount = EnumAmount.NONE.gauge;
    }

    enum EnumReactionRule{
        NONE,
        NORMAL,//Melt, Vaporize, Overload, Superconduct, Crystallize, and Swirl
        ELEC_CHARGED,
        FORZEN_SHATTER;
    }

    public enum EnumReaction{
        NONE,
        MELT_S(ModConfig.ELEMCONF.R_STRONG_MELT),
        MELT_W(ModConfig.ELEMCONF.R_WEAK_MELT),
        VAPORIZE_S(ModConfig.ELEMCONF.R_STRONG_VAPORIZE),
        VAPORIZE_W(ModConfig.ELEMCONF.R_WEAK_VAPORIZE),//cold to hot
        OVERLOAD(ModConfig.ELEMCONF.R_OVERLOAD),
        SUPERCONDUCT(ModConfig.ELEMCONF.R_SUPERCONDUCT),
        CRYSTALLIZE(ModConfig.ELEMCONF.R_CRYTALLIZE),
        SWIRL(ModConfig.ELEMCONF.R_SWIRL),
        ELECTRO_CHARGED(ModConfig.ELEMCONF.R_SHOCK),
        FROZEN,
        SHATTER;

        double factor = 0;

        EnumReaction(double factor) {
            this.factor = factor;
        }

        EnumReaction() {
        }
    }

    public static ReactionResult normalCalculation(ElemTuple aura, ElemTuple apply, EnumReaction reaction, int lv1, int lv2)
    {
        double resultAura = aura.amount;

        resultAura -= reaction.factor * apply.amount;

        //todo: this is different from R
        float dmgFactor = 1f;

        if (resultAura <= 0)
        {
            return new ReactionResult(new ElemTuple(aura.enumElemental, 0), new ElemTuple(apply.enumElemental, 0), reaction, reaction.factor, lv1,lv2);
            //return new ElemTuple(aura.enumElemental, resultAura);
        }
        else {
            return new ReactionResult(new ElemTuple(aura.enumElemental, resultAura), new ElemTuple(apply.enumElemental, 0), reaction, reaction.factor, lv1,lv2);
        }
    }

    public static ReactionResult shockCalculation(ElemTuple aura, ElemTuple apply,  int lv1, int lv2)
    {
        double resultAura = aura.amount;

        resultAura -= aura.amount - EnumReaction.ELECTRO_CHARGED.factor * apply.amount;
        if (resultAura <= 0)
        {
            return new ReactionResult(ElemTuple.ZERO, apply, EnumReaction.ELECTRO_CHARGED, 0, lv1,lv2);
        }
        else {
            return new ReactionResult(new ElemTuple(aura.enumElemental, resultAura), apply,  EnumReaction.ELECTRO_CHARGED, 0, lv1,lv2);
        }
    }

    static float SHOCK_THRESHOLD = 0.4f;

    //units until either element's gauge value reaches 0.
    //The target is inflicted by both Electro and Hydro auras at the same time for the duration of Electro-Charged thus reactions involving these elements are still possible during its duration (i.e. a Pyro attack can trigger both Vaporize and Overload simultaneously on an Electro-Charged target).
    public static ReactionResult reactionResult(ElemTuple aura, ElemTuple apply, int lv1, int lv2)
    {
        double resultAura = aura.amount;
        double reactionFactor = 1f;
        if (aura.enumElemental == apply.enumElemental || aura.enumElemental == EnumElemental.PHYSICAL)
        {
            //todo: same element repeat apply
            return ReactionResult.NONE;
        }

        EnumReactionRule style = EnumReactionRule.NONE;
        switch (apply.enumElemental)
        {
            case CHRONO:
            case PHYSICAL:
                break;
            case ANEMO:
                if (aura.enumElemental == EnumElemental.GEO)
                {
                    return ReactionResult.NONE;
                }
                return normalCalculation(aura,apply,EnumReaction.SWIRL, lv1,lv2);
            case GEO:
                if (aura.enumElemental == EnumElemental.ANEMO)
                {
                    return ReactionResult.NONE;
                }
                return normalCalculation(aura,apply,EnumReaction.CRYSTALLIZE, lv1,lv2);
            case ELECTRO:
                switch (aura.enumElemental)
                {
                    case PHYSICAL:
                    case CHRONO:
                    case ANEMO:
                    case GEO:
                        //todo: normally won't
                        break;
                    case DENDRO:
                        //??
                        break;
                    case HYDRO:
                        //todo: incorrect
                        return shockCalculation(aura, apply, lv1,lv2);
                    case PYRO:
                        normalCalculation(aura, apply, EnumReaction.OVERLOAD, lv1,lv2);
                    case CYRO:
                        normalCalculation(aura, apply, EnumReaction.SUPERCONDUCT, lv1,lv2);
                }
                break;
            case DENDRO:
                break;
            case HYDRO:
                switch (aura.enumElemental) {
                    case PHYSICAL:
                    case CHRONO:
                    case ANEMO:
                    case GEO:
                        //todo: normally won't
                        break;
                    case ELECTRO:
                        if (aura.amount >= SHOCK_THRESHOLD && apply.amount >= SHOCK_THRESHOLD)
                        {
                            //todo: each reduce 0.4
                        }
                        break;
                    case DENDRO:
                        break;
                    case PYRO:
                        return normalCalculation(aura,apply,EnumReaction.VAPORIZE_S, lv1,lv2);
                    case CYRO:
                        break;
                }
                break;
            case PYRO:
                switch (aura.enumElemental) {
                    case PHYSICAL:
                    case CHRONO:
                    case ANEMO:
                    case GEO:
                        //todo: normally won't
                        break;
                    case ELECTRO:
                        return normalCalculation(aura,apply,EnumReaction.OVERLOAD, lv1,lv2);
                    case DENDRO:
                        //burn
                        break;
                    case HYDRO:
                        return normalCalculation(aura,apply,EnumReaction.VAPORIZE_W, lv1,lv2);
                    case CYRO:
                        return normalCalculation(aura,apply,EnumReaction.MELT_S, lv1,lv2);
                }
                break;
            case CYRO:
                switch (aura.enumElemental)
                {
                    case PHYSICAL:
                    case CHRONO:
                    case ANEMO:
                    case GEO:
                        //todo: normally won't
                        break;
                    case ELECTRO:
                        return normalCalculation(aura,apply,EnumReaction.SUPERCONDUCT, lv1,lv2);
                    case DENDRO:
                        break;
                    case HYDRO:
                        break;
                    case PYRO:
                        return normalCalculation(aura,apply,EnumReaction.MELT_W, lv1,lv2);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + apply.enumElemental);
        }

        return ReactionResult.NONE;
    }
}

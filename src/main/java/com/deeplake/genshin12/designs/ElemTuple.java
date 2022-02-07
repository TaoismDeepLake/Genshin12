package com.deeplake.genshin12.designs;

import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.util.EnumAmount;
import com.deeplake.genshin12.util.EnumElemental;

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

    public static ReactionResult normalCalculation(ElemTuple aura, ElemTuple apply, EnumReaction reaction)
    {
        double resultAura = aura.amount;

        resultAura -= aura.amount - reaction.factor * apply.amount;
        if (resultAura <= 0)
        {
            return new ReactionResult(ElemTuple.ZERO, reaction, 0);
            //return new ElemTuple(aura.enumElemental, resultAura);
        }
        else {
            return new ReactionResult(new ElemTuple(aura.enumElemental, resultAura), reaction, 0);
        }
    }

    public static ReactionResult shockCalculation(ElemTuple aura, ElemTuple apply)
    {
        double resultAura = aura.amount;

        resultAura -= aura.amount - EnumReaction.ELECTRO_CHARGED.factor * apply.amount;
        if (resultAura <= 0)
        {
            return new ReactionResult(ElemTuple.ZERO, apply, EnumReaction.ELECTRO_CHARGED, 0);
        }
        else {
            return new ReactionResult(new ElemTuple(aura.enumElemental, resultAura), apply,  EnumReaction.ELECTRO_CHARGED, 0);
        }
    }

    //units until either element's gauge value reaches 0.
    //The target is inflicted by both Electro and Hydro auras at the same time for the duration of Electro-Charged thus reactions involving these elements are still possible during its duration (i.e. a Pyro attack can trigger both Vaporize and Overload simultaneously on an Electro-Charged target).
    public static ReactionResult reactionResult(ElemTuple aura, ElemTuple apply)
    {
        double resultAura = aura.amount;
        double reactionFactor = 1f;
        if (aura.enumElemental == apply.enumElemental)
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
                return normalCalculation(aura,apply,EnumReaction.SWIRL);
            case GEO:
                if (aura.enumElemental == EnumElemental.ANEMO)
                {
                    return ReactionResult.NONE;
                }
                return normalCalculation(aura,apply,EnumReaction.CRYSTALLIZE);
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
                        return shockCalculation(aura, apply);
                    case PYRO:
                        normalCalculation(aura, apply, EnumReaction.OVERLOAD);
                    case CYRO:
                        normalCalculation(aura, apply, EnumReaction.SUPERCONDUCT);
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
                        break;
                    case DENDRO:
                        break;
                    case PYRO:
                        return normalCalculation(aura,apply,EnumReaction.VAPORIZE_W);
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
                        return normalCalculation(aura,apply,EnumReaction.OVERLOAD);
                    case DENDRO:
                        //burn
                        break;
                    case HYDRO:
                        return normalCalculation(aura,apply,EnumReaction.VAPORIZE_S);
                    case CYRO:
                        return normalCalculation(aura,apply,EnumReaction.MELT_S);
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
                        return normalCalculation(aura,apply,EnumReaction.SUPERCONDUCT);
                    case DENDRO:
                        break;
                    case HYDRO:
                        break;
                    case PYRO:
                        return normalCalculation(aura,apply,EnumReaction.MELT_S);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + apply.enumElemental);
        }

        return ReactionResult.NONE;
    }
}

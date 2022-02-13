package com.deeplake.genshin12.entity.creatures.attribute;

import com.deeplake.genshin12.IdlFramework;
import com.deeplake.genshin12.util.EnumElemental;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;

public class ModAttributes {
    static final double MIN = -9999999;
    static final double MAX = 999999f;

    //Elemental Mastery
    public static final IAttribute DEFENSE = getNewAttr( "defense");
    //reduct = DEF / (Def + 5x Lv Atk + 500)

    //Elemental Mastery
    public static final IAttribute ELEM_MASTERY = getNewAttr("elem_mastery");

    //Critical Rate
    public static final IAttribute CRIT_RATE = getNewAttr("crit_rate");

    //Critical Damage
    public static final IAttribute CRIT_DMG = new RangedAttribute(null, getAttrName("elem_mastery"), 0, MIN, MAX).setShouldWatch(false);// get 1+damage

    //Healing Bonus
    public static final IAttribute HEAL_BONUS = getNewAttr("heal_bonus");// get 1+damage

    //Incoming Healing Bonus
    public static final IAttribute IN_HEAL_BONUS = getNewAttr("in_heal_bonus");// get 1+damage

    //Energy Recharge
    public static final IAttribute ENERGY_RECHARGE = new RangedAttribute(null, getAttrName("e_recharge"), 1, MIN, MAX).setShouldWatch(false);

    //Cooldown Reduction
    public static final IAttribute CD_REDUCT = getNewAttr("cd_reduct");

    //Shield Strength
    public static final IAttribute SHIELD_STR = getNewAttr("shield_str");

    //Damage Reduction
    public static final IAttribute DMG_REDUCT = getNewAttr("dmg_reduct");

    public static IAttribute getNewAttr(String name)
    {
        return new RangedAttribute(null, getAttrName(name), 0, MIN, MAX).setShouldWatch(false);
    }


    //Elemental
    //-Damage Bonus
    //-Resistance
    static final IAttribute[] ELEM_DMG_BONUS = new IAttribute[EnumElemental.values().length];
    static final IAttribute[] ELEM_RES = new IAttribute[EnumElemental.values().length];

    static final String NAME_DMG_BONUS = "_bonus";
    static final String NAME_DMG_RES = "_res";

    static {
        int count = EnumElemental.values().length;
        for (int i = 0; i < count; i++)
        {
            ELEM_DMG_BONUS[i] = getNewAttr(EnumElemental.values()[i].name()+NAME_DMG_BONUS);
            ELEM_RES[i] = getNewAttr(EnumElemental.values()[i].name()+NAME_DMG_RES);
        }
    }

    public static IAttribute getElemBonus(EnumElemental elemental)
    {
        return ELEM_DMG_BONUS[elemental.ordinal()];
    }


    public static IAttribute getElemRes(EnumElemental elemental)
    {
        return ELEM_RES[elemental.ordinal()];
    }

    static final String MID_NAME = ".attr.";
    static String getAttrName(String name){
        return IdlFramework.MODID + MID_NAME + name;
    }
}

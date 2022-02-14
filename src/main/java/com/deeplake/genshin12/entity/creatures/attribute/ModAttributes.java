package com.deeplake.genshin12.entity.creatures.attribute;

import com.deeplake.genshin12.IdlFramework;
import com.deeplake.genshin12.util.EnumElemental;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

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


    static Dictionary<EnumElemental, EnumAttr> resDict = new Hashtable<>();
    static Dictionary<EnumElemental, EnumAttr> dmgDict = new Hashtable<>();

    public EnumAttr getResistance(EnumElemental elemental)
    {
        return resDict.get(elemental);
    }

    public EnumAttr getDamage(EnumElemental elemental)
    {
        return dmgDict.get(elemental);
    }

    static final int BASE_1 = 1000;
    static final int BASE_2 = 2000;
    static final int BASE_3 = 3000;

    public enum EnumAttr{
        NONE(SharedMonsterAttributes.FLYING_SPEED, 0),

        HP(SharedMonsterAttributes.MAX_HEALTH, 1),
        DEF(ModAttributes.DEFENSE, 2),
        ATK(SharedMonsterAttributes.ATTACK_DAMAGE, 3),

        ELEM_MASTERY(ModAttributes.ELEM_MASTERY, 4),
        RECHARGE(ModAttributes.ENERGY_RECHARGE, 5),
        CRIT(ModAttributes.CRIT_RATE, 6),
        CRIT_DMG(ModAttributes.CRIT_DMG, 7),
        HEAL(ModAttributes.HEAL_BONUS, 8),


        HP_P(SharedMonsterAttributes.MAX_HEALTH, BASE_1+HP.id, 1),
        DEF_P(ModAttributes.DEFENSE, BASE_1+DEF.id, 1),
        ATK_P(SharedMonsterAttributes.ATTACK_DAMAGE, BASE_1+ATK.id, 1),

        PHYSICAL(EnumElemental.PHYSICAL, false),
        ANEMO(EnumElemental.ANEMO, false),
        GEO(EnumElemental.GEO, false),
        ELECTRO(EnumElemental.ELECTRO, false),
        DENDRO(EnumElemental.DENDRO, false),
        HYDRO(EnumElemental.HYDRO, false),
        PYRO(EnumElemental.PYRO, false),
        CYRO(EnumElemental.CYRO, false),
        CHRONO(EnumElemental.CHRONO, false),

        RES_PHYSICAL(EnumElemental.PHYSICAL, true),
        RES_ANEMO(EnumElemental.ANEMO, true),
        RES_GEO(EnumElemental.GEO, true),
        RES_ELECTRO(EnumElemental.ELECTRO, true),
        RES_DENDRO(EnumElemental.DENDRO, true),
        RES_HYDRO(EnumElemental.HYDRO, true),
        RES_PYRO(EnumElemental.PYRO, true),
        RES_CYRO(EnumElemental.CYRO, true),
        RES_CHRONO(EnumElemental.CHRONO, true),
        ;

        IAttribute attr;
        int id;
        int type;


        EnumAttr(IAttribute attr, int id) {
            this.attr = attr;
            this.id = id;
            this.type = 0;
        }

        EnumAttr(EnumElemental elemental, boolean resitance) {
            this.attr = resitance ? getElemRes(elemental) : getElemBonus(elemental);
            this.id = elemental.ordinal() + (resitance ? BASE_2 : BASE_3);
            this.type = 0;
            if (resitance)
            {
                resDict.put(elemental, this);
            }
            else {
                dmgDict.put(elemental, this);
            }
        }

        EnumAttr(IAttribute attr, int id, int type) {
            this.attr = attr;
            this.id = id;
            this.type = type;
        }
    }
}

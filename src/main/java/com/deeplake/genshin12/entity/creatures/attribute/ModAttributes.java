package com.deeplake.genshin12.entity.creatures.attribute;

import com.deeplake.genshin12.Idealland;
import com.deeplake.genshin12.util.EnumElemental;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.*;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class ModAttributes {
    static final double MIN = -9999999;
    static final double MAX = 999999f;
    public static float BASE_CRIT_DMG = 50;

    public static final HashSet<IAttribute> allNewAttrs = new HashSet<>();

    public static final IAttribute GEN_ATK = getNewAttrNonpercent( "genshin_atk");

    public static final IAttribute DEFENSE = getNewAttrNonpercent( "defense");
    //reduct = DEF / (Def + 5x Lv Atk + 500)

    //Elemental Mastery
    public static final IAttribute ELEM_MASTERY = getNewAttrNonpercent("elem_mastery");

    //Critical Rate
    public static final IAttribute CRIT_RATE = getNewAttr("crit_rate");

    //Critical Damage
    public static final IAttribute CRIT_DMG = new RangedAttribute(null, getAttrName("crit_dmg"), BASE_CRIT_DMG, MIN, MAX).setShouldWatch(false);// get 1+damage

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

    //"description" is merely another matching name.
    //used by net.minecraft.entity.ai.attributes.AttributeMap::getAttributeInstanceByName
    //which is used in some places. It is not intended for human reading nor translation, more like a key.
    public static IAttribute getNewAttr(String name)
    {
        IAttribute attribute = new RangedAttribute(null, getAttrName(name), 0, MIN, MAX).setDescription(name).setShouldWatch(false);
        allNewAttrs.add(attribute);
        return attribute;
    }

    public static IAttribute getNewAttrNonpercent(String name)
    {
        IAttribute attribute = new RangedAttribute(null, getAttrName(name), 0, MIN, MAX).setDescription(name).setShouldWatch(false);
        allNewAttrs.add(attribute);
        return attribute;
    }

    public static float getElemMastery(EntityLivingBase livingBase)
    {
        IAttributeInstance instance = livingBase.getEntityAttribute(ELEM_MASTERY);
        if (instance != null)
        {
            return (float) instance.getAttributeValue();
        }
        return 0f;
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
            ELEM_DMG_BONUS[i] = getNewAttr(EnumElemental.values()[i].name().toLowerCase()+NAME_DMG_BONUS);
            ELEM_RES[i] = getNewAttr(EnumElemental.values()[i].name().toLowerCase()+NAME_DMG_RES);
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
        return Idealland.MODID + MID_NAME + name;
    }


    static Dictionary<EnumElemental, EnumAttr> resDict = new Hashtable<>();
    static Dictionary<EnumElemental, EnumAttr> dmgDict = new Hashtable<>();

    public static EnumAttr getEnumResistance(EnumElemental elemental)
    {
        return resDict.get(elemental);
    }

    public static EnumAttr getEnumDamage(EnumElemental elemental)
    {
        return dmgDict.get(elemental);
    }

    public static double getActualPercentRate(EntityLivingBase livingBase, IAttribute attribute)
    {
        IAttributeInstance attributeInstance = livingBase.getEntityAttribute(attribute);
        if (attributeInstance != null)
        {
            return attributeInstance.getAttributeValue() / 100f;
        }
        return 0;
    }

    public static double getDirectValue(EntityLivingBase livingBase, IAttribute attribute)
    {
        IAttributeInstance attributeInstance = livingBase.getEntityAttribute(attribute);
        if (attributeInstance != null)
        {
            return attributeInstance.getAttributeValue();
        }
        return 0;
    }

    //for percentage ones
    //100% is stored as 100
    public static double convert(double val)
    {
        return val * 100;
    }

    public static double getCritRate(EntityLivingBase livingBase)
    {
        return getActualPercentRate(livingBase, CRIT_RATE);
    }

    public static boolean getCritCheck(EntityLivingBase livingBase)
    {
        return livingBase.getRNG().nextDouble() < getCritRate(livingBase);
    }

    static final int BASE_1 = 1000;
    static final int BASE_2 = 2000;
    static final int BASE_3 = 3000;

    static HashMap<Integer, EnumAttr> ID_TO_ENUM = new HashMap<>();
    static HashMap<IAttribute, EnumAttr> ATTR_TO_ENUM = new HashMap<>();

    static final int REAL_PERCENT = 1;
    static final int FAKE_PERCENT = 0;

    public enum EnumAttr{
        NONE(SharedMonsterAttributes.FLYING_SPEED, 0),

        HP(SharedMonsterAttributes.MAX_HEALTH, 1),
        DEF(ModAttributes.DEFENSE, 2),
        ATK(ModAttributes.GEN_ATK, 3),
//        ATK(SharedMonsterAttributes.ATTACK_DAMAGE, 3),


        ELEM_MASTERY(ModAttributes.ELEM_MASTERY, 4),
        RECHARGE(ModAttributes.ENERGY_RECHARGE, 5, FAKE_PERCENT),

        CRIT(ModAttributes.CRIT_RATE, 6, FAKE_PERCENT),
        CRIT_DMG(ModAttributes.CRIT_DMG, 7, FAKE_PERCENT),
        HEAL(ModAttributes.HEAL_BONUS, 8, FAKE_PERCENT),

//        ATK_G(SharedMonsterAttributes.ATTACK_DAMAGE, 9),

        HP_P(SharedMonsterAttributes.MAX_HEALTH, BASE_1+HP.id, REAL_PERCENT),
        DEF_P(ModAttributes.DEFENSE, BASE_1+DEF.id, 1),
//        ATK_P(SharedMonsterAttributes.ATTACK_DAMAGE, BASE_1+ATK.id, REAL_PERCENT),
        ATK_P(ModAttributes.GEN_ATK, BASE_1+ATK.id, REAL_PERCENT),

//        ATK_G_P(SharedMonsterAttributes.ATTACK_DAMAGE, BASE_1+ATK_G.id),

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

        public final IAttribute attr;
        public final int id;
        public final int type;

        EnumAttr(IAttribute attr, int id) {
            this(attr, id, 0);
        }

        EnumAttr(EnumElemental elemental, boolean resitance) {
            this.attr = resitance ? getElemRes(elemental) : getElemBonus(elemental);
            this.id = elemental.ordinal() + (resitance ? BASE_2 : BASE_3);
            this.type = FAKE_PERCENT;
            if (resitance)
            {
                resDict.put(elemental, this);
            }
            else {
                dmgDict.put(elemental, this);
            }
            ID_TO_ENUM.put(id, this);
            ATTR_TO_ENUM.put(attr, this);
        }

        EnumAttr(IAttribute attr, int id, int type) {
            this.attr = attr;
            this.id = id;
            this.type = type;
            ID_TO_ENUM.put(id, this);
            ATTR_TO_ENUM.put(attr, this);
        }

        public static IAttribute getAttr(int id)
        {
            EnumAttr type = ID_TO_ENUM.get(id);
            if (type != null)
            {
                return type.attr;
            }
            return SharedMonsterAttributes.MAX_HEALTH;
        }

        public static int getID(IAttribute iAttribute, int type)
        {
            //
            for (EnumAttr attr :
                    EnumAttr.values()) {
                if (attr.attr == iAttribute && attr.type == type)
                {
                    return attr.id;
                }
            }
            return 1;
        }

        public static EnumAttr getEnum(int id)
        {
            EnumAttr type = ID_TO_ENUM.get(id);
            if (type != null)
            {
                return type;
            }
            return EnumAttr.HP;
        }

        public boolean isSpecialRarirtyRateSubAttr()
        {
            return this== EnumAttr.HP || this == EnumAttr.ATK || this == EnumAttr.DEF;
        }

        public boolean isSpecialRarirtyRateMainAttr()
        {
            return this== EnumAttr.HP_P || this == EnumAttr.ATK_P || this == EnumAttr.DEF_P;
        }
    }

    @SubscribeEvent
    public static void onConstruct(EntityEvent.EntityConstructing entityConstructing)
    {
        Entity entity = entityConstructing.getEntity();
        if (entity instanceof EntityLivingBase)
        {
            EntityLivingBase livingBase = (EntityLivingBase) entity;
            for (IAttribute attr:
                 allNewAttrs) {
                livingBase.getAttributeMap().registerAttribute(attr);
            }

            if (entity instanceof EntityPlayer)
            {
                livingBase.getEntityAttribute(DEFENSE).setBaseValue(225);
                livingBase.getEntityAttribute(GEN_ATK).setBaseValue(75);
            }
        }
    }

    public static float getAtkG(EntityLivingBase livingBase)
    {
        if (livingBase == null)
        {
            return 0f;
        }

        IAttributeInstance instance = livingBase.getEntityAttribute(GEN_ATK);
        if (instance != null)
        {
            return (float) instance.getAttributeValue();
        }
        //fallback
        instance = livingBase.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        if (instance != null)
        {
            return (float) instance.getAttributeValue();
        }
        return 0f;
    }
}

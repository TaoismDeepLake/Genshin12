package com.deeplake.genshin12.potion.buff;

import com.deeplake.genshin12.Idealland;
import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.util.*;
import com.deeplake.genshin12.util.NBTStrDef.IDLNBTDef;
import com.deeplake.genshin12.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.UUID;

import static com.deeplake.genshin12.util.NBTStrDef.IDLNBTDef.HU_TAO_TICK;

public class PotionHuTaoDebuff extends BaseSimplePotion {
    double[] damage = {64,68.8,73.6,80,84.8,89.6,96,102.4,108.8,115.2,121.6,128,136};

    public static final int PERIOD = 4 * CommonDef.TICK_PER_SECOND;

    //This buff require special rendering, hence need a uuid

//    Blood Blossom
//Enemies affected by Blood Blossom will take Pyro DMG every 4s. This DMG is considered Elemental Skill DMG.
//Each enemy can be affected by only one Blood Blossom effect at a time, and its duration may only be refreshed by Hu Tao herself.

    public PotionHuTaoDebuff(boolean isBadEffectIn, int liquidColorIn, String name, int icon) {
        super(isBadEffectIn, liquidColorIn, name, icon);
    }

    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
        super.performEffect(entityLivingBaseIn, amplifier);
        int getTargetTick = IDLNBTUtil.GetInt(entityLivingBaseIn, HU_TAO_TICK, 0);
        //potion class does not know dura.
        try {
            int dura = entityLivingBaseIn.getActivePotionEffect(this).getDuration();

//            Idealland.Log("dura = %s", dura);
            if (dura % PERIOD == getTargetTick)
            {
                World world = entityLivingBaseIn.getEntityWorld();
                if (!world.isRemote)
                {
                    String strUUID = IDLNBTUtil.getStringAuto(entityLivingBaseIn, IDLNBTDef.HU_TAO_CASTER, CommonDef.EMPTY);
                    if (strUUID.isEmpty())
                    {
                        //no caster error
                        Idealland.LogWarning("Error : hu tao buff cannot find caster record");
                        return;
                    }

                    try
                    {
                        UUID uuid = UUID.fromString(strUUID);

                        EntityPlayer player = world.getPlayerEntityByUUID(uuid);

                        if (player != null)
                        {
                            //perform
                            ElementalUtil.applyElementalDamage(player,
                                    entityLivingBaseIn,
                                    (getDamage(amplifier) * ModConfig.GeneralConf.DMG_ATK_PERCENT_GENSHIN_TO_MC),
                                    EnumElemental.PYRO,
                                    EnumAmount.SMALL);
                        }
                    }
                    catch (IllegalArgumentException e)
                    {
                        Idealland.LogWarning(e.toString());
                    }
                }

            }
        }
        catch (NullPointerException e)
        {
            Idealland.LogWarning("Error : hu tao buff cannot find itself");
        }
    }

    //Note that the first tick will call this. So this must not contradict as a Init
    @Override
    public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier) {

        super.removeAttributesModifiersFromEntity(entityLivingBaseIn, attributeMapIn, amplifier);
    }

    double getDamage(int level)
    {
        try {
            return damage[level] / 100f;
        }
        catch (ArrayIndexOutOfBoundsException e){
            return 1f;
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        //Enemies affected by Blood Blossom will take Pyro DMG every 4s
        //If the player attacks often, this will not trigger.
        //need nbt.
        //return duration % period == (period - 1);
        return true;
    }
}

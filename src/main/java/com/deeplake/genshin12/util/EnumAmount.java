package com.deeplake.genshin12.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import javax.annotation.Nullable;

public enum EnumAmount
{
    NONE(0,0, -1),
    SMALL(1, 1 / 9.5f, 0),
    MEDIUM(2, 1 / 6f, 1),
    LARGE(4, 1 / 4.25f, 2);

    public final float gauge;
    public final float decayRate;
    final int level;
    final float defaultDura;
    final int defaultDuraTicks;

    public static final int MAX_LEVEL = 2;

    EnumAmount(float gauge, float decayRate, int level) {
        this.gauge = gauge;
        this.decayRate = decayRate;
        this.level = level;
        defaultDura = gauge / decayRate;
        defaultDuraTicks = (int) (CommonDef.TICK_PER_SECOND * defaultDura);
    }

    public static float getValue(int ticks, int level)
    {
        float seconds = (float) ticks / CommonDef.TICK_PER_SECOND;
        switch (level)
        {
            case 0:
                return seconds * SMALL.decayRate;
            case 1:
                return seconds * MEDIUM.decayRate;
            case 2:
                return seconds * LARGE.decayRate;

            default:
                throw new IllegalStateException("Unexpected value: " + level);
        }
    }

    public static int getTicks(double amount, int level)
    {
        return (int) (amount / getAmountType(level).decayRate * CommonDef.TICK_PER_SECOND);
    }

    public static EnumAmount getAmountType(int potionLevel)
    {
        switch (potionLevel)
        {
            case 0:
                return SMALL;
            case 1:
                return MEDIUM;
            case 2:
                return LARGE;

            default:
                throw new IllegalStateException("Unexpected value: " + potionLevel);
        }
    }


    @Nullable
    public static PotionEffect getPotionEffect(EnumElemental elemental, int ticks, int level)
    {
        if (elemental.potion != null)
        {
            return new PotionEffect(elemental.potion, ticks, level);
        }
        return null;
    }

    public static int level(EntityLivingBase livingBase) {
        for (EnumElemental elem :
                EnumElemental.values()) {
            Potion potion = elem.getPotion();
            if (potion != null && livingBase.isPotionActive(potion))
            {
                PotionEffect effect = livingBase.getActivePotionEffect(potion);
                int level = effect.getAmplifier();
                return level;
            }
        }

        return -1;
    }


}

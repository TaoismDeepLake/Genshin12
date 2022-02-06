package com.deeplake.genshin12.util;

import net.minecraft.potion.PotionEffect;

import javax.annotation.Nullable;

public enum EnumAmount
{
    NONE(0,0, -1),
    SMALL(1, 1 / 9.5f, 0),
    MEDIUM(2, 1 / 6f, 1),
    LARGE(4, 1 / 4.25f, 2);

    final float gauge;
    final float decayRate;
    final int level;
    final float defaultDura;
    final int defaultDuraTicks;

    EnumAmount(float gauge, float decayRate, int level) {
        this.gauge = gauge;
        this.decayRate = decayRate;
        this.level = level;
        defaultDura = gauge / decayRate;
        defaultDuraTicks = (int) (CommonDef.TICK_PER_SECOND * defaultDura);
    }

    @Nullable
    public PotionEffect getPotionEffect(EnumElemental elemental)
    {
        if (elemental.potion != null)
        {
            return new PotionEffect(elemental.potion, defaultDuraTicks, level);
        }
        return null;
    }
}

package com.deeplake.genshin12.item;

import com.deeplake.genshin12.item.artifact.ArtifactUtil;
import net.minecraft.item.EnumRarity;

public enum EnumModRarity {
    //9b9b9b
    //69a489
    //73a9c8
    //9072bc
    //df9150
    GREY(0x9b9b9b, EnumRarity.COMMON),
    GREEN(0x69a489, EnumRarity.UNCOMMON),
    BLUE(0x73a9c8, EnumRarity.RARE),
    PURPLE(0x9072bc, EnumRarity.EPIC),
    GOLD(0xdf9150, ArtifactUtil.LEGEND);

    public final int color;
    public final EnumRarity rarity;
    EnumModRarity(int color, EnumRarity rarity)
    {
        this.color = color;
        this.rarity = rarity;
        ArtifactUtil.QUALITY_MAP.put(rarity, this);
    }

    //rarity starts from 1
    public static EnumModRarity getQuality(int rarity)
    {
        try {
            return values()[rarity - 1];
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            return GREY;
        }
    }

    public static int getColor(int rarity)
    {
        return getQuality(rarity).color;
    }
}

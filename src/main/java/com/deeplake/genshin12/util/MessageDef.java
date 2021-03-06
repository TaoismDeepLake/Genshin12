package com.deeplake.genshin12.util;

import com.deeplake.genshin12.Idealland;
import net.minecraft.item.ItemStack;

public class MessageDef {
    //GENERAL:
    public static final String OUT_OF_RANGE = "genshin12.msg.out_of_range";
    public static final String IN_COOLDOWN = "genshin12.skill.msg.cool_down";
    public static final String NOT_CASTABLE_MAINHAND = "genshin12.skill.msg.not_castable_mainhand";
    public static final String NOT_CASTABLE_OFFHAND = "genshin12.skill.msg.not_castable_offhand";
    public static final String LACK_MP = Idealland.MODID + ".skill.msg.lack_mp";
    public static final String CHARGE_FULL = Idealland.MODID + ".skill.msg.charge_full";
    public static final String OBTAIN_PRIMO = Idealland.MODID + ".msg.obtain_primo";
    public static final String XIAO_PLUNGE = Idealland.MODID + ".msg.xiao.plunge.speech.";

    public static final String ARTIFACT_TRIGGER_MINER= Idealland.MODID + ".msg.artifact.miner";
    public static final String ARTIFACT_TRIGGER_LUMBER= Idealland.MODID + ".msg.artifact.lumber";

    public static String getSkillCastKey(ItemStack stack, int index)
    {
        //remove"item."
        return String.format("msg.%s.cast.%d", stack.getUnlocalizedName().substring(5), index);
    }

    public static String getXiaoPlungeKey(int index)
    {
        //remove"item."
        return XIAO_PLUNGE + index;
    }
}

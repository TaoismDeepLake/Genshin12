package com.deeplake.genshin12.item;

import com.deeplake.genshin12.util.IDLSkillNBT;
import net.minecraft.item.ItemStack;

public interface ILeveler {

    int[] levelupNeedXp(ItemStack stack);



    int getMaxLevel(ItemStack stack);

    default int getCurXP(ItemStack stack)
    {
        return IDLSkillNBT.getXP(stack);
    }
}

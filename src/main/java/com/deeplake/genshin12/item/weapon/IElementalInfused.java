package com.deeplake.genshin12.item.weapon;

import com.deeplake.genshin12.util.EnumAmount;
import com.deeplake.genshin12.util.EnumElemental;

public interface IElementalInfused {
    default boolean isActive()
    {
        return true;
    }

    EnumElemental getElemType();

    EnumAmount getAttackAmount();
}

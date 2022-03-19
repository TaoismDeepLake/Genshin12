package com.deeplake.genshin12.item.weapon;

import com.deeplake.genshin12.item.ItemSwordBase;
import com.deeplake.genshin12.util.EnumAmount;
import com.deeplake.genshin12.util.EnumElemental;

public class ItemInfusedMelee extends ItemSwordBase implements IElementalInfused{

    EnumElemental enumElemental = EnumElemental.PHYSICAL;
    EnumAmount enumAmount = EnumAmount.NONE;

    public ItemInfusedMelee(String name, ToolMaterial material, EnumElemental enumElemental, EnumAmount enumAmount) {
        super(name, material);
        this.enumElemental = enumElemental;
        this.enumAmount = enumAmount;
    }

    @Override
    public EnumElemental getElemType() {
        return enumElemental;
    }

    @Override
    public EnumAmount getAttackAmount() {
        return enumAmount;
    }
}

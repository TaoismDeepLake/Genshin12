package com.deeplake.genshin12.item.artifact;

import com.deeplake.genshin12.entity.creatures.attribute.ModAttributes;
import com.deeplake.genshin12.item.ItemBase;
import com.deeplake.genshin12.item.ItemVariantBase;
import com.deeplake.genshin12.util.EnumElemental;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ItemArtifactBase extends ItemVariantBase {
    public ItemArtifactBase(String name, int count) {
        super(name, count);
    }

    static List<ModAttributes.EnumAttr> listMain1 = new ArrayList<>();
    static List<ModAttributes.EnumAttr> listMain2 = new ArrayList<>();
    static List<ModAttributes.EnumAttr> listMain3 = new ArrayList<>();
    static List<ModAttributes.EnumAttr> listMain4 = new ArrayList<>();
    static List<ModAttributes.EnumAttr> listMain5 = new ArrayList<>();

    static {
        listMain1.add(ModAttributes.EnumAttr.HP);

        listMain2.add(ModAttributes.EnumAttr.ATK);

        listMain3.add(ModAttributes.EnumAttr.ELEM_MASTERY);
        listMain3.add(ModAttributes.EnumAttr.RECHARGE);
        listMain3.add(ModAttributes.EnumAttr.ATK_P);
        listMain3.add(ModAttributes.EnumAttr.HP_P);
        listMain3.add(ModAttributes.EnumAttr.DEF_P);

        listMain4.add(ModAttributes.EnumAttr.ATK_P);
        for (EnumElemental elemental :
                EnumElemental.values()) {
            if (elemental != EnumElemental.CHRONO)
            {
                listMain4.add(ModAttributes.getEnumDamage(elemental));
            }
        }
        listMain4.add(ModAttributes.EnumAttr.ATK_P);
        listMain4.add(ModAttributes.EnumAttr.HP_P);
        listMain4.add(ModAttributes.EnumAttr.DEF_P);

        listMain5.add(ModAttributes.EnumAttr.HEAL);
        listMain5.add(ModAttributes.EnumAttr.CRIT);
        listMain5.add(ModAttributes.EnumAttr.CRIT_DMG);
        listMain5.add(ModAttributes.EnumAttr.ATK_P);
        listMain5.add(ModAttributes.EnumAttr.HP_P);
        listMain5.add(ModAttributes.EnumAttr.DEF_P);
    }

    @Nullable
    @Override
    public EntityEquipmentSlot getEquipmentSlot(ItemStack stack) {
        switch (stack.getItemDamage())
        {
            case 0:
                return EntityEquipmentSlot.HEAD;
            case 1:
                return EntityEquipmentSlot.CHEST;
            case 2:
                return EntityEquipmentSlot.LEGS;
            case 3:
                return EntityEquipmentSlot.FEET;
        }
        return super.getEquipmentSlot(stack);
    }
}

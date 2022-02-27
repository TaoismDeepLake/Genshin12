package com.deeplake.genshin12.item;

import com.deeplake.genshin12.entity.creatures.attribute.ModAttributes;
import com.google.common.collect.Multimap;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class ItemDebugSword extends ItemSwordBase implements IWIP {
    public ItemDebugSword(String name, ToolMaterial material) {
        super(name, material);
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(slot);

        if (slot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(ModAttributes.CRIT_RATE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 0.5f, 0));
        }

        return multimap;
    }
}

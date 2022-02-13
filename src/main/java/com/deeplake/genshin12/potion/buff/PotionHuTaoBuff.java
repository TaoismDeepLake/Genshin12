package com.deeplake.genshin12.potion.buff;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

import java.util.Map;

import static com.deeplake.genshin12.potion.ModPotions.UUID_HUTAO_BUFF;

public class PotionHuTaoBuff extends BaseSimplePotion {
    double[] atkRatio = {3.84,4.07,4.3,4.6,4.83,5.06,5.36,5.66,5.96,6.26,6.55,6.85,7.15};

    AttributeModifier modifier = new AttributeModifier(UUID_HUTAO_BUFF, "1", 0, 0);

    public PotionHuTaoBuff(boolean isBadEffectIn, int liquidColorIn, String name, int icon) {
        super(isBadEffectIn, liquidColorIn, name, icon);
    }

    double getAtkBuffRatio(int level)
    {
        try {
            return atkRatio[level - 1] / 100f;
        }
        catch (ArrayIndexOutOfBoundsException e){
            return 1f;
        }
    }

    @Override
    public void applyAttributesModifiersToEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier) {

        IAttributeInstance iattributeinstance = attributeMapIn.getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE);

        if (iattributeinstance != null)
        {
            iattributeinstance.removeModifier(modifier);
            iattributeinstance.applyModifier(new AttributeModifier(UUID_HUTAO_BUFF, this.getName() + " " + amplifier, entityLivingBaseIn.getMaxHealth() * getAtkBuffRatio(amplifier + 1), 0));
        }

        super.applyAttributesModifiersToEntity(entityLivingBaseIn, attributeMapIn, amplifier);
    }
}

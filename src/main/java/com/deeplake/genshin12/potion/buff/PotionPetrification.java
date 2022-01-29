package com.deeplake.genshin12.potion.buff;

import com.deeplake.genshin12.entity.creatures.ai.EntityAIPerification;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;

import javax.annotation.Nullable;

public class PotionPetrification extends BaseSimplePotion
{
    public PotionPetrification(boolean isBadEffectIn, int liquidColorIn, String name, int icon) {
        super(isBadEffectIn, liquidColorIn, name, icon);
    }

    public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier)
    {
        if (entityLivingBaseIn instanceof EntityLiving)
        {
            EntityLiving living = (EntityLiving) entityLivingBaseIn;
            for (EntityAITasks.EntityAITaskEntry ai:
                    living.tasks.taskEntries) {
                if (ai.action instanceof EntityAIPerification )
                {
                    living.tasks.removeTask(ai.action);
                    return;
                }
            }

            ((EntityLiving) entityLivingBaseIn).tasks.addTask(0, new EntityAIPerification());
        }
        super.removeAttributesModifiersFromEntity(entityLivingBaseIn, attributeMapIn, amplifier);
    }

    public void applyAttributesModifiersToEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier)
    {
        if (entityLivingBaseIn instanceof EntityLiving)
        {
            EntityLiving living = (EntityLiving) entityLivingBaseIn;
            for (EntityAITasks.EntityAITaskEntry ai:
                    living.tasks.taskEntries) {
                if (ai.action instanceof EntityAIPerification )
                {
                    return;
                }
            }

            ((EntityLiving) entityLivingBaseIn).tasks.addTask(0, new EntityAIPerification());
        }
        super.applyAttributesModifiersToEntity(entityLivingBaseIn, attributeMapIn, amplifier);
    }
}

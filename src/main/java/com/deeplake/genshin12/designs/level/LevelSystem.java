package com.deeplake.genshin12.designs.level;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

import javax.annotation.Nullable;
import java.util.UUID;

public class LevelSystem {
    static final UUID LEVEL_MODIFIER_UUID = UUID.fromString("1903c8a4-8314-4864-81c0-3ffd5ed631f4");
    static final String LEVEL_MOD_NAME = "level_count";
    static final AttributeModifier LVL_MODIFIER_DEFAULT = new AttributeModifier(LEVEL_MODIFIER_UUID, LEVEL_MOD_NAME, 0, 0);

    //Level 0 means no HP.
    //Level 1 means level is not specifically set.
    public static int getLevel(@Nullable EntityLivingBase livingBase)
    {
        if (livingBase == null)
        {
            return 1;
        }

        IAttributeInstance hp_instance = livingBase.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
        if (hp_instance != null)
        {
            if (livingBase.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).hasModifier(LVL_MODIFIER_DEFAULT)) {
                return (int) livingBase.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getModifier(LEVEL_MODIFIER_UUID).getAmount();
            }

            return 1;
        }
        else {
            //fail
            return 0;
        }
    }

    public static boolean setLevel(EntityLivingBase livingBase, int level)
    {
        IAttributeInstance hp_instance = livingBase.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
        if (hp_instance != null)
        {
            if (livingBase.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).hasModifier(LVL_MODIFIER_DEFAULT)) {
                livingBase.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).removeModifier(LVL_MODIFIER_DEFAULT);
            }

                hp_instance.applyModifier(new AttributeModifier(LEVEL_MODIFIER_UUID, LEVEL_MOD_NAME, level, 0));
            return true;
        }
        else {
            //fail
            return false;
        }
    }
}

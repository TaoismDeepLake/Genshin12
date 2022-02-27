package com.deeplake.genshin12.potion.buff;

import com.deeplake.genshin12.entity.creatures.attribute.ModAttributes;
import com.deeplake.genshin12.potion.ModPotions;
import com.deeplake.genshin12.util.CommonDef;
import com.deeplake.genshin12.util.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.init.MobEffects;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

import static com.deeplake.genshin12.potion.ModPotions.*;

public class PotionJadeShield extends BasePotion {
    float range = 10f;//confirmed with wiki

    AttributeModifier modifier = new AttributeModifier(UUID_JADE_SHIELD, "Jade Shield", 0, 0);

    public PotionJadeShield(boolean isBadEffectIn, int liquidColorIn, String name, int icon) {
        super(isBadEffectIn, liquidColorIn, name, icon);
        setKBResistance(1f, 0f);
    }

    @Override
    public boolean hasStatusIcon() {
        return super.hasStatusIcon();
    }

    @Override
    public void performEffect(@Nonnull EntityLivingBase living, int amplified) {
        super.performEffect(living, amplified);
        World world = living.getEntityWorld();
        if (world.isRemote)
        {
            float entityYaw = living.rotationYaw * CommonDef.DEG_TO_RAD;

            float theta = entityYaw;
            float thetaMax = (float) (Math.PI * 2) + entityYaw;
            float yBase = 0.5f;
            float yMaxDelta = 0.5f;
            float radius = 0.5f;

            for (; theta < thetaMax; theta += Math.PI * 2 / 16f)
            {
                double x = (living.posX + radius * Math.cos(theta));
                double y = (living.posY + yBase + yMaxDelta * Math.cos(theta+entityYaw));
                double y2 = (living.posY + yBase - yMaxDelta * Math.cos(theta+entityYaw));
                double z = (living.posZ + radius * Math.sin(theta));

//                world.spawnParticle(EnumParticleTypes.REDSTONE, x,y,z,0,0,0);
//                world.spawnParticle(EnumParticleTypes.REDSTONE, x,y2,z,0,0,0);
                if (world.getWorldTime() % 4 == 0) {
                    world.spawnParticle(EnumParticleTypes.REDSTONE, x, y, z, 1.0f, 0.9f, 0.6f);
                    world.spawnParticle(EnumParticleTypes.REDSTONE, x, y2, z, 1.0f, 0.9f, 0.6f);
                }
            }
        }
        else {
            List<EntityLivingBase> livingBaseList =
                    EntityUtil.getEntitiesWithinAABB(world, EntityLiving.class, living.getPositionVector(), range, null);
            for (EntityLivingBase target :
                    livingBaseList) {
                EntityUtil.ApplyBuff(target, ModPotions.JADE_SHIELD_DEBUFF, 0, 0.5f);
            }
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    //level starts from 0
    double getAtkBuffRatio(int amplifier)
    {
        try {
            return 0.5f + amplifier*0.05f;
        }
        catch (ArrayIndexOutOfBoundsException e){
            return 0.5f;
        }
    }

    @Override
    public void applyAttributesModifiersToEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier) {

        IAttributeInstance iattributeinstance = attributeMapIn.getAttributeInstance(ModAttributes.SHIELD_STR);

        if (iattributeinstance != null)
        {
            iattributeinstance.removeModifier(modifier);
            iattributeinstance.applyModifier(new AttributeModifier(UUID_JADE_SHIELD, this.getName() + " " + amplifier, ModAttributes.convert(getAtkBuffRatio(amplifier)), 0));
        }

        super.applyAttributesModifiersToEntity(entityLivingBaseIn, attributeMapIn, amplifier);
    }
}

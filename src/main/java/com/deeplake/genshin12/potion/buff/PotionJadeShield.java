package com.deeplake.genshin12.potion.buff;

import com.deeplake.genshin12.potion.ModPotions;
import com.deeplake.genshin12.util.CommonDef;
import com.deeplake.genshin12.util.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class PotionJadeShield extends BasePotion {
    float range = 10f;//confirmed with wiki
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
}

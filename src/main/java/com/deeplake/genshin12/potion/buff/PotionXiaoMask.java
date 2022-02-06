package com.deeplake.genshin12.potion.buff;

import com.deeplake.genshin12.IdlFramework;
import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.potion.ModPotions;
import com.deeplake.genshin12.util.CommonDef;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.MobEffects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = IdlFramework.MODID)
public class PotionXiaoMask extends BasePotion {
    public PotionXiaoMask(boolean isBadEffectIn, int liquidColorIn, String name, int icon) {
        super(isBadEffectIn, liquidColorIn, name, icon);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration % CommonDef.TICK_PER_SECOND == 0;
    }

    //starts from lv.1
    float getDrainRatio(int level)
    {
        if (level <= 3)
        {
            return 0.03f;
        }
        else if (level <= 6)
        {
            return 0.025f;
        }
        else
        {
            return 0.02f;
        }
    }

    @Override
    public void performEffect(@Nonnull EntityLivingBase living, int amplified) {
        super.performEffect(living, amplified);
        float drain = living.getMaxHealth() * getDrainRatio(amplified + 1);
        if (living.getHealth() - drain <= 0f)
        {
            //trigger revival
            living.attackEntityFrom(DamageSource.WITHER, drain * 9f);
        }
        else {
            living.setHealth(living.getHealth() - drain);
        }
    }

    double[] dmgBonus = new double[]
            {
                    58.45,61.95,65.45,70,73.5,77,81.55,86.1,90.65,95.2,99.75,104.3,108.85,113
            };

    public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier)
    {
        return getAtkBonus(amplifier + 1);
    }

    double getAtkBonus(int level)
    {
        try {
            return dmgBonus[level - 1];
        }
        catch (ArrayIndexOutOfBoundsException e){
            return dmgBonus[0];
        }
    }

    //This is sure to conflict with other mods using the same method...
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onJump(LivingEvent.LivingJumpEvent event) {
        if (!ModConfig.DEBUG_CONF.ENABLE_XIAO_JUMP_BOOST)
        {
            return;
        }

        World world = event.getEntity().getEntityWorld();
        if (world.isRemote)//Yep. Client for players
        {
            EntityLivingBase livingEntity = event.getEntityLiving();
            if (!livingEntity.onGround) {
                return;
            }

            double jumpFactorMax = 1;

            jumpFactorMax *= 0.42;//const

            if (livingEntity.isPotionActive(ModPotions.YAKSHA_MASK))
            {
                //Yaksha mask overrides all jump buffs. Jump boost will be ignored.
                jumpFactorMax *= ModConfig.DEBUG_CONF.XIAO_JUMP_FACTOR;
                livingEntity.setVelocity(livingEntity.motionX, jumpFactorMax, livingEntity.motionZ);
            }
        }
    }
}

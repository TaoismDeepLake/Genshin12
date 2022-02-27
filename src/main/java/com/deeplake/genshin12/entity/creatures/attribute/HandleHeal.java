package com.deeplake.genshin12.entity.creatures.attribute;

import com.deeplake.genshin12.IdlFramework;
import com.deeplake.genshin12.util.EnumElemental;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = IdlFramework.MODID)
public class HandleHeal {
    @SubscribeEvent
    public static void onHurt(LivingHealEvent event)
    {
        EntityLivingBase hurtOne = event.getEntityLiving();
        float factor = (float) (1 + ModAttributes.getActualPercentRate(hurtOne, ModAttributes.IN_HEAL_BONUS));
        event.setAmount(factor * event.getAmount());
    }

    public static void genshinHeal(EntityLivingBase source, EntityLivingBase target, float amount)
    {
        float factor = (float) (1 + ModAttributes.getActualPercentRate(source, ModAttributes.HEAL_BONUS));
        target.heal(amount * factor);
    }
}

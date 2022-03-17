package com.deeplake.genshin12.entity.creatures.attribute;

import com.deeplake.genshin12.Idealland;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
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

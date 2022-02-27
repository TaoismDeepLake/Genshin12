package com.deeplake.genshin12.entity.creatures.attribute;

import com.deeplake.genshin12.IdlFramework;
import com.deeplake.genshin12.init.ModConfig;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = IdlFramework.MODID)
public class EventsHandleShield {

    @SubscribeEvent
    public static void handleShield(LivingHurtEvent event) {
        World world = event.getEntity().getEntityWorld();
        EntityLivingBase hurtOne = event.getEntityLiving();

        DamageSource source = event.getSource();
        if (source.isDamageAbsolute())// || source.isUnblockable())
            // should I consider axe as able to penetrate shields? No. This is too strong for abyss mages
        {
            return;
        }

        float shieldFactor = 1f;
        if (hurtOne.getAbsorptionAmount() > 0)
        {
            //When the Jade Shield takes DMG, it will Fortify:
            //Fortified characters have 5% increased Shield Strength.
            //Can stack up to 5 times, and lasts until the Jade Shield disappears
            shieldFactor = 1 + (float) ModAttributes.getActualPercentRate(hurtOne, ModAttributes.SHIELD_STR);
            if (ModConfig.GeneralConf.MOVIE_MODE)
            {
                shieldFactor += 99f;
            }
        }

        event.setAmount(event.getAmount() / shieldFactor);
    }
}

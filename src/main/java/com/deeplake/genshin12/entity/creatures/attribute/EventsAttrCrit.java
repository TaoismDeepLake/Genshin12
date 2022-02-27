package com.deeplake.genshin12.entity.creatures.attribute;

import com.deeplake.genshin12.IdlFramework;
import com.deeplake.genshin12.init.ModConfig;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

@Mod.EventBusSubscriber(modid = IdlFramework.MODID)
public class EventsAttrCrit {

    @SubscribeEvent
    public static void onCritCheck(CriticalHitEvent event)
    {
        //only players go over this check
        EntityPlayer attacker = event.getEntityPlayer();
        Random random = attacker.getRNG();
        boolean disable_vanilla_crit = ModConfig.GeneralConf.DISABLE_VANILLA_CRIT;
        double crit_dmg_bonus = ModAttributes.getActualPercentRate(attacker, ModAttributes.CRIT_DMG);
        float modifier = (float) (ModConfig.GeneralConf.OVERRIDE_CRIT_DMG ?
                        (1f + crit_dmg_bonus) :
                        (event.getDamageModifier() + crit_dmg_bonus - 0.5f));

        if (event.getResult() == Event.Result.ALLOW) {
            //critical!
            applyCriticalDamage(event, modifier);
        } else {
            if (ModAttributes.getCritCheck(attacker))
            {
                //critical
                event.setResult(Event.Result.ALLOW);
                applyCriticalDamage(event, modifier);
            }
            else {
                if (disable_vanilla_crit)
                {
                    event.setResult(Event.Result.DENY);
                }
                else {
                    if (event.isVanillaCritical())
                    {
                        applyCriticalDamage(event, modifier);
                    }
                    //DEFAULT, remains unchanged
                }
            }
        }

    }

    public static void applyCriticalDamage(CriticalHitEvent event, float modifier) {
        event.setDamageModifier(modifier);
        if (ModConfig.DEBUG_CONF.DEBUG_MODE)
        {
            IdlFramework.Log("Critical. %s to %s, damage = %s x 100%", event.getEntityPlayer(), event.getTarget(), modifier);
        }
    }

}

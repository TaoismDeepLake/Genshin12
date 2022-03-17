package com.deeplake.genshin12.entity.creatures.attribute;


import com.deeplake.genshin12.Idealland;
import com.deeplake.genshin12.designs.level.LevelSystem;
import com.deeplake.genshin12.init.ModConfig;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class EventsHandleDamage {
    @SubscribeEvent
    public static void onHurt(LivingHurtEvent event)
    {
        //Note that isUnblockable = bypass armor. They are bond together.
        DamageSource source = event.getSource();
        if (source.isDamageAbsolute() || source.isUnblockable())
        {
            return;
        }

        EntityLivingBase hurtOne = event.getEntityLiving();
        EntityLivingBase attacker = null;
        if (event.getSource().getTrueSource() instanceof EntityLivingBase)
        {
            attacker = (EntityLivingBase) event.getSource().getTrueSource();
        }

        handleDefense(event, hurtOne, attacker);
        handleReduction(event, hurtOne, attacker);
    }

    public static void handleDefense(LivingHurtEvent event, EntityLivingBase hurtOne, EntityLivingBase attacker)
    {
        int lvAtk = LevelSystem.getLevel(attacker);
        double def = ModAttributes.getDirectValue(hurtOne, ModAttributes.DEFENSE);
        double modifier = 1 - (def) / (def + ModConfig.DEBUG_CONF.DEF_LEVEL_FACTOR * lvAtk + ModConfig.DEBUG_CONF.DEF_STATIC_PLUS);
        event.setAmount((float) (event.getAmount() * modifier));
    }

    public static void handleReduction(LivingHurtEvent event, EntityLivingBase hurtOne, EntityLivingBase attacker)
    {
        double def = ModAttributes.getDirectValue(hurtOne, ModAttributes.DMG_REDUCT);
        double modifier = 1 - def;
        event.setAmount((float) (event.getAmount() * modifier));
    }
}

package com.deeplake.genshin12.entity.creatures.attribute;

import com.deeplake.genshin12.IdlFramework;
import com.deeplake.genshin12.util.EnumElemental;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = IdlFramework.MODID)
public class HandleResistance {
    @SubscribeEvent
    public static void onHurt(LivingHurtEvent event)
    {
        EntityLivingBase hurtOne = event.getEntityLiving();
        EntityLivingBase attacker = null;
        if (event.getSource().getTrueSource() instanceof EntityLivingBase)
        {
            attacker = (EntityLivingBase) event.getSource().getTrueSource();
        }

        DamageSource source = event.getSource();
        if (!source.isMagicDamage() && !source.isFireDamage() && !source.isExplosion() && !source.isDamageAbsolute())
        {
            handleResistance(hurtOne, attacker, EnumElemental.PHYSICAL);
        }
    }

    public static double handleResistance(EntityLivingBase hurtOne, EntityLivingBase attacker, EnumElemental elemental)
    {
        double res = ModAttributes.getActualPercentRate(hurtOne, ModAttributes.getElemRes(elemental));
        double bonus = ModAttributes.getActualPercentRate(hurtOne, ModAttributes.getElemBonus(elemental));
        return 1 + bonus - res;
    }
}

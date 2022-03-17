package com.deeplake.genshin12.designs.elements;

import com.deeplake.genshin12.Idealland;
import com.deeplake.genshin12.entity.special.EntityEnergyOrb;
import com.deeplake.genshin12.util.EnumElemental;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class EventDeathDropElement {

    @SubscribeEvent
    public static void id(LivingDeathEvent event)
    {
        EntityLivingBase livingBase = event.getEntityLiving();
        //players does not drop orbs
        if (livingBase instanceof EntityLiving)
        {
            //only fightable creatures drop orbs
            if (livingBase.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE) != null)
            {
                EntityEnergyOrb.drop(livingBase, 1, EnumElemental.PHYSICAL);
            }
        }

    }
}

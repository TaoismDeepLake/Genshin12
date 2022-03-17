package com.deeplake.genshin12.designs.client;

import com.deeplake.genshin12.Idealland;
import com.deeplake.genshin12.potion.ModPotions;
import com.deeplake.genshin12.potion.buff.BaseSimplePotion;
import com.deeplake.genshin12.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = Idealland.MODID, value = Side.CLIENT)
public class RenderElementalParticles {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void renderParticles(final RenderLivingEvent.Specials.Pre ev) {
        EntityLivingBase entity = ev.getEntity();

        if (entity.posX == 0 && entity.posY == 0 && entity.posZ == 0)
            return;

        for (BaseSimplePotion potion:
                RenderElementalIcon.map.keySet()) {
            if (potion.hasPotion(entity))
            {
                if (potion == ModPotions.HYDRO)
                {
                    EntityUtil.spawnParticleOver(entity, EnumParticleTypes.WATER_DROP);
                }
                else if (potion == ModPotions.CYRO || potion == ModPotions.FREEZE)
                {
                    EntityUtil.spawnParticleOver(entity, EnumParticleTypes.SNOW_SHOVEL);
                }
                else if (potion == ModPotions.PYRO)
                {
                    EntityUtil.spawnParticleOver(entity, EnumParticleTypes.FLAME);
                }
            }
        }
    }
}

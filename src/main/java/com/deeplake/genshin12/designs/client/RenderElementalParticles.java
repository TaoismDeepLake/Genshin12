package com.deeplake.genshin12.designs.client;

import com.deeplake.genshin12.IdlFramework;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = IdlFramework.MODID)
@SideOnly(Side.CLIENT)
public class RenderElementalParticles {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void renderIcons(final RenderLivingEvent.Specials.Pre ev) {

    }
}

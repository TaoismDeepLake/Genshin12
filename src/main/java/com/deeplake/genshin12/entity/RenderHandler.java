package com.deeplake.genshin12.entity;

import com.deeplake.genshin12.entity.creatures.render.*;
import com.deeplake.genshin12.entity.special.EntityEnergyOrb;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderHandler {

    public static void registerEntityRenders() {
        RenderingRegistry.registerEntityRenderingHandler(EntityPlanetBefall.class, RenderPlanetBefallLiving::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityEnergyOrb.class, RenderEnergyOrb::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGlacialWaltz.class, RenderNone::new);

//        RenderingRegistry.registerEntityRenderingHandler(EntityPlanetBefall.class, renderManager -> new RenderPlanetBefall<>(renderManager));
    }
}

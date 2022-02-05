package com.deeplake.genshin12.entity;

import com.deeplake.genshin12.IdlFramework;
import com.deeplake.genshin12.entity.creatures.moroon.EntityMoroonUnitBase;
import com.deeplake.genshin12.entity.creatures.render.*;
import com.deeplake.genshin12.entity.projectiles.EntityIdlProjectile;
import com.deeplake.genshin12.entity.special.EntityEnergyOrb;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderHandler {

    public static void registerEntityRenders() {
        RenderingRegistry.registerEntityRenderingHandler(EntityPlanetBefall.class, RenderPlanetBefallLiving::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityEnergyOrb.class, RenderEnergyOrb::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGlacialWaltz.class, RenderNone::new);

//        RenderingRegistry.registerEntityRenderingHandler(EntityPlanetBefall.class, renderManager -> new RenderPlanetBefall<>(renderManager));
    }
}

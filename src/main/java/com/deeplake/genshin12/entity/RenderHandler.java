package com.deeplake.genshin12.entity;

import com.deeplake.genshin12.IdlFramework;
import com.deeplake.genshin12.entity.creatures.moroon.EntityMoroonUnitBase;
import com.deeplake.genshin12.entity.creatures.render.*;
import com.deeplake.genshin12.entity.projectiles.EntityIdlProjectile;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderHandler {

    public static void registerEntityRenders() {
        RenderingRegistry.registerEntityRenderingHandler(EntityMoroonUnitBase.class, RenderMoroonHumanoid::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityIdlProjectile.class, renderManager -> new RenderBullet<>(renderManager, new ResourceLocation(IdlFramework.MODID,
                "textures/entity/projectiles/bullet_norm.png")));
    }
}

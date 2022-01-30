package com.deeplake.genshin12.entity.creatures.render;

import com.deeplake.genshin12.IdlFramework;
import com.deeplake.genshin12.entity.EntityPlanetBefall;
import com.deeplake.genshin12.entity.creatures.model.ModelLubanLock;
import com.deeplake.genshin12.init.ModConfig;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

public class RenderPlanetBefallLiving<T extends EntityPlanetBefall> extends RenderLiving<T> {
    private static final ResourceLocation DEFAULT_RES_LOC = new ResourceLocation(IdlFramework.MODID,"textures/entity/zhongli/luban_lock.png");

    public RenderPlanetBefallLiving(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelLubanLock(), 3f);
    }

    @Override
    protected void preRenderCallback(T entitylivingbaseIn, float partialTickTime) {
        super.preRenderCallback(entitylivingbaseIn, partialTickTime);
        float size = ModConfig.DEBUG_CONF.METEOR_SIZE;

//        GlStateManager.translate(0, size, 0);
        GlStateManager.scale(size, size, size);
        GlStateManager.rotate(ModConfig.DEBUG_CONF.METEOR_OMEGA * entitylivingbaseIn.ticksExisted, 0.3f, 0.5f, 0.2f);

//        GlStateManager.translate(0, -entitylivingbaseIn.getPositionRatio() * ModConfig.DEBUG_CONF.METEOR_HEIGHT, 0);
    }

    //    //set the scale here
//    @Override
//    protected void preRenderCallback(EntityMediumTurret entitylivingbaseIn, float partialTickTime) {
//        super.preRenderCallback(entitylivingbaseIn, partialTickTime);
//        entitylivingbaseIn.getFaction().applyColor();

//    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return DEFAULT_RES_LOC;
    }
}

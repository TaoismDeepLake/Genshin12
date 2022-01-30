package com.deeplake.genshin12.entity.creatures.render;

import com.deeplake.genshin12.entity.EntityPlanetBefall;
import com.deeplake.genshin12.entity.creatures.model.ModelLubanLock;
import com.deeplake.genshin12.entity.projectiles.EntityIdlProjectile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nullable;

public class RenderPlanetBefall<T extends EntityPlanetBefall> extends Render<T> {
    private static final ResourceLocation DEFAULT_RES_LOC = new ResourceLocation("textures/entity/zhongli/luban_lock.png");

    protected ModelBase mainModel = new ModelLubanLock();

    public RenderPlanetBefall(RenderManager renderManager) {
        super(renderManager);
    }

    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        this.bindEntityTexture(entity);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
        GlStateManager.enableRescaleNormal();

        GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
//        GlStateManager.scale(0.05625F, 0.05625F, 0.05625F);
        GlStateManager.translate(-4.0F, 0.0F, 0.0F);

        if (this.renderOutlines)
        {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        renderMainModel(entity);

        if (this.renderOutlines)
        {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.disableRescaleNormal();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    public void renderMainModel(T entity) {
        if (!this.bindEntityTexture(entity)) {
            return;
        }

        GlStateManager.enableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
        float scale = 3f;

        GlStateManager.scale(scale, scale, scale);

        this.mainModel.render(entity, 0,0,0,0,0,0);

        GlStateManager.disableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
    }


    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return DEFAULT_RES_LOC;
    }
}

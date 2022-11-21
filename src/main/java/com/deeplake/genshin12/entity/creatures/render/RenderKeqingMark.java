package com.deeplake.genshin12.entity.creatures.render;

import com.deeplake.genshin12.entity.creatures.model.ModelKeqingMark;
import com.deeplake.genshin12.entity.special.EntityBarbaraBuff;
import com.deeplake.genshin12.entity.special.EntityBarbaraBuffClientVer;
import com.deeplake.genshin12.entity.special.EntityKeqingMark;
import com.deeplake.genshin12.util.Reference;
import net.minecraft.client.model.IMultipassModel;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;

public class RenderKeqingMark extends Render<EntityKeqingMark> {
    ModelKeqingMark modelKeqingMark = new ModelKeqingMark();
    public static final ResourceLocation RES_LOC = new ResourceLocation(Reference.MOD_ID + ":textures/entity/keqing_mark.png");
    public RenderKeqingMark(RenderManager renderManager) {
        super(renderManager);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityKeqingMark entity) {
        return RES_LOC;
    }

    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(EntityKeqingMark entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GlStateManager.pushMatrix();
        this.setupTranslation(x, y+1f, z);
        this.setupRotation(entity, entityYaw, partialTicks);
        this.bindEntityTexture(entity);

        if (this.renderOutlines)
        {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        this.modelKeqingMark.render(entity, partialTicks, 0.0F, 0.1F, 0.0F, 0.0F, 0.0625F);

        if (this.renderOutlines)
        {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    public void setupRotation(EntityKeqingMark p_188311_1_, float p_188311_2_, float p_188311_3_)
    {
        GlStateManager.rotate(5.729f * p_188311_1_.world.getTotalWorldTime(), 0f, 1f, 0f);//rotation with time
        GlStateManager.rotate(180.0F - p_188311_2_, 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
    }

    public void setupTranslation(double p_188309_1_, double p_188309_3_, double p_188309_5_)
    {
        GlStateManager.translate((float)p_188309_1_, (float)p_188309_3_ + 0.375F, (float)p_188309_5_);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityBarbaraBuffClientVer entity)
    {
        return RES_LOC;
    }

    public boolean isMultipass()
    {
        return false;
    }

    public void renderMultipass(EntityKeqingMark p_188300_1_, double p_188300_2_, double p_188300_4_, double p_188300_6_, float p_188300_8_, float p_188300_9_)
    {
        GlStateManager.pushMatrix();
        this.setupTranslation(p_188300_2_, p_188300_4_, p_188300_6_);
        this.setupRotation(p_188300_1_, p_188300_8_, p_188300_9_);
        this.bindEntityTexture(p_188300_1_);
//        ((IMultipassModel)this.modelKeqingMark).renderMultipass(p_188300_1_, p_188300_9_, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GlStateManager.popMatrix();
    }
}

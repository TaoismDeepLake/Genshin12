package com.deeplake.genshin12.entity.creatures.render;

import com.deeplake.genshin12.entity.creatures.model.ModelBarbara;
import com.deeplake.genshin12.entity.creatures.model.ModelBarbara2;
import com.deeplake.genshin12.entity.special.EntityBarbaraBuffClientVer;
import com.deeplake.genshin12.util.Reference;
import net.minecraft.client.model.IMultipassModel;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderBarbaraEClient extends Render<EntityBarbaraBuffClientVer> {
    ModelBarbara2 modelBarbara = new ModelBarbara2();
    public static final ResourceLocation RES_LOC = new ResourceLocation(Reference.MOD_ID + ":textures/entity/barbara_e_1.png");

    public RenderBarbaraEClient(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
        this.shadowSize = 0.5F;
    }

    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(EntityBarbaraBuffClientVer entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GlStateManager.pushMatrix();
        this.setupTranslation(x, y, z);
        this.setupRotation(entity, entityYaw, partialTicks);
        this.bindEntityTexture(entity);

        if (this.renderOutlines)
        {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        this.modelBarbara.render(entity, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        if (this.renderOutlines)
        {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    public void setupRotation(EntityBarbaraBuffClientVer p_188311_1_, float p_188311_2_, float p_188311_3_)
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
        return true;
    }

    public void renderMultipass(EntityBarbaraBuffClientVer p_188300_1_, double p_188300_2_, double p_188300_4_, double p_188300_6_, float p_188300_8_, float p_188300_9_)
    {
        GlStateManager.pushMatrix();
        this.setupTranslation(p_188300_2_, p_188300_4_, p_188300_6_);
        this.setupRotation(p_188300_1_, p_188300_8_, p_188300_9_);
        this.bindEntityTexture(p_188300_1_);
        ((IMultipassModel)this.modelBarbara).renderMultipass(p_188300_1_, p_188300_9_, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GlStateManager.popMatrix();
    }
}

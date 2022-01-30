package com.deeplake.genshin12.entity.creatures.render.layer;

import com.deeplake.genshin12.IdlFramework;
import com.deeplake.genshin12.util.EntityUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

@SideOnly(Side.CLIENT)
public class LayerPetrify implements LayerRenderer<EntityLivingBase> {
    private final RenderLivingBase<?> renderer;
    public static ResourceLocation OVERLAY = new ResourceLocation("textures/blocks/dirt.png");
    public static ResourceLocation OVERLAY_ROCK = new ResourceLocation(IdlFramework.MODID,"textures/blocks/petrify.png");
    public static ResourceLocation OVERLAY_LIGHT = new ResourceLocation(IdlFramework.MODID,"textures/blocks/petrify_light.png");

    public LayerPetrify(RenderLivingBase<?> rendererIn)
    {
        this.renderer = rendererIn;
    }

    @Override
    public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        int i = 30;
        doRenderLayerPetrify(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
    }

    public void doRenderLayerPetrify(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (EntityUtil.getAttr(entitylivingbaseIn, SharedMonsterAttributes.MOVEMENT_SPEED) > 0.0001f)
        {
            return;
        }

        //bg
        this.renderer.bindTexture(OVERLAY_ROCK);
        this.renderer.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        //light
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(!entitylivingbaseIn.isInvisible());
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 61680.0F, 0.0F);
        GlStateManager.enableLighting();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
        this.renderer.bindTexture(OVERLAY_LIGHT);
        this.renderer.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        Minecraft.getMinecraft().entityRenderer.setupFogColor(false);

        if (renderer instanceof RenderLiving && entitylivingbaseIn instanceof EntityLiving)
        {
            ((RenderLiving)renderer).setLightmap((EntityLiving) entitylivingbaseIn);
        }
//        GlStateManager.depthMask(true);
//        GlStateManager.disableBlend();
//        GlStateManager.enableAlpha();
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}

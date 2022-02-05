package com.deeplake.genshin12.entity.creatures.render.layer;

import com.deeplake.genshin12.IdlFramework;
import com.deeplake.genshin12.potion.ModPotions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerFrozen implements LayerRenderer<EntityLivingBase> {
    private final RenderLivingBase<?> renderer;
    public static ResourceLocation OVERLAY_ICE = new ResourceLocation("textures/blocks/ice.png");

    public LayerFrozen(RenderLivingBase<?> rendererIn)
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
        IAttributeInstance attribute = entitylivingbaseIn.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED) ;
        if (attribute==null || attribute.getModifier(ModPotions.UUID_FREEZE) == null)
        {
            return;
        }

        //bg
        this.renderer.bindTexture(OVERLAY_ICE);
        this.renderer.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}

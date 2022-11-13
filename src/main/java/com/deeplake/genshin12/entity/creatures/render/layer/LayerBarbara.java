package com.deeplake.genshin12.entity.creatures.render.layer;

import com.deeplake.genshin12.entity.special.EntityBarbaraBuff;
import com.deeplake.genshin12.entity.special.EntityBarbaraBuffClientVer;
import com.deeplake.genshin12.potion.ModPotions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//discarded.
@SideOnly(Side.CLIENT)
public class LayerBarbara implements LayerRenderer<EntityLivingBase> {
    private final RenderLivingBase<?> renderer;
    private EntityBarbaraBuff entityBarbaraBuff;

    public LayerBarbara(RenderLivingBase<?> rendererIn)
    {
        this.renderer = rendererIn;
    }

    @Override
    public void doRenderLayer(EntityLivingBase player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (!ModPotions.BUFF_BARBARA.hasPotion(player))
        {
            return;
        }

//        if (!(player instanceof EntityPlayer))
//        {
//            return;
//        }

        if (entityBarbaraBuff == null)
        {
            entityBarbaraBuff = new EntityBarbaraBuffClientVer(player.world, player);
        }

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(!player.isInvisible());
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 61680.0F, 0.0F);
        GlStateManager.enableLighting();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().entityRenderer.setupFogColor(true);

        World world = player.world;
        long ticks = world.getTotalWorldTime();
        if (ticks % 8 == 0)
        {
            double omega = 0.1f;
            double cycle = 2 * Math.PI;
            double delta = (omega * ticks) % cycle;
            int MAX_NOTE = 6;

            for (int i = 0; i < MAX_NOTE; i++) {
                double phase = i / (double)MAX_NOTE;
                double theta = cycle * phase + delta;
                world.spawnParticle(EnumParticleTypes.NOTE,
                        player.posX + Math.cos(theta),
                        player.posY + 0.7f,
                        player.posZ + Math.sin(theta),
                        phase,0,0);
            }
        }

        entityBarbaraBuff.setPosition(player.posX, player.posY, player.posZ);
        //will crash should I use this ver! careful.
        //entityBarbaraBuff.setPositionAndUpdate(player.posX, player.posY, player.posZ);

        this.renderer.getRenderManager().renderEntity(entityBarbaraBuff, 0.0D, 0.5, 0.0D, player.rotationYaw, partialTicks, false);
        Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();

        GlStateManager.popMatrix();
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}

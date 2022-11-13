package com.deeplake.genshin12.entity.creatures.render;

import com.deeplake.genshin12.entity.creatures.model.ModelBarbara;
import com.deeplake.genshin12.entity.special.EntityBarbaraBuff;
import com.deeplake.genshin12.entity.special.EntityEnergyOrb;
import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;

public class RenderBarbaraE extends Render<EntityBarbaraBuff> {
    ModelBarbara modelBarbara = new ModelBarbara();
    public static final ResourceLocation RES_LOC = new ResourceLocation(Reference.MOD_ID + ":textures/entity/barbara_e_1.png");
    public RenderBarbaraE(RenderManager renderManager) {
        super(renderManager);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityBarbaraBuff entity) {
        return RES_LOC;
    }

    @Override
    public void doRender(EntityBarbaraBuff entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        Vec3d pos;
        if (partialTicks == 9.0F)
        {
            pos = new Vec3d(0,0,0);
        }
        else
        {
            double d0 = -(entity.posX - entity.prevPosX) * (double)partialTicks;
            double d1 = -(entity.posY - entity.prevPosY) * (double)partialTicks;
            double d2 = -(entity.posZ - entity.prevPosZ) * (double)partialTicks;

//            EntityPlayer player = Minecraft.getMinecraft().player;
//            double d0 = (player.posX - player.prevPosX) * (double)partialTicks;
//            double d1 = (player.posY - player.prevPosY) * (double)partialTicks;
//            double d2 = (player.posZ - player.prevPosZ) * (double)partialTicks;
            pos = new Vec3d(d0, d1, d2);
        }

        GlStateManager.pushMatrix();
//        GlStateManager.translate(-x, -y, -z);
        GlStateManager.translate(x, y, z);
//        GlStateManager.translate(pos.x,pos.y,pos.z);
        GlStateManager.scale(0.1, 0.1, 0.1);
        GlStateManager.rotate(2f * entity.world.getTotalWorldTime(), 0f, 1f, 0f);
        GlStateManager.enableLighting();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        renderModel(entity, 0,0,0,0,0,1f);
        GlStateManager.popMatrix();
    }

    protected void renderModel(Entity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor)
    {
        this.bindTexture(RES_LOC);
        this.modelBarbara.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
    }
}

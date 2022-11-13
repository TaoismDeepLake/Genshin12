package com.deeplake.genshin12.entity.creatures.render.layer;

import com.deeplake.genshin12.entity.creatures.render.RenderBarbaraE;
import com.deeplake.genshin12.entity.special.EntityBarbaraBuff;
import com.deeplake.genshin12.potion.ModPotions;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

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
//        if (!ModPotions.BUFF_BARBARA.hasPotion(player))
//        {
//            return;
//        }

        if (!(player instanceof EntityPlayer))
        {
            return;
        }

        if (entityBarbaraBuff == null)
        {
            entityBarbaraBuff = new EntityBarbaraBuff(player.world);
        }
//        entityBarbaraBuff.setPosition(player.posX, player.posY, player.posZ);

        GlStateManager.pushMatrix();
//        Random random = new Random(player.getEntityId());
//        ModelRenderer modelrenderer = this.renderer.getMainModel().getRandomModelBox(random);
//        modelrenderer.postRender(0.0625F);

        this.renderer.getRenderManager().renderEntity(entityBarbaraBuff, 0.0D, 0, 0.0D, player.rotationYaw, partialTicks, false);
        GlStateManager.popMatrix();
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}

package com.deeplake.genshin12.designs.client;

import com.deeplake.genshin12.IdlFramework;
import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.potion.ModPotions;
import com.deeplake.genshin12.potion.buff.BaseSimplePotion;
import com.google.common.base.Enums;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import scala.xml.dtd.impl.Base;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Mod.EventBusSubscriber(modid = IdlFramework.MODID)
@SideOnly(Side.CLIENT)
public class RenderElementalIcon {

    static HashMap<BaseSimplePotion, ResourceLocation> map = new HashMap<>();

    static ResourceLocation location(String name)
    {
        return new ResourceLocation(IdlFramework.MODID, "textures/items/basic/" + name + ".png");
    }

    public static void processElemental(BaseSimplePotion potion)
    {
        map.put(potion, location(potion.getRegistryName().getResourcePath()));
    }

    public static void init()
    {
        processElemental(ModPotions.ANEMO);
        processElemental(ModPotions.GEO);
        processElemental(ModPotions.PYRO);
        processElemental(ModPotions.CYRO);
        processElemental(ModPotions.DENDRO);
        processElemental(ModPotions.ELECTRO);
        processElemental(ModPotions.HYDRO);
    }

    //learnt from AOA3
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void renderIcons(final RenderLivingEvent.Specials.Pre ev) {

        EntityLivingBase entity = ev.getEntity();

        if (entity.posX == 0 && entity.posY == 0 && entity.posZ == 0)
            return;

        HashSet<BaseSimplePotion> valid = new HashSet<>();
        for (BaseSimplePotion potion:
             map.keySet()) {
            if (potion.hasPotion(entity))
            {
                valid.add(potion);
            }
        }

        int maxCountPerRow = 5;
        float padding = 0.05f;
        float size = 0.4f;
        float perUnit = padding + size;

        float yOffset = (float) ModConfig.GUI_CONF.RENDER_ELEM_Y_OFFSET;
        float initXOffset = (size - Math.min(maxCountPerRow, valid.size()) * perUnit) / 2f;
        float xOffset = initXOffset;
        float rowMax = (size * maxCountPerRow + padding * (maxCountPerRow - 1)) / 2f - size;

        for (BaseSimplePotion potion:
                valid) {
            renderIcon(map.get(potion), xOffset, yOffset, ev);

            xOffset += perUnit;

            if (xOffset > rowMax) {
                yOffset += perUnit;
                xOffset = initXOffset;
            }
        }
    }

    private static void renderIcon(ResourceLocation texture, float xOffset, float yOffset, RenderLivingEvent.Specials.Pre event, String... msg) {
        GlStateManager.pushMatrix();
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buffer = tess.getBuffer();
        Minecraft mc = Minecraft.getMinecraft();

        GlStateManager.translate(event.getX(), 0.2d + event.getY() + event.getEntity().getEntityBoundingBox().maxY - event.getEntity().getEntityBoundingBox().minY, event.getZ());
        GlStateManager.rotate(180f - mc.getRenderManager().playerViewY, 0, 1, 0);
        GlStateManager.rotate(-mc.getRenderManager().playerViewX, 1, 0, 0);
        GlStateManager.translate(xOffset, yOffset, 0);
        GlStateManager.scale(0.45f, 0.45f, 0.45f);
        GL11.glDisable(2896);
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
        buffer.pos(-0.5d, -0.25d, 0).tex(0, 1).normal(0.0f, 1.0f, 0.0f).endVertex();
        buffer.pos(0.5d, -0.25d, 0).tex(1, 1).normal(0.0f, 1.0f, 0.0f).endVertex();
        buffer.pos(0.5d, 0.75d, 0).tex(1, 0).normal(0.0f, 1.0f, 0.0f).endVertex();
        buffer.pos(-0.5d, 0.75d, 0).tex(0, 0).normal(0.0f, 1.0f, 0.0f).endVertex();
        tess.draw();
        GlStateManager.popMatrix();
    }
}

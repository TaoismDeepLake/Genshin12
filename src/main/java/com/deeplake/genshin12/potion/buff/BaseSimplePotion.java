package com.deeplake.genshin12.potion.buff;

import com.deeplake.genshin12.potion.ModPotions;
import com.deeplake.genshin12.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.UUID;

public class BaseSimplePotion extends Potion {
    public static final ResourceLocation resource = new ResourceLocation("genshin12","textures/misc/potions.png");
    protected final int iconIndex;
    public UUID UUID_CLIENT = null;

    public BaseSimplePotion(boolean isBadEffectIn, int liquidColorIn, String name, int icon) {
        super(isBadEffectIn, liquidColorIn);
        setRegistryName(new ResourceLocation(Reference.MOD_ID, name));
        setPotionName("genshin12.potion." + name);
        iconIndex = icon;

        ModPotions.INSTANCES.add(this);
    }

    public BaseSimplePotion setUUID_CLIENT(String s)
    {
        UUID_CLIENT = UUID.fromString(s);
        registerPotionAttributeModifier(SharedMonsterAttributes.MAX_HEALTH, s, 0f, 0);
        return this;
    }

    public boolean hasPotion(EntityLivingBase livingBase)
    {
        if (livingBase.getEntityWorld() != null)
        {
            if (livingBase.getEntityWorld().isRemote)
            {
                IAttributeInstance attribute = livingBase.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH) ;
                if (attribute==null || attribute.getModifier(UUID_CLIENT) == null)
                {
                    return false;
                }
                else {
                    return true;
                }
            } else {
                return livingBase.isPotionActive(this);
            }
        }
        else {
            return false;
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean hasStatusIcon()
    {
        return this.iconIndex >= 0;
    }

    @SideOnly(Side.CLIENT)
    protected void render(int x, int y, float alpha) {
        Minecraft.getMinecraft().renderEngine.bindTexture(resource);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buf = tessellator.getBuffer();
        buf.begin(7, DefaultVertexFormats.POSITION_TEX);
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.color(1, 1, 1, alpha);

        int textureX = iconIndex % 14 * 18;
        int textureY = 234 - iconIndex / 14 * 18;

        buf.pos(x, y + 18, 0).tex(textureX * 0.00390625, (textureY + 18) * 0.00390625).endVertex();
        buf.pos(x + 18, y + 18, 0).tex((textureX + 18) * 0.00390625, (textureY + 18) * 0.00390625).endVertex();
        buf.pos(x + 18, y, 0).tex((textureX + 18) * 0.00390625, textureY * 0.00390625).endVertex();
        buf.pos(x, y, 0).tex(textureX * 0.00390625, textureY * 0.00390625).endVertex();

        tessellator.draw();
    }




    @Override
    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
        render(x + 6, y + 7, 1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
        render(x + 3, y + 3, alpha);
    }
}

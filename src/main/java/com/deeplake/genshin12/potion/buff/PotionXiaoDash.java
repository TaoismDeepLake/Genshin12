package com.deeplake.genshin12.potion.buff;

import com.deeplake.genshin12.Idealland;
import com.deeplake.genshin12.potion.ModPotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class PotionXiaoDash extends BaseSimplePotion {
    public PotionXiaoDash(boolean isBadEffectIn, int liquidColorIn, String name, int icon) {
        super(isBadEffectIn, liquidColorIn, name, icon);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
        super.performEffect(entityLivingBaseIn, amplifier);
//        if (!entityLivingBaseIn.isServerWorld())
//        {
//            entityLivingBaseIn.motionX /= 0.98D;
//            entityLivingBaseIn.motionY /= 0.98D;
//            entityLivingBaseIn.motionZ /= 0.98D;
//        }

        //attack nearby here?
    }

    @Override
    public void applyAttributesModifiersToEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier) {
        super.applyAttributesModifiersToEntity(entityLivingBaseIn, attributeMapIn, amplifier);
        entityLivingBaseIn.setNoGravity(true);
    }

    @Override
    public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier) {
        super.removeAttributesModifiersFromEntity(entityLivingBaseIn, attributeMapIn, amplifier);
        entityLivingBaseIn.setNoGravity(false);
    }

    @SubscribeEvent
    public static void onKB(LivingKnockBackEvent event)
    {
        if (event.getEntityLiving().isPotionActive(ModPotions.XIAO_DASH))
        {
            event.setCanceled(true);
        }
    }
}

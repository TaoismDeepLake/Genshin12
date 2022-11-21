package com.deeplake.genshin12.potion.buff;

import com.deeplake.genshin12.potion.ModPotions;
import com.deeplake.genshin12.util.ElementalUtil;
import com.deeplake.genshin12.util.EnumAmount;
import com.deeplake.genshin12.util.EnumElemental;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PotionKeqingEnchant extends BaseSimplePotion{
    public PotionKeqingEnchant(boolean isBadEffectIn, int liquidColorIn, String name, int icon) {
        super(isBadEffectIn, liquidColorIn, name, icon);
    }

    @SubscribeEvent
    public void onHit(LivingHurtEvent event)
    {
        EntityLivingBase hurtOne = event.getEntityLiving();
        DamageSource source = event.getSource();
        World world = hurtOne.getEntityWorld();
        if (source.getTrueSource() instanceof EntityPlayer)
        {
            float amount = event.getAmount();
            EntityPlayer attacker = (EntityPlayer) source.getTrueSource();
            if (attacker.isPotionActive(ModPotions.HUTAO_BUFF))
//                && attacker.getCooledAttackStrength(0f) > 0.99f)
            {
                PotionEffect buff = attacker.getActivePotionEffect(ModPotions.KEQING_ENCHANT);

                if (!world.isRemote)
                {
                    //replace the damage with fire-magic
                    if (source.isMagicDamage()) {
                        return;
                    }
                    if (source.isProjectile()) {
                        return;
                    }
                    event.setCanceled(true);
                    hurtOne.hurtResistantTime = 0;
                    ElementalUtil.applyElementalDamage(attacker, hurtOne, event.getAmount(), EnumElemental.ELECTRO, EnumAmount.SMALL);

                }
            }
        }
    }
}

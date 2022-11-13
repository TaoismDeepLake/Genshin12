package com.deeplake.genshin12.potion.buff;

import com.deeplake.genshin12.Idealland;
import com.deeplake.genshin12.entity.special.EntityBarbaraBuff;
import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.potion.ModPotions;
import com.deeplake.genshin12.util.*;
import com.deeplake.genshin12.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

//  /effect @e[type=genshin12:hilichurl] genshin12:barbara_e 30
@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class BuffBarbara extends BaseSimplePotion{
    public BuffBarbara(boolean isBadEffectIn, int liquidColorIn, String name, int icon) {
        super(isBadEffectIn, liquidColorIn, name, icon);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    //this only runs on server. client should figure out another way.
    @Override
    public void applyAttributesModifiersToEntity(EntityLivingBase livingBase, AbstractAttributeMap attributeMapIn, int amplifier) {
        super.applyAttributesModifiersToEntity(livingBase, attributeMapIn, amplifier);
//        World world = livingBase.world;
//        if (livingBase.getEntityWorld().isRemote)
//        {
//            //pure client side
//            EntityBarbaraBuff barbaraBuff = new EntityBarbaraBuff(world, livingBase);
//            world.spawnEntity(barbaraBuff);
//        }
    }

    @Override
    public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier) {
        super.removeAttributesModifiersFromEntity(entityLivingBaseIn, attributeMapIn, amplifier);
    }

    public static final int PERIOD = 5 * CommonDef.TICK_PER_SECOND;
    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
        super.performEffect(entityLivingBaseIn, amplifier);
        int getTargetTick = entityLivingBaseIn.ticksExisted;
        //potion class does not know dura.

        if (getTargetTick % PERIOD == 0) {
            World world = entityLivingBaseIn.getEntityWorld();
            if (!world.isRemote) {
                entityLivingBaseIn.heal((float) getHeal(amplifier + 1, entityLivingBaseIn));
                ElementalUtil.applyElemental(entityLivingBaseIn, EnumElemental.HYDRO, EnumAmount.SMALL);
                //not correct. should remove the 0.8 penalty
            }
        }
    }

    public static double[] onHit_regen_ratio = new double[]{0.75, 0.81, 0.86, 0.94, 0.99, 1.05, 1.13, 1.2, 1.27, 1.35, 1.43, 1.5, 1.59};
    public static double[] onHit_regen_fixed = new double[]{72.2, 79.44, 97.27, 95.69, 104.72, 124.59, 135.42, 146.86, 158.9, 171.54, 184.78, 198.63};

    public static double[] continue_regen = new double[]{4f,4.3f,4.6f,5,5.3f,5.6,6.4,6.8,7.2,7.6,8,8.5};
    public static double[] continue_regen_fixed = new double[]{385.18,423.71,465.44,510.39,558.54,609.91,664.48,722.27,783.27,847.47,914.89,985.52,1059};

    double getHeal(int level, EntityLivingBase caster) {
        try {
            return continue_regen[level - 1] / 100f * caster.getMaxHealth() + continue_regen_fixed[level - 1] / ModConfig.GeneralConf.HP_GENSHIN_TO_MC;
        } catch (ArrayIndexOutOfBoundsException e) {
            return continue_regen[0] / 100f * caster.getMaxHealth() + continue_regen_fixed[0] / ModConfig.GeneralConf.HP_GENSHIN_TO_MC;
        }
    }

    @SubscribeEvent
    public static void onHit(LivingHurtEvent event)
    {
        EntityLivingBase hurtOne = event.getEntityLiving();
        DamageSource source = event.getSource();
        World world = hurtOne.getEntityWorld();
        if (source.getTrueSource() instanceof EntityPlayer)
        {
            float amount = event.getAmount();
            EntityPlayer attacker = (EntityPlayer) source.getTrueSource();
            if (attacker.isPotionActive(ModPotions.BUFF_BARBARA))
//                && attacker.getCooledAttackStrength(0f) > 0.99f)
            {
                int level = EntityUtil.getBuffLevelIDL(attacker, ModPotions.BUFF_BARBARA);
                if (!world.isRemote)
                {
                    //replace the damage with water-magic
                    if (source.isMagicDamage()) {
                        return;
                    }
                    if (source.isProjectile()) {
                        return;
                    }
                    event.setCanceled(true);
                    hurtOne.hurtResistantTime = 0;
                    ElementalUtil.applyElemental(hurtOne, EnumElemental.HYDRO, EnumAmount.SMALL);
                    double heal = 0;
                    try {
                        heal = onHit_regen_ratio[level -1] / 100f * attacker.getMaxHealth() + onHit_regen_fixed[level -1] / ModConfig.GeneralConf.HP_GENSHIN_TO_MC;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        heal = onHit_regen_ratio[0] / 100f * attacker.getMaxHealth() + onHit_regen_fixed[0] / ModConfig.GeneralConf.HP_GENSHIN_TO_MC;
                    }
                    attacker.heal((float) heal);
                    //note that blood blossom will also trigger this, hence dura will be constantly refreshed.

                }
            }
        }
    }
}

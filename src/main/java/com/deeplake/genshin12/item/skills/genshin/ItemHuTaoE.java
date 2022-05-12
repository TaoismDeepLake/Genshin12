package com.deeplake.genshin12.item.skills.genshin;

import com.deeplake.genshin12.entity.special.EntityEnergyOrb;
import com.deeplake.genshin12.item.IWIP;
import com.deeplake.genshin12.potion.ModPotions;
import com.deeplake.genshin12.potion.buff.PotionHuTaoDebuff;
import com.deeplake.genshin12.util.*;
import com.deeplake.genshin12.util.NBTStrDef.IDLNBTDef;
import com.deeplake.genshin12.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemHuTaoE extends ItemGenshinSkillBase {
    int buff_ticks = CommonDef.TICK_PER_SECOND * 9;
    int debuff_ticks = CommonDef.TICK_PER_SECOND * 8 + 2;//it will deal damage twice.

//    3.84,4.07,4.3,4.6,4.83,5.06,5.36,5.66,5.96,6.26,6.55,6.85,7.15
//
//            64,68.8,73.6,80,84.8,89.6,96,102.4,108.8,115.2,121.6,128,136

    public ItemHuTaoE(String name) {
        super(name, EnumElemental.PYRO);
        setCD(16f, 0f);
        setMaxLevel(13);
        CommonFunctions.addToEventBus(this);
    }

    @Override
    public boolean applyCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot) {
        if (!worldIn.isRemote)
        {
            GenshinUtil.dealAoEKnockBack(livingBase.getPositionVector(), livingBase, 0.4f, 3f);

            livingBase.setHealth(Math.max(0.1f, livingBase.getHealth() - livingBase.getHealth() * 0.3f));
            livingBase.addPotionEffect(new PotionEffect(ModPotions.HUTAO_BUFF, buff_ticks, getLevel(stack) - 1));
            //drop immediately. otherwise too complicated!
            EntityEnergyOrb.drop(livingBase, -3, EnumElemental.PYRO);
        }
        return super.applyCast(worldIn, livingBase, stack, slot);
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
                PotionEffect buff = attacker.getActivePotionEffect(ModPotions.HUTAO_BUFF);

                if (!world.isRemote)
                {
                    //replace the damage with fire-magic
                    if (source.isFireDamage() && source.isMagicDamage()) {
                        return;
                    }
                    if (source.isProjectile()) {
                        return;
                    }
                    event.setCanceled(true);
                    hurtOne.hurtResistantTime = 0;
                    ElementalUtil.applyElementalDamage(attacker, hurtOne, event.getAmount(), EnumElemental.PYRO, EnumAmount.SMALL);
                    //note that blood blossom will also trigger this, hence dura will be constantly refreshed.
                    if (buff != null)
                    {
                        //it's ok to repeat apply.
                        applyBloodBlossom(hurtOne, world, attacker, buff.getAmplifier());
                    }
                }
            }
        }
    }

    public void applyBloodBlossom(EntityLivingBase hurtOne, World world, EntityPlayer attacker, int level) {
        IDLNBTUtil.SetString(hurtOne, IDLNBTDef.HU_TAO_CASTER, attacker.getUniqueID().toString());

        //don't interrupt existing tick
        if (IDLNBTUtil.GetIntAuto(hurtOne, IDLNBTDef.HU_TAO_TICK, -1) < 0)
        {
            IDLNBTUtil.SetInt(hurtOne, IDLNBTDef.HU_TAO_TICK, (int) ((world.getTotalWorldTime() - 1) % PotionHuTaoDebuff.PERIOD));
        }

        hurtOne.addPotionEffect(new PotionEffect(ModPotions.HUTAO_DEBUFF, debuff_ticks, level));
    }


//    Hu Tao consumes a set portion of her HP to knock the surrounding enemies back and enter the Paramita Papilio state.
//
//            Paramita Papilio
//    Increases Hu Tao's ATK based on her Max HP at the time of entering this state. ATK Bonus gained this way cannot exceed 400% of Hu Tao's Base ATK.
//    Converts attack DMG to Pyro DMG, which cannot be overridden by any other elemental infusion.
//    Charged Attacks apply the Blood Blossom effect to the enemies hit.
//    Increases Hu Tao's resistance to interruption.
//    Blood Blossom
//    Enemies affected by Blood Blossom will take Pyro DMG every 4s. This DMG is considered Elemental Skill DMG.
//    Each enemy can be affected by only one Blood Blossom effect at a time, and its duration may only be refreshed by Hu Tao herself.
}

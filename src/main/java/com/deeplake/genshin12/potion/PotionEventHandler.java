package com.deeplake.genshin12.potion;

import com.deeplake.genshin12.entity.creatures.attribute.EventsHandleShield;
import com.deeplake.genshin12.potion.buff.BasePotion;
import com.deeplake.genshin12.util.EntityUtil;
import com.deeplake.genshin12.util.NBTStrDef.IDLNBTDef;
import com.deeplake.genshin12.util.NBTStrDef.IDLNBTUtil;
import com.deeplake.genshin12.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Collection;

import static net.minecraftforge.fml.common.eventhandler.Event.Result.*;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class PotionEventHandler {

//    @SideOnly(Side.CLIENT)
//    @SubscribeEvent
//    public static void onRender(RenderLivingEvent.Pre event)
//    {
//        EntityLivingBase livingBase = event.getEntity();
//        if (EntityUtil.getID(livingBase, SharedMonsterAttributes.MOVEMENT_SPEED) < 0.0001f)
//        {
//            GlStateManager.color(ModConfig.DEBUG_CONF.PERTIFY_R,
//                    ModConfig.DEBUG_CONF.PERTIFY_G,
//                    ModConfig.DEBUG_CONF.PERTIFY_B);
//        }
//    }

    @SubscribeEvent
    public static void onUpdate(LivingEvent.LivingUpdateEvent evt) {
        EntityLivingBase livingBase = evt.getEntityLiving();
        if (!livingBase.world.isRemote)
        {
            if (livingBase.getActivePotionEffect(ModPotions.HUTAO_DEBUFF) == null)
            {
                if (IDLNBTUtil.GetIntAuto(livingBase, IDLNBTDef.HU_TAO_TICK, -1) >= 0)
                {
                    //clear ticker
                    IDLNBTUtil.SetInt(livingBase, IDLNBTDef.HU_TAO_TICK, -1);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onCreatureHurt(LivingHurtEvent evt) {
        handleGeneralEffects(evt);
        //special effects
        jadeShieldFortify(evt);
    }

    public static void jadeShieldFortify(LivingHurtEvent evt) {
        World world = evt.getEntity().getEntityWorld();
        if (!evt.isCanceled() && !world.isRemote)
        {
            EntityLivingBase hurtOne = evt.getEntityLiving();

            int buffLevel = EntityUtil.getBuffLevelIDL(hurtOne, ModPotions.JADE_SHIELD);
            if (buffLevel > 0 && hurtOne.getAbsorptionAmount() > 0 && buffLevel <= 5)
            {
                //When the Jade Shield takes DMG, it will Fortify:
                //Fortified characters have 5% increased Shield Strength.
                //Can stack up to 5 times, and lasts until the Jade Shield disappears
                //Fortify times = 0,1,2,3,4,5
                int dura = hurtOne.getActivePotionEffect(ModPotions.JADE_SHIELD).getDuration();
                EntityUtil.ApplyBuff(hurtOne, ModPotions.JADE_SHIELD, buffLevel, dura);//actually +1 level
            }
        }
    }

    public static void handleGeneralEffects(LivingHurtEvent evt) {
        World world = evt.getEntity().getEntityWorld();
        EntityLivingBase hurtOne = evt.getEntityLiving();

        //Base Damage Reduction
        Collection<PotionEffect> activePotionEffects = hurtOne.getActivePotionEffects();
        for (int i = 0; i < activePotionEffects.size(); i++) {
            PotionEffect buff = (PotionEffect)activePotionEffects.toArray()[i];
            if (buff.getPotion() instanceof BasePotion)
            {
                BasePotion modBuff = (BasePotion)buff.getPotion();
                if (!world.isRemote)
                {
                    float reduceRatio = modBuff.getDamageReductionMultiplier(buff.getAmplifier());
                    evt.setAmount((1 - reduceRatio) * evt.getAmount());
                }
            }
        }

        Entity trueSource = evt.getSource().getTrueSource();
        if (trueSource instanceof EntityLivingBase){
            EntityLivingBase sourceCreature = (EntityLivingBase)trueSource;

            //Apply damage multiplier
            Collection<PotionEffect> activePotionEffectsAttacker = sourceCreature.getActivePotionEffects();
            for (int i = 0; i < activePotionEffectsAttacker.size(); i++) {
                PotionEffect buff = (PotionEffect)activePotionEffectsAttacker.toArray()[i];
                if (buff.getPotion() instanceof BasePotion)
                {
                    BasePotion modBuff = (BasePotion)buff.getPotion();
                    if (!world.isRemote)
                    {
                        evt.setAmount((1 + modBuff.getAttackMultiplier(buff.getAmplifier())) * evt.getAmount());
                    }
                }
            }

            //give up idl critical system
//            //Critical Judgement
//            if (!(trueSource instanceof EntityPlayer)) {//Players have their own critical judgement system. Now we add the non-player system.
//                float critRate = 0.1f;
//                boolean isCritical = false;
//
//                //Critical chance buff
//                activePotionEffects = ((EntityLivingBase) trueSource).getActivePotionEffects();
//                for (int i = 0; i < activePotionEffects.size(); i++) {
//                    PotionEffect buff = (PotionEffect) activePotionEffects.toArray()[i];
//                    if (buff.getPotion() instanceof BasePotion) {
//                        BasePotion modBuff = (BasePotion) buff.getPotion();
//
//                        critRate += modBuff.getCritRate(buff.getAmplifier());
//                    }
//                }
//
//                if (critRate > 0 && ((EntityLivingBase) trueSource).getRNG().nextFloat() < critRate) {
//                    isCritical = true;
//                }
//
//                //Critical damage multiplier buff
//                if (isCritical) {
//                    float critDmg = 1.5f;//vanilla
//
//                    activePotionEffects = ((EntityLivingBase) trueSource).getActivePotionEffects();
//                    for (int i = 0; i < activePotionEffects.size(); i++) {
//                        PotionEffect buff = (PotionEffect) activePotionEffects.toArray()[i];
//                        if (buff.getPotion() instanceof BasePotion) {
//                            BasePotion modBuff = (BasePotion) buff.getPotion();
//
//                            critDmg += modBuff.getCritDmgModifier(buff.getAmplifier());
//                        }
//                    }
//
//                    evt.setAmount((critDmg) * evt.getAmount());
//                    //Idealland.Log(String.format("%s:isCrit = %s, x%s =%s, ", trueSource.getName(), isCritical, critDmg, evt.getAmount()));
//                }
//                //Idealland.Log(String.format("%s:isCrit = %s, x1f =%s, ", trueSource.getName(), isCritical, evt.getAmount()));
//            }
        }

        if (evt.isCanceled())
        {
            return;
        }

        //onHit effect
        for (int i = 0; i < activePotionEffects.size(); i++) {
            PotionEffect buff = (PotionEffect)activePotionEffects.toArray()[i];
            if (buff.getPotion() instanceof BasePotion)
            {
                BasePotion modBuff = (BasePotion)buff.getPotion();
                if (world.isRemote)
                {
                    modBuff.playOnHitEffect(hurtOne, evt.getAmount());
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onCreatureKB(LivingKnockBackEvent evt) {
        World world = evt.getEntity().getEntityWorld();
        EntityLivingBase hurtOne = evt.getEntityLiving();
        if (!world.isRemote) {
            //Handle virtue and undead
            Entity trueSource = evt.getOriginalAttacker();
            if (trueSource instanceof EntityLivingBase){
                EntityLivingBase sourceCreature = (EntityLivingBase)trueSource;
            }

            //KB Reduction
            if (evt.isCanceled())
            {
                return;
            }

            Collection<PotionEffect> activePotionEffects = hurtOne.getActivePotionEffects();
            for (int i = 0; i < activePotionEffects.size(); i++) {
                PotionEffect buff = (PotionEffect)activePotionEffects.toArray()[i];
                if (buff.getPotion() instanceof BasePotion)
                {
                    BasePotion modBuff = (BasePotion)buff.getPotion();

                    float reduceRatio = modBuff.getKBResistanceMultiplier(buff.getAmplifier());
                    evt.setStrength((1 - reduceRatio) * evt.getStrength());
                }
            }
        } else {

        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPlayerCriticalJudge(CriticalHitEvent evt) {
//        public class CriticalHitEvent extends PlayerEvent {
//            private float damageModifier;
//            private final float oldDamageModifier;
//            private final Entity target;
//            private final boolean vanillaCritical;
//        }
        EntityPlayer player = evt.getEntityPlayer();
        float critRate = 0f;

        Collection<PotionEffect> activePotionEffects = player.getActivePotionEffects();
        for (int i = 0; i < activePotionEffects.size(); i++) {
            PotionEffect buff = (PotionEffect)activePotionEffects.toArray()[i];
            if (buff.getPotion() instanceof BasePotion)
            {
                BasePotion modBuff = (BasePotion)buff.getPotion();

                critRate += modBuff.getCritRate(buff.getAmplifier());
            }
        }

        if (evt.isVanillaCritical())
        {
            if (critRate < 0 && player.getRNG().nextFloat() < (1 + critRate))
            {
                evt.setResult(DENY);
            }
        }else {
            if (critRate > 0 && player.getRNG().nextFloat() < (critRate))
            {
                evt.setResult(ALLOW);
            }
        }

        boolean isCrit = evt.getResult() == ALLOW || (evt.getResult() == DEFAULT && evt.isVanillaCritical());
        //is critical
        if (isCrit)
        {
            float critDmg = 0f;

            activePotionEffects = player.getActivePotionEffects();
            for (int i = 0; i < activePotionEffects.size(); i++) {
                PotionEffect buff = (PotionEffect)activePotionEffects.toArray()[i];
                if (buff.getPotion() instanceof BasePotion)
                {
                    BasePotion modBuff = (BasePotion)buff.getPotion();

                    critDmg += modBuff.getCritDmgModifier(buff.getAmplifier());
                }
            }

            float originalModifier = evt.getDamageModifier();
            evt.setDamageModifier(originalModifier + critDmg);
        }
    }
}

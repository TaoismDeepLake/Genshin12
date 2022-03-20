package com.deeplake.genshin12.events;

import com.deeplake.genshin12.Idealland;
import com.deeplake.genshin12.entity.creatures.attribute.ModAttributes;
import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.potion.ModPotions;
import com.deeplake.genshin12.util.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class EventsPlungedAttack {
    static final float XIAO_Q_PLUNGE_RANGE = 6f;
    static final float NORMAL_PLUNGE_RANGE_GREAT = 5f;
    static final float NORMAL_PLUNGE_RANGE = 3f;
    //sword, claymore, polearm:3m,5m;
    //catalyst & bow: 3m,3.5m

    static float getDamageFactorFromHeight(float height)
    {
        if (height >= ModConfig.DEBUG_CONF.PLUNGE_MAX_HEIGHT)
        {
            return ModConfig.DEBUG_CONF.PLUNGE_MAX_DAMAGE_FACTOR;
        }
        else if (height >= ModConfig.DEBUG_CONF.PLUNGE_MIN_HEIGHT)
        {
            return ModConfig.DEBUG_CONF.PLUNGE_MIN_DAMAGE_FACTOR;
        }
        else
        {
            return 0f;
        }
    }

    static float getRangeFromHeight(float height)
    {
        if (height >= ModConfig.DEBUG_CONF.PLUNGE_MAX_HEIGHT)
        {
            return NORMAL_PLUNGE_RANGE_GREAT;
        }
        else if (height >= ModConfig.DEBUG_CONF.PLUNGE_MIN_HEIGHT)
        {
            return NORMAL_PLUNGE_RANGE;
        }
        else
        {
            return 0f;
        }
    }

    @SubscribeEvent
    public static void onFall(LivingFallEvent event)
    {
        if (!ModConfig.GeneralConf.ENABLE_PLUNGE_ATTACK)
        {
            return;
        }

        EntityLivingBase livingBase = event.getEntityLiving();
        World world = livingBase.getEntityWorld();
        final float distance = event.getDistance();
        if (livingBase instanceof EntityPlayer && distance > ModConfig.DEBUG_CONF.PLUNGE_MIN_HEIGHT)
        {
            if (world.isRemote)
            {
                if (distance > ModConfig.DEBUG_CONF.PLUNGE_MAX_DAMAGE_FACTOR)
                {
                    world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, livingBase.posX, livingBase.posY+0.5f, livingBase.posZ, 0,0,0);
                }
                else {
                    world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, livingBase.posX, livingBase.posY+0.5f, livingBase.posZ, 0,0,0);
                }

            } else {
                float basicDamage = (float) (ModConfig.GeneralConf.DMG_ATK_PERCENT_GENSHIN_TO_MC * getDamageFactorFromHeight(distance) * ModAttributes.getAtkG(livingBase));
                float range = getRangeFromHeight(distance);

                int maskLevel = EntityUtil.getBuffLevelIDL(livingBase, ModPotions.YAKSHA_MASK);
                if (maskLevel > 0)
                {
                    //xiao mask plunge, 1U element attaching
                    GenshinUtil.dealAoEDamage(livingBase.getPositionVector(), livingBase, (float) (basicDamage * (1f + ModPotions.YAKSHA_MASK.getAtkBonus(maskLevel) / 100f)), XIAO_Q_PLUNGE_RANGE, EnumElemental.ANEMO, EnumAmount.SMALL);
                    CommonFunctions.SafeSendMsgToPlayer(TextFormatting.GREEN, livingBase, MessageDef.getXiaoPlungeKey(livingBase.getRNG().nextInt(2)));
                }
                else {
                    //normal plunge
                    GenshinUtil.dealAoEDamagePhysical(livingBase.getPositionVector(), livingBase, basicDamage, range);
                }

                if (GenshinUtil.isXiao(livingBase))
                {
                    event.setDamageMultiplier(0f);
                }
            }
        }
    }


    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onKeyPressed(InputEvent.MouseInputEvent event)
    {
        if (!ModConfig.GeneralConf.ENABLE_PLUNGE_CLIENT)
        {
            return;
        }

        Minecraft mc = Minecraft.getMinecraft();

        if (mc.player == null)
            return;

        EntityPlayerSP player = mc.player;
        //if the player is helding a genshin skill in either hand, his mid-air attack will cause plunging
        //maybe some height checking is required
        if (GenshinUtil.isGenshin(player) && player.isAirBorne && Minecraft.getMinecraft().gameSettings.keyBindAttack.isKeyDown())
        {
            for (int i = 1; i <= 2; i++)
            {
                Material materialDown = player.getEntityWorld().getBlockState(player.getPosition().down(i)).getMaterial();
                if (materialDown.isSolid() || materialDown.isLiquid())
                {
                    return;
                }
            }

            if (player.motionY > -ModConfig.DEBUG_CONF.PLUNGE_SPEED)
            {
                player.setVelocity(0f, -ModConfig.DEBUG_CONF.PLUNGE_SPEED, 0f);
            }
        }

    }
}

package com.deeplake.genshin12.designs.scaling;

import com.deeplake.genshin12.Idealland;
import com.deeplake.genshin12.designs.level.LevelSystem;
import com.deeplake.genshin12.entity.creatures.attribute.ModAttributes;
import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.item.LevelingUtil;
import com.deeplake.genshin12.util.NBTStrDef.IDLNBTDef;
import com.deeplake.genshin12.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class PlayerScaling {

    static HashSet<DimensionType> useAutoScaling = new HashSet<>();

    static double hp2 = 0.087;
    static double hp1 = 0.8563;

    //starts from 0, uses scaling of 5-star characters
    public static double getHPModifierFromLevel(int level)
    {
        float lv = level;
        return ModConfig.SPAWN_CONF.ENEMY_HP_SCALE_FACTOR * ((hp2 * lv + hp1) - 1);
//        return hp4*lv*lv*lv*lv + hp3*lv*lv*lv + hp2*lv*lv + hp1*lv;
    }

    //starts from 0
    public static double getATKModifierFromLevel(int level)
    {
        return getHPModifierFromLevel(level);
    }

    public static double getDefModifierFromLevel(int level)
    {
        return getHPModifierFromLevel(level);
    }


//     new AttributeModifier(
//            ArtifactUtil.UUID_ARTIFACT,
//            ArtifactUtil.FIX_NAME,
//            totalModifier,
//            ModAttributes.EnumAttr.getEnum(IDLNBTUtil.GetInt(stack, ArtifactUtil.KEY_MAIN_ATTR)).type);
    static final UUID LEVEL_SCALE = UUID.fromString("77ba7468-bac8-4e85-8fc8-ac7f403d998b");
    static final String LEVEL_NAME = "Genshin12 Leveling";

    public static void levelUp(EntityPlayer player)
    {
        int level = getLevel(player);
        if (level < ModConfig.SPAWN_CONF.MAX_AUTO_LEVEL)
        {
            setLevel(player, level+1);
        }
    }

    public static void setLevel(EntityPlayer player, int level)
    {
        if (!player.getEntityWorld().isRemote)
        {
            IDLNBTUtil.setPlayerIdeallandTagSafe(player, IDLNBTDef.LEVEL_TAG, level);
            LevelSystem.setLevel(player, level);
        }
    }

    //remote
    public static int getLevel(EntityPlayer player)
    {
        if (player.getEntityWorld().isRemote)
        {
            return LevelSystem.getLevel(player);
        }
        else {
            return IDLNBTUtil.getPlayerIdeallandIntSafe(player, IDLNBTDef.LEVEL_TAG);
        }
    }

    @SubscribeEvent
    public static void keep(TickEvent.PlayerTickEvent event)
    {
        EntityPlayer player = event.player;
        World world = player.world;

        if (!world.isRemote && ModConfig.DEBUG_CONF.PLAYER_LEVEL_SCALE_ENABLED)
        {
            int level = IDLNBTUtil.getPlayerIdeallandIntSafe(player, IDLNBTDef.LEVEL_TAG);

            try
            {
                IAttributeInstance def = player.getEntityAttribute(ModAttributes.DEFENSE);
                if (def != null)
                {
                    double defModifierFromLevel = getDefModifierFromLevel(level);
                    def.removeModifier(LEVEL_SCALE);
                    def.applyModifier(new AttributeModifier(LEVEL_SCALE, LEVEL_NAME, defModifierFromLevel, 1));
//                        if (ModConfig.DEBUG_CONF.DEBUG_MODE)
//                        {
//                            Idealland.Log("ATK: Lv%d, modifier = %s", level, atkMod);
//                        }
                }

                IAttributeInstance atk = player.getEntityAttribute(ModAttributes.GEN_ATK);
                if (atk != null)
                {
                    double atkMod = getATKModifierFromLevel(level) * ModConfig.DEBUG_CONF.PLAYER_ATK_SCALE_FACTOR;
                    atk.removeModifier(LEVEL_SCALE);
                    atk.applyModifier(new AttributeModifier(LEVEL_SCALE, LEVEL_NAME, atkMod, 1));
//                        if (ModConfig.DEBUG_CONF.DEBUG_MODE)
//                        {
//                            Idealland.Log("ATK: Lv%d, modifier = %s", level, atkMod);
//                        }
                }

                atk = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
                if (atk != null)
                {
                    atk.removeModifier(LEVEL_SCALE);
                }

                float ratio = player.getHealth() / player.getMaxHealth();

                IAttributeInstance hp = player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
                if (hp != null) {
                    double hpMod = getHPModifierFromLevel(level)* ModConfig.DEBUG_CONF.PLAYER_HP_SCALE_FACTOR;
                    hp.removeModifier(LEVEL_SCALE);
                    hp.applyModifier(new AttributeModifier(LEVEL_SCALE, LEVEL_NAME, hpMod, 1));

                    player.setHealth(ratio * player.getMaxHealth());

//                        if (ModConfig.DEBUG_CONF.DEBUG_MODE)
//                        {
//                            Idealland.Log("HP: Lv%d, modifier = %s", level, hpMod);
//                        }
                }

            }catch (NullPointerException e)
            {
                Idealland.LogWarning("%s, scaling NPE", player.getName());
            }

        }
    }

//    @SubscribeEvent
//    public static void onDropEXP(LivingExperienceDropEvent event)
//    {
//        int level = LevelSystem.getLevel(event.getEntityLiving());
//        int oriXp = event.getOriginalExperience();
//        float dropFactor = 10 / ModConfig.SPAWN_CONF.ENEMY_DROP_SCALE_FACTOR;
//        while (level >= dropFactor)
//        {
//            oriXp *= 2;
//            level -= dropFactor;
//        }
//
//        event.setDroppedExperience(oriXp);
//    }

//    @SubscribeEvent
//    public static void onDrop(LivingDropsEvent event)
//    {
//        int level = LevelSystem.getLevel(event.getEntityLiving());
//        List<EntityItem> oriList = new ArrayList<>(event.getDrops());
//        List<EntityItem> totalList = new ArrayList<>(event.getDrops());
//
//        float dropFactor = 10 / ModConfig.SPAWN_CONF.ENEMY_DROP_SCALE_FACTOR;
//        while (level >= dropFactor)
//        {
//            for (EntityItem item :
//                    oriList) {
//                EntityItem copy = new EntityItem(item.world, item.posX, item.posY, item.posZ, item.getItem());
//                item.world.spawnEntity(copy);
//                totalList.add(copy);
//            }
//            level -= dropFactor;
//        }
//    }
}

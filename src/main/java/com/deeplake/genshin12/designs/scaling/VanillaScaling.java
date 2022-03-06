package com.deeplake.genshin12.designs.scaling;

import com.deeplake.genshin12.IdlFramework;
import com.deeplake.genshin12.designs.level.LevelSystem;
import com.deeplake.genshin12.entity.creatures.attribute.ModAttributes;
import com.deeplake.genshin12.init.InitDimension;
import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.item.artifact.ArtifactUtil;
import com.deeplake.genshin12.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.*;

@Mod.EventBusSubscriber(modid = IdlFramework.MODID)
public class VanillaScaling {

    static HashSet<DimensionType> useAutoScaling = new HashSet<>();


    static
    {
        useAutoScaling.add(DimensionType.OVERWORLD);
        useAutoScaling.add(DimensionType.NETHER);
        useAutoScaling.add(DimensionType.THE_END);
    }

    static double hp4 = 0.00000996;
    static double hp3 = - 0.00051014;
    static double hp2 = 0.02610909;
    static double hp1 = 0.10133259;

    //starts from 0
    public static double getHPModifierFromLevel(int level)
    {
        float lv = level;
        return ModConfig.SPAWN_CONF.ENEMY_HP_SCALE_FACTOR * ((((hp4 * lv) + hp3) * lv + hp2) * lv + hp1) * lv;
//        return hp4*lv*lv*lv*lv + hp3*lv*lv*lv + hp2*lv*lv + hp1*lv;
    }

    static double atk2 = 0.0089;
    static double atk1 = 0.0369;
    //starts from 0
    public static double getATKModifierFromLevel(int level)
    {
        return ModConfig.SPAWN_CONF.ENEMY_ATK_SCALE_FACTOR * ((atk2 * (float) level) + atk1) * (float) level;
    }

    public static double getDefFromLevel(int level)
    {
        return 1 + (float) level / 100;
    }

//     new AttributeModifier(
//            ArtifactUtil.UUID_ARTIFACT,
//            ArtifactUtil.FIX_NAME,
//            totalModifier,
//            ModAttributes.EnumAttr.getEnum(IDLNBTUtil.GetInt(stack, ArtifactUtil.KEY_MAIN_ATTR)).type);
    static final UUID LEVEL_SCALE = UUID.fromString("77ba7468-bac8-4e85-8fc8-ac7f403d998b");
    static final String LEVEL_NAME = "Genshin12 Leveling";

    @SubscribeEvent
    public static void onSpawn(LivingSpawnEvent.SpecialSpawn event)
    {
        World world = event.getWorld();
        EntityLivingBase livingBase = event.getEntityLiving();

        if (ModConfig.SPAWN_CONF.AUTO_LEVEL && !world.isRemote && livingBase instanceof IMob)
        {
            DimensionType dimensionType = world.provider.getDimensionType();

            if (useAutoScaling.contains(dimensionType))
            {
                int level = (int) (Math.abs(event.getX()) / ModConfig.SPAWN_CONF.BLOCK_PER_LEVEL);
                int maxLevel = ModConfig.SPAWN_CONF.MAX_AUTO_LEVEL;
                if (maxLevel > 0 && level > maxLevel)
                {
                    level = maxLevel;
                }

                LevelSystem.setLevel(livingBase, level);

                try
                {
                    livingBase.getEntityAttribute(ModAttributes.DEFENSE).setBaseValue(getDefFromLevel(level));
                    IAttributeInstance atk = livingBase.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
                    if (atk != null)
                    {
                        double atkMod = getATKModifierFromLevel(level);
                        atk.removeModifier(LEVEL_SCALE);
                        atk.applyModifier(new AttributeModifier(LEVEL_SCALE, LEVEL_NAME, atkMod, 1));
//                        if (ModConfig.DEBUG_CONF.DEBUG_MODE)
//                        {
//                            IdlFramework.Log("ATK: Lv%d, modifier = %s", level, atkMod);
//                        }
                    }

                    float ratio = livingBase.getHealth() / livingBase.getMaxHealth();

                    IAttributeInstance hp = livingBase.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
                    if (hp != null) {
                        double hpMod = getHPModifierFromLevel(level);
                        hp.removeModifier(LEVEL_SCALE);
                        hp.applyModifier(new AttributeModifier(LEVEL_SCALE, LEVEL_NAME, hpMod, 1));

//                        livingBase.setHealth(ratio * livingBase.getMaxHealth());
                        livingBase.setHealth(livingBase.getMaxHealth());

//                        if (ModConfig.DEBUG_CONF.DEBUG_MODE)
//                        {
//                            IdlFramework.Log("HP: Lv%d, modifier = %s", level, hpMod);
//                        }
                    }

                }catch (NullPointerException e)
                {
                    IdlFramework.LogWarning("%s, scaling NPE", livingBase.getName());
                }
            }
        }
    }

    @SubscribeEvent
    public static void onDropEXP(LivingExperienceDropEvent event)
    {
        int level = LevelSystem.getLevel(event.getEntityLiving());
        int oriXp = event.getOriginalExperience();
        float dropFactor = 10 / ModConfig.SPAWN_CONF.ENEMY_DROP_SCALE_FACTOR;
        while (level >= dropFactor)
        {
            oriXp *= 2;
            level -= dropFactor;
        }

        event.setDroppedExperience(oriXp);
    }

    @SubscribeEvent
    public static void onDrop(LivingDropsEvent event)
    {
        int level = LevelSystem.getLevel(event.getEntityLiving());
        List<EntityItem> oriList = new ArrayList<>(event.getDrops());
        List<EntityItem> totalList = new ArrayList<>(event.getDrops());

        float dropFactor = 10 / ModConfig.SPAWN_CONF.ENEMY_DROP_SCALE_FACTOR;
        while (level >= dropFactor)
        {
            for (EntityItem item :
                    oriList) {
                EntityItem copy = new EntityItem(item.world, item.posX, item.posY, item.posZ, item.getItem());
                item.world.spawnEntity(copy);
                totalList.add(copy);
            }
            level -= dropFactor;
        }
    }
}

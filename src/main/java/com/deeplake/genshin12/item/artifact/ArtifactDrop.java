package com.deeplake.genshin12.item.artifact;


import com.deeplake.genshin12.IdlFramework;
import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.item.ModItems;
import com.deeplake.genshin12.util.EntityUtil;
import net.minecraft.block.BlockLeaves;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber()
public class ArtifactDrop {

    @SubscribeEvent
    public static void onBreakBlock(BlockEvent.HarvestDropsEvent event)
    {
        if (event.isSilkTouching())
        {
            return;
        }

        try
        {
            if (event.getHarvester().getRNG().nextFloat() <= ModConfig.DEBUG_CONF.LUMBER_CHANCE)
            {
                if (event.getState().getBlock() instanceof BlockLeaves)
                {
                    ItemStack stack = ModItems.AR_LUMBER.getRandomBlankInstance();
                    event.getDrops().add(stack);
                }
            }
        }
        catch (NullPointerException e)
        {
            IdlFramework.LogWarning(e.toString());
        }

    }

    @SubscribeEvent
    public static void onDrop(LivingDropsEvent event)
    {
        float playerRange = ModConfig.DEBUG_CONF.PLAYER_DETECT_RANGE;

        EntityLivingBase livingBase = event.getEntityLiving();
        List<EntityItem> stacks = event.getDrops();
        if (livingBase instanceof EntityDragon)
        {
            int count = EntityUtil.getEntitiesWithinAABB(livingBase.getEntityWorld(), EntityPlayer.class, livingBase.getPositionVector(), playerRange, null).size();
            for (int i = 0; i < count; i++)
            {
                bossDrop(stacks,livingBase, livingBase.getRNG());
            }
        }
        else if (livingBase instanceof EntityWither)
        {
            bossDrop(stacks,livingBase, livingBase.getRNG());
        }
    }

    static void bossDrop(List<EntityItem> stacks, EntityLivingBase livingBase, Random random)
    {
        for(int i = 0; i <= 3; i++)
        {
            ItemStack stack = ModItems.AR_GLADIATOR.getRandomBlankInstance(4);
            stacks.add(livingBase.entityDropItem(stack,1f));
        }

        ItemStack stack = ModItems.AR_GLADIATOR.getRandomBlankInstance( 5);
        stacks.add(livingBase.entityDropItem(stack,1f));
    }


}

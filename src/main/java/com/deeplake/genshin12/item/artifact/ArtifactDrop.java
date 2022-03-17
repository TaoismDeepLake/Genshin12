package com.deeplake.genshin12.item.artifact;


import com.deeplake.genshin12.Idealland;
import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.item.ModItems;
import com.deeplake.genshin12.util.EntityUtil;
import com.deeplake.genshin12.util.PlayerUtil;
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

import java.util.List;

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
            if (event.getHarvester() == null)
            {
                return;
            }
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
            Idealland.LogWarning(e.toString());
        }

    }

    //Server only
    @SubscribeEvent
    public static void onDrop(LivingDropsEvent event)
    {
        float playerRange = ModConfig.DEBUG_CONF.PLAYER_DETECT_RANGE;

        EntityLivingBase livingBase = event.getEntityLiving();
        List<EntityItem> stacks = event.getDrops();
        if (livingBase instanceof EntityDragon || livingBase instanceof EntityWither)
        {
            List<EntityPlayer> players = EntityUtil.getEntitiesWithinAABB(livingBase.getEntityWorld(), EntityPlayer.class, livingBase.getPositionVector(), playerRange, null);
            int count = players.size();
            for (EntityPlayer player :
                    players) {

                bossDrop(stacks, player, livingBase);

            }
        }
    }

    static void bossDrop(List<EntityItem> stacks, EntityPlayer player, EntityLivingBase livingBase)
    {
        for(int i = 0; i <= 3; i++)
        {
            ItemStack stack = ModItems.AR_GLADIATOR.getRandomBlankInstance(4);
            PlayerUtil.giveDrop(stacks, player, livingBase, stack);
        }

        ItemStack stack = ModItems.AR_GLADIATOR.getRandomBlankInstance( 5);
        PlayerUtil.giveDrop(stacks, player, livingBase, stack);
    }


}

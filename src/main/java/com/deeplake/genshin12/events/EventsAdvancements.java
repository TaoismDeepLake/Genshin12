package com.deeplake.genshin12.events;

import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.item.ModItems;
import com.deeplake.genshin12.util.PlayerUtil;
import net.minecraft.advancements.FrameType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventsAdvancements {
    @SubscribeEvent
    public static void onAdv(AdvancementEvent event)
    {
        EntityPlayer player = event.getEntityPlayer();
        if (player.world.isRemote)
        {
            return;
        }

        //prevent errors
        if (event.getAdvancement().getDisplay() == null || !event.getAdvancement().getDisplay().shouldShowToast())
        {
            return;
        }

//        task\ chall \ goal. which is which
        int count = 0;
        switch (event.getAdvancement().getDisplay().getFrame())
        {
            case TASK:
            case GOAL:
                count = ModConfig.GACHA_CONF.PRIMO_PER_ADVANCEMENT;
                break;
            case CHALLENGE:
                count = ModConfig.GACHA_CONF.PRIMO_PER_CHALLENGE;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + event.getAdvancement().getDisplay().getFrame());
        }
        PlayerUtil.giveToPlayer(player, new ItemStack(ModItems.PRIMOGEM, count));

    }
}

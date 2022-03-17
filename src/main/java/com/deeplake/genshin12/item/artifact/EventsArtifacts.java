package com.deeplake.genshin12.item.artifact;

import com.deeplake.genshin12.Idealland;
import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.item.artifact.set.ArtifactSetManager;

import com.deeplake.genshin12.util.CommonFunctions;
import com.deeplake.genshin12.util.MessageDef;
import com.deeplake.genshin12.util.PlayerUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class EventsArtifacts {
    @SubscribeEvent
    public static void onDig(PlayerEvent.BreakSpeed event)
    {
        float bonus = ModConfig.DEBUG_CONF.MINER_BONUS;

        final EntityPlayer player = event.getEntityPlayer();
        IBlockState state = event.getState();
        if (ArtifactSetManager.LUMBER.getNowCount(player) >= 2) {
            if (state.getMaterial() == Material.WOOD) {
                event.setNewSpeed(event.getNewSpeed() + event.getOriginalSpeed() * bonus);
            }
        }

        if (ArtifactSetManager.MINER.getNowCount(player) >= 2) {
            if (state.getMaterial() == Material.ROCK) {
                event.setNewSpeed(event.getNewSpeed() + event.getOriginalSpeed() * bonus);
            }
        }
    }

    //pure server side
    @SubscribeEvent
    public static void onBreak(BlockEvent.BreakEvent event)
    {
        final EntityPlayer player = event.getPlayer();
        if (ArtifactSetManager.LUMBER.getNowCount(player) >= 4)
        {
            if (event.getState().getMaterial() == Material.WOOD)
            {
                if (player.getRNG().nextFloat() <= ModConfig.DEBUG_CONF.LUMBER_SET_CHANCE && player.getFoodStats().needFood())
                {
                    player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() + 1);
                    player.world.playSound(null, event.getPos(), SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.PLAYERS, 1f, 1f);
                    CommonFunctions.SafeSendMsgToPlayer(player, MessageDef.ARTIFACT_TRIGGER_LUMBER);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onCraft(net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent event)
    {
        final EntityPlayer player = event.player;
        if (ArtifactSetManager.MINER.getNowCount(player) >= 4)
        {
            if (event.crafting.getItem() == Item.getItemFromBlock(Blocks.TORCH))
            {
                if (player.getRNG().nextFloat() <= 0.12f)
                {
                    PlayerUtil.giveToPlayer(player, new ItemStack(event.crafting.getItem(), event.crafting.getCount()));
                    player.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS, 1f, 0.5f);
                    CommonFunctions.SafeSendMsgToPlayer(player, MessageDef.ARTIFACT_TRIGGER_MINER);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onHurt(LivingHurtEvent event)
    {

    }


}

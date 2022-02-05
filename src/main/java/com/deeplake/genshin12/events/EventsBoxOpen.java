package com.deeplake.genshin12.events;

import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.item.ModItems;
import com.deeplake.genshin12.util.CommonFunctions;
import com.deeplake.genshin12.util.MessageDef;
import com.deeplake.genshin12.util.NBTStrDef.IDLNBTUtil;
import com.deeplake.genshin12.util.PlayerUtil;
import com.deeplake.genshin12.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.ILootContainer;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class EventsBoxOpen {
    @SubscribeEvent
    public static void onPlayerInteractBlock(PlayerInteractEvent.RightClickBlock event)
    {
        World world = event.getWorld();
        if (world.isRemote)
        {
            return;
        }

        EntityPlayer player = event.getEntityPlayer();
        BlockPos pos = event.getPos();
        Block block = world.getBlockState(pos).getBlock();
        if (block instanceof BlockChest)
        {
            BlockChest blockChest = (BlockChest) block;
            ILockableContainer container = blockChest.getContainer(world, pos, false);
            if (container != null)//successfully opened
            {
                //first time to open
                if (container instanceof ILootContainer && ((ILootContainer)container).getLootTable() != null)
                {
                    int count = ModConfig.GACHA_CONF.PRIMO_PER_CHEST;
                    PlayerUtil.giveToPlayer(player, new ItemStack(ModItems.PRIMOGEM, count));
                    CommonFunctions.SafeSendMsgToPlayer(player, MessageDef.OBTAIN_PRIMO, count);
                }
            }
        }
    }
}

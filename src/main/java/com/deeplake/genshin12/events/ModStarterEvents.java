package com.deeplake.genshin12.events;

import com.deeplake.genshin12.util.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import static com.deeplake.genshin12.util.IDLNBT.*;
import static com.deeplake.genshin12.util.NBTStrDef.IDLNBTDef.*;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ModStarterEvents {
	  @SubscribeEvent
	  public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		  EntityPlayer player = event.player;
		  //Idealland.Log(getPlyrIdlTagSafe(player).toString());
		  int lastStarterVer = getPlayerIdeallandIntSafe(player, STARTER_KIT_VERSION_TAG);
		  if(lastStarterVer < CUR_STARTER_KIT_VERSION) {
//			  IDLNBT.setPlayerIdeallandTagSafe(player, STARTER_KIT_VERSION_TAG, CUR_STARTER_KIT_VERSION);
//
//			  ItemStack scry = new ItemStack(Items.QUARTZ);
//			  player.addItemStackToInventory(scry);
//
//			  if (player instanceof EntityPlayerMP) {
//				  CommonFunctions.SendMsgToPlayerStyled((EntityPlayerMP)player, "genshin12.msg.starter_kit_given", TextFormatting.AQUA);
//			  }
//			  Idealland.Log(String.format("Given starter items to player %s, ver %d", player.getDisplayNameString(), CUR_STARTER_KIT_VERSION));
		  }
	  }
	
}

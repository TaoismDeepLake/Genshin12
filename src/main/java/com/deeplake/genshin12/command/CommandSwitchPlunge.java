package com.deeplake.genshin12.command;

import com.deeplake.genshin12.Idealland;
import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.util.CommonFunctions;
import com.deeplake.genshin12.util.NBTStrDef.IDLNBTDef;
import com.deeplake.genshin12.util.NBTStrDef.IDLNBTUtil;
import com.google.common.collect.Lists;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

import java.util.List;


public class CommandSwitchPlunge extends CommandBase {

    private final List<String> aliases = Lists.newArrayList(Idealland.MODID, "swpl", "plunge","switchpl");

    @Override
    public String getName() {
        return "switchPlunge";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "switchPlunge";
    }

    @Override
    public List<String> getAliases() {
        return aliases;
    }


    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        if (sender instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) sender;
            if (!ModConfig.GeneralConf.ENABLE_PLUNGE_ATTACK)
            {
                CommonFunctions.SafeSendMsgToPlayer(player, MSG_SERVER_BAN);
                return;
            }

            boolean curCunPlunge = !IDLNBTUtil.getPlayerIdeallandBoolSafe(player, IDLNBTDef.KEY_PLUNGE_BAN);
            CommonFunctions.SafeSendMsgToPlayer(player, curCunPlunge ? MSG_TURN_OFF : MSG_TURN_ON);
            IDLNBTUtil.setPlayerIdeallandTagSafe(player, IDLNBTDef.KEY_PLUNGE_BAN, curCunPlunge);
        }
    }

    public static final String MSG_TURN_OFF = Idealland.MODID + ".msg.plunge_turn_off";
    public static final String MSG_TURN_ON = Idealland.MODID + ".msg.plunge_turn_on";
    public static final String MSG_SERVER_BAN = Idealland.MODID + ".msg.plunge_server_ban";
}

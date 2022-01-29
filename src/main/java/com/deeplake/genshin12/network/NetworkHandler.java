package com.deeplake.genshin12.network;

import com.deeplake.genshin12.IdlFramework;
import com.deeplake.genshin12.network.protocols.PacketCast;
import com.deeplake.genshin12.network.protocols.PacketMouseFire;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {
    public static final ResourceLocation MSG_RESOURCE = new ResourceLocation(IdlFramework.MODID, "msg");

    public static final SimpleNetworkWrapper channel = NetworkRegistry.INSTANCE.newSimpleChannel(IdlFramework.MODID);

    static int id = 0;
    public static void init()
    {
        //C2S
        channel.registerMessage(PacketCast.Handler.class, PacketCast.class, id++, Side.SERVER);
        //just call SendToServer

        //C2S
        channel.registerMessage(PacketMouseFire.Handler.class, PacketMouseFire.class, id++, Side.SERVER);
        //just call SendToServer

        //S2C
        //PacketUtil.network.sendTo(new PacketRevenge(cap.isRevengeActive()), (EntityPlayerMP)e.player);
    }

    public static void SendToServer(IMessage packet)
    {
        channel.sendToServer(packet);
    }
}

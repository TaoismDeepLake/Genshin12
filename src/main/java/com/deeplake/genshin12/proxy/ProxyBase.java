package com.deeplake.genshin12.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

public class ProxyBase {
	public boolean isServer()
	{
		return false;
	}

	public void registerItemRenderer(Item item, int meta, String id) {
		//Ignored
	}

	public void registerItemRenderer(Item item, int meta, String fileName, String id) {
		//Ignored
	}

	public void registerTESR(RegistryEvent.Register<Block> event) {

	}

	public void registerParticles() {

	}

	public void loadComplete(FMLPostInitializationEvent event)
	{

	}

}

package com.deeplake.genshin12.proxy;

import com.deeplake.genshin12.IdlFramework;
import com.deeplake.genshin12.designs.client.RenderCreatureLevelNumber;
import com.deeplake.genshin12.designs.client.RenderElementalIcon;
import com.deeplake.genshin12.entity.creatures.render.layer.LayerFrozen;
import com.deeplake.genshin12.entity.creatures.render.layer.LayerPetrify;
import com.deeplake.genshin12.item.IHasVariant;
import com.deeplake.genshin12.keys.ModKeyBinding;
import com.deeplake.genshin12.util.CommonFunctions;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class ClientProxy extends ProxyBase {
	public static final List<KeyBinding> KEY_BINDINGS = new ArrayList<KeyBinding>();

	public static final KeyBinding CAST_MAINHAND = new ModKeyBinding("activate_skill_mainhand", KeyConflictContext.IN_GAME, KeyModifier.NONE, Keyboard.KEY_R, "key.category.genshin12");
	public static final KeyBinding CAST_OFFHAND = new ModKeyBinding("activate_skill_offhand", KeyConflictContext.IN_GAME, KeyModifier.NONE, Keyboard.KEY_GRAVE, "key.category.genshin12");
	public static final KeyBinding CAST_HELMET = new ModKeyBinding("activate_skill_helmet", KeyConflictContext.IN_GAME, KeyModifier.ALT, Keyboard.KEY_GRAVE, "key.category.genshin12");

	public boolean isServer()
	{
		return false;
	}

	public void registerItemRenderer(Item item, int meta, String id)
	{
		if (item instanceof IHasVariant)
		{
			ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName()+"_"+meta, id));
		}
		else {
			ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
		}
	}

	public void registerItemRenderer(Item item, int meta, String fileName, String id)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(new ResourceLocation(IdlFramework.MODID, fileName), id));
	}

	@Override
	public void registerTESR(RegistryEvent.Register<Block> event) {
//		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChestCustom.class, new RenderCustomChest());
	}

	@Override
	public void registerParticles() {
//		InitParticles.registerParticles();
	}

	public void loadComplete(FMLPostInitializationEvent evt) {
		Minecraft.getMinecraft().getRenderManager().entityRenderMap.values().forEach(r -> {
			if (r instanceof RenderLivingBase) {
				attachRenderLayers((RenderLivingBase<?>) r);
			}
		});
		CommonFunctions.addToEventBus(new RenderCreatureLevelNumber());
		RenderElementalIcon.init();
	}

	private static <T extends EntityLivingBase> void attachRenderLayers(RenderLivingBase<T> renderer) {
		renderer.addLayer(new LayerPetrify(renderer));
		renderer.addLayer(new LayerFrozen(renderer));
	}
}

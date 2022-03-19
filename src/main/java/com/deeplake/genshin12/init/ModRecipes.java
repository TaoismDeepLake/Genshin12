package com.deeplake.genshin12.init;

import com.deeplake.genshin12.recipe.special.ArtifactEnhance;
import com.deeplake.genshin12.recipe.special.RecycleArtifact;
import com.deeplake.genshin12.recipe.special.WeaponEnhance;
import com.deeplake.genshin12.util.Reference;
import com.google.common.collect.Sets;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Set;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ModRecipes {
	
	static Set<ResourceLocation> locationSet = Sets.newHashSet();

	public static void Init() {
		//Only smelting recipes
//		GameRegistry.addSmelting(ModItems.PURE_INGOT,
//				new ItemStack(ModItems.WEAPON_PEARL),
//				0.1f);

	}
	
	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> evt) {
		IForgeRegistry<IRecipe> r = evt.getRegistry();
		//Example
		r.register(new RecycleArtifact().setRegistryName(new ResourceLocation(Reference.MOD_ID, "artifact_recycle")));
		r.register(new ArtifactEnhance().setRegistryName(new ResourceLocation(Reference.MOD_ID, "artifact_enhance")));
		r.register(new WeaponEnhance().setRegistryName(new ResourceLocation(Reference.MOD_ID, "weapon_enhance")));

		//recipe deleting
//		ItemStack stack = new ItemStack(Items.DIAMOND);
//		CraftingManager.REGISTRY.forEach(
//				(recipe) -> {
//					if (ItemStack.areItemsEqual(recipe.getRecipeOutput(), stack))
//					{
//						locationSet.add(recipe.getRegistryName());
//						Idealland.LogWarning("added %s", recipe.getRegistryName());
//					}
//				}
//		);
//
//		Idealland.LogWarning("Go remove");
//		locationSet.forEach(rl -> {
//		    Idealland.LogWarning("Removed");
//            ((IForgeRegistryModifiable<IRecipe>)r).remove(rl);
//        });
	}
}

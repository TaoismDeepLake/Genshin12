package com.deeplake.genshin12.recipe.special;

import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.item.ModItems;
import com.deeplake.genshin12.item.artifact.ArtifactUtil;
import com.deeplake.genshin12.item.artifact.ItemArtifactBase;
import com.deeplake.genshin12.item.goblet.ItemP2WGoblet;
import com.deeplake.genshin12.util.IDLSkillNBT;
import com.deeplake.genshin12.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

import static net.minecraft.init.Items.GOLD_INGOT;

public class ArtifactEnhance extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

	@Override
	public boolean isDynamic() {
		return true;
	}

	@Override
	public boolean matches(@Nonnull InventoryCrafting var1, @Nonnull World var2) {
		int foundXP = 0;
		boolean mainArtifact = false;

		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(!stack.isEmpty()) {
				if(stack.getItem() instanceof ItemArtifactBase)
				{
					if (i != 0 && ModConfig.GeneralConf.ARTIFACT_ENHANCE_MUST_FIRST_SLOT)
					{
						return false;
					}

					if (mainArtifact) {
						return false;//only one sword at a time
					}
					mainArtifact = true;

					//maxed-out
					if (ItemArtifactBase.getLevelArtifact(stack) >= ArtifactUtil.getMaxLevel(ItemArtifactBase.getRarityArtifact(stack)))
					{
						return false;
					}

				}
				else if (stack.getItem() == ModItems.ARTIFACT_XP_BOTTLE)
				{//found a xp item
					foundXP++;
				}
				else
				{
					return false;
				}
			}
		}
		return foundXP > 0 && mainArtifact;
	}

	@Nonnull
	@Override
	public ItemStack getCraftingResult(@Nonnull InventoryCrafting var1) {
		int payingXP = 0;
		
		ItemStack main = ItemStack.EMPTY;
		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(!stack.isEmpty()) {
				if(stack.getItem() instanceof ItemArtifactBase)
				{
					main = stack;
					payingXP = IDLSkillNBT.getXP(stack);
				}
				else if(stack.getItem() == ModItems.ARTIFACT_XP_BOTTLE) {
					payingXP += ItemArtifactBase.getXPWorth(stack);
				}
			}
		}

		if(main.isEmpty() || payingXP == 0) {
			return ItemStack.EMPTY;
		}

		//todo: x2 & x5
		ItemStack artifactResult = main.copy();
		IDLSkillNBT.setXP(artifactResult, payingXP);
//		ArtifactUtil.getXPUpdateResult(artifactResult);

		return ArtifactUtil.getXPUpdateResult(artifactResult);
	}

	@Override
	public boolean canFit(int width, int height) {
		return true;
	}

	@Nonnull
	@Override
	public ItemStack getRecipeOutput() {
		return ItemStack.EMPTY;
	}
}


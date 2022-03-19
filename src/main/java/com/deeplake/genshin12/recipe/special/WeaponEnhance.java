package com.deeplake.genshin12.recipe.special;

import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.item.LevelingUtil;
import com.deeplake.genshin12.item.ModItems;
import com.deeplake.genshin12.item.artifact.ArtifactUtil;
import com.deeplake.genshin12.item.weapon.ItemPlayerWeapon;
import com.deeplake.genshin12.util.IDLSkillNBT;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

public class WeaponEnhance extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

	@Override
	public boolean isDynamic() {
		return true;
	}

	@Override
	public boolean matches(@Nonnull InventoryCrafting var1, @Nonnull World var2) {
		int foundXP = 0;
		boolean mainWeapon = false;

		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(!stack.isEmpty()) {
				if(stack.getItem() instanceof ItemPlayerWeapon)
				{
					if (i != 0 && ModConfig.GeneralConf.ARTIFACT_ENHANCE_MUST_FIRST_SLOT)
					{
						return false;
					}

					if (mainWeapon) {
						return false;//only one sword at a time
					}
					mainWeapon = true;

					//maxed-out
					if (LevelingUtil.getLevelForItem(stack) >= ArtifactUtil.getMaxLevel(LevelingUtil.getRarityArtifact(stack)))
					{
						return false;
					}

				}
				else if (stack.getItem() == ModItems.WEAPON_XP_STONE)
				{//found a xp item
					foundXP++;
				}
				else
				{
					return false;
				}
			}
		}
		return foundXP > 0 && mainWeapon;
	}

	@Nonnull
	@Override
	public ItemStack getCraftingResult(@Nonnull InventoryCrafting var1) {
		int payingXP = 0;
		
		ItemStack main = ItemStack.EMPTY;
		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(!stack.isEmpty()) {
				if(stack.getItem() instanceof ItemPlayerWeapon)
				{
					main = stack;
					payingXP = IDLSkillNBT.getXP(stack);
				}
				else if(stack.getItem() == ModItems.WEAPON_XP_STONE) {
					payingXP += ItemPlayerWeapon.getXPWorth(stack);
				}
			}
		}

		if(main.isEmpty() || payingXP == 0) {
			return ItemStack.EMPTY;
		}

		ItemStack weaponResult = main.copy();
		IDLSkillNBT.setXP(weaponResult, payingXP);

		return ArtifactUtil.getXPUpdateResult(weaponResult);
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


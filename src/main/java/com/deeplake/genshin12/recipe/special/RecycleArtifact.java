package com.deeplake.genshin12.recipe.special;

import com.deeplake.genshin12.item.IGuaEnhance;
import com.deeplake.genshin12.item.ModItems;
import com.deeplake.genshin12.item.artifact.ArtifactUtil;
import com.deeplake.genshin12.item.artifact.ItemArtifactBase;
import com.deeplake.genshin12.util.IDLGeneral;
import com.deeplake.genshin12.util.IDLSkillNBT;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

//all single artifacts can be recycled. Clean ones can stack.
public class RecycleArtifact extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        boolean foundMainItem = false;
        int index = -1;

        for(int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if(!stack.isEmpty()) {
                if(stack.getItem() instanceof ItemArtifactBase)
                {
                    if (foundMainItem) {
                        return false;
                    }
                    foundMainItem = true;
                    index = i;
                }
                else
                {
                    return false; //Found something else
                }
            }
        }

        return foundMainItem;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        boolean foundMainItem = false;

        for(int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if(!stack.isEmpty()) {
                if(stack.getItem() instanceof ItemArtifactBase)
                {
//                    if (foundMainItem) {
                        int rarity = ItemArtifactBase.getRarityArtifact(stack);
                        int level = ItemArtifactBase.getLevelArtifact(stack);
                        int xp = IDLSkillNBT.getXP(stack);
                        ItemStack result;
                        if (level == 0 && xp == 0)
                        {
                            result = new ItemStack(ModItems.ARTIFACT_XP_BOTTLE, 1, rarity);
                        }
                        else {
                            result = new ItemStack(ModItems.ARTIFACT_XP_BOTTLE, 1, 0);
                            IDLSkillNBT.setXP(stack, ItemArtifactBase.getTotalXP(rarity, level));
                        }

                        return result;
//                    }
                }
                else
                {
                    return ItemStack.EMPTY; //Found something else
                }
            }
        }

        return ItemStack.EMPTY;
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

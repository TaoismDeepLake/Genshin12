package com.deeplake.genshin12.designs.villagers.merchantTrade;

import com.deeplake.genshin12.item.artifact.ItemArtifactBase;
import com.deeplake.genshin12.util.CommonFunctions;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

import java.util.Random;

public class VTradeArtifacts extends VTradeItemToItem {

    ItemArtifactBase artifact;
    int rarity;
    int slot;

    public VTradeArtifacts(CostType costType, EntityVillager.PriceInfo costPriceInfo, ItemArtifactBase artifact, int rarity, int slot) {
        super(costType, costPriceInfo, ItemStack.EMPTY);
        this.artifact = artifact;
        this.rarity = rarity;
        this.slot = slot;
    }

//    public VTradeArtifacts()
//    {
//        super(Items.AIR, PRICE_ONE, Items.AIR, PRICE_ONE, getCostFromType(rarity), PRICE_ONE);
//    }

    @Override
    public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
        recipeList.add(
                new MerchantRecipe(
                        CommonFunctions.copyAndSetCount(buyingItemStack, buyingPriceInfo.getPrice(random)),
                        ItemStack.EMPTY,
                        artifact.getRandomBlankInstance(slot, rarity),
                        0, 1
                )
        );
    }

}

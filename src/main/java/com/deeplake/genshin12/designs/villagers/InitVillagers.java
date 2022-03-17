package com.deeplake.genshin12.designs.villagers;

import com.deeplake.genshin12.Idealland;
import com.deeplake.genshin12.designs.villagers.merchantTrade.VTradeArtifacts;
import com.deeplake.genshin12.designs.villagers.merchantTrade.VTradeItemToItem;
import com.deeplake.genshin12.item.ModItems;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

import static com.deeplake.genshin12.designs.villagers.merchantTrade.VTradeItemToItem.PRICE_ONE;
import static com.deeplake.genshin12.item.artifact.ArtifactUtil.MAX_SLOT_INDEX;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class InitVillagers {

    public static final List<VillagerRegistry.VillagerProfession> PROFESSION_LIST = new ArrayList<>();

    public static final EntityVillager.PriceInfo PRICE_32 = new EntityVillager.PriceInfo(32, 32);
    public static final EntityVillager.PriceInfo PRICE_64 = new EntityVillager.PriceInfo(64, 64);
    public static final EntityVillager.PriceInfo PRICE_1_32_INV = new EntityVillager.PriceInfo(-32, -1);

//    public static final ModVProfession CARD =
//            new ModVProfession( "card");
//
//    public static final ModVCareer CARD_0 = new ModVCareer(CARD, "card_player");
//    public static final ModVCareer CARD_1 = new ModVCareer(CARD, "card_player_pauper");
//    public static final ModVCareer CARD_2 = new ModVCareer(CARD, "card_player_spike");
//
//    public static final ModVProfession SCRYER =
//            new ModVProfession( "scryer");
//
//    public static final ModVCareer SCRYER_C0 = new ModVCareer(SCRYER, "scryer_0");
//    public static final ModVCareer SCRYER_C1 = new ModVCareer(SCRYER, "scryer_1");
//
//    public static final ModVProfession VENDOR =
//            new ModVProfession( "vendor");
//
//    public static final ModVCareer VENDOR_C0 = new ModVCareer(VENDOR, "vendor_0");
//    public static final ModVCareer VENDOR_C1 = new ModVCareer(VENDOR, "vendor_1");
//    public static final ModVCareer VENDOR_C2 = new ModVCareer(VENDOR, "vendor_2");
//    public static final ModVCareer VENDOR_AMMO = new ModVCareer(VENDOR, "ammo_dealer");
//    public static final ModVCareer VENDOR_STONE = new ModVCareer(VENDOR, "stone_dealer");
//
//    public static final ModVProfession ENGINEER =
//            new ModVProfession( "engineer");
//
//    public static final ModVCareer VENDOR_PHOTO_ELECTRONICS = new ModVCareer(ENGINEER, "engineer_light");
//    public static final ModVCareer VENDOR_LOGISTICS = new ModVCareer(ENGINEER, "engineer_logistics");
//    public static final ModVCareer VENDOR_ELECTRO_DYNAMICS = new ModVCareer(ENGINEER, "engineer_electro_dynamics");
//    public static final ModVCareer VENDOR_MICRO_ELECTRONICS = new ModVCareer(ENGINEER, "engineer_logical");
//    public static final ModVCareer VENDOR_RAIL = new ModVCareer(ENGINEER, "engineer_rail");

    public static final ModVProfession VENDOR_MISC =
            new ModVProfession( "vendor_misc");
    public static final ModVCareer VENDOR_MISC_LIYUE = new ModVCareer(VENDOR_MISC, "v_misc_liyue");

    public static final ModVProfession VENDOR_ARTIFACTS =
            new ModVProfession( "vendor_artifacts");

    public static final ModVCareer VENDOR_AR_MINER = new ModVCareer(VENDOR_ARTIFACTS, "v_ar_miner");
    public static final ModVCareer VENDOR_AR_LUMBER = new ModVCareer(VENDOR_ARTIFACTS, "v_ar_lumber");


    public static final ModVProfession QUEST_GIVER =
            new ModVProfession( "quest_giver");
    public static final ModVCareer QUEST_UNDEAD_HUNTER = new ModVCareer(QUEST_GIVER, "v_qst_undead_hunter");
    public static final ModVCareer QUEST_MINER_HEAD = new ModVCareer(QUEST_GIVER, "v_qst_miner_lord");
    public static final ModVCareer QUEST_BROCK = new ModVCareer(QUEST_GIVER, "v_qst_brock");
    public static final ModVCareer QUEST_BREWER = new ModVCareer(QUEST_GIVER, "v_qst_brewer");

    public static final EntityVillager.PriceInfo PRICE_ARTIFACTS = new EntityVillager.PriceInfo(2, 2);

    public static final ItemStack GOLD_INGOT = new ItemStack(Items.GOLD_INGOT);

    public static final EntityVillager.PriceInfo PRICE_QUEST_COMMON = new EntityVillager.PriceInfo(8, 16);

    public static final ItemStack getPotion(PotionType potionType)
    {
        return PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.AWKWARD);
    }

    @SubscribeEvent
    public static void onRegistry(RegistryEvent.Register<VillagerRegistry.VillagerProfession> event)
    {

        IForgeRegistry<VillagerRegistry.VillagerProfession> registry = event.getRegistry();

        for(int slot = 0; slot <= MAX_SLOT_INDEX; slot++)
        {
            VENDOR_AR_MINER.addTrade(1,
                new VTradeArtifacts(VTradeItemToItem.CostType.GOLD_NUGGET, PRICE_ARTIFACTS, ModItems.AR_MINER, 2, slot));

            VENDOR_AR_LUMBER.addTrade(1,
                    new VTradeArtifacts(VTradeItemToItem.CostType.GOLD_NUGGET, PRICE_ARTIFACTS, ModItems.AR_LUMBER, 2, slot));
        }

        QUEST_UNDEAD_HUNTER.addTrade(1,
                new VTradeItemToItem(Items.ROTTEN_FLESH, PRICE_QUEST_COMMON, ModItems.PRIMOGEM, PRICE_ONE));
        QUEST_UNDEAD_HUNTER.addTrade(1,
                new VTradeItemToItem(Items.BONE, PRICE_QUEST_COMMON, ModItems.PRIMOGEM, PRICE_ONE));
        QUEST_UNDEAD_HUNTER.addTrade(2,
                new VTradeItemToItem(Items.GUNPOWDER, PRICE_QUEST_COMMON, ModItems.PRIMOGEM, PRICE_ONE));
        QUEST_UNDEAD_HUNTER.addTrade(2,
            new VTradeItemToItem(Items.ENDER_PEARL, PRICE_QUEST_COMMON, ModItems.PRIMOGEM, PRICE_ONE));

        QUEST_MINER_HEAD.addTrade(1,
                new VTradeItemToItem(Items.QUARTZ, PRICE_QUEST_COMMON, ModItems.PRIMOGEM, PRICE_ONE));
        QUEST_MINER_HEAD.addTrade(1,
                new VTradeItemToItem(Item.getItemFromBlock(Blocks.IRON_ORE), PRICE_10, ModItems.PRIMOGEM, PRICE_ONE));
        QUEST_MINER_HEAD.addTrade(2,
                new VTradeItemToItem(Items.REDSTONE, PRICE_QUEST_COMMON, ModItems.PRIMOGEM, PRICE_ONE));
        QUEST_MINER_HEAD.addTrade(2,
                new VTradeItemToItem(Items.DIAMOND, PRICE_QUEST_COMMON, ModItems.PRIMOGEM, PRICE_ONE));

        QUEST_BROCK.addTrade(1,
            new VTradeItemToItem(Items.PORKCHOP, PRICE_QUEST_COMMON, ModItems.PRIMOGEM, PRICE_ONE));
        QUEST_BROCK.addTrade(1,
            new VTradeItemToItem(VTradeItemToItem.CostType.GOLD_NUGGET, PRICE_ONE, Items.COOKED_BEEF));
        QUEST_BROCK.addTrade(1,
                new VTradeItemToItem(VTradeItemToItem.CostType.GOLD_NUGGET, PRICE_ONE, Items.COOKED_CHICKEN));
        QUEST_BROCK.addTrade(2,
                new VTradeItemToItem(Items.CHICKEN, PRICE_QUEST_COMMON, ModItems.PRIMOGEM, PRICE_ONE));

        QUEST_BREWER.addTrade(1,
                new VTradeItemToItem(VTradeItemToItem.CostType.GOLD_NUGGET, PRICE_ONE, getPotion(PotionTypes.AWKWARD)));
        QUEST_BREWER.addTrade(1,
                new VTradeItemToItem(VTradeItemToItem.CostType.GOLD_NUGGET, PRICE_ONE, getPotion(PotionTypes.MUNDANE)));
        QUEST_BREWER.addTrade(1,
                new VTradeItemToItem(VTradeItemToItem.CostType.GOLD_NUGGET, PRICE_ONE, getPotion(PotionTypes.INVISIBILITY)));
        QUEST_BREWER.addTrade(2,
                new VTradeItemToItem(Items.WHEAT, PRICE_10, ModItems.PRIMOGEM, PRICE_ONE));

        registry.registerAll(PROFESSION_LIST.toArray(new VillagerRegistry.VillagerProfession[0]));

    }

    static final EntityVillager.PriceInfo PRICE_36 = new EntityVillager.PriceInfo(36,36);
    static final EntityVillager.PriceInfo PRICE_10 = new EntityVillager.PriceInfo(10,10);

    static void registerRedstone(ModVCareer career, ItemStack[] stacks)
    {
        career.addTrade(1,
                new VTradeItemToItem(VTradeItemToItem.CostType.GOLD_NUGGET, new EntityVillager.PriceInfo(1,2), new ItemStack(Items.REDSTONE)),
                new VTradeItemToItem(VTradeItemToItem.CostType.GOLD_INGOT, new EntityVillager.PriceInfo(4,4), new ItemStack(Blocks.REDSTONE_BLOCK), new EntityVillager.PriceInfo(4,4)));

        career.addTrade(2,
                new VTradeItemToItem(new ItemStack(Blocks.GOLD_BLOCK), PRICE_ONE, new ItemStack(Items.REDSTONE), new EntityVillager.PriceInfo(27,27)));

        registerMassive(career, stacks);
    }

    static void registerMassive(ModVCareer career, ItemStack[] stacks)
    {
        for (ItemStack stack : stacks) {
            career.addTrade(1, new VTradeItemToItem(VTradeItemToItem.CostType.GOLD_INGOT, stack, 4));
            career.addTrade(2, new VTradeItemToItem(new ItemStack(Blocks.GOLD_BLOCK), PRICE_ONE, stack, PRICE_36));
        }
    }

    static void registerMassive(ModVCareer career, ItemStack stack, int countPerGoldIngot)
    {
        if (countPerGoldIngot > 7)
        {
            Idealland.LogWarning("An villager trading is not satisfying: 1 ingot for %d %s, 1 block will cut down to 64", countPerGoldIngot, stack.getDisplayName());
        }
        career.addTrade(1, new VTradeItemToItem(VTradeItemToItem.CostType.GOLD_INGOT, stack, countPerGoldIngot));
        career.addTrade(2, new VTradeItemToItem(new ItemStack(Blocks.GOLD_BLOCK), PRICE_ONE, stack, new EntityVillager.PriceInfo(countPerGoldIngot * 9, countPerGoldIngot * 9)));
    }

    static void registerMassiveTiny(ModVCareer career, ItemStack stack, int countPerGoldNugget)
    {
        if (countPerGoldNugget > 7)
        {
            Idealland.LogWarning("An villager trading is not satisfying: 1 nugget for %d %s, 1 ingot will cut down to 64", countPerGoldNugget, stack.getDisplayName());
        }
        career.addTrade(1, new VTradeItemToItem(VTradeItemToItem.CostType.GOLD_NUGGET, stack, countPerGoldNugget));
        career.addTrade(2, new VTradeItemToItem(VTradeItemToItem.CostType.GOLD_INGOT, PRICE_ONE, stack, new EntityVillager.PriceInfo(countPerGoldNugget * 9, countPerGoldNugget * 9)));
    }
}

package com.deeplake.genshin12.item;

import com.deeplake.genshin12.item.artifact.ItemArtifactBase;
import com.deeplake.genshin12.item.artifact.set.ArtifactSetManager;
import com.deeplake.genshin12.item.consumables.*;
import com.deeplake.genshin12.item.skills.genshin.*;
import com.deeplake.genshin12.item.weapon.ItemFireClub;
import com.deeplake.genshin12.item.weapon.ItemPlayerWeapon;
import com.deeplake.genshin12.util.EnumAmount;
import com.deeplake.genshin12.util.EnumElemental;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ModItems {

    public static final List<Item> ITEMS = new ArrayList<Item>();

    public static final Item PRIMOGEM = new ItemBase("primogem");
    public static final Item INTERWINED_FATE = new ItemInterwinedFate("interwined_fate");

    public static final Item WEAPON_PACK = new ItemPackageWeapon("weapon_pack");
    public static final ItemStellaFortuna STELLA_FORTUNA = new ItemStellaFortuna("stella_fortuna", EnumCharacter.values().length);

    public static final Item SKILL_UPGRADE = new ItemVariantBase("skill_upgrade", 9);
    public static final Item COR_LAPIS = new ItemBase("cor_lapis");

    //Character
    //https://www.minecraftskins.com/skin/18941060/zhongli--------/
    public static final ItemZhongliE ZHONGLI_E = new ItemZhongliE("zhongli_e");
    public static final ItemZhongliQ ZHONGLI_Q = new ItemZhongliQ("zhongli_q");

    public static final ItemKaeyaE KAEYA_E = new ItemKaeyaE("kaeya_e");
    public static final ItemKaeyaQ KAEYA_Q = new ItemKaeyaQ("kaeya_q");

    public static final ItemXiaoE XIAO_E = new ItemXiaoE("xiao_e");
    public static final ItemXiaoQ XIAO_Q = new ItemXiaoQ("xiao_q");

    public static final ItemHuTaoE HU_TAO_E = new ItemHuTaoE("hu_tao_e");
    public static final ItemHuTaoQ HU_TAO_Q = new ItemHuTaoQ("hu_tao_q");

    public static final ItemBarbaraE BARBARA_E = new ItemBarbaraE("barbara_e");
    public static final ItemBarbaraQ BARBARA_Q = new ItemBarbaraQ("barbara_q");

    public static final ItemHuTaoE KEQING_E = new ItemHuTaoE("keqing_e");
    public static final ItemHuTaoQ KEQING_Q = new ItemHuTaoQ("keqing_q");
    public static final ItemArtifactBase AR_DEFAULT = new ItemArtifactBase("artifact_default");
    public static final ItemArtifactBase AR_LUMBER = new ItemArtifactBase("artifact_lumber", ArtifactSetManager.LUMBER);
    public static final ItemArtifactBase AR_MINER = new ItemArtifactBase("artifact_miner", ArtifactSetManager.MINER);
    public static final ItemArtifactBase AR_GLADIATOR = new ItemArtifactBase("artifact_gladiator", ArtifactSetManager.GLADIATOR);
    //	public static final ItemArtifactBase AR_WANDERER = new ItemArtifactBase("artifact_wanderer", ArtifactSetManager.WANDERER);
    public static final ItemArtifactBase AR_BLANK = new ItemArtifactBase("artifact_blank", ArtifactSetManager.BLANK);

    public static final ItemBase ARTIFACT_XP_BOTTLE = new ItemVariantBase("artf_xp_bottle", 6);
    public static final ItemBase WEAPON_XP_STONE = new ItemVariantBase("weapon_stone", 6);

    public static final ItemFireClub FIRE_CLUB = new ItemFireClub("fire_club", Item.ToolMaterial.WOOD, EnumElemental.PYRO, EnumAmount.SMALL);

    public static final ItemBase ATTR_REPORT = (ItemBase) new ItemVariantBase("attr_report", 4);

    //	public static final ItemDebugSword DEBUG = new ItemDebugSword("debug_item", Item.ToolMaterial.DIAMOND);
    public static final ItemBase DEBUG = (ItemBase) new ItemPlayerWeapon("debug_item").setCreativeTab(null);
    public static final Item DEBUG_2 = new ItemLevelUp("debug_item_2").setCreativeTab(null);
	/*
	WOOD(0, 59, 2.0F, 0.0F, 15),
    OVERLAY(1, 131, 4.0F, 1.0F, 5),
    IRON(2, 250, 6.0F, 2.0F, 14),
    DIAMOND(3, 1561, 8.0F, 3.0F, 10),
    GOLD(0, 32, 12.0F, 0.0F, 22);

    harvestLevel, maxUses, efficiency, damage, enchantability
	*/

    //Tool Material
//	public static final Item BLOOD_IRON_INGOT = new ItemBase("blood_iron_ingot");
//
//    public static final Item.ToolMaterial TOOL_MATERIAL_BLOOD =
//			EnumHelper.addToolMaterial("material_blood", 3, 512, 3.0F, 4F, 20).setRepairItem(new ItemStack( ModItems.BLOOD_IRON_INGOT));
//
//	public static final ItemKinshipSword KINSHIP_SWORD = new ItemKinshipSword("kinship_sword", TOOL_MATERIAL_BLOOD);

    //Armor
//    LEATHER("leather", 5, new int[]{1, 2, 3, 1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F),
//    CHAIN("chainmail", 15, new int[]{1, 4, 5, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 0.0F),
//    IRON("iron", 15, new int[]{2, 5, 6, 2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F),
//    GOLD("gold", 7, new int[]{1, 3, 5, 2}, 25, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0F),
//    DIAMOND("diamond", 33, new int[]{3, 6, 8, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F);
    //Note that if you want to set a mod thing as repair material, define them before the material, otherwise it will be empty.

//    public static final ItemArmor.ArmorMaterial moroonArmorMaterial = EnumHelper.addArmorMaterial(
//            "genshin12:armor_moroon", "genshin12:armor_moroon", 80, new int[] {3, 6, 8, 3}, 2, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 2)
//            .setRepairItem(new ItemStack(Items.QUARTZ));
//

    //Food
//	static PotionEffect eff = new PotionEffect(MobEffects.LEVITATION, TICK_PER_SECOND * 60, 0);
//	public static final ItemFoodBase FIGHT_BREAD = (ItemFoodBase) new ItemFoodBase("war_bread", 10, 10, false).
//			setPotionEffect(eff, 1.0f).
//			setAlwaysEdible();
//    public static final ItemFoodBase MEMORY_BREAD = new ItemFoodBase("memory_bread", 3, 0.6f, false).SetXP(10);





    //Armor
//	public static final ItemHelmSniper helmetSniper = (ItemHelmSniper) new ItemHelmSniper("helmet_sniper", moroonArmorMaterialSniper, 1, EntityEquipmentSlot.HEAD);
//
//	public static final ItemArmorBase[] MOR_GENERAL_ARMOR =
//			{        new ItemArmorBase("mor_armor_1", moroonArmorMaterial, 1, EntityEquipmentSlot.HEAD)
//					,new ItemArmorBase("mor_armor_2", moroonArmorMaterial, 1, EntityEquipmentSlot.CHEST)
//					,new ItemArmorBase("mor_armor_3", moroonArmorMaterial, 1, EntityEquipmentSlot.LEGS)
//					,new ItemArmorBase("mor_armor_4", moroonArmorMaterial, 1, EntityEquipmentSlot.FEET)
//			};

    //public static final ItemSkillDecodeItem skillDecodeItem = (ItemSkillDecodeItem) new ItemSkillDecodeItem("skill_decode_item").setRarity(EnumRarity.RARE);

    //Package Example
//	public static final ItemPackage RANDOM_SKILL = (ItemPackage) new ItemPackage("random_skill", new Item[]{
//			Items.BLAZE_ROD, Items.PAPER
//	}).setMaxStackSize(1);
}

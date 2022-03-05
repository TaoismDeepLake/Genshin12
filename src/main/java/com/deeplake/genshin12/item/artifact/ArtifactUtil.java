package com.deeplake.genshin12.item.artifact;

import com.deeplake.genshin12.entity.creatures.attribute.ModAttributes;
import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.item.EnumModRarity;
import com.deeplake.genshin12.util.EnumElemental;
import com.deeplake.genshin12.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;

import static com.deeplake.genshin12.item.artifact.ItemArtifactBase.getRarityArtifact;

@Mod.EventBusSubscriber
public class ArtifactUtil {

    public static final EnumRarity LEGEND = EnumHelper.addRarity("legend", TextFormatting.GOLD, "Legendary");
    public static final int INT_SCALER = 1;
    public static HashMap<EnumRarity, EnumModRarity> QUALITY_MAP = new HashMap<>();

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onToolTip(ItemTooltipEvent event)
    {
        ItemStack stack = event.getItemStack();
        if (stack.getItem() instanceof ItemArtifactBase)
        {
            ItemArtifactBase artifactBase = (ItemArtifactBase) stack.getItem();
            int rarity = getRarityArtifact(stack);
            int level = IDLNBTUtil.GetInt(stack, ArtifactUtil.KEY_LEVEL);
            int maxLevel = getMaxLevel(rarity);

            List<String> strings = event.getToolTip();
            strings.add(1, I18n.format("genshin12.artifact.rarity." + rarity));

            if (level == maxLevel)
            {
                strings.add(1, I18n.format("genshin12.artifact.level.max"));
            }
            else if (level != 0) {
                strings.add(1, I18n.format("genshin12.artifact.level", level));
            }

            strings.add(1, I18n.format("genshin12.artifact.slot." + (IDLNBTUtil.GetInt(stack, ArtifactUtil.KEY_SLOT, 0)+1)));

            int ready = IDLNBTUtil.GetInt(stack, ArtifactUtil.KEY_READY_ATTR);
            if (ready > 0)
            {
                strings.add(1, I18n.format("genshin12.artifact.upgrade", ready));
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onToolTipColor(RenderTooltipEvent.Color event) {
        if (event.getStack().getItem() instanceof ItemArtifactBase) {
            int rarity = getRarityArtifact(event.getStack());

//            event.setBackground(0xf0330000);
            event.setBorderStart(0xf0000000 | EnumModRarity.getColor(rarity));
//            event.setBorderEnd(0xf0cc0000);
        }
    }

    public static final String KEY_LEVEL = "artlvl";
    public static final String KEY_RARITY = "rarity";
    public static final String KEY_READY_ATTR = "ready";//the random attrs that will be given soon
    public static final String KEY_SLOT = "slot";
    public static final String KEY_MAIN_ATTR = "main";
    public static final String[] KEY_SUB_ATTR = {"sub0","sub1","sub2","sub3"};
    public static final String KEY_SUB_SEQ = "subseq";
    static final String FIX_NAME = "Artifact";
    static final String FIX_NAME_SUB = "Artifact Sub";
    final static int MAX_ATTR_SUB_TYPE = 4;
    static final int MAX_RARITY = 6;
    static Random rand = new Random();

    static ArrayList<ModAttributes.EnumAttr> listMainFlowerChest = new ArrayList<>();
    static ArrayList<ModAttributes.EnumAttr> listMain2 = new ArrayList<>();
    static ArrayList<ModAttributes.EnumAttr> listMainGobletLeg = new ArrayList<>();
    static ArrayList<ModAttributes.EnumAttr> listMainSandsBoot = new ArrayList<>();
    static ArrayList<ModAttributes.EnumAttr> listMainCircletHead = new ArrayList<>();

    public static List<ModAttributes.EnumAttr>[] mainAttrList = new ArrayList[]{listMainCircletHead, listMainFlowerChest, listMainGobletLeg, listMainSandsBoot};

    //starts from 0
    public static List<ModAttributes.EnumAttr> getMainAttrListFromSlot(int slot)
    {
        try {
            return mainAttrList[slot];
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            return mainAttrList[0];
        }
    }
    static List<ModAttributes.EnumAttr> listSub = new ArrayList<>();
    static UUID UUID_ARTIFACT = UUID.fromString("63eae879-6870-4d85-aa4b-e9ce52c7e34e");
    static UUID[] UUID_ARTIFACT_SUB =
            {
                    UUID.fromString("a49528cd-bd7e-4663-89a8-00953a3d664c"),
                    UUID.fromString("6cd8fb0c-0a2f-410d-9605-0a98bd19ee35"),
                    UUID.fromString("38ea4903-ed91-4480-86f3-f827c54644af"),
                    UUID.fromString("1c6b68c5-1d8b-4106-bf82-0bc002ea8533"),
            };
    //
    static float[][] subRatios = new float[][]{
      new float[]{0.8f, 1f, 1f, 1f},///only 2 possibilities
      new float[]{0.7f, 0.85f, 1f, 1f},//only 3
      new float[]{0.7f, 0.8f, 0.9f, 1.0f},
      new float[]{0.7f, 0.8f, 0.9f, 1.0f},
      new float[]{0.7f, 0.8f, 0.9f, 1.0f},
    };
    //each star sub stat is that of 5-star....
    static float[] sub_attr_5star_flat = {0.25f, 0.4f, 0.6f, 0.8f, 1f};
    static float[] sub_attr_5star_percent = {0.1f, 0.24f, 0.48f, 0.8f, 1f,};
    static float[] main_attr_5star_percent = {0.18f, 0.36f, 0.6f, 0.9f, 1f};
    static float[] main_attr_5star_flat = {0.45f, 0.6f, 0.75f, 0.9f, 1f};
    static float[] main_min_by_rarity = {0.4f, 0.46f, 0.22f, 0.18f, 0.15f};
    static HashMap<ModAttributes.EnumAttr, Double> subAttr5Star;
    static HashMap<ModAttributes.EnumAttr, Double> mainAttr5Star;

    static int getRandomSubAttrQuality(Random random, int rarity)
    {
        switch (rarity)
        {
            case 1:
                return 1 + random.nextInt(2);
            case 2:
                return 1 + random.nextInt(3);
            case 3:
            case 4:
            case 5:
                return 1 + random.nextInt(4);
            default:
                return 1;
        }
    }

    static int initAttrCount(int rarity)
    {
        switch (rarity)
        {
            case 1:
                return 0;
            case 2:
                return rand.nextInt(2);
            case 3:
                return rand.nextInt(2) + 1;
            case 4:
                return rand.nextInt(2) + 2;
            case 5:
                return rand.nextInt(2) + 3;
            default:
                return 4;
        }
    }

    //start from +0
    static int getMaxLevel(int rarity)
    {
        switch (rarity)
        {
            case 1:
            case 2:
                return 4;
            case 3:
                return 12;
            case 4:
                return 16;
            case 5:
                return 20;
            default:
                return 32;
        }
    }

    //quality of the same attr
    static float getRatioToFullQuality(int rarity, int quality)
    {
        try {
            return subRatios[rarity][quality - 1];
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            return 1f;
        }
    }

    //level starts from +0
    public static float getLevelRatio(int rarity, int level)
    {
        try {
            float min = main_min_by_rarity[rarity - 1];
            int maxLevel = getMaxLevel(rarity);
            return ((1f - min) / maxLevel * (float) level + min) / min;

        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            return 1f;
        }
    }


    public static void initValues()
    {
        subAttr5Star = new HashMap<>();
        subAttr5Star.put(ModAttributes.EnumAttr.HP, 298.750 / ModConfig.DEBUG_CONF.HP_CONVERT_RATIO);
        subAttr5Star.put(ModAttributes.EnumAttr.HP_P, 5.83/ 100);
        subAttr5Star.put(ModAttributes.EnumAttr.ATK, 19.45 / ModConfig.DEBUG_CONF.ATK_CONVERT_RATIO);
        subAttr5Star.put(ModAttributes.EnumAttr.ATK_P, 5.83/ 100);
        subAttr5Star.put(ModAttributes.EnumAttr.DEF, 23.15);
        subAttr5Star.put(ModAttributes.EnumAttr.DEF_P, 7.29/ 100);
        subAttr5Star.put(ModAttributes.EnumAttr.CRIT, 3.89/ INT_SCALER);
        subAttr5Star.put(ModAttributes.EnumAttr.CRIT_DMG, 7.77/ INT_SCALER);
        subAttr5Star.put(ModAttributes.EnumAttr.RECHARGE, 6.48/ INT_SCALER);
        subAttr5Star.put(ModAttributes.EnumAttr.ELEM_MASTERY, 23.31);

        mainAttr5Star = new HashMap<>();
        mainAttr5Star.put(ModAttributes.EnumAttr.HP, 717d / ModConfig.DEBUG_CONF.HP_CONVERT_RATIO);
        mainAttr5Star.put(ModAttributes.EnumAttr.HP_P, 7d/ 100);
        mainAttr5Star.put(ModAttributes.EnumAttr.ATK, 47d);
        mainAttr5Star.put(ModAttributes.EnumAttr.ATK_P, 7d/ 100);

        mainAttr5Star.put(ModAttributes.EnumAttr.DEF_P, 8.7d/ 100);
        mainAttr5Star.put(ModAttributes.EnumAttr.CRIT, 4.7d/ INT_SCALER);
        mainAttr5Star.put(ModAttributes.EnumAttr.CRIT_DMG, 9.3d/ INT_SCALER);
        mainAttr5Star.put(ModAttributes.EnumAttr.RECHARGE, 7.8d/ INT_SCALER);
        mainAttr5Star.put(ModAttributes.EnumAttr.HEAL, 5.4d/ INT_SCALER);
        mainAttr5Star.put(ModAttributes.EnumAttr.ELEM_MASTERY, 28d);
        for (EnumElemental elem :
                EnumElemental.values()) {
            mainAttr5Star.put(ModAttributes.getEnumDamage(elem), 7d/ INT_SCALER);
        }
        mainAttr5Star.put(ModAttributes.EnumAttr.PHYSICAL, 8.7d/ INT_SCALER);
    }
}

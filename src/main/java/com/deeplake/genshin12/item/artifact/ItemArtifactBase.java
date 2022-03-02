package com.deeplake.genshin12.item.artifact;

import com.deeplake.genshin12.ILogNBT;
import com.deeplake.genshin12.entity.creatures.attribute.ModAttributes;
import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.item.ItemVariantBase;
import com.deeplake.genshin12.util.EnumElemental;
import com.deeplake.genshin12.util.NBTStrDef.IDLNBTUtil;
import com.google.common.collect.Multimap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.*;

public class ItemArtifactBase extends ItemVariantBase implements ILogNBT {
    public ItemArtifactBase(String name) {
        super(name, 8);
    }

    static Random rand = new Random();

    static List<ModAttributes.EnumAttr> listMain1 = new ArrayList<>();
    static List<ModAttributes.EnumAttr> listMain2 = new ArrayList<>();
    static List<ModAttributes.EnumAttr> listMain3 = new ArrayList<>();
    static List<ModAttributes.EnumAttr> listMain4 = new ArrayList<>();
    static List<ModAttributes.EnumAttr> listMain5 = new ArrayList<>();

    static List<ModAttributes.EnumAttr> listSub = new ArrayList<>();

    static {
        listMain1.add(ModAttributes.EnumAttr.HP);

        listMain2.add(ModAttributes.EnumAttr.ATK);

        listMain3.add(ModAttributes.EnumAttr.ELEM_MASTERY);
        listMain3.add(ModAttributes.EnumAttr.RECHARGE);
        listMain3.add(ModAttributes.EnumAttr.ATK_P);
        listMain3.add(ModAttributes.EnumAttr.HP_P);
        listMain3.add(ModAttributes.EnumAttr.DEF_P);

        listMain4.add(ModAttributes.EnumAttr.ATK_P);
        for (EnumElemental elemental :
                EnumElemental.values()) {
            if (elemental != EnumElemental.CHRONO)
            {
                listMain4.add(ModAttributes.getEnumDamage(elemental));
            }
        }
        listMain4.add(ModAttributes.EnumAttr.ATK_P);
        listMain4.add(ModAttributes.EnumAttr.HP_P);
        listMain4.add(ModAttributes.EnumAttr.DEF_P);

        listMain5.add(ModAttributes.EnumAttr.HEAL);
        listMain5.add(ModAttributes.EnumAttr.CRIT);
        listMain5.add(ModAttributes.EnumAttr.CRIT_DMG);
        listMain5.add(ModAttributes.EnumAttr.ATK_P);
        listMain5.add(ModAttributes.EnumAttr.HP_P);
        listMain5.add(ModAttributes.EnumAttr.DEF_P);

        listSub.add(ModAttributes.EnumAttr.ELEM_MASTERY);
        listSub.add(ModAttributes.EnumAttr.RECHARGE);

        listSub.add(ModAttributes.EnumAttr.ATK_P);
        listSub.add(ModAttributes.EnumAttr.HP_P);
        listSub.add(ModAttributes.EnumAttr.DEF_P);

        //listSub.add(ModAttributes.EnumAttr.ATK);
        listSub.add(ModAttributes.EnumAttr.HP);
        listSub.add(ModAttributes.EnumAttr.DEF);

        listSub.add(ModAttributes.EnumAttr.CRIT);
        listSub.add(ModAttributes.EnumAttr.CRIT_DMG);

    }

    public static final String KEY_LEVEL = "artlvl";
    public static final String KEY_RARITY = "rarity";
    public static final String KEY_READY_ATTR = "ready";//the random attrs that will be given soon
    public static final String KEY_SLOT = "slot";
    public static final String KEY_MAIN_ATTR = "main";
    public static final String[] KEY_SUB_ATTR = {"sub0","sub1","sub2","sub3"};
    public static final String KEY_SUB_SEQ = "subseq";

    static UUID UUID_ARTIFACT = UUID.fromString("63eae879-6870-4d85-aa4b-e9ce52c7e34e");
    static UUID[] UUID_ARTIFACT_SUB =
            {
                    UUID.fromString("a49528cd-bd7e-4663-89a8-00953a3d664c"),
                    UUID.fromString("6cd8fb0c-0a2f-410d-9605-0a98bd19ee35"),
                    UUID.fromString("38ea4903-ed91-4480-86f3-f827c54644af"),
                    UUID.fromString("1c6b68c5-1d8b-4106-bf82-0bc002ea8533"),
            };
    static final String FIX_NAME = "Artifact";
    static final String FIX_NAME_SUB = "Artifact Sub";

    public IAttribute getAttrMain(ItemStack stack)
    {
        return ModAttributes.EnumAttr.getAttr(IDLNBTUtil.GetInt(stack, KEY_MAIN_ATTR));
    }

    public AttributeModifier getAttrMainModifier(ItemStack stack)
    {
        int rarity = getRarityArtifact(stack);
        ModAttributes.EnumAttr attr;
        try {
            attr = ModAttributes.EnumAttr.getEnum(IDLNBTUtil.GetInt(stack, KEY_MAIN_ATTR));
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            attr = ModAttributes.EnumAttr.HP;
        }

        //todo: exact value from level
        double totalModifier;
        float ratioTo5star = attr.isPercent() ? main_attr_5star_percent[rarity - 1] : main_attr_5star_flat[rarity - 1];
        //Shouldn't be null when it gets.
        try {
            totalModifier = mainAttr5Star.get(attr) * ratioTo5star;
        }
        catch (NullPointerException e)
        {
            totalModifier = 1f;
        }

        //+0

        return new AttributeModifier(
                UUID_ARTIFACT,
                FIX_NAME,
                totalModifier,
                ModAttributes.EnumAttr.getEnum(IDLNBTUtil.GetInt(stack, KEY_MAIN_ATTR)).type);
    }

    public IAttribute getAttrSub(ItemStack stack, int index)
    {
        try {
            return ModAttributes.EnumAttr.getAttr(IDLNBTUtil.GetInt(stack, KEY_SUB_ATTR[index]));
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            return SharedMonsterAttributes.MAX_HEALTH;
        }
    }

    final static int MAX_ATTR_SUB_TYPE = 4;
//    final static int SUB_QUALITY_COUNT = 4;

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

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        if (!worldIn.isRemote)
        {
            Random random = entityIn instanceof EntityLivingBase ? ((EntityLivingBase) entityIn).getRNG() : new Random();

            int rarity = getRarityArtifact(stack);

            //init sub attr type
            initSubAttrType(stack, random);

            //Materialize the random attrs.
            int ready = IDLNBTUtil.GetInt(stack, KEY_READY_ATTR);
            if (ready > 0)
            {
                int[] array = IDLNBTUtil.GetIntArray(stack, KEY_SUB_SEQ);
                List<Integer> list = new ArrayList<>();
                Arrays.stream(array).forEach(list::add);
                while (ready > 0)
                {
                    int typeIndex;
                    if (list.size() < MAX_ATTR_SUB_TYPE)
                    {
                        typeIndex = list.size() + 1;//1,2,3,4
                    }
                    else {
                        typeIndex = random.nextInt(MAX_ATTR_SUB_TYPE) + 1;//1,2,3,4
                    }

                    int quality = getRandomSubAttrQuality(random, rarity);

                    int arrayItem = quality + typeIndex * 100;
                    list.add(arrayItem);
                    ready--;

                    //todo: notify player here.
                }
                IDLNBTUtil.SetInt(stack, KEY_READY_ATTR, 0);
                IDLNBTUtil.SetIntArray(stack, KEY_SUB_SEQ, Arrays.stream(list.toArray(new Integer[0])).mapToInt(Integer::valueOf).toArray());
            }

        }
    }

    static void initSubAttrType(ItemStack stack, Random random) {
        if (IDLNBTUtil.GetInt(stack, KEY_SUB_ATTR[0], -1) == -1)
        {
            List<Integer> used = new ArrayList<>();
            //main attr
            used.add(IDLNBTUtil.GetInt(stack, KEY_MAIN_ATTR));
            for (String key : KEY_SUB_ATTR)
            {
                //find an unused attr
                int trial;
                do {
                    trial = listSub.get(random.nextInt(listSub.size())).id;
                }
                while(used.contains(trial));

                used.add(trial);
                IDLNBTUtil.SetInt(stack, key, trial);
            }
        }
    }

    static final int MAX_RARITY = 6;
    //returns 1,2,3,4,5,6
    static int getRarityArtifact(ItemStack stack)
    {
        int rarity = IDLNBTUtil.GetInt(stack, KEY_RARITY);
        if (rarity <= 0)
        {
            return 1;
        }
        else if (rarity >= MAX_RARITY) {
            return MAX_RARITY;
        }
        else {
            return rarity;
        }
    }

    public static AttributeModifier getAttrSubModifier(ItemStack stack, final int index)
    {
        int rarity = getRarityArtifact(stack);
        double totalModifier = 0f;

        ModAttributes.EnumAttr attr;
        try {
            attr = ModAttributes.EnumAttr.getEnum(IDLNBTUtil.GetInt(stack, KEY_SUB_ATTR[index]));
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            attr = ModAttributes.EnumAttr.HP;
        }

        int[] array = IDLNBTUtil.GetIntArray(stack, KEY_SUB_SEQ);
        for (int num:
             array) {
            if (num / 100 - 1 == index)
            {
                int quality = num % 100;
                float ratioTo5star = attr.isPercent() ? sub_attr_5star_percent[rarity - 1] : sub_attr_5star_flat[rarity - 1];
                //Shouldn't be null when it gets.
                totalModifier = subAttr5Star.get(attr) * getRatioToFullQuality(rarity, quality) * ratioTo5star;
            }
        }

        return new AttributeModifier(
                UUID_ARTIFACT_SUB[index],
                FIX_NAME_SUB,
                totalModifier,
                attr.type);
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

    //
    static float[][] subRatios = new float[][]{
      new float[]{0.8f, 1f, 1f, 1f},///only 2 possibilities
      new float[]{0.7f, 0.85f, 1f, 1f},//only 3
      new float[]{0.7f, 0.8f, 0.9f, 1.0f},
      new float[]{0.7f, 0.8f, 0.9f, 1.0f},
      new float[]{0.7f, 0.8f, 0.9f, 1.0f},
    };

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

    //each star sub stat is that of 5-star....
    static float[] sub_attr_5star_flat = {1f, 0.8f, 0.6f, 0.4f, 0.25f};
    static float[] sub_attr_5star_percent = {1f, 0.8f, 0.48f, 0.24f, 0.1f};

    static float[] main_attr_5star_percent = {1f, 0.9f, 0.6f, 0.36f, 0.18f};
    static float[] main_attr_5star_flat = {1f, 0.9f, 0.75f, 0.6f, 0.45f};

    static HashMap<ModAttributes.EnumAttr, Double> subAttr5Star;
    static HashMap<ModAttributes.EnumAttr, Double> mainAttr5Star;
    public static void initValues()
    {
        subAttr5Star = new HashMap<>();
        subAttr5Star.put(ModAttributes.EnumAttr.HP, 298.750 / ModConfig.DEBUG_CONF.HP_CONVERT_RATIO);
        subAttr5Star.put(ModAttributes.EnumAttr.HP_P, 5.83/100);
        subAttr5Star.put(ModAttributes.EnumAttr.ATK, 19.45 / ModConfig.DEBUG_CONF.ATK_CONVERT_RATIO);
        subAttr5Star.put(ModAttributes.EnumAttr.ATK_P, 5.83/100);
        subAttr5Star.put(ModAttributes.EnumAttr.DEF, 23.15);
        subAttr5Star.put(ModAttributes.EnumAttr.DEF_P, 7.29/100);
        subAttr5Star.put(ModAttributes.EnumAttr.CRIT, 3.89/100);
        subAttr5Star.put(ModAttributes.EnumAttr.CRIT_DMG, 7.77/100);
        subAttr5Star.put(ModAttributes.EnumAttr.RECHARGE, 6.48/100);
        subAttr5Star.put(ModAttributes.EnumAttr.ELEM_MASTERY, 23.31);

        mainAttr5Star = new HashMap<>();
        mainAttr5Star.put(ModAttributes.EnumAttr.HP, 717d / ModConfig.DEBUG_CONF.HP_CONVERT_RATIO);
        mainAttr5Star.put(ModAttributes.EnumAttr.HP_P, 7d/100);
        mainAttr5Star.put(ModAttributes.EnumAttr.ATK, 47d);
        mainAttr5Star.put(ModAttributes.EnumAttr.ATK_P, 7d/100);

        mainAttr5Star.put(ModAttributes.EnumAttr.DEF_P, 8.7d/100);
        mainAttr5Star.put(ModAttributes.EnumAttr.CRIT, 4.7d/100);
        mainAttr5Star.put(ModAttributes.EnumAttr.CRIT_DMG, 9.3d/100);
        mainAttr5Star.put(ModAttributes.EnumAttr.RECHARGE, 7.8d/100);
        mainAttr5Star.put(ModAttributes.EnumAttr.HEAL, 5.4d/100);
        mainAttr5Star.put(ModAttributes.EnumAttr.ELEM_MASTERY, 28d);
        for (EnumElemental elem :
                EnumElemental.values()) {
            mainAttr5Star.put(ModAttributes.getEnumDamage(elem), 8.7d/100);
        }
        mainAttr5Star.put(ModAttributes.EnumAttr.PHYSICAL, 8.7d/100);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab))
        {
//            for (int level = 1; level <= 20; level++) {
                for (int level = 1; level <= 20; level++) {
                    for (ModAttributes.EnumAttr attr : listMain3) {
                        ItemStack stack = new ItemStack(this);
                        IDLNBTUtil.SetInt(stack, KEY_LEVEL, level);
                        IDLNBTUtil.SetInt(stack, KEY_MAIN_ATTR, attr.id);

                        IDLNBTUtil.SetInt(stack, KEY_READY_ATTR, initAttrCount(5) + level / 4);

                        items.add(stack);
                    }
                }
//            }

        }
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);

        if (slot == getEquipmentSlot(stack))
        {
            multimap.put(getAttrMain(stack).getName(), getAttrMainModifier(stack));

            for (int i = 0; i <= 3; i++)
            {
                multimap.put(getAttrSub(stack, i).getName(), getAttrSubModifier(stack, i));
            }
        }

        return multimap;
    }

    @Nullable
    @Override
    public EntityEquipmentSlot getEquipmentSlot(ItemStack stack) {
        switch (IDLNBTUtil.GetInt(stack, KEY_SLOT))
        {
            case 0:
                return EntityEquipmentSlot.HEAD;
            case 1:
                return EntityEquipmentSlot.CHEST;
            case 2:
                return EntityEquipmentSlot.LEGS;
            case 3:
                return EntityEquipmentSlot.FEET;
        }
        return super.getEquipmentSlot(stack);
    }
}

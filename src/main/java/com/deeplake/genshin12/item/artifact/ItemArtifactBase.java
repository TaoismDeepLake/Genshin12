package com.deeplake.genshin12.item.artifact;

import com.deeplake.genshin12.ILogNBT;
import com.deeplake.genshin12.Idealland;
import com.deeplake.genshin12.entity.creatures.attribute.ModAttributes;
import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.init.ModCreativeTab;
import com.deeplake.genshin12.item.*;
import com.deeplake.genshin12.item.artifact.set.ArtifactSetManager;
import com.deeplake.genshin12.item.artifact.set.ArtifactSetBase;
import com.deeplake.genshin12.util.EnumElemental;
import com.deeplake.genshin12.util.IDLSkillNBT;
import com.deeplake.genshin12.util.NBTStrDef.IDLNBTUtil;
import com.google.common.collect.Multimap;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.*;

public class ItemArtifactBase extends ItemBase implements ILogNBT, ILeveler {
    final ArtifactSetBase set;

    public ItemArtifactBase(String name) {
        this(name, ArtifactSetManager.DEFAULT);
    }

    static final String PROPERTY_SLOT = "slot";
    public ItemArtifactBase(String name, ArtifactSetBase set) {
        super(name);
        this.set = set;
        this.setHasSubtypes(true);
        setCreativeTab(ModCreativeTab.ARTIFACTS);
        this.addPropertyOverride(new ResourceLocation(PROPERTY_SLOT), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return IDLNBTUtil.GetInt(stack, ArtifactUtil.KEY_SLOT);
            }
        });
    }

    public ArtifactSetBase getSet() {
        return set;
    }

    static {
        ArtifactUtil.listMainFlowerChest.add(ModAttributes.EnumAttr.HP);

        ArtifactUtil.listMain2.add(ModAttributes.EnumAttr.ATK);

        ArtifactUtil.listMainSandsBoot.add(ModAttributes.EnumAttr.ELEM_MASTERY);
        ArtifactUtil.listMainSandsBoot.add(ModAttributes.EnumAttr.RECHARGE);
        ArtifactUtil.listMainSandsBoot.add(ModAttributes.EnumAttr.ATK_P);
        ArtifactUtil.listMainSandsBoot.add(ModAttributes.EnumAttr.HP_P);
        ArtifactUtil.listMainSandsBoot.add(ModAttributes.EnumAttr.DEF_P);

        ArtifactUtil.listMainGobletLeg.add(ModAttributes.EnumAttr.ATK_P);
        for (EnumElemental elemental :
                EnumElemental.values()) {
            if (elemental != EnumElemental.CHRONO)
            {
                ArtifactUtil.listMainGobletLeg.add(ModAttributes.getEnumDamage(elemental));
            }
        }
        ArtifactUtil.listMainGobletLeg.add(ModAttributes.EnumAttr.ATK_P);
        ArtifactUtil.listMainGobletLeg.add(ModAttributes.EnumAttr.HP_P);
        ArtifactUtil.listMainGobletLeg.add(ModAttributes.EnumAttr.DEF_P);

        ArtifactUtil.listMainCircletHead.add(ModAttributes.EnumAttr.HEAL);
        ArtifactUtil.listMainCircletHead.add(ModAttributes.EnumAttr.CRIT);
        ArtifactUtil.listMainCircletHead.add(ModAttributes.EnumAttr.CRIT_DMG);
        ArtifactUtil.listMainCircletHead.add(ModAttributes.EnumAttr.ATK_P);
        ArtifactUtil.listMainCircletHead.add(ModAttributes.EnumAttr.HP_P);
        ArtifactUtil.listMainCircletHead.add(ModAttributes.EnumAttr.DEF_P);

        ArtifactUtil.listSub.add(ModAttributes.EnumAttr.ELEM_MASTERY);
        ArtifactUtil.listSub.add(ModAttributes.EnumAttr.RECHARGE);

        ArtifactUtil.listSub.add(ModAttributes.EnumAttr.ATK_P);
        ArtifactUtil.listSub.add(ModAttributes.EnumAttr.HP_P);
        ArtifactUtil.listSub.add(ModAttributes.EnumAttr.DEF_P);

        ArtifactUtil.listSub.add(ModAttributes.EnumAttr.ATK);
        ArtifactUtil.listSub.add(ModAttributes.EnumAttr.HP);
        ArtifactUtil.listSub.add(ModAttributes.EnumAttr.DEF);

        ArtifactUtil.listSub.add(ModAttributes.EnumAttr.CRIT);
        ArtifactUtil.listSub.add(ModAttributes.EnumAttr.CRIT_DMG);

    }

    public static IAttribute getAttrMain(ItemStack stack)
    {
        return ModAttributes.EnumAttr.getAttr(IDLNBTUtil.GetInt(stack, ArtifactUtil.KEY_MAIN_ATTR));
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumModRarity.getQuality(IDLNBTUtil.GetInt(stack, ArtifactUtil.KEY_RARITY, 1)).rarity;
    }

    public AttributeModifier getAttrMainModifier(ItemStack stack)
    {
        int rarity = LevelingUtil.getRarityArtifact(stack);
        int level = LevelingUtil.getLevelForItem(stack);
        ModAttributes.EnumAttr attr;
        try {
            attr = ModAttributes.EnumAttr.getEnum(IDLNBTUtil.GetInt(stack, ArtifactUtil.KEY_MAIN_ATTR));
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            attr = ModAttributes.EnumAttr.HP;
        }

        double totalModifier;
        float ratioTo5star = attr.isSpecialRarirtyRateMainAttr() ? ArtifactUtil.main_attr_5star_percent[rarity - 1] : ArtifactUtil.main_attr_5star_flat[rarity - 1];
        //Shouldn't be null when it gets.
        try {
            totalModifier = ArtifactUtil.mainAttr5Star.get(attr) * ratioTo5star * ArtifactUtil.getLevelRatio(rarity, level);
        }
        catch (NullPointerException e)
        {
            totalModifier = 1f;
        }

        return new AttributeModifier(
                ArtifactUtil.UUID_ARTIFACT,
                ArtifactUtil.FIX_NAME,
                totalModifier,
                ModAttributes.EnumAttr.getEnum(IDLNBTUtil.GetInt(stack, ArtifactUtil.KEY_MAIN_ATTR)).type);
    }

    public IAttribute getAttrSub(ItemStack stack, int index)
    {
        try {
            return ModAttributes.EnumAttr.getAttr(IDLNBTUtil.GetInt(stack, ArtifactUtil.KEY_SUB_ATTR[index]));
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            return SharedMonsterAttributes.MAX_HEALTH;
        }
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        if (!worldIn.isRemote)
        {
            Random random = entityIn instanceof EntityLivingBase ? ((EntityLivingBase) entityIn).getRNG() : new Random();

            int rarity = LevelingUtil.getRarityArtifact(stack);

            //init sub attr type
            initSubAttrType(stack, random);

            //Materialize the random attrs.
            int ready = IDLNBTUtil.GetInt(stack, ArtifactUtil.KEY_READY_ATTR);
            if (ready > 0)
            {
                int[] array = IDLNBTUtil.GetIntArray(stack, ArtifactUtil.KEY_SUB_SEQ);
                List<Integer> list = new ArrayList<>();
                Arrays.stream(array).forEach(list::add);
                while (ready > 0)
                {
                    int typeIndex;
                    if (list.size() < ArtifactUtil.MAX_ATTR_SUB_TYPE)
                    {
                        typeIndex = list.size() + 1;//1,2,3,4
                    }
                    else {
                        typeIndex = random.nextInt(ArtifactUtil.MAX_ATTR_SUB_TYPE) + 1;//1,2,3,4
                    }

                    int quality = ArtifactUtil.getRandomSubAttrQuality(random, rarity);

                    int arrayItem = quality + typeIndex * 100;
                    list.add(arrayItem);
                    ready--;

                    //todo: notify player here.
                }
                IDLNBTUtil.SetInt(stack, ArtifactUtil.KEY_READY_ATTR, 0);
                IDLNBTUtil.SetIntArray(stack, ArtifactUtil.KEY_SUB_SEQ, Arrays.stream(list.toArray(new Integer[0])).mapToInt(Integer::valueOf).toArray());
            }

        }
    }

    static void initSubAttrType(ItemStack stack, Random random) {
        if (IDLNBTUtil.GetInt(stack, ArtifactUtil.KEY_SUB_ATTR[0], -1) == -1)
        {
            List<Integer> used = new ArrayList<>();
            //main attr
            used.add(IDLNBTUtil.GetInt(stack, ArtifactUtil.KEY_MAIN_ATTR));
            for (String key : ArtifactUtil.KEY_SUB_ATTR)
            {
                //find an unused attr
                int trial;
                do {
                    trial = ArtifactUtil.listSub.get(random.nextInt(ArtifactUtil.listSub.size())).id;
                }
                while(used.contains(trial));

                used.add(trial);
                IDLNBTUtil.SetInt(stack, key, trial);
            }
        }
    }

    public static AttributeModifier getAttrSubModifier(ItemStack stack, final int index)
    {
        int rarity = LevelingUtil.getRarityArtifact(stack);
        double totalModifier = 0f;

        ModAttributes.EnumAttr attr;
        try {
            attr = ModAttributes.EnumAttr.getEnum(IDLNBTUtil.GetInt(stack, ArtifactUtil.KEY_SUB_ATTR[index]));
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            attr = ModAttributes.EnumAttr.HP;
        }

        int[] array = IDLNBTUtil.GetIntArray(stack, ArtifactUtil.KEY_SUB_SEQ);
        for (int num:
             array) {
            if (num / 100 - 1 == index)
            {
                int quality = num % 100;
                float ratioTo5star = attr.isSpecialRarirtyRateSubAttr() ? ArtifactUtil.sub_attr_5star_percent[rarity - 1] : ArtifactUtil.sub_attr_5star_flat[rarity - 1];
                //Shouldn't be null when it gets.
                totalModifier += ArtifactUtil.subAttr5Star.get(attr) * ArtifactUtil.getRatioToFullQuality(rarity, quality) * ratioTo5star;
            }
        }

        return new AttributeModifier(
                ArtifactUtil.UUID_ARTIFACT_SUB[index],
                ArtifactUtil.FIX_NAME_SUB,
                totalModifier,
                attr.type);
    }

    public ItemStack getRandomBlankInstance(int slot, int rarity)
    {
        List<ModAttributes.EnumAttr> attrList = ArtifactUtil.getMainAttrListFromSlot(slot);

        ItemStack stack = new ItemStack(this);
        IDLNBTUtil.SetInt(stack, ArtifactUtil.KEY_LEVEL, 0);
        IDLNBTUtil.SetInt(stack, ArtifactUtil.KEY_MAIN_ATTR, attrList.get(itemRand.nextInt(attrList.size())).id);
        IDLNBTUtil.SetInt(stack, ArtifactUtil.KEY_RARITY, rarity);
        IDLNBTUtil.SetInt(stack, ArtifactUtil.KEY_SLOT, slot);

        return stack;
    }

    public ItemStack getRandomBlankInstance(int rarity)
    {
        int slot = itemRand.nextInt(4);
        return getRandomBlankInstance(slot, rarity);
    }

    public ItemStack getRandomBlankInstance()
    {
        int slot = itemRand.nextInt(4);
        int rarity = set.minRarity;
        if (set.maxRarity != set.minRarity)
        {
            rarity += itemRand.nextInt(set.maxRarity - set.minRarity + 1);
        }

        return getRandomBlankInstance(slot, rarity);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab))
        {
            boolean isDebug = ModConfig.DEBUG_CONF.DEBUG_MODE;
            if (set == ArtifactSetManager.DEFAULT)
            {
                //hide the debug set. too annoying.
                if (!isDebug)
                {
                    return;
                }
            }

            for (int slot = 0; slot <= 3; slot++) {
                for (int rarity = set.minRarity; rarity <= set.maxRarity; rarity++) {
                    for (int level = 0; level <= ArtifactUtil.getMaxLevel(rarity); level++) {
                        if (level != 0 && level != ArtifactUtil.getMaxLevel(rarity))
                        {
                            if (!isDebug)
                            {
                                continue;
                            }
                        }

                        for (ModAttributes.EnumAttr attr : ArtifactUtil.getMainAttrListFromSlot(slot))                           {
                            ItemStack stack = new ItemStack(this);
                            IDLNBTUtil.SetInt(stack, ArtifactUtil.KEY_LEVEL, level);
                            IDLNBTUtil.SetInt(stack, ArtifactUtil.KEY_MAIN_ATTR, attr.id);
                            IDLNBTUtil.SetInt(stack, ArtifactUtil.KEY_RARITY, rarity);
                            IDLNBTUtil.SetInt(stack, ArtifactUtil.KEY_SLOT, slot);

                            IDLNBTUtil.SetInt(stack, ArtifactUtil.KEY_READY_ATTR, ArtifactUtil.initAttrCount(rarity) + level / 4);

                            items.add(stack);
                        }
                    }
                }
            }
        }

        else {
            super.getSubItems(tab, items);
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
        switch (IDLNBTUtil.GetInt(stack, ArtifactUtil.KEY_SLOT))
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

    @SideOnly(Side.CLIENT)
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        int slot = IDLNBTUtil.GetInt(stack, ArtifactUtil.KEY_SLOT);
        return I18n.format(String.format("genshin12.artifact.%s.%s", getSet().key, slot)).trim();
    }

    public static int getXPWorth(ItemStack stack)
    {
        if (stack.getItem() instanceof ItemArtifactBase)
        {
//            ItemArtifactBase artifactBase = (ItemArtifactBase) stack.getItem();
            int rarity = LevelingUtil.getRarityArtifact(stack);
            int level = LevelingUtil.getLevelForItem(stack);
            int base = 100;
            try {
                base = xp_worth[rarity - 1];
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
                Idealland.LogWarning("Wrong rarity for artifact: %s", rarity);
            }

            return (int) (base + getTotalXP(rarity, level) * 0.8f + IDLSkillNBT.getXP(stack));
        }
        else if (stack.getItem() == ModItems.ARTIFACT_XP_BOTTLE)
        {
            int meta = stack.getItemDamage();
            if (meta == 0)
            {
                return IDLSkillNBT.getXP(stack);
            }
            else {
                try {
                    return xp_worth[meta - 1];
                }
                catch (ArrayIndexOutOfBoundsException e)
                {
                    Idealland.LogWarning(e.toString());
                    return -1;
                }
            }
        }
        else {
            return -1;
        }

    }

    public static int[] xp_worth = {420,840,1260,2520,3780};

    //level 2 = +2, the first 2 items in the array.
    public static int getTotalXP(int rarity, int level)
    {
        int[] table = getXPTable(rarity);
        int total = 0;
        for (int i = 0; i < level; i++)
        {
            try {
                total += table[i];
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
                Idealland.LogWarning("Wrong level %s for rarity %s", level, rarity);
            }
        }
        return total;
    }

    public static int[] getXPTable(int rarity)
    {
        switch (rarity)
        {
            case 1:
                return exp_artifact_1;
                case 2:
                return exp_artifact_2;
                case 3:
                return exp_artifact_3;
                case 4:
                return exp_artifact_4;
                case 5:
                return exp_artifact_5;
            default:
                Idealland.LogWarning("unexpected rarity %s", rarity);
                return exp_artifact_5;
        }
    }

    public static int[] exp_artifact_1 = {
            600,750,874,1025
    };

    public static int[] exp_artifact_2 = {
            1200,1500,1775,2050
    };

    public static int[] exp_artifact_3 = {
            1800,2225,2650,3100,3550,4000,4500,5000,5525,6075,6625,7225
    };

    public static int[] exp_artifact_4 = {
            2400,2975,3550,4125,4725,5350,6000,6675,7375,8100,8850,9625,10425,12125,14075,16300
    };

    public static int[] exp_artifact_5 = {
            3000,
            3725,
            4425,
            5150,
            5900,
            6675,
            7500,
            8350,
            9225,
            10125,
            11050,
            12025,
            13025,
            15150,
            17600,
            20375,
            23500,
            27050,
            31050,
            35575
    };

    @Override
    public int[] levelupNeedXp(ItemStack stack) {
        return getXPTable(LevelingUtil.getRarityArtifact(stack));
    }

    @Override
    public int getMaxLevel(ItemStack stack) {
        return ArtifactUtil.getMaxLevel(LevelingUtil.getRarityArtifact(stack));
    }
}

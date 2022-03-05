package com.deeplake.genshin12.item.artifact;

import com.deeplake.genshin12.ILogNBT;
import com.deeplake.genshin12.entity.creatures.attribute.ModAttributes;
import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.item.EnumModRarity;
import com.deeplake.genshin12.item.ItemVariantBase;
import com.deeplake.genshin12.item.artifact.set.ArtifactSetManager;
import com.deeplake.genshin12.item.artifact.set.ArtifactSetBase;
import com.deeplake.genshin12.util.EnumElemental;
import com.deeplake.genshin12.util.NBTStrDef.IDLNBTUtil;
import com.google.common.collect.Multimap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.init.Items;
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

public class ItemArtifactBase extends ItemVariantBase implements ILogNBT {
    final ArtifactSetBase set;

    public ItemArtifactBase(String name) {
        this(name, ArtifactSetManager.DEFAULT);
    }

    static final String PROPERTY_SLOT = "slot";
    public ItemArtifactBase(String name, ArtifactSetBase set) {
        super(name, 1);
        this.set = set;
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

        ArtifactUtil.listMainGobletLeg.add(ModAttributes.EnumAttr.ELEM_MASTERY);
        ArtifactUtil.listMainGobletLeg.add(ModAttributes.EnumAttr.RECHARGE);
        ArtifactUtil.listMainGobletLeg.add(ModAttributes.EnumAttr.ATK_P);
        ArtifactUtil.listMainGobletLeg.add(ModAttributes.EnumAttr.HP_P);
        ArtifactUtil.listMainGobletLeg.add(ModAttributes.EnumAttr.DEF_P);

        ArtifactUtil.listMainSandsBoot.add(ModAttributes.EnumAttr.ATK_P);
        for (EnumElemental elemental :
                EnumElemental.values()) {
            if (elemental != EnumElemental.CHRONO)
            {
                ArtifactUtil.listMainSandsBoot.add(ModAttributes.getEnumDamage(elemental));
            }
        }
        ArtifactUtil.listMainSandsBoot.add(ModAttributes.EnumAttr.ATK_P);
        ArtifactUtil.listMainSandsBoot.add(ModAttributes.EnumAttr.HP_P);
        ArtifactUtil.listMainSandsBoot.add(ModAttributes.EnumAttr.DEF_P);

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

        //listSub.add(ModAttributes.EnumAttr.ATK);
        ArtifactUtil.listSub.add(ModAttributes.EnumAttr.HP);
        ArtifactUtil.listSub.add(ModAttributes.EnumAttr.DEF);

        ArtifactUtil.listSub.add(ModAttributes.EnumAttr.CRIT);
        ArtifactUtil.listSub.add(ModAttributes.EnumAttr.CRIT_DMG);

    }

    public IAttribute getAttrMain(ItemStack stack)
    {
        return ModAttributes.EnumAttr.getAttr(IDLNBTUtil.GetInt(stack, ArtifactUtil.KEY_MAIN_ATTR));
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumModRarity.getQuality(IDLNBTUtil.GetInt(stack, ArtifactUtil.KEY_RARITY)).rarity;
    }

    public AttributeModifier getAttrMainModifier(ItemStack stack)
    {
        int rarity = getRarityArtifact(stack);
        int level = IDLNBTUtil.GetInt(stack, ArtifactUtil.KEY_LEVEL);
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

        //+0

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

            int rarity = getRarityArtifact(stack);

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

    //returns 1,2,3,4,5,6
    static int getRarityArtifact(ItemStack stack)
    {
        int rarity = IDLNBTUtil.GetInt(stack, ArtifactUtil.KEY_RARITY);
        if (rarity <= 0)
        {
            return 1;
        }
        else if (rarity >= ArtifactUtil.MAX_RARITY) {
            return ArtifactUtil.MAX_RARITY;
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


    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (ModConfig.DEBUG_CONF.DEBUG_MODE)
        {
            if (this.isInCreativeTab(tab))
            {
                for (int slot = 0; slot <= 3; slot++) {
                    for (int rarity = 1; rarity <= 5; rarity++) {
                        for (int level = 1; level <= ArtifactUtil.getMaxLevel(rarity); level++) {
                            for (ModAttributes.EnumAttr attr : ArtifactUtil.getMainAttrListFromSlot(slot)) {
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
}

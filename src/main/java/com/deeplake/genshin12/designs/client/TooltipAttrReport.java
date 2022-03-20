package com.deeplake.genshin12.designs.client;

import com.deeplake.genshin12.Idealland;
import com.deeplake.genshin12.entity.creatures.attribute.ModAttributes;
import com.deeplake.genshin12.item.ModItems;
import com.deeplake.genshin12.item.artifact.ArtifactUtil;
import com.deeplake.genshin12.item.weapon.ItemPlayerWeapon;
import com.deeplake.genshin12.util.EnumElemental;
import com.deeplake.genshin12.util.IDLSkillNBT;
import com.deeplake.genshin12.util.NBTStrDef.IDLNBTUtil;
import com.google.common.collect.Multimap;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static com.deeplake.genshin12.item.LevelingUtil.getRarityArtifact;
import static net.minecraft.item.ItemStack.DECIMALFORMAT;

@Mod.EventBusSubscriber(Side.CLIENT)
public class TooltipAttrReport {
    static Collection<IAttribute> collectionVanilla = new HashSet<>();
    static Collection<IAttribute> collectionGenshinBasic = new HashSet<>();
    static Collection<IAttribute> collectionGenshinResist = new HashSet<>();
    static Collection<IAttribute> collectionGenshinDamage = new HashSet<>();

    static {
        collectionVanilla.add(SharedMonsterAttributes.MAX_HEALTH);
        collectionVanilla.add(SharedMonsterAttributes.ATTACK_DAMAGE);
        collectionVanilla.add(SharedMonsterAttributes.ATTACK_SPEED);
        collectionVanilla.add(SharedMonsterAttributes.MOVEMENT_SPEED);
        collectionVanilla.add(SharedMonsterAttributes.ARMOR);
        collectionVanilla.add(SharedMonsterAttributes.ARMOR_TOUGHNESS);
        collectionVanilla.add(SharedMonsterAttributes.LUCK);

        collectionGenshinBasic.add(SharedMonsterAttributes.MAX_HEALTH);
        collectionGenshinBasic.add(ModAttributes.GEN_ATK);
        collectionGenshinBasic.add(ModAttributes.DEFENSE);
        collectionGenshinBasic.add(ModAttributes.CRIT_RATE);
        collectionGenshinBasic.add(ModAttributes.CRIT_DMG);
        collectionGenshinBasic.add(ModAttributes.ELEM_MASTERY);
        collectionGenshinBasic.add(ModAttributes.SHIELD_STR);
        collectionGenshinBasic.add(ModAttributes.HEAL_BONUS);
        collectionGenshinBasic.add(ModAttributes.IN_HEAL_BONUS);

        for (EnumElemental elem :
                EnumElemental.values()) {
            collectionGenshinDamage.add(ModAttributes.getElemBonus(elem));
            collectionGenshinResist.add(ModAttributes.getElemRes(elem));
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onToolTip(ItemTooltipEvent event)
    {
        //todo: separate it.
        //type1: basic MC attr + some genshin attr
        //type2: elemental attr
        //type3: whatnot attr
        //type4: others. should there be too many, cycle.
        ItemStack stack = event.getItemStack();
        EntityPlayer player = event.getEntityPlayer();
        Collection<IAttributeInstance> collection;
        if (stack.getItem() == ModItems.ATTR_REPORT && player != null)
        {
            Collection<IAttribute> attributes;
            switch (stack.getMetadata())
            {
                case 0:
                    attributes = collectionVanilla;
                    break;
                case 1:
                    attributes = collectionGenshinBasic;
                    break;
                case 2:
                    attributes = collectionGenshinDamage;
                    break;
                case 3:
                    attributes = collectionGenshinResist;
                    break;
                default:
                    attributes = ModAttributes.allNewAttrs;
            }
            List<String> list = event.getToolTip();
            for (IAttributeInstance iAttributeInstance: player.getAttributeMap().getAllAttributes())
            {
                if (attributes.contains(iAttributeInstance.getAttribute()))
                {
                    list.add(" " + net.minecraft.util.text.translation.I18n.translateToLocalFormatted("attribute.modifier.equals.0" , DECIMALFORMAT.format(iAttributeInstance.getAttributeValue()), net.minecraft.util.text.translation.I18n.translateToLocal("attribute.name." + iAttributeInstance.getAttribute().getName())));
                }
            }
        }
    }
}

package com.deeplake.genshin12.designs.client;

import com.deeplake.genshin12.Idealland;
import com.deeplake.genshin12.item.ModItems;
import com.deeplake.genshin12.item.artifact.ArtifactUtil;
import com.deeplake.genshin12.item.weapon.ItemPlayerWeapon;
import com.deeplake.genshin12.util.IDLSkillNBT;
import com.deeplake.genshin12.util.NBTStrDef.IDLNBTUtil;
import com.google.common.collect.Multimap;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
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

import java.util.List;
import java.util.Map;

import static com.deeplake.genshin12.item.LevelingUtil.getRarityArtifact;
import static net.minecraft.item.ItemStack.DECIMALFORMAT;

@Mod.EventBusSubscriber(Side.CLIENT)
public class TooltipAttrReport {
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
        if (stack.getItem() == ModItems.ATTR_REPORT && player != null)
        {
            List<String> list = event.getToolTip();
            for (IAttributeInstance iAttributeInstance: player.getAttributeMap().getAllAttributes())
            {
                list.add(" " + net.minecraft.util.text.translation.I18n.translateToLocalFormatted("attribute.modifier.equals.0" , DECIMALFORMAT.format(iAttributeInstance.getAttributeValue()), net.minecraft.util.text.translation.I18n.translateToLocal("attribute.name." + iAttributeInstance.getAttribute().getName())));
            }
        }
    }
}

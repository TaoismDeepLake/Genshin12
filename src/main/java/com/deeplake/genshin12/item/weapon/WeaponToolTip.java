package com.deeplake.genshin12.item.weapon;

import com.deeplake.genshin12.Idealland;
import com.deeplake.genshin12.item.EnumModRarity;
import com.deeplake.genshin12.item.artifact.ArtifactUtil;
import com.deeplake.genshin12.item.artifact.ItemArtifactBase;
import com.deeplake.genshin12.item.artifact.set.ArtifactSetBase;
import com.deeplake.genshin12.util.IDLSkillNBT;
import com.deeplake.genshin12.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static com.deeplake.genshin12.item.LevelingUtil.getRarityArtifact;

@Mod.EventBusSubscriber(Side.CLIENT)
public class WeaponToolTip {

    public static final String GENSHIN_12_WEAPON_RARITY = "genshin12.artifact.rarity.";
    public static final String GENSHIN_12_WEAPON_LEVEL_MAX = "genshin12.artifact.level.max";
    public static final String GENSHIN_12_WEAPON_LEVEL = "genshin12.weapon.level";
//    public static final String GENSHIN_12_ARTIFACT_SLOT = "genshin12.artifact.slot.";
    public static final String GENSHIN_12_WEAPON_UPGRADE = "genshin12.weapon.upgrade";
    public static final String GENSHIN_12_ARTIFACT_XP = "genshin12.artifact.xp";

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onToolTipColor(RenderTooltipEvent.Color event) {
        if (event.getStack().getItem() instanceof ItemPlayerWeapon) {
            int rarity = getRarityArtifact(event.getStack());

//            event.setBackground(0xf0330000);
            event.setBorderStart(0xf0000000 | EnumModRarity.getColor(rarity));
            event.setBorderEnd(0x00cc0000);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onToolTip(ItemTooltipEvent event)
    {
        ItemStack stack = event.getItemStack();
        if (stack.getItem() instanceof ItemPlayerWeapon)
        {
            ItemPlayerWeapon artifactBase = (ItemPlayerWeapon) stack.getItem();
            int rarity = getRarityArtifact(stack);
            int level = IDLNBTUtil.GetInt(stack, ArtifactUtil.KEY_LEVEL);
            int maxLevel = ArtifactUtil.getMaxLevel(rarity);
            List<String> strings = event.getToolTip();

            strings.add(1, I18n.format(GENSHIN_12_WEAPON_RARITY + rarity));

            try
            {
                if (level != maxLevel)
                {
                    strings.add(1, I18n.format(GENSHIN_12_ARTIFACT_XP, IDLSkillNBT.getXP(stack), artifactBase.levelupNeedXp(stack)[level]));
                }
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
                Idealland.LogWarning(e.toString());
            }


            if (level == maxLevel)
            {
                strings.add(1, I18n.format(GENSHIN_12_WEAPON_LEVEL_MAX));
            }
            else if (level != 0) {
                strings.add(1, I18n.format(GENSHIN_12_WEAPON_LEVEL, level));
            }

//            IAttribute mainAttr = ItemArtifactBase.getAttrMain(stack);
//            strings.add(1, net.minecraft.util.text.translation.I18n.translateToLocal("attribute.name." + mainAttr.getName()));

//            strings.add(1, I18n.format(GENSHIN_12_ARTIFACT_SLOT + (IDLNBTUtil.GetInt(stack, ArtifactUtil.KEY_SLOT, 0)+1)));

//            int ready = IDLNBTUtil.GetInt(stack, ArtifactUtil.KEY_READY_ATTR);
//            if (ready > 0)
//            {
//                strings.add(1, I18n.format(GENSHIN_12_WEAPON_UPGRADE, ready));
//            }
        }
    }

//    static final String SINGLE_COUNT = "genshin12.artifact.suit.count.single";

//    @SideOnly(Side.CLIENT)
//    public static void descArtifactSet(ItemTooltipEvent event, ItemArtifactBase artifactBase, int level, List<String> strings) {
//        ArtifactSetBase setBase = artifactBase.set;
//        if (setBase != null && event.getEntityPlayer() != null)
//        {
//            EntityPlayer player = event.getEntityPlayer();
//            int now = artifactBase.set.getNowCount(player);
//
//            int inverseCount = 1;
//            for (int count :
//                    setBase.suitList) {
//                TextFormatting textFormatting = now >= count ? TextFormatting.GREEN : TextFormatting.GRAY;
//                strings.add(inverseCount, textFormatting + I18n.format(ArtifactSetBase.SUIT_COUNT_KEY, now, count) + I18n.format(setBase.getSuitDescKey(count), level));
//                inverseCount++;
//            }
//
//            strings.add(1, I18n.format(setBase.getSuitNameKey()) + I18n.format(SINGLE_COUNT, now));
//        }
//    }
}

package com.deeplake.genshin12.item.artifact;

import com.deeplake.genshin12.item.EnumModRarity;
import com.deeplake.genshin12.item.artifact.set.ArtifactSetBase;
import com.deeplake.genshin12.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.client.resources.I18n;
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

import static com.deeplake.genshin12.item.artifact.ItemArtifactBase.getRarityArtifact;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ArtifactToolTip {

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
            int maxLevel = ArtifactUtil.getMaxLevel(rarity);
            List<String> strings = event.getToolTip();

            descArtifactSet(event, artifactBase, level, strings);

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
    public static void descArtifactSet(ItemTooltipEvent event, ItemArtifactBase artifactBase, int level, List<String> strings) {
        ArtifactSetBase setBase = artifactBase.set;
        if (setBase != null && event.getEntityPlayer() != null)
        {
            EntityPlayer player = event.getEntityPlayer();
            int now = artifactBase.set.getNowCount(player);

            int inverseCount = 1;
            for (int count :
                    setBase.suitList) {
                TextFormatting textFormatting = now >= count ? TextFormatting.GREEN : TextFormatting.GRAY;
                strings.add(inverseCount, textFormatting + I18n.format(ArtifactSetBase.SUIT_COUNT_KEY, now, count) + I18n.format(setBase.getSuitDescKey(count), level));
                inverseCount++;
            }

            strings.add(1, I18n.format(setBase.getSuitNameKey()));
        }
    }
}

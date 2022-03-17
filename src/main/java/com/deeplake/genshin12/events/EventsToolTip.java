package com.deeplake.genshin12.events;

import com.deeplake.genshin12.ILogNBT;
import com.deeplake.genshin12.Idealland;
import com.deeplake.genshin12.item.IWIP;
import com.deeplake.genshin12.item.skills.ItemSkillBase;
import com.deeplake.genshin12.util.IDLSkillNBT;
import com.deeplake.genshin12.util.NBTStrDef.IDLNBTDef;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.deeplake.genshin12.util.NBTStrDef.IDLNBTDef.WIP_DESC;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class EventsToolTip {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onToolTipColor(RenderTooltipEvent.Color event) {
        if (event.getStack().getItem() instanceof IWIP) {
            event.setBackground(0xf0330000);
            event.setBorderStart(0xf0cc0000);
            event.setBorderEnd(0xf0cc0000);
        }
    }


    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    static void onToolTip(ItemTooltipEvent event)
    {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        if (item instanceof IWIP)
        {
            event.getToolTip().add(1, TextFormatting.RED + I18n.format(WIP_DESC));
        }

        if (item instanceof ILogNBT)
        {
            event.getToolTip().add(String.valueOf(stack.getTagCompound()));
        }

        if (item instanceof ItemSkillBase)
        {
            ItemSkillBase skillBase = (ItemSkillBase) item;
            if (skillBase.useXP_level)
            {
                int lv = IDLSkillNBT.getLevel(stack);
                boolean isFull = lv < 0 || lv >= skillBase.levelup_need_xp.length;
                String tip2 = isFull ? I18n.format(IDLNBTDef.MAX) : String.valueOf(skillBase.levelup_need_xp[lv]);
                event.getToolTip().add(1, TextFormatting.AQUA + I18n.format(IDLNBTDef.XP_GAUGE, IDLSkillNBT.getXP(stack), tip2));
            }
        }
    }
}
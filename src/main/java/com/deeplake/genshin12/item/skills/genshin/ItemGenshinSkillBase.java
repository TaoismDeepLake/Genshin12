package com.deeplake.genshin12.item.skills.genshin;

import com.deeplake.genshin12.item.skills.ItemSkillBase;
import com.deeplake.genshin12.util.CommonFunctions;

import com.deeplake.genshin12.util.EnumElemental;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemGenshinSkillBase extends ItemSkillBase {
    int speechCount = 3;

    public static final String SPEECH_KEY = "speech";
    public static final String SPEECH_KEY_LONG = "speech.long";

    EnumElemental elemental;

    public ItemGenshinSkillBase(String name, EnumElemental elemental) {
        super(name);
        setMaxLevel(13);
        this.elemental = elemental;
    }

    public EnumElemental getElemental() {
        return elemental;
    }

    @Override
    public boolean applyCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot) {
        if (livingBase instanceof EntityPlayer)
        {
            talkShort((EntityPlayer) livingBase);
        }
        return super.applyCast(worldIn, livingBase, stack, slot);
    }

    @Override
    public boolean applyLongCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot) {
        if (livingBase instanceof EntityPlayer)
        {
            talkLong((EntityPlayer) livingBase);
        }
        return super.applyLongCast(worldIn, livingBase, stack, slot);
    }

    void talkShort(EntityPlayer player)
    {
        int index = player.getRNG().nextInt(speechCount) + 1;
        CommonFunctions.SafeSendMsgToPlayer(TextFormatting.ITALIC, player, String.format("%s.%s.%d", getUnlocalizedName(), SPEECH_KEY, index));
    }

    void talkLong(EntityPlayer player)
    {
        int index = player.getRNG().nextInt(speechCount) + 1;
        CommonFunctions.SafeSendMsgToPlayer(TextFormatting.ITALIC, player, String.format("%s.%s.%d", getUnlocalizedName(), SPEECH_KEY_LONG, index));
    }
}

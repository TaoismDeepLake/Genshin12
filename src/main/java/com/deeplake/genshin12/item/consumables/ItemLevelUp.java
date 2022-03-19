package com.deeplake.genshin12.item.consumables;

import com.deeplake.genshin12.designs.scaling.PlayerScaling;
import com.deeplake.genshin12.util.CommonFunctions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemLevelUp extends ItemConsumableBase {

    public static final String GENSHIN_12_MSG_PLAYER_LEVELUP = "genshin12.msg.player.levelup";

    public ItemLevelUp(String name) {
        super(name);
    }

    @Override
    public ActionResult<ItemStack> onConsume(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (!worldIn.isRemote) {
            PlayerScaling.levelUp(playerIn);
            worldIn.playSound(null, playerIn.getPosition(), SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS, 1f, 1f);
            CommonFunctions.SafeSendMsgToPlayer(TextFormatting.GREEN, playerIn, GENSHIN_12_MSG_PLAYER_LEVELUP, PlayerScaling.getLevel(playerIn));

            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }
        return new ActionResult<>(EnumActionResult.FAIL, stack);
    }
}

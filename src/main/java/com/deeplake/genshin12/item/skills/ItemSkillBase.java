package com.deeplake.genshin12.item.skills;

import com.deeplake.genshin12.IdlFramework;
import com.deeplake.genshin12.init.ModCreativeTab;
import com.deeplake.genshin12.item.ItemAdaptingBase;
import com.deeplake.genshin12.util.CommonFunctions;
import com.deeplake.genshin12.util.IDLSkillNBT;
import com.deeplake.genshin12.util.MessageDef;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static com.deeplake.genshin12.util.CommonDef.G_SKY;
import static com.deeplake.genshin12.util.CommonDef.TICK_PER_SECOND;
import static com.deeplake.genshin12.util.CommonFunctions.*;
import static com.deeplake.genshin12.util.EntityUtil.findSlot;
import static com.deeplake.genshin12.util.MessageDef.getSkillCastKey;

enum SKILL_MSG_TYPE
{
    SUCCESS,
    CD,
}


public class ItemSkillBase extends ItemAdaptingBase implements ICastable{
    public boolean isMartial = false;

    public int long_press_ticks = -1;
    public boolean force_release = false;

    protected int maxDialogues = 0;

    public ItemSkillBase(String name) {
        super(name);
        setMaxStackSize(1);
        setNoRepair();
        setCreativeTab(ModCreativeTab.IDL_MISC);
        offHandCast = true;
        mainHandCast = true;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return super.getRarity(stack);
    }

    public ItemSkillBase setPassive()
    {
        cannotMouseCast = true;
        offHandCast = false;
        mainHandCast = false;
        return this;
    }

    public ItemSkillBase setMaxLevel(int maxLevel)
    {
        this.maxLevel = maxLevel;
        return this;
    }

    public ItemSkillBase setVal(float val, float val_per_level)
    {
        basic_val = val;
        this.val_per_level = val_per_level;
        return this;
    }

    public ItemSkillBase setCD(float val, float val_per_level)
    {
        cool_down = val;
        this.cool_down_reduce_per_lv = val_per_level;
        return this;
    }

    public ItemSkillBase setCDLong(float val, float val_per_level)
    {
        cool_down_long = val;
        this.cool_down_long_reduce_per_lv = val_per_level;
        return this;
    }

    public ItemSkillBase setDura(float val, float val_per_level)
    {
        dura_val = val;
        this.dura_per_level = val_per_level;
        return this;
    }

    public ItemSkillBase setRange(float val, float val_per_level)
    {
        base_range = val;
        this.range_per_level = val_per_level;
        return this;
    }

    //starts from 1
    public int getLevel(ItemStack stack)
    {
        return IDLSkillNBT.getLevel(stack);
    }


    public float getRange(ItemStack stack)
    {
        return (IDLSkillNBT.getLevel(stack) - 1) * range_per_level + base_range;
    }

    public float getDura(ItemStack stack)
    {
        return  (IDLSkillNBT.getLevel(stack) - 1) * dura_per_level + dura_val;
    }
    public float getVal(ItemStack stack)
    {
        return  (IDLSkillNBT.getLevel(stack) - 1) * val_per_level + basic_val;
    }
    public float getCoolDown(ItemStack stack) {
        float result = -(IDLSkillNBT.getLevel(stack) - 1) * cool_down_reduce_per_lv + cool_down;
        return result > 0.1f ? result : 0.1f; }

    public float getCoolDownLong(ItemStack stack) {
        float result = -(IDLSkillNBT.getLevel(stack) - 1) * cool_down_long_reduce_per_lv + cool_down_long;
        return result > 0.1f ? result : 0.1f; }

    protected int getCoolDownLongTicks(ItemStack stack) {
        return (int) (getCoolDownLong(stack) * TICK_PER_SECOND);
    }


    //leveling-------------------------------------

    public int GetLevelMax(ItemStack stack)
    {
        if (!(stack.getItem() instanceof ItemSkillBase)) {
            return 0;
        }
        return maxLevel;
    }

    public void SendDefaultMsg(EntityPlayer player, ItemStack stack, SKILL_MSG_TYPE msg_type)
    {
        switch (msg_type)
        {
            case CD:
                SendMsgToPlayer((EntityPlayerMP)player, stack.getUnlocalizedName()+ IDLSkillNBT.IN_CD_DESC_KEY);
            case SUCCESS:
                SendMsgToPlayer((EntityPlayerMP)player, stack.getUnlocalizedName()+ IDLSkillNBT.SUCCESS_DESC_KEY);
                default:
                    break;
        }
    }

    public static void activateCoolDown(EntityPlayer player, ItemStack stack)
    {
        Item item = stack.getItem();
        if (item instanceof ItemSkillBase)
        {
            player.getCooldownTracker().setCooldown(stack.getItem(), ((ItemSkillBase) item).getCoolDownTicks(stack));
        }
    }

    public static void activateCoolDownLong(EntityPlayer player, ItemStack stack)
    {
        Item item = stack.getItem();
        if (item instanceof ItemSkillBase)
        {
            player.getCooldownTracker().setCooldown(stack.getItem(), ((ItemSkillBase) item).getCoolDownLongTicks(stack));
        }
    }

    public static void notifyCoolingDown(EntityPlayerMP player)
    {
        CommonFunctions.SendMsgToPlayer(player, "genshin12.skill.msg.cool_down");
    }

    public static boolean isStackReady(EntityPlayer player, ItemStack stack)
    {
        return !player.getCooldownTracker().hasCooldown(stack.getItem());
        //return stack.getItemDamage() == 0;
    }

    //--------------------------

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        if (isForceRelease())
        {
            return long_press_ticks;
        }
        else {
            return super.getMaxItemUseDuration(stack);
        }
    }

    public boolean isForceRelease() {
        return force_release && long_press_ticks > 0;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return long_press_ticks > 0 ? EnumAction.BOW : super.getItemUseAction(stack);
    }



    //Some skills cannot be held for too long. It will be force-released.
    //use force_release for this.
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase playerIn) {
        if (force_release)
        {
            if (cannotMouseCast)
            {
                return stack;
            }

            EntityEquipmentSlot slot = findSlot(stack, playerIn);
            if (slot != null)
            {
                if (canCast(worldIn, playerIn, stack, slot, !worldIn.isRemote))
                {
                    applyLongCast(worldIn, playerIn, stack, slot);
                    if (playerIn instanceof EntityPlayer)
                    {
                        activateCoolDownLong((EntityPlayer) playerIn, stack);
                    }
                    return stack;
                }
                else {
                    return stack;
                }
            }
        }

        return super.onItemUseFinish(stack, worldIn, playerIn);
    }

    //Some skills can be held for very long, just like bow aiming.
    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        super.onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);
        int usedTicks = getMaxItemUseDuration(stack) - timeLeft;
        EntityEquipmentSlot slot = findSlot(stack, entityLiving);

        if (!canCast(worldIn, entityLiving, stack, slot))
        {
            return;
        }

        if (long_press_ticks > 0 && usedTicks >= long_press_ticks && slot != null && !force_release)
        {
            applyLongCast(worldIn, entityLiving, stack, slot);
            if (entityLiving instanceof EntityPlayer)
            {
//                activateCoolDownLong((EntityPlayer) entityLiving, stack);
            }
        }
        else if (long_press_ticks < 0 || usedTicks < long_press_ticks) {
            applyCast(worldIn, entityLiving, stack, slot);
            if (entityLiving instanceof EntityPlayer)
            {
//                activateCoolDown((EntityPlayer) entityLiving, stack);
            }
        }

    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (cannotMouseCast)
        {
            return new ActionResult<>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
        }

        ItemStack stack = playerIn.getHeldItem(handIn);
        if (isStackReady(playerIn, stack))
        {
            if (canCast(worldIn, playerIn, stack, slotFromHand(handIn), !worldIn.isRemote))
            {
//                applyCast(worldIn, playerIn, stack, slotFromHand(handIn));
//                activateCoolDown(playerIn, stack);
                playerIn.setActiveHand(handIn);
                return new ActionResult<>(EnumActionResult.SUCCESS, stack);
            }
            else {
                return new ActionResult<>(EnumActionResult.FAIL, stack);

            }
        }

        return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
    }

    //This will just check, will not give error message. use hasErrorMessage for hinting.
    @Override
    public boolean canCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot) {
        return canCast(worldIn, livingBase, stack, slot, false);
    }

    public boolean canCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot, boolean hintErrorMsg) {
        if (livingBase instanceof EntityPlayer)
        {
            switch (slot)
            {
                case MAINHAND:
                    if (!mainHandCast)
                        return false;
                    break;
                case OFFHAND:
                    if (!offHandCast)
                        return false;
                    break;
                case FEET:
                    break;
                case LEGS:
                    break;
                case CHEST:
                    break;
                case HEAD:
                    break;
                default:
                    IdlFramework.LogWarning("Cast error", new IllegalStateException("Unexpected value: " + slot)); ;
            }
            return isStackReady((EntityPlayer) livingBase, stack);
        }
        return false;
    }

    public boolean applyCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot)
    {
        if (livingBase instanceof EntityPlayer)
        {
            activateCoolDown((EntityPlayer) livingBase, stack);
        }
        return true;
    }

    public boolean applyLongCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot)
    {
        if (livingBase instanceof EntityPlayer)
        {
            activateCoolDownLong((EntityPlayer) livingBase, stack);
        }
        return true;
    }

    public boolean onKeyboardCast(EntityLivingBase caster, ItemStack stack, EnumHand hand)
    {
        World world = caster.world;
        boolean casterIsPlayer = caster instanceof EntityPlayer;
        if (!casterIsPlayer || isStackReady((EntityPlayer) caster, stack)) {
            if (canCast(world, caster, stack, slotFromHand(hand))) {
                if (hand == EnumHand.MAIN_HAND) {
                    if (mainHandCast) {
                        return applyCast(caster.world, caster, stack, slotFromHand(hand));
                    } else  {
                        if (casterIsPlayer)
                        {
                            CommonFunctions.SafeSendMsgToPlayer(TextFormatting.RED, (EntityPlayer) caster, MessageDef.NOT_CASTABLE_MAINHAND);
                        }
                        else {
                            IdlFramework.LogWarning("Trying to do invalid cast from a creature: %s", caster.getName());
                        }
                    }
                } else if (hand == EnumHand.OFF_HAND)
                    if (offHandCast) {
                        return applyCast(caster.world, caster, stack, slotFromHand(hand));
                    }else {
                        if (casterIsPlayer)
                        {
                            CommonFunctions.SafeSendMsgToPlayer(TextFormatting.RED, (EntityPlayer) caster, MessageDef.NOT_CASTABLE_OFFHAND);
                        }
                        else {
                            IdlFramework.LogWarning("Trying to do invalid cast from a creature: %s", caster.getName());
                        }
                    }
            }
        }
        else {
            EntityPlayer player = (EntityPlayer) caster;
            CommonFunctions.SafeSendMsgToPlayer(TextFormatting.YELLOW, player, MessageDef.IN_COOLDOWN);
        }

        return false;
    }

    /**
     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
     */
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        return false;
    }

    //Desc
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        boolean shiftPressed = !shiftToShowDesc || isShiftPressed();
        if (shiftPressed)
        {
            if (showCDDesc)
                tooltip.add(I18n.format("genshin12.skill.shared.cool_down_desc", getCoolDown(stack)));
            if (showDamageDesc && getVal(stack) > 0)
                tooltip.add(I18n.format("genshin12.skill.shared.power_desc", getVal(stack)));
            if (showRangeDesc)
                tooltip.add(I18n.format("genshin12.skill.shared.range_desc", getRange(stack)));
            if (showDuraDesc)
                tooltip.add(GetDuraDescString(getDura(stack)));

            if (maxLevel != 1)
            {
                tooltip.add(I18n.format("genshin12.skill.shared.level_desc", IDLSkillNBT.getLevel(stack), maxLevel));
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack stack)
    {
        if (maxLevel == 1)
        {
            return super.getItemStackDisplayName(stack);
        }

//    	String strMain ="";
////    	if (IsNameHidden(stack))
////    	{
////    		strMain = I18n.format(getUnlocalizedName(stack) + IDLNBTDef.TOOLTIP_HIDDEN);
////
////    	}
////    	else
//    	{
//    		strMain = super.getItemStackDisplayName(stack);
//    	}

        String strMain = super.getItemStackDisplayName(stack);
        int lv = IDLSkillNBT.getLevel(stack);
        String strMaxLv = lv == maxLevel ? I18n.format("genshin12.skill.shared.lv_max") : "";

        return I18n.format("genshin12.skill.shared.name_format_with_lv",strMain, IDLSkillNBT.getLevel(stack), strMaxLv);
    }

    public String GetDuraDescString(float val)
    {
        return I18n.format("genshin12.skill.shared.duration_desc", val);
    }

    public void trySayDialogue(EntityLivingBase livingBase, ItemStack stack)
    {
        if (maxDialogues > 0)
        {
            int index = livingBase.getRNG().nextInt(maxDialogues);
            if (livingBase instanceof EntityPlayer)
            {
                CommonFunctions.SafeSendMsgToPlayer((EntityPlayer) livingBase, getSkillCastKey(stack, index));
            }
        }
    }
}

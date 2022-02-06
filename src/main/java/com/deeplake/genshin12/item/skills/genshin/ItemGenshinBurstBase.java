package com.deeplake.genshin12.item.skills.genshin;

import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.util.CommonFunctions;
import com.deeplake.genshin12.util.EnumElemental;
import com.deeplake.genshin12.util.MessageDef;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static com.deeplake.genshin12.util.CommonDef.DURA_PER_ENERGY;
import static com.deeplake.genshin12.util.NBTStrDef.IDLNBTDef.NAME_POSTFIX;

public class ItemGenshinBurstBase extends ItemGenshinSkillBase {
    protected int maxCharge = 40;

    public ItemGenshinBurstBase(String name, int maxCharge, EnumElemental elemental) {
        super(name, elemental);
        this.maxCharge = maxCharge;
        setMaxDamage(maxCharge * DURA_PER_ENERGY);
    }

    public void chargeEnergy(ItemStack stack, @Nullable EntityLivingBase livingBase, float val)
    {
        int curLack = stack.getItemDamage();
        if (curLack <= 0)
        {
            //overflow
            return;
        }

        float modifier = 1f;
        //handle charging attribute here. todo.

        int increase = (int) (val * modifier * DURA_PER_ENERGY);
        if (increase >= curLack)
        {
            //full
            stack.setItemDamage(0);
            if (livingBase != null && !livingBase.world.isRemote)
            {
                //notify player
                CommonFunctions.SafeSendMsgToPlayer(TextFormatting.AQUA, livingBase, MessageDef.CHARGE_FULL, new TextComponentTranslation(getUnlocalizedName(stack) + NAME_POSTFIX));
            }
        }
        else {
            stack.setItemDamage(curLack - increase);
        }
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (ModConfig.GeneralConf.MOVIE_MODE && entityIn instanceof EntityLivingBase)
        {
            chargeEnergy(stack, (EntityLivingBase) entityIn, 3);
        }
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    //burst normally will not drop
    @Override
    int getDropAmount(World world, Vec3d pos, EntityLivingBase caster, ItemStack stack) {
        return 0;
    }

    @Override
    public boolean canCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot, boolean hintErrorMsg) {
        if (ModConfig.GeneralConf.BURST_REQ_SHIFT)
        {
            if (!livingBase.isSneaking())
            {
                return false;
            }
        }

        int lack_mp = stack.getItemDamage();
        if (lack_mp > 0)
        {
            worldIn.playSound(null, livingBase.getPosition(), SoundEvents.BLOCK_DISPENSER_FAIL, SoundCategory.PLAYERS, 1f, 1f);
            if (hintErrorMsg)
            {
                CommonFunctions.SafeSendMsgToPlayer(TextFormatting.YELLOW, livingBase, MessageDef.LACK_MP, maxCharge - lack_mp, maxCharge);
            }
            return false;
        }

        return super.canCast(worldIn, livingBase, stack, slot, hintErrorMsg);
    }

    @Override
    public boolean applyCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot) {
        stack.setItemDamage(maxCharge * DURA_PER_ENERGY - 1);
        return super.applyCast(worldIn, livingBase, stack, slot);
    }

    @Override
    public boolean applyLongCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot) {
        stack.setItemDamage(maxCharge * DURA_PER_ENERGY);
        return super.applyCast(worldIn, livingBase, stack, slot);
    }
}

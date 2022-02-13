package com.deeplake.genshin12.item.skills.genshin;

import com.deeplake.genshin12.ILogNBT;
import com.deeplake.genshin12.entity.special.EntityEnergyOrb;
import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.item.skills.ItemSkillBase;
import com.deeplake.genshin12.util.*;

import com.deeplake.genshin12.util.NBTStrDef.IDLNBTDef;
import com.deeplake.genshin12.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

import static com.deeplake.genshin12.util.NBTStrDef.IDLNBTDef.RECHARGE_TIMESTAMP;

public class ItemGenshinSkillBase extends ItemSkillBase{
    int speechCount = 3;
    protected float aoeRange = 3f;

    public static final String SPEECH_KEY = "speech";
    public static final String SPEECH_KEY_LONG = "speech.long";

    EnumElemental elemental;

    EnumAmount amount = EnumAmount.SMALL;
    int maxStack = 1;

    public float[] initDamageRatio = new float[]{252.8f, 271.76f, 290.72f, 316f, 334.96f, 353.92f, 379.2f, 404.48f, 429.76f, 455.04f, 480.32f, 505.6f, 537.2f};

    public ItemGenshinSkillBase(String name, EnumElemental elemental) {
        super(name);
        setMaxLevel(13);
        this.elemental = elemental;
    }

    public void setMaxStack(int maxStack) {
        this.maxStack = maxStack;
        setMaxDamage(maxStack * 100);
    }

    public boolean isDepleted(ItemStack stack)
    {
        int damage = stack.getItemDamage();
        return damage >= stack.getMaxDamage();
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        if (!worldIn.isRemote)
        {
            int damage = stack.getItemDamage();
            if (maxStack > 1 && damage > 0)
            {
                long cur = worldIn.getTotalWorldTime();
                long lastMark = IDLNBTUtil.GetLong(stack, RECHARGE_TIMESTAMP, -1);

                if (cur >= lastMark)
                {
                    damage = damage - 100;
                    stack.setItemDamage(damage);
                    if (damage > 0)
                    {
                        tryMarkTimeStamp(stack, worldIn);
                    }
                }
            }
        }
    }

    public EnumElemental getElemental() {
        return elemental;
    }

    public void setAmount(EnumAmount amount) {
        this.amount = amount;
    }

    @Override
    public boolean canCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot) {
        if (maxStack > 1)
        {
            if (isDepleted(stack))
            {
                return false;
            }
        }
        return super.canCast(worldIn, livingBase, stack, slot);
    }

    public void tryMarkTimeStamp(ItemStack stack, World worldIn)
    {
        long cur = worldIn.getTotalWorldTime();
        long lastMark = IDLNBTUtil.GetLong(stack, RECHARGE_TIMESTAMP, -1);
        if (lastMark > 0 & lastMark > cur)
        {
            return;//keep the older mark
        }
        IDLNBTUtil.SetLong(stack, RECHARGE_TIMESTAMP, cur + getCoolDownTicks(stack));
    }

    @Override
    public boolean applyCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot) {
        if (livingBase instanceof EntityPlayer)
        {
            talkShort((EntityPlayer) livingBase);
        }

        if (maxStack > 1)
        {
            tryMarkTimeStamp(stack, worldIn);
            stack.setItemDamage(stack.getItemDamage() + 100);
//            if (!isDepleted(stack))
            {
                if (livingBase instanceof EntityPlayer)
                {
                    ((EntityPlayer) livingBase).getCooldownTracker().setCooldown(stack.getItem(), CommonDef.TICK_PER_SECOND);
                    return true;
                }
            }
        }

        if (livingBase instanceof EntityPlayer)
        {
            activateCoolDown((EntityPlayer) livingBase, stack);
        }
        return true;
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

    float getInitDamage(int level, EntityLivingBase caster)
    {
        return getInitDamage(level);
    }

    float getInitDamage(int level)
    {
        try {
            return initDamageRatio[level - 1] / 100f;
        }
        catch (ArrayIndexOutOfBoundsException e){
            return initDamageRatio[0] / 100f;
        }
    }

    void dealDamage(World world, Vec3d pos, EntityLivingBase caster, ItemStack stack)
    {
        dealDamage(world, pos, caster, stack, true);
    }

    public int calcStatus(World world, Vec3d pos, EntityLivingBase caster, ItemStack stack)
    {
        return 0;
    }

    public void onHit(World world, Vec3d pos, EntityLivingBase caster, ItemStack stack, int index, int status)
    {

    }

    void dealDamage(World world, Vec3d pos, EntityLivingBase caster, ItemStack stack, boolean dropBalls)
    {
        List<EntityLivingBase> list = EntityUtil.getEntitiesWithinAABB(world, EntityLiving.class, pos,aoeRange, null);

                float damageFactor = getInitDamage(getLevel(stack), caster);
                float damage = damageFactor * ModConfig.GeneralConf.DMG_ATK_PERCENT_GENSHIN_TO_MC;

                boolean needDrop = dropBalls;
                int index = 0;
                if (caster instanceof EntityPlayer)
                {
                    int status = calcStatus(world, pos, caster, stack);
                    for (EntityLivingBase target :
                            list) {
                        if (target.getIsInvulnerable())
                        {
                            continue;
                        }
                        onHit(world, pos, caster, stack, index, status);
                        index++;
                        if (target.hurtResistantTime > 0)
                        {
                            continue;
                        }

                ElementalUtil.applyElementalDamage((EntityPlayer) caster, target, damage, elemental, amount);

                if (needDrop)
                {
                    dropEnergy(world, pos, caster, stack);
                    needDrop = false;
                }
            }
        }
    }

    int getDropAmount(World world, Vec3d pos, EntityLivingBase caster, ItemStack stack)
    {
        return 3;
    }

    void dropEnergy(World world, Vec3d pos, EntityLivingBase caster, ItemStack stack)
    {
        EntityEnergyOrb.drop(caster, getDropAmount(world, pos, caster, stack), elemental);
    }
}

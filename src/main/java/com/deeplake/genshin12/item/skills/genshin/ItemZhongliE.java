package com.deeplake.genshin12.item.skills.genshin;

import com.deeplake.genshin12.blocks.ModBlocks;
import com.deeplake.genshin12.blocks.tileEntity.genshin.TEZhongliPillar;
import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.item.skills.ItemSkillBase;
import com.deeplake.genshin12.potion.ModPotions;
import com.deeplake.genshin12.util.CommonDef;
import com.deeplake.genshin12.util.EntityUtil;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class ItemZhongliE extends ItemGenshinSkillBase {

    float distance = 2f;
    int ySeekRange = 4;
    float aoeRange = 3f;

    float zhongliHP90 = 14695;
    float shieldHPRatio = 20;
    final float[] shieldAmount1 = new float[]{1232,1355,1489,1633,1787,1951,2126,2310,2506,2711,2927,3153,3389};
    final float[] shieldAmount2 = new float[]{12.8f,13.76f,14.72f,16.00f,16.96f,17.92f,19.20f,20.48f,21.76f,23.04f,24.32f,25.60f,27.20f};


    final float[] initDamageRatio = new float[]{16f, 17.2f, 18.4f, 20.0f, 21.2f, 22.4f, 25.6f, 27.2f, 28.8f, 30.4f, 32f, 34f};

    final float[] initDamageRatioHold = new float[]{80f, 86f, 92f, 100f, 106f, 112f, 120f, 128, 136f, 144f, 152f, 160f, 170f};

    public static final int PILLAR_HEIGHT = 4;

    public ItemZhongliE(String name) {
        super(name);
        setCD(4f, 0f);
        setCDLong(12f, 0f);
        if (ModConfig.DEBUG_CONF.DEBUG_MODE)
        {
            setCD(1f, 0f);
            setCDLong(2f, 0f);
        }

        long_press_ticks = CommonDef.TICK_PER_SECOND;
        force_release = true;
    }

    @Override
    public boolean applyCast(World worldIn, EntityLivingBase caster, ItemStack stack, EntityEquipmentSlot slot) {
        castE(worldIn, caster, stack, false);
        return super.applyCast(worldIn, caster, stack, slot);
    }

    @Override
    public boolean applyLongCast(World worldIn, EntityLivingBase caster, ItemStack stack, EntityEquipmentSlot slot) {
        castE(worldIn, caster, stack, true);
        return super.applyLongCast(worldIn, caster, stack, slot);
    }

    public void castE(World worldIn, EntityLivingBase caster, ItemStack stack, boolean isHold) {
        Vec3d lookVecRaw = caster.getLookVec();
        Vec3d lockVecProjY = new Vec3d(lookVecRaw.x, 0, lookVecRaw.z).normalize();
        Vec3d targetPosF = caster.getPositionVector().add(lockVecProjY.scale(distance));
        BlockPos pos = new BlockPos(targetPosF);

        boolean isValid = false;
        int y;
        for (y =  ySeekRange; y >= - ySeekRange; y--)
        {
            if (worldIn.getBlockState(pos.offset(EnumFacing.UP, y)).getMaterial().isReplaceable())
            {
                if (!worldIn.getBlockState(pos.offset(EnumFacing.UP, y - 1)).getMaterial().isReplaceable())
                {
                    isValid = true;
                    break;
                }
            }
        }

        targetPosF = targetPosF.add(new Vec3d(0, y, 0));

        if (!worldIn.isRemote)
        {
            if (isValid)
            {
                for (int yOffset = 0; yOffset < PILLAR_HEIGHT; yOffset++)
                {
                    BlockPos target = pos.add(0, yOffset + y, 0);
                    worldIn.setBlockState(target, ModBlocks.ZL_PILLAR.getDefaultState());
                    TileEntity te = worldIn.getTileEntity(target);
                    if (te instanceof TEZhongliPillar)
                    {
                        TEZhongliPillar teZhongliPillar = (TEZhongliPillar) te;
                        teZhongliPillar.setDamage(getInitDamage(getLevel(stack)) * 2f);
                    }
                }
            }


            dealDamage(worldIn, targetPosF, caster, stack, isHold);
//            caster.playSound(SoundEvents.BLOCK_ANVIL_PLACE, 2f, 0.75f);
            worldIn.playSound(null, targetPosF.x, targetPosF.y, targetPosF.z, SoundEvents.BLOCK_STONE_FALL, SoundCategory.BLOCKS, 8f, 0.75f);

            if (isHold)
            {
                caster.setAbsorptionAmount(getShieldAmount(getLevel(stack), caster.getMaxHealth()));
                EntityUtil.ApplyBuff(caster, ModPotions.JADE_SHIELD, 0, 20f);
                EntityUtil.ApplyBuff(caster, MobEffects.WATER_BREATHING, 0, 3f);
            }
        }
        else {
            worldIn.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, targetPosF.x, targetPosF.y, targetPosF.z, 0,0,0);
        }
    }

    void dealDamage(World world, Vec3d pos, EntityLivingBase caster, ItemStack stack, boolean isHold)
    {
        List<EntityLivingBase> list = EntityUtil.getEntitiesWithinAABB(world, EntityLiving.class, pos, aoeRange, null);

        float damageFactor = isHold ? getHoldDamage(getLevel(stack)) : getInitDamage(getLevel(stack));
        float damage = damageFactor / 100f * caster.getMaxHealth();

        for (EntityLivingBase target :
                list) {
            if (caster instanceof EntityPlayer)
            {
                target.attackEntityFrom(
                        DamageSource.causePlayerDamage((EntityPlayer) caster),
                        damage);
            }
            else {
                //todo
            }

        }
    }

    float getInitDamage(int level)
    {
        try {
            return initDamageRatio[level - 1];
        }
        catch (ArrayIndexOutOfBoundsException e){
            return initDamageRatio[0];
        }
    }

    float getHoldDamage(int level)
    {
        try {
            return initDamageRatioHold[level - 1];
        }
        catch (ArrayIndexOutOfBoundsException e){
            return initDamageRatioHold[0];
        }
    }

    float getShieldAmount(int level, float maxHP)
    {
        try {
            float result = 0;
            result += maxHP * shieldAmount2[level - 1] / 100f
                    + shieldAmount1[level] * shieldHPRatio / zhongliHP90;
            return result;
        }
        catch (ArrayIndexOutOfBoundsException e){
            return 1f;
        }
    }

}

package com.deeplake.genshin12.item.skills.genshin;

import com.deeplake.genshin12.entity.special.EntityEnergyOrb;
import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.util.CommonDef;
import com.deeplake.genshin12.util.CommonFunctions;
import com.deeplake.genshin12.util.EntityUtil;
import com.deeplake.genshin12.util.EnumElemental;
import net.minecraft.block.BlockOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class ItemKaeyaE extends ItemGenshinSkillBase {
    public float aoeRange = 3f;
    final float[] initDamageRatio = new float[]{191.2f,205.54f,219.8f,239f,253.34f,267.68f,30.92f,325.04f,344.16f,363.28f,382.4f,406.3f};

    public ItemKaeyaE(String name) {
        super(name, EnumElemental.CYRO);
        //Frostgnaw freezes water for 9 seconds.
        setCD(6f, 0f);//
    }

    @Override
    public boolean applyCast(World worldIn, EntityLivingBase caster, ItemStack stack, EntityEquipmentSlot slot) {
        castE(worldIn, caster, stack);
        return super.applyCast(worldIn, caster, stack, slot);
    }

    public void castE(World worldIn, EntityLivingBase caster, ItemStack stack) {
        Vec3d lookVecRaw = caster.getLookVec();
        Vec3d lockVecProjY = new Vec3d(lookVecRaw.x, 0, lookVecRaw.z).normalize();
        Vec3d targetPosF = caster.getPositionVector().add(lockVecProjY.scale(aoeRange));

        //todo: consider freezing water here
        freezeWater(worldIn, targetPosF);

        if (!worldIn.isRemote)
        {
            dealDamage(worldIn, targetPosF, caster, stack);
            worldIn.playSound(null, targetPosF.x, targetPosF.y, targetPosF.z, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 8f, 0.75f);
        }
        else {
            for (int i = -10; i <= 10; i++)
            {
                Vec3d dir = lockVecProjY.rotateYaw(i * 5 * CommonDef.DEG_TO_RAD);
                worldIn.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, targetPosF.x, targetPosF.y, targetPosF.z, dir.x,0,dir.z);
            }
        }
    }

    void dealDamage(World world, Vec3d pos, EntityLivingBase caster, ItemStack stack)
    {
        List<EntityLivingBase> list = EntityUtil.getEntitiesWithinAABB(world, EntityLiving.class, pos,aoeRange, null);

        float damageFactor = getInitDamage(getLevel(stack));
        float damage = damageFactor / 100f * ModConfig.GeneralConf.DMG_ATK_PERCENT_GENSHIN_TO_MC;

        boolean needDrop = true;
        if (caster instanceof EntityPlayer)
        {
            for (EntityLivingBase target :
                    list) {
                target.attackEntityFrom(
                        DamageSource.causePlayerDamage((EntityPlayer) caster),
                        damage);

                if (needDrop)
                {
                    //2.5 orbs
                    EntityEnergyOrb.drop(caster, -3, EnumElemental.CYRO);
                    needDrop = false;
                }
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

    void freezeWater(World world, Vec3d pos)
    {
        int range = (int) aoeRange;
        BlockPos posBase = new BlockPos(pos);

        for (int x = -range; x <= range; x++)
        {
            for (int y = -range; y <= range; y++)
            {
                for (int z = -range; z <= range; z++)
                {
                    BlockPos target = posBase.add(x,y,z);
                    if (world.isRemote)
                    {
                        //todo: effect
                    }
                    else {
                        CommonFunctions.freeze(world, target);
                    }
                }
            }
        }
    }

}

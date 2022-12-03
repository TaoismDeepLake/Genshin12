package com.deeplake.genshin12.entity.special;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

public class EntityKeqingMark extends Entity {
    EntityLivingBase caster;
    Vec3d fromPos;
    Vec3d deltaPos;
    float totalTime = 0.5f;
    float curTime = 0;

    public EntityKeqingMark(World worldIn) {
        super(worldIn);
        totalTime = 0.1f;
    }

    public void init(EntityLivingBase caster)
    {
        this.caster = caster;
        fromPos = caster.getPositionVector();
        deltaPos = getPositionVector().subtract(fromPos);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (world.isRemote)
        {
            world.spawnParticle(EnumParticleTypes.PORTAL, posX, posY, posZ,0,0,0);

        }
        else
        {
            //server side
            if (caster != null)
            {
                curTime += 0.02f;
                if (curTime >= totalTime)
                {
                    //self destruct
                    caster.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 30));
                    setDead();
                }
                else {
                    Vec3d tempPos = fromPos.add(deltaPos.scale(curTime / totalTime));
                    caster.setPositionAndUpdate(tempPos.x, tempPos.y, tempPos.z);
                }
            }
        }
    }

    @Override
    protected void entityInit() {

    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {

    }

    public boolean attemptTeleport(double x, double y, double z)
    {
        double d0 = this.posX;
        double d1 = this.posY;
        double d2 = this.posZ;
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        boolean flag = false;
        BlockPos blockpos = new BlockPos(this);
        World world = this.world;
        Random random = this.rand;

        if (world.isBlockLoaded(blockpos))
        {
            boolean flag1 = false;

            while (!flag1 && blockpos.getY() > 0)
            {
                BlockPos blockpos1 = blockpos.down();
                IBlockState iblockstate = world.getBlockState(blockpos1);

                if (iblockstate.getMaterial().blocksMovement())
                {
                    flag1 = true;
                }
                else
                {
                    --this.posY;
                    blockpos = blockpos1;
                }
            }

            if (flag1)
            {
                this.setPositionAndUpdate(this.posX, this.posY, this.posZ);

                if (world.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty() && !world.containsAnyLiquid(this.getEntityBoundingBox()))
                {
                    flag = true;
                }
            }
        }

        if (!flag)
        {
            this.setPositionAndUpdate(d0, d1, d2);
            return false;
        }
        else
        {
            int i = 128;

            for (int j = 0; j < 128; ++j)
            {
                double d6 = (double)j / 127.0D;
                float f = (random.nextFloat() - 0.5F) * 0.2F;
                float f1 = (random.nextFloat() - 0.5F) * 0.2F;
                float f2 = (random.nextFloat() - 0.5F) * 0.2F;
                double d3 = d0 + (this.posX - d0) * d6 + (random.nextDouble() - 0.5D) * (double)this.width * 2.0D;
                double d4 = d1 + (this.posY - d1) * d6 + random.nextDouble() * (double)this.height;
                double d5 = d2 + (this.posZ - d2) * d6 + (random.nextDouble() - 0.5D) * (double)this.width * 2.0D;
                world.spawnParticle(EnumParticleTypes.PORTAL, d3, d4, d5, (double)f, (double)f1, (double)f2);
            }

            return true;
        }
    }
}

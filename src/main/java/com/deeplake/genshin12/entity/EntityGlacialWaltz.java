package com.deeplake.genshin12.entity;

import com.deeplake.genshin12.entity.special.EntityEnergyOrb;
import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.util.CommonDef;
import com.deeplake.genshin12.util.CommonFunctions;
import com.deeplake.genshin12.util.EntityUtil;
import com.deeplake.genshin12.util.EnumElemental;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class EntityGlacialWaltz extends Entity {
    float damage = 2f;
    float aoeRange = 2f;
    int lifeTicks = 8 * CommonDef.TICK_PER_SECOND;
    Entity owner;
    public EntityGlacialWaltz(World worldIn) {
        super(worldIn);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        lifeTicks--;
        if (!world.isRemote)
        {
            if (owner == null || lifeTicks <= 0)
            {
                setDead();
                return;
            }
            setPositionAndUpdate(owner.posX, owner.posY, owner.posZ);
            dealDamage();
            freezeWater(world, getPositionVector());
        }
        else {
            for (int i = 0; i <= 2; i++)
            {
                double theta = lifeTicks * ModConfig.DEBUG_CONF.KAEYA_OMEGA + Math.PI * 2 / 3 * i;
                world.spawnParticle(EnumParticleTypes.SNOW_SHOVEL,
                        posX + Math.cos(theta),
                        posY + 0.5f,
                        posZ + Math.sin(theta),
                        0,0,0);
            }

        }
    }

    void dealDamage()
    {
        List<EntityLivingBase> list = EntityUtil.getEntitiesWithinAABB(world, EntityLiving.class, getPositionVector(), aoeRange, null);

        for (EntityLivingBase target :
                list) {

            target.attackEntityFrom(
                    DamageSource.causeIndirectMagicDamage(this, owner),
                    damage);
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

    @Override
    protected void entityInit() {

    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public void setOwner(Entity owner) {
        this.owner = owner;
        setPositionAndUpdate(owner.posX, owner.posY, owner.posZ);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {

    }
}

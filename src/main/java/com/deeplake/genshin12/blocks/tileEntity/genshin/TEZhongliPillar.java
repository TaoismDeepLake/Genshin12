package com.deeplake.genshin12.blocks.tileEntity.genshin;

import com.deeplake.genshin12.blocks.tileEntity.builder.TileEntityBuilderBase;
import com.deeplake.genshin12.entity.special.EntityEnergyOrb;
import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.util.CommonDef;
import com.deeplake.genshin12.util.EntityUtil;
import com.deeplake.genshin12.util.EnumElemental;
import com.deeplake.genshin12.util.NBTStrDef.IDLNBTDef;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class TEZhongliPillar extends TileEntity implements ITickable {
    final int MAX_LIFE = (int) (ModConfig.DEBUG_CONF.PILLAR_LIFE * CommonDef.TICK_PER_SECOND);
    final static int RESONATE_PERIOD  = 2 * CommonDef.TICK_PER_SECOND;
    final static float RESONATE_RANGE  = 3f;
    int currentLife = MAX_LIFE;
    float damage = 1f;

    public void setDamage(float value)
    {
        damage = value;
    }


    @Override
    public void update() {
        currentLife--;
        if (currentLife == 0)
        {
            destroy();
            return;
        }

        if (currentLife != MAX_LIFE && currentLife % RESONATE_PERIOD == 0)
        {
            resonate();
        }
    }

    void destroy()
    {
        world.setBlockState(pos, Blocks.AIR.getDefaultState());
    }

    void resonate()
    {
        if (world.isRemote)
        {
            int count = ModConfig.DEBUG_CONF.PARTICLE_COUNT;
            float angle = (float) (Math.PI * 2f / (float) count);
            float radius = 1f;
            float speed = ModConfig.DEBUG_CONF.PARTICLE_SPEED;

            int stateID = Block.getStateId(CommonDef.DIRT);

            double _x = getVec3d().x;
            double _y = getVec3d().y;
            double _z = getVec3d().z;

            for (int i = 0; i <= count; i++)
            {
                final double cos = Math.cos(angle * i);
                final double sin = Math.sin(angle * i);

                float x = (float) (_x + cos);
                float y = (float) (_y + world.rand.nextFloat());
                float z = (float) (_z + sin);

                float vx = (float) (speed * cos);
                float vz = (float) (speed * sin);

                world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, x, y, z, vx, 0, vz);
            }

            for (float delta = -radius; delta <= radius; delta+=0.2f)
            {
                float ratio = delta / radius;
                world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, _x, _y, _z,  speed, 0, ratio * speed, stateID);
                world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, _x, _y, _z, -speed, 0, ratio * speed, stateID);
                world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, _x, _y, _z, ratio * speed, 0,  speed, stateID);
                world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, _x, _y, _z, ratio * speed, 0, -speed, stateID);
            }

        }
        else {
            dealDamage();
        }
    }

    Vec3d getVec3d()
    {
        return new Vec3d(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f);
    }

    void dealDamage()
    {

        List<EntityLivingBase> list = EntityUtil.getEntitiesWithinAABB(world, EntityLiving.class, getVec3d(), RESONATE_RANGE, null);

        boolean needDrop = true;

        for (EntityLivingBase target :
                list) {

            target.attackEntityFrom(
                    DamageSource.MAGIC,
                    damage);

            if (needDrop && target.getRNG().nextBoolean())
            {
                EntityEnergyOrb.drop(target, 1, EnumElemental.GEO);
                needDrop = false;
            }

        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setFloat(IDLNBTDef.STATE, damage);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        damage = compound.getFloat(IDLNBTDef.STATE);
    }
}

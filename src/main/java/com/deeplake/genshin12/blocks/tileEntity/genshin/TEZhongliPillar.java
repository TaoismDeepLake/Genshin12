package com.deeplake.genshin12.blocks.tileEntity.genshin;

import com.deeplake.genshin12.blocks.tileEntity.builder.TileEntityBuilderBase;
import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.util.CommonDef;
import com.deeplake.genshin12.util.EntityUtil;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
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
            for (int i = 0; i <= count; i++)
            {
                final double cos = Math.cos(angle * i);
                final double sin = Math.sin(angle * i);

                float x = (float) (getPos().getX() + 0.5f + cos);
                float y = getPos().getY() + world.rand.nextFloat();
                float z = (float) (getPos().getZ() + 0.5f + sin);

                float vx = (float) (speed * cos);
                float vz = (float) (speed * sin);

                world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, x, y, z, vx, 0, vz);
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

        for (EntityLivingBase target :
                list) {

            target.attackEntityFrom(
                    DamageSource.MAGIC,
                    damage);


        }
    }

}

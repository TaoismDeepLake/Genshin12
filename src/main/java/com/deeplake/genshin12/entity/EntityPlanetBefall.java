package com.deeplake.genshin12.entity;

import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.potion.ModPotions;
import com.deeplake.genshin12.util.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

public class EntityPlanetBefall extends EntityLiving {
    int lifeMax = (int) (ModConfig.DEBUG_CONF.METEOR_LIFE * CommonDef.TICK_PER_SECOND);
    int life = lifeMax;
    float damageAmount = 5;
    float range = 10f;
    float dura = 8f;
    float fallSpeed = ModConfig.DEBUG_CONF.METEOR_HEIGHT / lifeMax;
    EntityLivingBase shooter;

    public EntityPlanetBefall(World worldIn) {
        super(worldIn);
        setNoGravity(true);
    }

    public void setDamageAmount(float damageAmount) {
        this.damageAmount = damageAmount;
    }

    @Override
    public void move(MoverType type, double x, double y, double z) {
        super.move(type, 0, y, 0);
    }

    public void setShooter(EntityLivingBase shooter)
    {
        this.shooter = shooter;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        life--;

        this.posY -= fallSpeed;
        this.setPosition(this.posX, this.posY, this.posZ);
        if (life <= 0) {
            explode();
        }
    }

    //from 1 to 0
    public float getPositionRatio()
    {
        return (float)life / lifeMax;
    }

    void explode()
    {
        if (world.isRemote)
        {
            world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, posX, posY+1, posZ, 0,0,0);

        } else {
            dealDamage(world, getPositionVector());
            world.playSound(null, getPosition(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 3f, 0.6f);
            world.playSound(null, getPosition(), ModSoundHandler.ZHONGLI_Q, SoundCategory.PLAYERS, 1f, 1f);

        }
        setDead();
    }


    void dealDamage(World world, Vec3d pos)
    {
        List<EntityLivingBase> list = EntityUtil.getEntitiesWithinAABB(world, EntityLiving.class, pos, range, null);

        float damage = damageAmount;

        for (EntityLivingBase target :
                list) {
            if (shooter instanceof EntityPlayer)
            {
                ElementalUtil.applyElementalDamage((EntityPlayer) shooter, target, damage, EnumElemental.GEO, EnumAmount.LARGE);
            }
            else { //including null
                target.attackEntityFrom(
                        DamageSource.ANVIL,
                        damage);

            }

            EntityUtil.ApplyBuff(target, ModPotions.ZL_PETRIFY, 0, dura);
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        return false;
    }

//    @Override
//    protected void readEntityFromNBT(NBTTagCompound compound) {
//
//    }
//
//    @Override
//    protected void writeEntityToNBT(NBTTagCompound compound) {
//
//    }


}

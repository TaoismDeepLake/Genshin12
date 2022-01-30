package com.deeplake.genshin12.entity;

import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.potion.ModPotions;
import com.deeplake.genshin12.util.CommonDef;
import com.deeplake.genshin12.util.EntityUtil;
import com.deeplake.genshin12.util.ModSoundHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
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

import java.util.List;

public class EntityPlanetBefall extends Entity {
    int lifeMax = (int) (ModConfig.DEBUG_CONF.METEOR_LIFE * CommonDef.TICK_PER_SECOND);
    int life = lifeMax;
    float damageAmount = 5;
    float range = 10f;
    float dura = 8f;
    EntityLivingBase shooter;

    public EntityPlanetBefall(World worldIn) {
        super(worldIn);
    }

    @Override
    protected void entityInit() {

    }

    public void setShooter(EntityLivingBase shooter)
    {
        this.shooter = shooter;
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        life--;
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
            world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, posX, posY, posZ, 0,0,0);

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

        float damageFactor = damageAmount;
        float damage = damageFactor / 100f * ModConfig.DEBUG_CONF.DMG_ATK_PERCENT_GENSHIN_TO_MC;

        for (EntityLivingBase target :
                list) {
            if (shooter instanceof EntityPlayer)
            {
                target.attackEntityFrom(
                        DamageSource.causePlayerDamage((EntityPlayer) shooter),
                        damage);
                EntityUtil.ApplyBuff(target, ModPotions.ZL_PETRIFY, 0, dura);
            }
            else { //including null
                target.attackEntityFrom(
                        DamageSource.ANVIL,
                        damage);
                EntityUtil.ApplyBuff(target, ModPotions.ZL_PETRIFY, 0, dura);
            }

        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        return false;
    }

//    @Override
//    public Iterable<ItemStack> getArmorInventoryList() {
//        return null;
//    }
//
//    @Override
//    public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn) {
//        return null;
//    }
//
//    @Override
//    public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {
//
//    }
//
//    @Override
//    public EnumHandSide getPrimaryHand() {
//        return null;
//    }
//
//    @Override
//    protected void entityInit() {
//
//    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {

    }


}

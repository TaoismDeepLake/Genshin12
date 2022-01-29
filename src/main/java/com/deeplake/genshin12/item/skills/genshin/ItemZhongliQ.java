package com.deeplake.genshin12.item.skills.genshin;

import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.potion.ModPotions;
import com.deeplake.genshin12.util.CommonDef;
import com.deeplake.genshin12.util.EntityUtil;
import com.deeplake.genshin12.util.ModSoundHandler;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ConcurrentModificationException;
import java.util.List;

public class ItemZhongliQ extends ItemGenshinSkillBase {

    final float[] skillDMG = new float[]{401.08f, 444.44f};//todo
    float range = 10f;

    public ItemZhongliQ(String name) {
        super(name);
        setCD(12, 0f);
//        setDura(3.1f, 0.1f);
        setDura(8, 0.1f);
    }

    @Override
    public boolean applyCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot) {
        if (worldIn.isRemote)
        {
            worldIn.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, livingBase.posX, livingBase.posY, livingBase.posZ, 0,0,0);

        } else {
            dealDamage(worldIn, livingBase.getPositionVector(), livingBase, stack);
            worldIn.playSound(null, livingBase.getPosition(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 3f, 0.6f);
            worldIn.playSound(null, livingBase.getPosition(), ModSoundHandler.ZHONGLI_Q, SoundCategory.PLAYERS, 1f, 1f);

        }
        return super.applyCast(worldIn, livingBase, stack, slot);
    }

    float getInitDamage(int level)
    {
        try {
            return skillDMG[level - 1];
        }
        catch (ArrayIndexOutOfBoundsException e){
            return skillDMG[0];
        }
    }

    void dealDamage(World world, Vec3d pos, EntityLivingBase caster, ItemStack stack)
    {
        List<EntityLivingBase> list = EntityUtil.getEntitiesWithinAABB(world, EntityLiving.class, pos, range, null);

        float damageFactor = getInitDamage(getLevel(stack));
        float damage = damageFactor / 100f * ModConfig.DEBUG_CONF.DMG_ATK_PERCENT_GENSHIN_TO_MC;

        for (EntityLivingBase target :
                list) {
            if (caster instanceof EntityPlayer)
            {
                target.attackEntityFrom(
                        DamageSource.causePlayerDamage((EntityPlayer) caster),
                        damage);
                EntityUtil.ApplyBuff(target, ModPotions.ZL_PETRIFY, 0, getDura(stack));
            }
            else {
                //todo
            }

        }
    }
}

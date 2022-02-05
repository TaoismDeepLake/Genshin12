package com.deeplake.genshin12.item.skills.genshin;

import com.deeplake.genshin12.entity.EntityPlanetBefall;
import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.potion.ModPotions;
import com.deeplake.genshin12.util.CommonDef;
import com.deeplake.genshin12.util.EntityUtil;
import com.deeplake.genshin12.util.EnumElemental;
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

public class ItemZhongliQ extends ItemGenshinBurstBase {

    final float[] skillDMG = new float[]{401.08f, 444.44f};//todo
    float range = 10f;
    float distance = 3f;

    public ItemZhongliQ(String name) {
        super(name, 40, EnumElemental.GEO);
        setCD(12, 0f);
//        setDura(3.1f, 0.1f);
        setDura(8, 0.1f);
    }

    @Override
    public boolean applyCast(World worldIn, EntityLivingBase caster, ItemStack stack, EntityEquipmentSlot slot) {
        Vec3d lookVecRaw = caster.getLookVec();
        Vec3d lockVecProjY = new Vec3d(lookVecRaw.x, 0, lookVecRaw.z).normalize();
        Vec3d targetPosF = caster.getPositionVector().add(lockVecProjY.scale(distance));

        if (worldIn.isRemote)
        {
            worldIn.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, targetPosF.x, targetPosF.y + ModConfig.DEBUG_CONF.METEOR_HEIGHT, targetPosF.z, 0,0,0);

        } else {
            EntityPlanetBefall befall = new EntityPlanetBefall(worldIn);
            befall.setPositionAndUpdate(targetPosF.x, targetPosF.y + ModConfig.DEBUG_CONF.METEOR_HEIGHT, targetPosF.z);
            befall.setShooter(caster);
            befall.setDamageAmount(getInitDamage(getLevel(stack)) * ModConfig.GeneralConf.DMG_ATK_PERCENT_GENSHIN_TO_MC);
            worldIn.spawnEntity(befall);
        }
        return super.applyCast(worldIn, caster, stack, slot);
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
}

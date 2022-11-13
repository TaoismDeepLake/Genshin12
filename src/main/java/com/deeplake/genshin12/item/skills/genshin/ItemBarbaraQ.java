package com.deeplake.genshin12.item.skills.genshin;

import com.deeplake.genshin12.entity.creatures.attribute.ModAttributes;
import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.util.CommonDef;
import com.deeplake.genshin12.util.ElementalUtil;
import com.deeplake.genshin12.util.EntityUtil;
import com.deeplake.genshin12.util.EnumElemental;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class ItemBarbaraQ extends ItemGenshinBurstBase{
    public static double[] regen_ratio = new double[]{0.75, 0.81, 0.86, 0.94, 0.99, 1.05, 1.13, 1.2, 1.27, 1.35, 1.43, 1.5, 1.59};
    public static double[] regen_fixed = new double[]{72.2, 79.44, 97.27, 95.69, 104.72, 124.59, 135.42, 146.86, 158.9, 171.54, 184.78, 198.63};


    public ItemBarbaraQ(String name) {
        super(name, 80, EnumElemental.HYDRO);
        setCD(20, 0);
    }

    double getHeal(int level, EntityLivingBase caster) {
        try {
            return regen_ratio[level - 1] / 100f * caster.getMaxHealth() + regen_fixed[level - 1] / ModConfig.GeneralConf.HP_GENSHIN_TO_MC;
        } catch (ArrayIndexOutOfBoundsException e) {
            return regen_ratio[0] / 100f * caster.getMaxHealth() + regen_fixed[0] / ModConfig.GeneralConf.HP_GENSHIN_TO_MC;
        }
    }

    @Override
    public boolean applyCast(World world, EntityLivingBase caster, ItemStack stack, EntityEquipmentSlot slot) {
        Vec3d lookVecRaw = caster.getLookVec();
        Vec3d lockVecProjY = new Vec3d(lookVecRaw.x, 0, lookVecRaw.z).normalize();
        Vec3d targetPosF = caster.getPositionVector();
        Vec3d pos = targetPosF;
        int level = getLevel(stack);

        if (!world.isRemote) {
            List<EntityLivingBase> list = EntityUtil.getEntitiesWithinAABB(world, EntityPlayer.class, pos,aoeRange, null);

            double heal = getHeal(level, caster);
            if (caster instanceof EntityPlayer)
            {
                for (EntityLivingBase target :
                        list) {
                    target.heal((float) heal);
                }
            }
            world.playSound(null, targetPosF.x, targetPosF.y, targetPosF.z, SoundEvents.ENTITY_FIREWORK_LARGE_BLAST, SoundCategory.BLOCKS, 8f, 0.75f);
        }
        else {
            for (int i = 0; i <= 72; i++)
            {
                Vec3d dir = lockVecProjY.rotateYaw(i * 5 * CommonDef.DEG_TO_RAD);
                world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, targetPosF.x, targetPosF.y, targetPosF.z, dir.x,0,dir.z);
            }
        }
        return super.applyCast(world, caster, stack, slot);
    }
}

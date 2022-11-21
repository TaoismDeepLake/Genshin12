package com.deeplake.genshin12.item.skills.genshin;

import com.deeplake.genshin12.util.CommonDef;
import com.deeplake.genshin12.util.EnumElemental;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemKeqingQ extends ItemGenshinBurstBase{
    public ItemKeqingQ(String name) {
        super(name, 40, EnumElemental.ELECTRO);
        setCD(12f,0);
    }

    @Override
    public boolean applyCast(World worldIn, EntityLivingBase caster, ItemStack stack, EntityEquipmentSlot slot) {
        Vec3d lookVecRaw = caster.getLookVec();
        Vec3d lockVecProjY = new Vec3d(lookVecRaw.x, 0, lookVecRaw.z).normalize();
        Vec3d targetPosF = caster.getPositionVector();

        if (!worldIn.isRemote) {
            dealDamage(worldIn, targetPosF, caster, stack);
            worldIn.playSound(null, targetPosF.x, targetPosF.y, targetPosF.z, SoundEvents.ENTITY_GHAST_SCREAM, SoundCategory.BLOCKS, 8f, 0.75f);
        }
        else {
            for (int i = 0; i <= 72; i++)
            {
                Vec3d dir = lockVecProjY.rotateYaw(i * 5 * CommonDef.DEG_TO_RAD);
                worldIn.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, targetPosF.x, targetPosF.y, targetPosF.z, dir.x,0,dir.z);
            }
        }
        return super.applyCast(worldIn, caster, stack, slot);
    }
}

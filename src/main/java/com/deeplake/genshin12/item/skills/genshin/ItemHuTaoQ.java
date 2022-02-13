package com.deeplake.genshin12.item.skills.genshin;

import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.item.IWIP;
import com.deeplake.genshin12.util.CommonDef;
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

public class ItemHuTaoQ extends ItemGenshinBurstBase{
//    Commands a blazing spirit to attack, dealing Pyro DMG in a large AoE.
//    Upon striking the enemy, regenerates a percentage of Hu Tao's Max HP. This effect can be triggered up to 5 times, based on the number of enemies hit.
//    If Hu Tao's HP is below or equal to 50% when the enemy is hit, both the DMG and HP Regeneration are increased.

//    Skill DMG (%)f,303.27f,321.43f,339.59f,363.2f,381.36f,399.52f,423.13f,446.74f,470.34f,493.95f,517.56f,541.17f,564.78f,588
//    Low HP Skill DMG (%)f,379.09f,401.79f,424.49f,454f,476.7f,499.4f,528.91f,558.42f,587.93f,617.44f,646.95f,676.46f,705.97f,735
//    Skill HP Regeneration (% Max HP)f,6.26f,6.64f,7.01f,7.5f,7.88f,8.25f,8.74f,9.22f,9.71f,10.2f,10.69f,11.18f,11.66f,12.15
//    Low HP Skill Regeneration (% Max HP)f,8.35f,8.85f,9.35f,10f,10.5f,11f,11.65f,12.3f,12.95f,13.6f,14.25f,14.9f,15.55f,16.20

    int maxCount = 5;

    public float[] lowHPDamageRatio = new float[]{79.09f,401.79f,424.49f,454f,476.7f,499.4f,528.91f,558.42f,587.93f,617.44f,646.95f,676.46f,705.97f,735f};

    //Skill HP Regeneration (% Max HP)
    public float[] regen = new float[]{6.26f,6.64f,7.01f,7.5f,7.88f,8.25f,8.74f,9.22f,9.71f,10.2f,10.69f,11.18f,11.66f,12.15f};

    // Low HP Skill Regeneration (% Max HP)
    public float[] lowRegen = new float[]{8.35f,8.85f,9.35f,10f,10.5f,11f,11.65f,12.3f,12.95f,13.6f,14.25f,14.9f,15.55f,16.20f};

    public ItemHuTaoQ(String name) {
        super(name, 60, EnumElemental.PYRO);
        setMaxLevel(14);
        setRange(10f,0f);
        initDamageRatio = new float[]{303.27f,321.43f,339.59f,363.2f,381.36f,399.52f,423.13f,446.74f,470.34f,493.95f,517.56f,541.17f,564.78f,588f};
        aoeRange = 6f;//confirmed with wiki
    }

    @Override
    public void onHit(World world, Vec3d pos, EntityLivingBase caster, ItemStack stack, int index, int status) {
        if (index < maxCount)
        {
            int level = getLevel(stack);
            caster.heal(getHeal(level, caster, status));
        }
        super.onHit(world, pos, caster, stack, index, status);
    }

    @Override
    public boolean applyCast(World worldIn, EntityLivingBase caster, ItemStack stack, EntityEquipmentSlot slot) {
        Vec3d lookVecRaw = caster.getLookVec();
        Vec3d lockVecProjY = new Vec3d(lookVecRaw.x, 0, lookVecRaw.z).normalize();
        Vec3d targetPosF = caster.getPositionVector();

        //todo:de-freeze

        if (!worldIn.isRemote) {
            dealDamage(worldIn, targetPosF, caster, stack);
            worldIn.playSound(null, targetPosF.x, targetPosF.y, targetPosF.z, SoundEvents.ENTITY_GHAST_SCREAM, SoundCategory.BLOCKS, 8f, 0.75f);
        }
        else {
            for (int i = 0; i <= 72; i++)
            {
                Vec3d dir = lockVecProjY.rotateYaw(i * 5 * CommonDef.DEG_TO_RAD);
                worldIn.spawnParticle(EnumParticleTypes.FLAME, targetPosF.x, targetPosF.y, targetPosF.z, dir.x,0,dir.z);
            }
        }
        return super.applyCast(worldIn, caster, stack, slot);
    }

    @Override
    public int calcStatus(World world, Vec3d pos, EntityLivingBase caster, ItemStack stack) {
        if (isReinforced(caster))
        {
            return STATUS_LOW_HP;
        }
        else {
            return STATUS_NORMAL_HP;
        }
    }

    final int STATUS_LOW_HP = 1;
    final int STATUS_NORMAL_HP = 0;

    float getHeal(int level, EntityLivingBase caster, int status) {
        if (status == STATUS_LOW_HP)
        {
            try {
                return lowRegen[level - 1] / 100f * caster.getMaxHealth();
            }
            catch (ArrayIndexOutOfBoundsException e){
                return lowRegen[0] / 100f * caster.getMaxHealth();
            }
        }
        else {
            try {
                return regen[level - 1] / 100f * caster.getMaxHealth();
            }
            catch (ArrayIndexOutOfBoundsException e){
                return regen[0] / 100f * caster.getMaxHealth();
            }
        }
    }

    @Override
    float getInitDamage(int level, EntityLivingBase caster) {
        if (isReinforced(caster))
        {
            try {
                return lowHPDamageRatio[level - 1] / 100f;
            }
            catch (ArrayIndexOutOfBoundsException e){
                return lowHPDamageRatio[0] / 100f;
            }
        }
        return super.getInitDamage(level, caster);
    }

    private boolean isReinforced(EntityLivingBase caster) {
        return caster.getHealth() / caster.getMaxHealth() <= 0.5f;
    }

    @Override
    void dealDamage(World world, Vec3d pos, EntityLivingBase caster, ItemStack stack, boolean dropBalls)    {
        super.dealDamage(world, pos, caster, stack, false);
    }
}

package com.deeplake.genshin12.item.skills;

import com.deeplake.genshin12.blocks.ModBlocks;
import com.deeplake.genshin12.util.EntityUtil;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class ItemZhongLingE extends ItemSkillBase {

    float distance = 2f;
    int ySeekRange = 4;
    float aoeRange = 3f;

    final float[] initDamageRatio = new float[]{16f, 17.2f, 18.4f, 20.0f, 21.2f, 22.4f, 25.6f, 27.2f, 28.8f, 30.4f, 32f, 34f};

    public static final int PILLAR_HEIGHT = 4;

    public ItemZhongLingE(String name) {
        super(name);
        setCD(4f, 0f);
    }

    @Override
    public boolean tryCast(World worldIn, EntityLivingBase caster, EnumHand handIn) {
        if (!worldIn.isRemote)
        {
            ItemStack stack = caster.getHeldItem(handIn);

            Vec3d lookVecRaw = caster.getLookVec();
            Vec3d lockVecProjY = new Vec3d(lookVecRaw.x, 0, lookVecRaw.z).normalize();
            Vec3d targetPosF = caster.getPositionVector().add(lockVecProjY.scale(distance));
            BlockPos pos = new BlockPos(targetPosF);

            boolean isValid = false;
            int y;
            for (y =  ySeekRange; y >= - ySeekRange; y--)
            {
                if (worldIn.getBlockState(pos.offset(EnumFacing.UP, y)).getMaterial().isReplaceable())
                {
                    if (!worldIn.getBlockState(pos.offset(EnumFacing.UP, y - 1)).getMaterial().isReplaceable())
                    {
                        isValid = true;
                        break;
                    }
                }
            }

            targetPosF = targetPosF.add(new Vec3d(0, y, 0));

            if (isValid)
            {
                for (int yOffset = 0; yOffset < PILLAR_HEIGHT; yOffset++)
                {
                    worldIn.setBlockState(pos.add(0, yOffset + y, 0), ModBlocks.ZL_PILLAR.getDefaultState());
                }
            }

            dealDamage(worldIn, targetPosF, caster, stack);
//            caster.playSound(SoundEvents.BLOCK_ANVIL_PLACE, 2f, 0.75f);
            worldIn.playSound(null, targetPosF.x, targetPosF.y, targetPosF.z, SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.BLOCKS, 2f, 0.75f);
        }

        return super.tryCast(worldIn, caster, handIn);
    }

    void dealDamage(World world, Vec3d pos, EntityLivingBase caster, ItemStack stack)
    {
        List<EntityLivingBase> list = EntityUtil.getEntitiesWithinAABB(world, EntityLiving.class, pos, aoeRange, null);

        float damage = getInitDamage(getLevel(stack)) / 100f * caster.getMaxHealth();

        for (EntityLivingBase target :
                list) {
            if (caster instanceof EntityPlayer)
            {
                target.attackEntityFrom(
                        DamageSource.causePlayerDamage((EntityPlayer) caster),
                        damage);
            }
            else {
                //todo
            }

        }
    }

    float getInitDamage(int level)
    {
        try {
            return initDamageRatio[level - 1];
        }
        catch (ArrayIndexOutOfBoundsException e){
            return initDamageRatio[0];
        }
    }

}

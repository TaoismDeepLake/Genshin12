package com.deeplake.genshin12.item.skills.genshin;

import com.deeplake.genshin12.Idealland;
import com.deeplake.genshin12.blocks.ModBlocks;
import com.deeplake.genshin12.blocks.blockMisc.BlockZhongliPillarDeco;
import com.deeplake.genshin12.blocks.tileEntity.genshin.TEZhongliPillar;
import com.deeplake.genshin12.entity.special.EntityKeqingMark;
import com.deeplake.genshin12.potion.ModPotions;
import com.deeplake.genshin12.util.CommonDef;
import com.deeplake.genshin12.util.CommonFunctions;
import com.deeplake.genshin12.util.EntityUtil;
import com.deeplake.genshin12.util.EnumElemental;
import com.deeplake.genshin12.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static net.minecraft.util.EntitySelectors.IS_ALIVE;

public class ItemKeqingE extends ItemGenshinSkillBase{
    public ItemKeqingE(String name) {
        super(name, EnumElemental.ELECTRO);
        setCD(7.5f,0f);
        long_press_ticks = CommonDef.TICK_PER_SECOND * 10;
        setMaxStack(2);

        this.addPropertyOverride(new ResourceLocation("state"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return IDLNBTUtil.GetState(stack);
            }
        });
    }

    float blockReachDistance = 5;//meter

    @Override
    public boolean applyCast(World worldIn, EntityLivingBase caster, ItemStack stack, EntityEquipmentSlot slot) {
        castE(worldIn, caster, stack, false);
        return super.applyCast(worldIn, caster, stack, slot);
    }

    @Override
    public boolean applyLongCast(World worldIn, EntityLivingBase caster, ItemStack stack, EntityEquipmentSlot slot) {
        castE(worldIn, caster, stack, true);
        return super.applyLongCast(worldIn, caster, stack, slot);
    }

    public void castE(World worldIn, EntityLivingBase caster, ItemStack stack, boolean isHold) {
        int state = IDLNBTUtil.GetState(stack);
        switch (state)
        {
            case 0:
                //throw mark
                if (!worldIn.isRemote) {
                    Vec3d lookVecRaw = caster.getLookVec();
                    Vec3d targetPosF = caster.getPositionEyes(0).add(lookVecRaw.scale(distance));

                    RayTraceResult result = keqingTrace(caster);
                    if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
                        targetPosF = result.hitVec;
                    }

                    EntityKeqingMark mark = new EntityKeqingMark(worldIn);//todo
//                    mark.setPosition(caster.posX, caster.posY, caster.posZ);
                    mark.setPosition(targetPosF.x, targetPosF.y, targetPosF.z);
                    worldIn.spawnEntity(mark);

                            worldIn.playSound(null, mark.getPosition(), SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.PLAYERS, 1f, 2f);

//                    mark.setPositionAndUpdate(targetPosF.x, targetPosF.y, targetPosF.z);
//                mark.attemptTeleport(targetPosF.x, targetPosF.y, targetPosF.z);
                    IDLNBTUtil.SetState(stack, 1);

                    //Note: Enchantment should be in a passive skill. Here it is temporary integration
                    caster.addPotionEffect(new PotionEffect(ModPotions.KEQING_ENCHANT, 5 * CommonDef.TICK_PER_SECOND, 0));
                }
                break;
            case 1:
                //have mark
                //todo: find nearest and owner
                EntityKeqingMark entityKeqingMark = null;
                List<EntityKeqingMark> list = worldIn.getEntities(EntityKeqingMark.class, IS_ALIVE);
                if (!list.isEmpty())
                {
                    entityKeqingMark = list.get(0);
                    if (worldIn.isRemote)
                    {
                        worldIn.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE,
                                entityKeqingMark.posX, entityKeqingMark.posY, entityKeqingMark.posZ,
                                0,0,0);
                        entityKeqingMark.init(caster);
                    }
                    else {
                        worldIn.playSound(null, entityKeqingMark.getPosition(), SoundEvents.ENTITY_LIGHTNING_THUNDER,
                                SoundCategory.PLAYERS, 1f, 1f);
                        entityKeqingMark.init(caster);
                        //todo: damage
                        //todo: teleportation has limited range
//                        caster.setPositionAndUpdate(entityKeqingMark.posX, entityKeqingMark.posY, entityKeqingMark.posZ);
//                        caster.attemptTeleport(entityKeqingMark.posX, entityKeqingMark.posY, entityKeqingMark.posZ);

                        //entityKeqingMark.setDead();
                    }
                }
                IDLNBTUtil.SetState(stack, 0);
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + state);
        }


        Vec3d lookVecRaw = caster.getLookVec();
        Vec3d lockVecProjY = new Vec3d(lookVecRaw.x, 0, lookVecRaw.z).normalize();
        Vec3d targetPosF = caster.getPositionVector().add(lockVecProjY.scale(distance));
        BlockPos pos = new BlockPos(targetPosF);

        boolean isValid = false;

    }

    public RayTraceResult keqingTrace(EntityLivingBase caster)
    {
        Vec3d vec3d = caster.getPositionEyes(0);
        Vec3d vec3d1 = caster.getLook(0);
        Vec3d vec3d2 = vec3d.addVector(vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance);
        return caster.world.rayTraceBlocks(vec3d, vec3d2, false, true, false);
    }

    float distance = 5;
    @Override
    public void clientUseTick(ItemStack stack, EntityLivingBase caster, int count) {
        super.clientUseTick(stack, caster, count);

        Vec3d lookVecRaw = caster.getLookVec();
        Vec3d targetPosF = caster.getPositionEyes(0).add(lookVecRaw.scale(distance));

        RayTraceResult result = keqingTrace(caster);
        if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK)
        {
            targetPosF = result.hitVec;
        }

        Random random = caster.getRNG();
        //temp
        World world = caster.getEntityWorld();
        world.spawnParticle(EnumParticleTypes.PORTAL,
                targetPosF.x + random.nextFloat()-0.5,
                targetPosF.y + random.nextFloat()-0.5,
                targetPosF.z + random.nextFloat()-0.5,
                0,0,0);
        world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL,
                targetPosF.x,
                targetPosF.y,
                targetPosF.z,
                0,0,0);
    }
}

package com.deeplake.genshin12.item.skills.genshin;

import com.deeplake.genshin12.entity.special.EntityEnergyOrb;
import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.item.IWIP;
import com.deeplake.genshin12.potion.ModPotions;
import com.deeplake.genshin12.util.EntityUtil;
import com.deeplake.genshin12.util.EnumElemental;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class ItemXiaoE extends ItemGenshinSkillBase{
    //should go 5~7.5m per dash.
    public ItemXiaoE(String name) {
        super(name, EnumElemental.ANEMO);
        setCD(10f,0f);
        setMaxStack(2);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        //so long as you have the buff, even if this is in back pack, it will work.
        if (!worldIn.isRemote && entityIn instanceof EntityLivingBase)
        {
            if (((EntityLivingBase) entityIn).getActivePotionEffect(ModPotions.XIAO_DASH) != null)
            {
                dealDamage(worldIn,entityIn.getPositionVector(), (EntityLivingBase) entityIn, stack);
            }
        }

        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @Override
    public boolean applyCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot) {

        if (worldIn.isRemote)
        {
            Vec3d look = livingBase.getLookVec();
            Vec3d lookVecProjY = new Vec3d(look.x, 0, look.z).normalize().scale(ModConfig.DEBUG_CONF.XIAO_DASH_SPEED);
            livingBase.setVelocity(lookVecProjY.x, 0, lookVecProjY.z);
        }

        if (!worldIn.isRemote)
        {
            dealDamage(worldIn,livingBase.getPositionVector(),livingBase, stack, !livingBase.isPotionActive(ModPotions.YAKSHA_MASK));

            livingBase.addPotionEffect(new PotionEffect(
                    ModPotions.XIAO_DASH,
                    ModConfig.DEBUG_CONF.XIAO_DASH_DURA,
                    0
            ));
        }

        return super.applyCast(worldIn, livingBase, stack, slot);
    }

    @Override
    int getDropAmount(World world, Vec3d pos, EntityLivingBase caster, ItemStack stack) {
//        if (caster.isPotionActive(ModPotions.YAKSHA_MASK))
//        {
//            return 0;
//        }
        return 3;
    }
}

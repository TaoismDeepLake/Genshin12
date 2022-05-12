package com.deeplake.genshin12.entity.projectiles;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityArrowFixed extends EntityTippedArrow {
    public EntityArrowFixed(World worldIn) {
        super(worldIn);
    }

    public EntityArrowFixed(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    public EntityArrowFixed(World worldIn, EntityLivingBase shooter)
    {
        super(worldIn, shooter);
    }


    /**
     * Called when the arrow hits a block or an entity
     * Makes the damage unrelated to speed.
     */
    protected void onHit(RayTraceResult raytraceResultIn)
    {
        float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
        if (f != 0)
        {
            this.setDamage(this.getDamage()/f);
        }

        super.onHit(raytraceResultIn);
    }

    public void setEnchantmentEffectsFromEntity(EntityLivingBase p_190547_1_, float p_190547_2_)
    {
        int i = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.POWER, p_190547_1_);
        int j = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.PUNCH, p_190547_1_);
        this.setDamage(getDamage() + this.rand.nextGaussian() * 0.25D + (double)((float)this.world.getDifficulty().getDifficultyId() * 0.11F));

        if (i > 0)
        {
            this.setDamage(this.getDamage() + (double)i * 0.5D + 0.5D);
        }

        if (j > 0)
        {
            this.setKnockbackStrength(j);
        }

        if (EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.FLAME, p_190547_1_) > 0)
        {
            this.setFire(100);
        }
    }
}

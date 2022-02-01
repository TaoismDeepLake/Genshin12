package com.deeplake.genshin12.entity.special;

import com.deeplake.genshin12.IdlFramework;
import com.deeplake.genshin12.blocks.blockBasic.IdeallandLight;
import com.deeplake.genshin12.entity.creatures.EntityModUnit;
import com.deeplake.genshin12.item.skills.genshin.ItemGenshinBurstBase;
import com.deeplake.genshin12.util.EnumElemental;
import com.deeplake.genshin12.util.NBTStrDef.IDLNBTDef;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//copied from EntityXPOrb
public class EntityEnergyOrb extends Entity {

//    public static final String KEY_HEALTH = "Health";
//    public static final String KEY_AGE = "Age";
//    public static final String KEY_VALUE = "Value";
    EnumElemental enumElemental = EnumElemental.NONE;
    protected static final DataParameter<Byte> ELEMENT = EntityDataManager.createKey(EntityEnergyOrb.class, DataSerializers.BYTE);

    /** A constantly increasing value that RenderEnergyOrb uses to control the colour shifting (Green / yellow) */
    public int xpColor;
    /** The age of the XP orb in ticks. */
    public int xpOrbAge;
    public int delayBeforeCanPickup;
    /** The health of this XP orb. */
    private int xpOrbHealth = 5;
    /** This is how much XP this orb has. */
    public int energyVal = 1;
    /** The closest EntityPlayer to this orb. */
    private EntityPlayer closestPlayer;
    /** Threshold color for tracking players */
    private int xpTargetColor;

    public EntityEnergyOrb(World worldIn, double x, double y, double z)   {
        super(worldIn);
        this.setSize(0.5F, 0.5F);
        this.setPosition(x, y, z);
        this.rotationYaw = (float)(Math.random() * 360.0D);
        this.motionX = (double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F);
        this.motionY = (double)((float)(Math.random() * 0.2D) * 2.0F);
        this.motionZ = (double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F);
    }

    public EntityEnergyOrb(World worldIn) {
        super(worldIn);
        this.setSize(0.25F, 0.25F);
    }

    static float NOT_OFFHAND = 0.6f;
    static float OFFHAND_EXTRA = 0.4f;

    static float NONE_ELEMENT = 2.0f;
    static float SAME_ELEMENT = 3.0f;
    static float WRONG_ELEMENT = 1.0f;

    //has optimization here.
    public void handlePlayer(EntityPlayer player)
    {
        int maxSize = player.inventory.getSizeInventory();
        InventoryPlayer inventoryPlayer = player.inventory;

        float valAllInventory = energyVal * NOT_OFFHAND;

        if (enumElemental == EnumElemental.NONE)
        {
            valAllInventory *= NONE_ELEMENT;

            for (int i = 0; i < maxSize; i++) {
                applyEnergyToSkillNoElement(inventoryPlayer.getStackInSlot(i), player, valAllInventory);
            }

            applyEnergyToSkillNoElement(player.getHeldItemOffhand(), player, energyVal * OFFHAND_EXTRA * NONE_ELEMENT);
        }
        else {
            //orb has a element

            for (int i = 0; i < maxSize; i++) {
                applyEnergyToSkillWithElement(inventoryPlayer.getStackInSlot(i), player, valAllInventory);
            }

            applyEnergyToSkillWithElement(player.getHeldItemOffhand(), player, energyVal * OFFHAND_EXTRA);
        }
    }

    //prevent repeat calculation.
    public void applyEnergyToSkillNoElement(ItemStack stack, EntityPlayer player, float val)
    {
        Item item = stack.getItem();
        if (item instanceof ItemGenshinBurstBase)
        {
            ItemGenshinBurstBase skillBase = (ItemGenshinBurstBase) stack.getItem();
            skillBase.chargeEnergy(stack, player, val);
        }
    }

    public void applyEnergyToSkillWithElement(ItemStack stack, EntityPlayer player, float val)
    {
        Item item = stack.getItem();
        if (item instanceof ItemGenshinBurstBase)
        {
            ItemGenshinBurstBase skillBase = (ItemGenshinBurstBase) stack.getItem();
            skillBase.chargeEnergy(stack, player,
                    skillBase.getElemental() == enumElemental ?
                      val * SAME_ELEMENT : val * WRONG_ELEMENT);
        }
    }

    //----

    public EnumElemental getEnumElemental() {
//        if (world.isRemote)
//        {
            try
            {
                return EnumElemental.values()[dataManager.get(ELEMENT)];
            }
            catch (Exception e)
            {
                return EnumElemental.NONE;
            }
//        }

//        return enumElemental;
    }

    public void setEnumElemental(EnumElemental enumElemental) {
        if (!world.isRemote)
        {
            getDataManager().set(ELEMENT, (byte)enumElemental.ordinal());
        }

        this.enumElemental = enumElemental;
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking()
    {
        return false;
    }

    protected void entityInit()
    {
        this.dataManager.register(ELEMENT, (byte)0);
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender()
    {
        float f = 0.5F;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        int i = super.getBrightnessForRender();
        int j = i & 255;
        int k = i >> 16 & 255;
        j = j + (int)(f * 15.0F * 16.0F);

        if (j > 240)
        {
            j = 240;
        }

        return j | k << 16;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();

        if (this.delayBeforeCanPickup > 0)
        {
            --this.delayBeforeCanPickup;
        }

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (!this.hasNoGravity())
        {
            this.motionY -= 0.029999999329447746D;
        }

        if (this.world.getBlockState(new BlockPos(this)).getMaterial() == Material.LAVA)
        {
            this.motionY = 0.20000000298023224D;
            this.motionX = (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
            this.motionZ = (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
            this.playSound(SoundEvents.ENTITY_GENERIC_BURN, 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
        }

        this.pushOutOfBlocks(this.posX, (this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0D, this.posZ);
        double d0 = 8.0D;

        if (this.xpTargetColor < this.xpColor - 20 + this.getEntityId() % 100)
        {
            if (this.closestPlayer == null || this.closestPlayer.getDistanceSq(this) > 64.0D)
            {
                this.closestPlayer = this.world.getClosestPlayerToEntity(this, 8.0D);
            }

            this.xpTargetColor = this.xpColor;
        }

        if (this.closestPlayer != null && this.closestPlayer.isSpectator())
        {
            this.closestPlayer = null;
        }

        if (this.closestPlayer != null)
        {
            double d1 = (this.closestPlayer.posX - this.posX) / 8.0D;
            double d2 = (this.closestPlayer.posY + (double)this.closestPlayer.getEyeHeight() / 2.0D - this.posY) / 8.0D;
            double d3 = (this.closestPlayer.posZ - this.posZ) / 8.0D;
            double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
            double d5 = 1.0D - d4;

            if (d5 > 0.0D)
            {
                d5 = d5 * d5;
                this.motionX += d1 / d4 * d5 * 0.1D;
                this.motionY += d2 / d4 * d5 * 0.1D;
                this.motionZ += d3 / d4 * d5 * 0.1D;
            }
        }

        this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        float f = 0.98F;

        if (this.onGround)
        {
            BlockPos underPos = new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.getEntityBoundingBox().minY) - 1, MathHelper.floor(this.posZ));
            net.minecraft.block.state.IBlockState underState = this.world.getBlockState(underPos);
            f = underState.getBlock().getSlipperiness(underState, this.world, underPos, this) * 0.98F;
        }

        this.motionX *= (double)f;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= (double)f;

        if (this.onGround)
        {
            this.motionY *= -0.8999999761581421D;
        }

        ++this.xpColor;
        ++this.xpOrbAge;

        if (this.xpOrbAge >= 6000)
        {
            this.setDead();
        }
    }

    /**
     * Returns if this entity is in water and will end up adding the waters velocity to the entity
     */
    public boolean handleWaterMovement()
    {
        return this.world.handleMaterialAcceleration(this.getEntityBoundingBox(), Material.WATER, this);
    }

    /**
     * Will deal the specified amount of fire damage to the entity if the entity isn't immune to fire damage.
     */
    protected void dealFireDamage(int amount)
    {
        this.attackEntityFrom(DamageSource.IN_FIRE, (float)amount);
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (this.world.isRemote || this.isDead) return false; //Forge: Fixes MC-53850
        if (this.isEntityInvulnerable(source))
        {
            return false;
        }
        else
        {
            this.markVelocityChanged();
            this.xpOrbHealth = (int)((float)this.xpOrbHealth - amount);

            if (this.xpOrbHealth <= 0)
            {
                this.setDead();
            }

            return false;
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
//        compound.setShort(KEY_HEALTH, (short)this.xpOrbHealth);
//        compound.setShort(KEY_AGE, (short)this.xpOrbAge);
//        compound.setShort(KEY_VALUE, (short)this.energyVal);
        compound.setByte(IDLNBTDef.ELEMENT, (byte) getEnumElemental().ordinal());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
//        this.xpOrbHealth = compound.getShort(KEY_HEALTH);
//        this.xpOrbAge = compound.getShort(KEY_AGE);
//        this.energyVal = compound.getShort(KEY_VALUE);
        try
        {
            enumElemental = EnumElemental.values()[compound.getByte(IDLNBTDef.ELEMENT)];
        }
        catch (Exception e)
        {
            enumElemental = EnumElemental.NONE;
            IdlFramework.LogWarning("Invalid element");
        }
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    public void onCollideWithPlayer(EntityPlayer entityIn) {
        if (!this.world.isRemote) {
            if (this.delayBeforeCanPickup == 0 && entityIn.xpCooldown == 0) {
//                    if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.player.PlayerPickupXpEvent(entityIn, this))) return;
                handlePlayer(entityIn);
                entityIn.xpCooldown = 2;
                entityIn.onItemPickup(this, 1);

                this.setDead();
            }
        }
    }

    /**
     * Returns true if it's possible to attack this entity with an item.
     */
    public boolean canBeAttackedWithItem()
    {
        return false;
    }

    public static void drop(EntityLivingBase livingBase, int amount, EnumElemental elemental)
    {
        drop(livingBase.world, livingBase.getPositionVector().addVector(0, livingBase.height / 2f, 0), amount, elemental);
    }

    //cool down unhandled.
    public static void drop(World world, Vec3d pos, int ball, EnumElemental elemental)
    {
        for (int i = 0; i < ball; i++)
        {
            EntityEnergyOrb orb = new EntityEnergyOrb(world, pos.x, pos.y, pos.z);
            orb.setEnumElemental(elemental);
            world.spawnEntity(orb);
        }
    }
}

package com.deeplake.genshin12.item.skills.arknights;

import com.deeplake.genshin12.IdlFramework;
import com.deeplake.genshin12.item.skills.ItemSkillBase;
import com.deeplake.genshin12.util.CommonFunctions;
import com.deeplake.genshin12.util.IDLSkillNBT;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import static com.deeplake.genshin12.util.CommonDef.TICK_PER_SECOND;

public class ItemArknightsSkillBase extends ItemSkillBase {

    protected int[] dura = {10};
    //int[] cd = {90};
    protected int[] initPower = {0};

    protected int[] max_charge = {5};

    boolean isAutoCharge = true;

    public ItemArknightsSkillBase(String name) {
        super(name);
        CommonFunctions.addToEventBus(this);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 100;
    }

    public float getInitPower(ItemStack stack)
    {
        int level = IDLSkillNBT.getLevel(stack) - 1;

        if (level < 0){
            return initPower[0];
        }
        else if(level >= initPower.length)
        {
            return initPower[initPower.length - 1];
        }

        return initPower[level];
    }

    public float getDurationMax(ItemStack stack)
    {
        int level = IDLSkillNBT.getLevel(stack) - 1;

        if (level < 0){
            return dura[0];
        }
        else if(level >= dura.length)
        {
            return dura[dura.length - 1];
        }

        return dura[level];
    }

    public float getChargeMax(ItemStack stack)
    {
        int level = IDLSkillNBT.getLevel(stack) - 1;

        if (level < 0){
            return max_charge[0];
        }
        else if(level >= max_charge.length)
        {
            return max_charge[max_charge.length - 1];
        }

        return max_charge[level];
    }

    @Override
    public float getCoolDown(ItemStack stack) {
        return getChargeMax(stack);
    }

    @Override
    public float getDura(ItemStack stack) {
        return getDurationMax(stack);
    }

    public void upkeep(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if (worldIn.isRemote || worldIn.getWorldTime() % TICK_PER_SECOND != 0)
        {
            return;
        }

        if (entityIn instanceof EntityPlayer)
        {
            boolean casting = IDLSkillNBT.IsCasting(stack);
            EntityPlayer player = (EntityPlayer) entityIn;
            player.getCooldownTracker().setCooldown(stack.getItem(), casting ? 3600 : 0);
            if (casting)
            {
                float dura = IDLSkillNBT.GetDura(stack);
                if (worldIn.getWorldTime() % TICK_PER_SECOND == 0)
                {
                    dura -= 1f;
                }

                if (dura <= 0)
                {
                    dura = 0;
                    IDLSkillNBT.SetCasting(stack, false);
                    activateCoolDownArknights(stack);
                    IdlFramework.Log("%s casting complete.", entityIn);
                }

                IDLSkillNBT.SetDura(stack, dura);
            }
            else {
                if (isAutoCharge)
                {
                    float charge = IDLSkillNBT.GetCharge(stack);
                    float curMaxCharge = getChargeMax(stack);

                    if (charge >= curMaxCharge)
                    {
                        if (charge > curMaxCharge)
                        {
                            charge = curMaxCharge;
                            IDLSkillNBT.SetCharge(stack, curMaxCharge);
                        }
                    }
                    else {
                        charge += 1f;
                        IDLSkillNBT.SetCharge(stack, charge);
                    }
                }
            }

            if (casting)
            {
                stack.setItemDamage((int) ((1 - IDLSkillNBT.GetDura(stack) / getDurationMax(stack)) * getMaxDamage(stack)));
            }else {
                stack.setItemDamage((int) ((1 - IDLSkillNBT.GetCharge(stack) / getChargeMax(stack)) * getMaxDamage(stack)));
            }
        }
    }

    public void activateCoolDownArknights(ItemStack stack)
    {
        Item item = stack.getItem();
        if (item instanceof ItemSkillBase)
        {
            IDLSkillNBT.SetCasting(stack, false);
            IDLSkillNBT.SetCharge(stack, 0);
        }
    }

    @Override
    public boolean canCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot handIn) {

        boolean casting = IDLSkillNBT.IsCasting(stack);
        if (casting)
        {
            return false;
        }

        float charge = IDLSkillNBT.GetCharge(stack);
        float curMaxCharge = getChargeMax(stack);

        if (charge >= curMaxCharge)
        {
            return super.canCast(worldIn, livingBase, stack, handIn);
        }
        else {
            return false;
        }
    }

    @Override
    public boolean applyCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot) {
        IDLSkillNBT.SetCasting(stack, true);
        IDLSkillNBT.SetCharge(stack, 0);
        IDLSkillNBT.SetDura(stack, getDurationMax(stack));
        trySayDialogue(livingBase, stack);
        return super.applyCast(worldIn, livingBase, stack, slot);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        upkeep(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    public void tryToInitializeCharge(EntityPlayer player)
    {
        for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
            ItemStack itemstack = player.inventory.getStackInSlot(i);
            {
                if (itemstack.getItem() == this)
                {
                    if (!IDLSkillNBT.IsCasting(itemstack))
                    {
                        float power = getInitPower(itemstack);
                        IDLSkillNBT.SetCharge(itemstack, power);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onLogIn(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (!event.player.world.isRemote)
            tryToInitializeCharge(event.player);
    }

    @SubscribeEvent
    public void onDeath(LivingDeathEvent event)
    {
        if (event.getEntityLiving() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            if (!player.world.isRemote)
                tryToInitializeCharge(player);
        }
    }
}

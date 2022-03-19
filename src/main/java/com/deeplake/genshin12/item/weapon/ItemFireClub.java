package com.deeplake.genshin12.item.weapon;

import com.deeplake.genshin12.util.EnumAmount;
import com.deeplake.genshin12.util.EnumElemental;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashSet;

public class ItemFireClub extends ItemInfusedMelee {
    public ItemFireClub(String name, ToolMaterial material, EnumElemental enumElemental, EnumAmount enumAmount) {
        super(name, material, enumElemental, enumAmount);
    }

    static HashSet<Enchantment> enchantments = new HashSet<>();
    {
        enchantments.add(Enchantments.UNBREAKING);
        enchantments.add(Enchantments.PUNCH);
        enchantments.add(Enchantments.SWEEPING);
        enchantments.add(Enchantments.FIRE_ASPECT);
    }

    @SubscribeEvent
    public void onPlayerHit(AttackEntityEvent entityEvent)
    {
        //different from mob
        if (entityEvent.getEntityPlayer().getHeldItemMainhand().getItem() == this)
        {
            entityEvent.getTarget().setFire(2);
        }
    }

    /**
     * Called when a Block is right-clicked with this Item
     */
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        pos = pos.offset(facing);
        ItemStack itemstack = player.getHeldItem(hand);

        if (!player.canPlayerEdit(pos, facing, itemstack))
        {
            return EnumActionResult.FAIL;
        }
        else
        {
            if (worldIn.isAirBlock(pos))
            {
                worldIn.playSound(player, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
                worldIn.setBlockState(pos, Blocks.FIRE.getDefaultState(), 11);
            }

            if (player instanceof EntityPlayerMP)
            {
                CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos, itemstack);
            }

            itemstack.damageItem(1, player);
            return EnumActionResult.SUCCESS;
        }
    }


    //    public static boolean onPlayerAttackTarget(EntityPlayer player, Entity target)
//    {
//        if (MinecraftForge.EVENT_BUS.post(new AttackEntityEvent(player, target))) return false;
//        ItemStack stack = player.getHeldItemMainhand();
//        return stack.isEmpty() || !stack.getItem().onLeftClickEntity(stack, player, target);
//    }
//    @Override
//    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
//        return super.onLeftClickEntity(stack, player, entity);
//    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantments.contains(enchantment) && super.canApplyAtEnchantingTable(stack, enchantment);
    }
}

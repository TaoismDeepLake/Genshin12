package com.deeplake.genshin12.item.weapon;

import com.deeplake.genshin12.item.ItemSwordBase;
import com.deeplake.genshin12.util.CommonFunctions;
import com.deeplake.genshin12.util.EnumAmount;
import com.deeplake.genshin12.util.EnumElemental;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashSet;

public class ItemInfusedMelee extends ItemSwordBase implements IElementalInfused{

    EnumElemental enumElemental = EnumElemental.PHYSICAL;
    EnumAmount enumAmount = EnumAmount.NONE;

    public ItemInfusedMelee(String name, ToolMaterial material, EnumElemental enumElemental, EnumAmount enumAmount) {
        super(name, material);
        this.enumElemental = enumElemental;
        this.enumAmount = enumAmount;
        CommonFunctions.addToEventBus(this);
    }

    @Override
    public EnumElemental getElemType() {
        return enumElemental;
    }

    @Override
    public EnumAmount getAttackAmount() {
        return enumAmount;
    }

}

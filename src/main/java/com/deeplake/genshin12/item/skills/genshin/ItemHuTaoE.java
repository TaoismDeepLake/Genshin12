package com.deeplake.genshin12.item.skills.genshin;

import com.deeplake.genshin12.potion.ModPotions;
import com.deeplake.genshin12.util.CommonDef;
import com.deeplake.genshin12.util.EnumElemental;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemHuTaoE extends ItemGenshinSkillBase {
    int buff_ticks = CommonDef.TICK_PER_SECOND * 9;

//    3.84,4.07,4.3,4.6,4.83,5.06,5.36,5.66,5.96,6.26,6.55,6.85,7.15
//
//            64,68.8,73.6,80,84.8,89.6,96,102.4,108.8,115.2,121.6,128,136

    //how to apply a buff that boost atk by HP????

    public ItemHuTaoE(String name) {
        super(name, EnumElemental.PYRO);
        setCD(16f, 0f);
        setMaxLevel(13);
    }

    @Override
    public boolean applyCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot) {
        if (!worldIn.isRemote)
        {
            livingBase.setHealth(Math.max(0.1f, livingBase.getHealth() - livingBase.getMaxHealth() * 0.3f));
            livingBase.addPotionEffect(new PotionEffect(ModPotions.HUTAO_BUFF, buff_ticks, getLevel(stack) - 1));
        }
        return super.applyCast(worldIn, livingBase, stack, slot);
    }

//    Hu Tao consumes a set portion of her HP to knock the surrounding enemies back and enter the Paramita Papilio state.
//
//            Paramita Papilio
//    Increases Hu Tao's ATK based on her Max HP at the time of entering this state. ATK Bonus gained this way cannot exceed 400% of Hu Tao's Base ATK.
//    Converts attack DMG to Pyro DMG, which cannot be overridden by any other elemental infusion.
//    Charged Attacks apply the Blood Blossom effect to the enemies hit.
//    Increases Hu Tao's resistance to interruption.
//    Blood Blossom
//    Enemies affected by Blood Blossom will take Pyro DMG every 4s. This DMG is considered Elemental Skill DMG.
//    Each enemy can be affected by only one Blood Blossom effect at a time, and its duration may only be refreshed by Hu Tao herself.
}

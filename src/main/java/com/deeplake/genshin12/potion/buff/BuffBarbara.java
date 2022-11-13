package com.deeplake.genshin12.potion.buff;

import com.deeplake.genshin12.entity.special.EntityBarbaraBuff;
import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.util.CommonDef;
import com.deeplake.genshin12.util.ElementalUtil;
import com.deeplake.genshin12.util.EnumAmount;
import com.deeplake.genshin12.util.EnumElemental;
import com.deeplake.genshin12.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.world.World;

//  /effect @e[type=genshin12:hilichurl] genshin12:barbara_e 30
public class BuffBarbara extends BaseSimplePotion{
    public BuffBarbara(boolean isBadEffectIn, int liquidColorIn, String name, int icon) {
        super(isBadEffectIn, liquidColorIn, name, icon);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    //this only runs on server. client should figure out another way.
    @Override
    public void applyAttributesModifiersToEntity(EntityLivingBase livingBase, AbstractAttributeMap attributeMapIn, int amplifier) {
        super.applyAttributesModifiersToEntity(livingBase, attributeMapIn, amplifier);
//        World world = livingBase.world;
//        if (livingBase.getEntityWorld().isRemote)
//        {
//            //pure client side
//            EntityBarbaraBuff barbaraBuff = new EntityBarbaraBuff(world, livingBase);
//            world.spawnEntity(barbaraBuff);
//        }
    }

    @Override
    public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier) {
        super.removeAttributesModifiersFromEntity(entityLivingBaseIn, attributeMapIn, amplifier);
    }

    public static final int PERIOD = 5 * CommonDef.TICK_PER_SECOND;
    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
        super.performEffect(entityLivingBaseIn, amplifier);
        int getTargetTick = entityLivingBaseIn.ticksExisted;
        //potion class does not know dura.

        int dura = entityLivingBaseIn.getActivePotionEffect(this).getDuration();
        if (dura % PERIOD == getTargetTick) {
            World world = entityLivingBaseIn.getEntityWorld();
            if (!world.isRemote) {
                entityLivingBaseIn.heal((float) getHeal(amplifier + 1, entityLivingBaseIn));
                ElementalUtil.applyElemental(entityLivingBaseIn, EnumElemental.HYDRO, EnumAmount.SMALL);
                //not correct. should remove the 0.8 penalty
            }
        }
    }

    public double[] continue_regen = new double[]{4f,4.3f,4.6f,5,5.3f,5.6,6.4,6.8,7.2,7.6,8,8.5};
    public double[] continue_regen_fixed = new double[]{385.18,423.71,465.44,510.39,558.54,609.91,664.48,722.27,783.27,847.47,914.89,985.52,1059};

    double getHeal(int level, EntityLivingBase caster) {
        try {
            return continue_regen[level - 1] / 100f * caster.getMaxHealth() + continue_regen_fixed[level - 1] / ModConfig.GeneralConf.HP_GENSHIN_TO_MC;
        } catch (ArrayIndexOutOfBoundsException e) {
            return continue_regen[0] / 100f * caster.getMaxHealth() + continue_regen_fixed[0] / ModConfig.GeneralConf.HP_GENSHIN_TO_MC;
        }
    }
}

package com.deeplake.genshin12.item.skills.genshin;

import com.deeplake.genshin12.util.EnumElemental;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemHuTaoQ extends ItemGenshinBurstBase {
    public ItemHuTaoQ(String name) {
        super(name, 70, EnumElemental.PYRO);
        setRange(10f,0f);
    }

    @Override
    void dealDamage(World world, Vec3d pos, EntityLivingBase caster, ItemStack stack, boolean dropBalls)   {
        super.dealDamage(world, pos, caster, stack, false);
    }
}

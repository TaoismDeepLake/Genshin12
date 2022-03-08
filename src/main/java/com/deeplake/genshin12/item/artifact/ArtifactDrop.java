package com.deeplake.genshin12.item.artifact;


import com.deeplake.genshin12.item.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber()
public class ArtifactDrop {

    @SubscribeEvent
    public static void onDrop(LivingDropsEvent event)
    {
        EntityLivingBase livingBase = event.getEntityLiving();
        List<EntityItem> stacks = event.getDrops();
        if (livingBase instanceof EntityDragon || livingBase instanceof EntityWither)
        {
            Random random = livingBase.getRNG();
            for(int i = 0; i <= 3; i++)
            {
                ItemStack stack = ModItems.AR_GLADIATOR.getRandomBlankInstance(random.nextInt(4), 4);
                stacks.add(livingBase.entityDropItem(stack,1f));
            }

            ItemStack stack = ModItems.AR_GLADIATOR.getRandomBlankInstance(random.nextInt(4), 5);
            stacks.add(livingBase.entityDropItem(stack,1f));

        }
    }

}

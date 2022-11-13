package com.deeplake.genshin12.entity.special;

import com.deeplake.genshin12.Idealland;
import com.deeplake.genshin12.potion.ModPotions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


//this is a entity just for rendering. client side just render the model.
@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class EntityBarbaraBuff extends Entity {

    EntityLivingBase center;
    static Map<EntityLivingBase, EntityBarbaraBuff> map = new HashMap<>();

    public EntityBarbaraBuff(World worldIn) {
        super(worldIn);
    }

    public EntityBarbaraBuff(World worldIn, EntityLivingBase center) {
        super(worldIn);
        this.center = center;
        posX = center.posX;
        posY = center.posY;
        posZ = center.posZ;
        map.put(center, this);
//        startRiding(center, true);
//        center.startRiding(this,true);
    }


    @SubscribeEvent
    public static void onTick(LivingEvent.LivingUpdateEvent event)
    {
//        EntityLivingBase livingBase = event.getEntityLiving();
//        if (!livingBase.getEntityWorld().isRemote && ModPotions.BUFF_BARBARA.hasPotion(livingBase) && !map.containsKey(livingBase))
//        {
//            EntityBarbaraBuff barbaraBuff = new EntityBarbaraBuff(livingBase.getEntityWorld(), livingBase);
//            livingBase.getEntityWorld().spawnEntity(barbaraBuff);
//        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!world.isRemote)
        {
            if (center != null && !center.isDead && center.getActivePotionEffect(ModPotions.BUFF_BARBARA) != null)
            {
//                setPositionAndUpdate(center.posX, center.posY, center.posZ);

            } else {
                setDead();
                if (map.containsValue(this))
                {
                    map.keySet().removeIf(livingBase -> map.get(livingBase) == this);
                }
            }
        }
    }

    @Override
    protected void entityInit() {

    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {

    }
}

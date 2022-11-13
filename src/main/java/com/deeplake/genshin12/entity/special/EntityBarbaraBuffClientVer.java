package com.deeplake.genshin12.entity.special;

import com.deeplake.genshin12.potion.ModPotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityBarbaraBuffClientVer extends EntityBarbaraBuff{
    public EntityBarbaraBuffClientVer(World worldIn) {
        super(worldIn);
    }

    public EntityBarbaraBuffClientVer(World worldIn, EntityLivingBase center) {
        super(worldIn);
        this.center = center;
        posX = center.posX;
        posY = center.posY;
        posZ = center.posZ;
        map.put(center, this);
    }

    @Override
    public void onUpdate() {
        //Entity::onUpdate
        if (!this.world.isRemote)
        {
            this.setFlag(6, this.isGlowing());
        }

        this.onEntityUpdate();

        //this logic
        if (world.isRemote)
        {
            if (center != null && !center.isDead && ModPotions.BUFF_BARBARA.hasPotion(center))
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

}

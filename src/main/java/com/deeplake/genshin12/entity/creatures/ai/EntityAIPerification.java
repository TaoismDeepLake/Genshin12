package com.deeplake.genshin12.entity.creatures.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIPerification extends EntityAIBase {
    public EntityAIPerification(){
        this.setMutexBits(1|2|4);
    }

    @Override
    public boolean shouldExecute() {
        return true;
    }

}

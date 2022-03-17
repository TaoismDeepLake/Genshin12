package com.deeplake.genshin12.entity.creatures.ai;

import com.deeplake.genshin12.Idealland;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class EntityAIPerification extends EntityAIBase {

    EntityLiving living;
    public EntityAIPerification(EntityLiving living){
        this.living = living;
        this.setMutexBits(1|2|4);
    }

    @Override
    public boolean shouldExecute() {
        return true;
    }


    @Override
    public void updateTask() {
        living.getNavigator().clearPath();
        super.updateTask();
    }
}

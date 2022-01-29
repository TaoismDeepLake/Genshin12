package com.deeplake.genshin12.entity.creatures.ai;

import com.deeplake.genshin12.IdlFramework;
import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.potion.ModPotions;
import com.deeplake.genshin12.util.EntityUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = IdlFramework.MODID)
public class EntityAIPerification extends EntityAIBase {
    public EntityAIPerification(){
        this.setMutexBits(1|2|4);
    }

    @Override
    public boolean shouldExecute() {
        return true;
    }

}

package com.deeplake.genshin12.potion;

import com.deeplake.genshin12.IdlFramework;
import com.deeplake.genshin12.potion.buff.BaseSimplePotion;
import com.deeplake.genshin12.potion.buff.PotionJadeShield;
import com.deeplake.genshin12.util.Reference;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ModPotions {

    public static final List<Potion> INSTANCES = new ArrayList<Potion>();


    public static final PotionJadeShield JADE_SHIELD = new PotionJadeShield(false, 0x333333, "jade_shield", 0);
    public static final Potion JADE_SHIELD_DEBUFF = new BaseSimplePotion(false, 0xcccc00, "jade_shield_debuff", 1);

    @Nullable
    private static Potion getRegisteredMobEffect(String id)
    {
        Potion potion = Potion.REGISTRY.getObject(new ResourceLocation(id));

        if (potion == null)
        {
            throw new IllegalStateException("Invalid MobEffect requested: " + id);
        }
        else
        {
            return potion;
        }
    }

    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Potion> evt)
    {
        //VIRUS_ONE.tuples.add(new EffectTuple(0.2f, MobEffects.NAUSEA, 100));

        evt.getRegistry().registerAll(INSTANCES.toArray(new Potion[0]));
        IdlFramework.LogWarning("registered %d potion", INSTANCES.size());
    }
}

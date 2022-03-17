package com.deeplake.genshin12.util;

import com.deeplake.genshin12.Idealland;
import com.deeplake.genshin12.util.sound.ModSoundEvent;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class ModSoundHandler {
    //To add a sound, remember assets.genshin12.sounds.json
    public static final List<ModSoundEvent> SOUNDS = new ArrayList<>();

    //https://musescore.com/user/18946026/scores/6691198
    public static SoundEvent ZHONGLI_Q = new ModSoundEvent("skill.zhongli.q");
//    public static SoundEvent SOUND_2 = new ModSoundEvent("entity.moroon.hurt");

    public static void soundRegister()
    {
        Idealland.Log("Registering %s sounds.", SOUNDS.size());
        ForgeRegistries.SOUND_EVENTS.registerAll(ModSoundHandler.SOUNDS.toArray(new SoundEvent[0]));
        Idealland.Log("Registered %s sounds.", SOUNDS.size());
    }

}

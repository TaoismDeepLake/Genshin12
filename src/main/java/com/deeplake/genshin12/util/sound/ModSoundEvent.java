package com.deeplake.genshin12.util.sound;

import com.deeplake.genshin12.util.ModSoundHandler;
import com.deeplake.genshin12.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class ModSoundEvent extends SoundEvent {
    public ModSoundEvent(String path) {
        super(new ResourceLocation(Reference.MOD_ID, path));
        ModSoundHandler.SOUNDS.add(this);
        setRegistryName(path);
    }
}

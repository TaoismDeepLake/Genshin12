package com.deeplake.genshin12.designs.villagers;

import com.deeplake.genshin12.IdlFramework;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

public class ModVProfession extends VillagerRegistry.VillagerProfession {
    public ModVProfession(String name, String texture, String zombie) {
        super(IdlFramework.MODID + ":" + name, texture, zombie);
        InitVillagers.PROFESSION_LIST.add(this);
    }

    public ModVProfession(String name) {
        this(name,
                "minecraft:textures/entity/villager/priest.png",
                "minecraft:textures/entity/zombie_villager/zombie_priest.png");
    }
}

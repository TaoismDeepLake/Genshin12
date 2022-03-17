package com.deeplake.genshin12.designs.villagers;

import com.deeplake.genshin12.Idealland;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;

public class ModVCareer extends VillagerCareer {
    public ModVCareer(VillagerRegistry.VillagerProfession parent, String name) {
        super(parent, Idealland.MODID + "." +name);
    }
}

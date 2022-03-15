package com.deeplake.genshin12.designs.villagers;

import com.deeplake.genshin12.IdlFramework;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;

public class ModVCareer extends VillagerCareer {
    public ModVCareer(VillagerRegistry.VillagerProfession parent, String name) {
        super(parent, IdlFramework.MODID + "." +name);
    }
}

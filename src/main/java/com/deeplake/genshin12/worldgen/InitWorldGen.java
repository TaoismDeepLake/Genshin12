package com.deeplake.genshin12.worldgen;

import com.deeplake.genshin12.blocks.ModBlocks;
import com.deeplake.genshin12.init.ModConfig;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class InitWorldGen {
    static int nextWeight = 100;
    static int layer1 = 8;
    static int layer2 = 32;
    static int layer3 = 64;
    static int layer4 = 128;


    public static void registerWorldGen()
    {
        register(new GenOreEmeraldLike(ModBlocks.COR_LAPIS_ORE.getDefaultState(), layer1, layer2, ModConfig.WorldGenConf.COR_LAPIS, ModConfig.WorldGenConf.COR_LAPIS_DELTA), nextWeight);

        register(new GenOreEmeraldLike(ModBlocks.ARTIFACT_ORE.getDefaultState(), layer1, layer3, ModConfig.WorldGenConf.ARTIFACT_ORE_BASE, ModConfig.WorldGenConf.ARTIFACT_ORE_DELTA), nextWeight);
    }

    static void register(IWorldGenerator generator, int modGenerationWeight)
    {
        GameRegistry.registerWorldGenerator(generator, modGenerationWeight);
        nextWeight++;
    }
}

package com.deeplake.genshin12.entity;

import com.deeplake.genshin12.Idealland;
import com.deeplake.genshin12.entity.creatures.mob.EntityHilichurl;
import com.deeplake.genshin12.entity.creatures.render.RenderBarbaraEClient;
import com.deeplake.genshin12.entity.projectiles.EntityArrowFixed;
import com.deeplake.genshin12.entity.special.*;
import com.deeplake.genshin12.init.ModConfig;
import com.deeplake.genshin12.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import static com.deeplake.genshin12.util.CommonDef.STANDARD_DUNGEON_MOB_RARITY;

public class ModEntityInit {
    private static int ENTITY_NEXT_ID = 1;
    public static void registerEntities()
    {
        registerEntityNoEgg("planet_befall", EntityPlanetBefall.class);
        registerEntityNoEgg("energy_orb", EntityEnergyOrb.class);
        registerEntityNoEgg("glacial_waltz", EntityGlacialWaltz.class);
        registerEntityNoEgg("fixed_arrow", EntityArrowFixed.class);
        if (ModConfig.DEBUG_CONF.DEBUG_MODE)
        {
            registerEntity("test_boss", EntityTestBoss.class,0xff00ff, 0x000033);
        }

        registerEntity("hilichurl", EntityHilichurl.class);
        registerEntityNoEgg("barbara_e", EntityBarbaraBuff.class);
        registerEntityNoEgg("barbara_e_c", EntityBarbaraBuffClientVer.class);
        registerEntityNoEgg("keqing_e", EntityKeqingMark.class);
        registerEntityNoEgg("raiden_ring", EntityRaidenRing.class);

        //Assign Dungeons
        DungeonHooks.addDungeonMob(EntityList.getKey(EntityHilichurl.class), STANDARD_DUNGEON_MOB_RARITY);

        DataFixer datafixer = new DataFixer(1343);
    }

    private  static  void registerEntity(String name, Class<? extends Entity> entity)
    {
        registerEntity(name, entity, ENTITY_NEXT_ID, 50, 0xff00ff, 0x000000);
    }

    private  static  void registerEntity(String name, Class<? extends Entity> entity, int color1, int color2)
    {
        registerEntity(name, entity, ENTITY_NEXT_ID, 50, color1, color2);
    }

    private  static  void registerEntity(String name, Class<? extends Entity> entity, int id, int range, int color1, int color2){
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":" + name),
                entity,
                name,
                id,
                Idealland.instance,
                range,
                1,
                true,
                color1, color2
                );
        ENTITY_NEXT_ID++;
    }

    private  static  void registerEntityNoEgg(String name, Class<? extends Entity> entity)
    {
        registerEntityNoEgg(name, entity, ENTITY_NEXT_ID, 50);
    }

    private  static  void registerEntityNoEgg(String name, Class<? extends Entity> entity, int id, int range){
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":" + name),
                entity,
                name,
                id,
                Idealland.instance,
                range,
                1,
                true
        );
        ENTITY_NEXT_ID++;
    }
}

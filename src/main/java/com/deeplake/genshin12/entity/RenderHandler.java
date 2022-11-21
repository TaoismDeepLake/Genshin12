package com.deeplake.genshin12.entity;

import com.deeplake.genshin12.entity.creatures.mob.EntityHilichurl;
import com.deeplake.genshin12.entity.creatures.model.ModelBarbara;
import com.deeplake.genshin12.entity.creatures.render.*;
import com.deeplake.genshin12.entity.special.EntityBarbaraBuff;
import com.deeplake.genshin12.entity.special.EntityBarbaraBuffClientVer;
import com.deeplake.genshin12.entity.special.EntityEnergyOrb;
import com.deeplake.genshin12.entity.special.EntityKeqingMark;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderHandler {

    public static void registerEntityRenders() {
        RenderingRegistry.registerEntityRenderingHandler(EntityPlanetBefall.class, RenderPlanetBefallLiving::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityEnergyOrb.class, RenderEnergyOrb::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGlacialWaltz.class, RenderNone::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityHilichurl.class, renderManager -> new RenderHumanoid(renderManager, "mobs/hilichurl"));
        RenderingRegistry.registerEntityRenderingHandler(EntityBarbaraBuff.class, RenderBarbaraE::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityBarbaraBuffClientVer.class, RenderBarbaraEClient::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityKeqingMark.class, RenderKeqingMark::new);

//        RenderingRegistry.registerEntityRenderingHandler(EntityPlanetBefall.class, renderManager -> new RenderPlanetBefall<>(renderManager));
    }
}

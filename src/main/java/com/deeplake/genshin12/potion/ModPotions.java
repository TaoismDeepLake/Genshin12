package com.deeplake.genshin12.potion;

import com.deeplake.genshin12.IdlFramework;
import com.deeplake.genshin12.potion.buff.*;
import com.deeplake.genshin12.util.EnumElemental;
import com.deeplake.genshin12.util.Reference;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ModPotions {

    public static final List<Potion> INSTANCES = new ArrayList<Potion>();

    public static UUID UUID_PETRYFY = UUID.fromString("0fcbf9d1-8028-4241-a38e-2fb86ebb8077");
    public static UUID UUID_CYRO = UUID.fromString("66c81232-b1c7-4b53-8b29-f1c3910a7422");
    public static UUID UUID_FREEZE = UUID.fromString("6470364b-d96e-43e5-911d-22b5e083849f");
    public static UUID UUID_XIAO_MASK = UUID.fromString("51319831-13c4-4b35-9632-d926c041bdae");
    public static UUID UUID_HUTAO_BUFF = UUID.fromString("2cf4ab68-2c9a-4738-9715-b71f95a287f5");
    public static final PotionJadeShield JADE_SHIELD = new PotionJadeShield(false, 0x333333, "jade_shield", 0);
    public static final Potion JADE_SHIELD_DEBUFF = new BaseSimplePotion(true, 0xcccc00, "jade_shield_debuff", 1);
    public static final Potion ZL_PETRIFY = new PotionPetrification(true, 0x555533, "petrification", 2).registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, UUID_PETRYFY.toString(), -1f, 2);

    public static final Potion CYRO = new BasePotion(true, 0x9dd3e0, "cyro", 3).setUUID_CLIENT("a9abeb50-f9e1-44fd-b82e-fce73a071fbf").registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, UUID_CYRO.toString(), -0.15f, 2);
    public static final Potion FREEZE = new PotionPetrification(true, 0x9dd3e0, "freeze", 3).setUUID_CLIENT("b0d94296-6366-42c3-879d-5450efa6e1ed").registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, UUID_FREEZE.toString(), -1f, 2);
    public static final Potion HYDRO = new BasePotion(true, EnumElemental.HYDRO.getColor(), "hydro", 4).setUUID_CLIENT("4a583711-8043-4b03-9974-7e1590818ba9");

    public static final Potion ANEMO = new BasePotion(true, EnumElemental.ANEMO.getColor(), "anemo", 5).setUUID_CLIENT("382e7fdd-f68b-4a65-af87-09b3328b4763");

    public static final Potion GEO = new BasePotion(true, EnumElemental.GEO.getColor(), "geo", 6).setUUID_CLIENT("e6e1ef34-516a-4b87-bb2f-9055c0a9e523");

    public static final Potion ELECTRO = new BasePotion(true, EnumElemental.ELECTRO.getColor(), "electro", 7).setUUID_CLIENT("fb521d4f-c203-4609-aba5-82721d6ff634");

    public static final Potion DENDRO = new BasePotion(true, EnumElemental.DENDRO.getColor(), "dendro", 8).setUUID_CLIENT("3facd155-5b9d-428b-9b4e-cd21232d00d4");

    public static final Potion PYRO = new BasePotion(true, EnumElemental.PYRO.getColor(), "pyro", 9).setUUID_CLIENT("f82fd4d6-3172-4596-af0f-096b04845578");

    public static final Potion CHRONO = new BasePotion(true, EnumElemental.CHRONO.getColor(), "chrono", 10).setUUID_CLIENT("c8725744-4929-46dc-bbf4-090c54d695df");

    public static final Potion XIAO_DASH = new PotionXiaoDash(false, EnumElemental.ANEMO.getColor(), "xiao_dash", 11);

    public static final PotionXiaoMask YAKSHA_MASK = (PotionXiaoMask) new PotionXiaoMask(false, EnumElemental.ANEMO.getColor(), "yaksha_mask", 12).registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE, UUID_XIAO_MASK.toString(), 0f, 1);

    public static final Potion HUTAO_BUFF = new PotionHuTaoBuff(false, EnumElemental.PYRO.getColor(), "hutao_buff", 13);

    public static final Potion HUTAO_DEBUFF = new PotionHuTaoDebuff(true, EnumElemental.PYRO.getColor(), "hutao_debuff", 14);

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
        evt.getRegistry().registerAll(INSTANCES.toArray(new Potion[0]));
        IdlFramework.Log("registered %d potion", INSTANCES.size());
    }

    public static void postInit()
    {
        EnumElemental.ANEMO.setPotion(ANEMO);
        EnumElemental.GEO.setPotion(GEO);
        EnumElemental.ELECTRO.setPotion(ELECTRO);
        EnumElemental.DENDRO.setPotion(DENDRO);
        EnumElemental.HYDRO.setPotion(HYDRO);
        EnumElemental.PYRO.setPotion(PYRO);
        EnumElemental.CYRO.setPotion(CYRO);
        EnumElemental.CHRONO.setPotion(CHRONO);
    }

}

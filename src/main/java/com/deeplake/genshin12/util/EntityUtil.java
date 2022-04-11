package com.deeplake.genshin12.util;

import com.deeplake.genshin12.Idealland;
import com.deeplake.genshin12.entity.creatures.EntityModUnit;
import com.deeplake.genshin12.meta.MetaUtil;
import com.deeplake.genshin12.potion.buff.BaseSimplePotion;
import com.google.common.base.Predicate;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static com.deeplake.genshin12.util.CommonDef.TICK_PER_SECOND;
import static net.minecraft.entity.SharedMonsterAttributes.*;
import static net.minecraftforge.fml.common.gameevent.TickEvent.Type.WORLD;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class EntityUtil {
    public static void simpleKnockBack(float power, EntityLivingBase source, EntityLivingBase target)
    {
        target.knockBack(source, power, (source.posX - target.posX), (source.posZ - target.posZ));
    }

    public static void TryRemoveDebuff(EntityLivingBase livingBase)
    {
        //washes away debuff
        Collection<PotionEffect> activePotionEffects = livingBase.getActivePotionEffects();
        for (int i = 0; i < activePotionEffects.size(); i++) {
            PotionEffect buff = (PotionEffect)activePotionEffects.toArray()[i];
            if (buff.getPotion().isBadEffect()){
                livingBase.removePotionEffect(buff.getPotion());
            }
        }
    }

    public static void TryRemoveGivenBuff(EntityLivingBase livingBase, Potion potion)
    {
        //washes away debuff
        Collection<PotionEffect> activePotionEffects = livingBase.getActivePotionEffects();
        for (int i = 0; i < activePotionEffects.size(); i++) {
            PotionEffect buff = (PotionEffect)activePotionEffects.toArray()[i];
            if (buff.getPotion() == potion){
                livingBase.removePotionEffect(buff.getPotion());
                return;
            }
        }
    }

    public static boolean ApplyBuff(EntityLivingBase livingBase, Potion potion, int level, float seconds)
    {
        if (livingBase == null || potion == null)
        {
            Idealland.LogWarning("Trying to apply illegal potion");
            return false;
        }
        livingBase.addPotionEffect(new PotionEffect(potion, (int) (seconds * TICK_PER_SECOND) + 1, level));
        return true;
    }

    public static String getModName(EntityLivingBase creature)
    {
        if (creature instanceof EntityPlayer || creature == null)
        {
            return "minecraft";
        }
        EntityRegistry.EntityRegistration er = EntityRegistry.instance().lookupModSpawn(creature.getClass(), true);
        if (er == null)
        {
            //Vanilla creatures don't have ER
            return "minecraft";
        }
        return er.getContainer().getModId();
    }

    //Player is not vanilla
    public static boolean isVanillaResident(EntityLivingBase creature)
    {
        if (creature instanceof EntityPlayer || creature == null)
        {
            return false;
        }

        EntityRegistry.EntityRegistration er = EntityRegistry.instance().lookupModSpawn(creature.getClass(), true);
        if (er == null)
        {
            return true;
        }
        String modid = er.getContainer().getModId();
        return modid.equals("minecraft");
    }

    //Player is not otherWorld
    public static boolean isOtherworldAggression(EntityLivingBase creature)
    {
        if (creature instanceof EntityPlayer || creature == null || isIdeallandTeam(creature))
        {
            return false;
        }

        EntityRegistry.EntityRegistration er = EntityRegistry.instance().lookupModSpawn(creature.getClass(), true);
        if (er == null)
        {
            //Normally this will be enough. Vanilla creatures don't have ER
            return false;
        }
        String modid = er.getContainer().getModId();
        //Idealland.Log("Atk ER.modid is %s, name is %s", modid, er.getRegistryName());

        return !modid.equals("minecraft");
    }

    public static boolean isIdeallandTeam(EntityLivingBase creature)
    {
        return (creature instanceof EntityModUnit && ((EntityModUnit) creature).isIdealland);
    }

    public static boolean isMoroonTeam(EntityLivingBase creature)
    {
        return (creature instanceof EntityModUnit && ((EntityModUnit) creature).isMoroon);
    }

    public static boolean isMechanical(EntityLivingBase creature)
    {
        return (creature instanceof EntityModUnit && ((EntityModUnit) creature).is_mechanic);
    }

    public static boolean isAOA3Creature(EntityLivingBase creature)
    {
        if (!MetaUtil.isLoaded_AOA3)
        {
            return false;
        }
        return getModName(creature).equals(CommonDef.MOD_NAME_AOA3);
    }

    public static boolean isGOGCreature(EntityLivingBase creature)
    {
        if (!MetaUtil.isLoaded_GOG)
        {
            return false;
        }
        return getModName(creature).equals(CommonDef.MOD_NAME_AOA3);
    }

    public static <T extends Entity> List<T> getEntitiesWithinAABB(World world, Class <? extends T > clazz, AxisAlignedBB aabb, @Nullable Predicate <? super T > filter)
    {
        return world.getEntitiesWithinAABB(clazz, aabb, filter);
    }

    public static <T extends Entity> List<T> getEntitiesWithinAABB(World world, Class <? extends T > clazz, Vec3d center, float range, @Nullable Predicate <? super T > filter)
    {
        return world.getEntitiesWithinAABB(clazz, IDLGeneral.ServerAABB(center.addVector(-range, -range, -range), center.addVector(range, range, range)) , filter);
    }

    public static Vec3d GetRandomAroundUnderfoot(EntityLivingBase entity, float radius)
    {
        float angle = entity.getRNG().nextFloat() * 6.282f;
        return new Vec3d(entity.posX + Math.sin(angle),  entity.posY, entity.posZ + Math.cos(angle));
    }

    public static Vec3d GetRandomAroundPos(Vec3d pos, float radius, Random rng)
    {
        float angle = rng.nextFloat() * 6.282f;
        return new Vec3d(pos.x + Math.sin(angle), pos.y, pos.z + Math.cos(angle));
    }

    public static void spawnParticleOver(EntityLivingBase entity, EnumParticleTypes particleTypes)
    {
        Vec3d pos = GetRandomAroundPos(entity.getPositionVector().addVector(0, entity.getRNG().nextFloat() * entity.height, 0), entity.width, entity.getRNG());
        entity.world.spawnParticle(particleTypes, pos.x, pos.y, pos.z, 0,0,0);
    }

    public static void SpawnParticleAround(EntityLivingBase entity, EnumParticleTypes particleTypes)
    {
        Vec3d pos = GetRandomAroundUnderfoot(entity,1f);
        entity.world.spawnParticle(particleTypes, pos.x, pos.y, pos.z, 0,0,0);
    }

    public static void SpawnParticleAround(EntityLivingBase entity, EnumParticleTypes particleTypes, int count)
    {
        for (int i = 0; i < count; i++)
        {
            SpawnParticleAround(entity, particleTypes);
        }
    }

    public static void createTeleportEffect(EntityLivingBase livingBase)
    {
        if (livingBase == null)
        {
            return;
        }

        World worldIn = livingBase.world;
        if (worldIn.isRemote)
        {
            Vec3d oriPos = livingBase.getPositionEyes(0);
            Random random = livingBase.getRNG();
            AxisAlignedBB bb = livingBase.getRenderBoundingBox();
            double radiusX = bb.maxX - bb.minX;
            double radiusY = bb.maxY - bb.minY;
            double radiusZ = bb.maxZ - bb.minZ;

            for (int i = 0; i <= 10; i++)
            {
                worldIn.spawnParticle(EnumParticleTypes.PORTAL,
                        CommonFunctions.flunctate(oriPos.x, radiusX, random),
                        CommonFunctions.flunctate(oriPos.y, radiusY, random),
                        CommonFunctions.flunctate(oriPos.z, radiusZ, random),
                        random.nextFloat(),
                        random.nextFloat(),
                        random.nextFloat()
                );
            }

            worldIn.playSound(oriPos.x, oriPos.y, oriPos.z, SoundEvents.ENTITY_ENDERMEN_TELEPORT, null, 1f, 1.3f, false);
        }
    }

    static float angle = 0f;

    @SubscribeEvent
    static void onWorldTick(TickEvent.WorldTickEvent event)
    {
        if (event.type == WORLD )
        {
            angle += 1.0f;//ModConfig.DEBUG_CONF.HALO_OMEGA;
            angle %= 6.282f;
        }
    }

    public static void spawnHaloParticleAround(EntityLivingBase entity, EnumParticleTypes particleTypes, float radius)
    {
        for (int i = 0; i < 10; i++)
        {
            float deltaOmega = 1.0f * i;//ModConfig.DEBUG_CONF.HALO_OMEGA;
            Vec3d pos = new Vec3d(entity.posX + radius * Math.sin(angle + deltaOmega),  entity.posY + 0.1f * entity.getRNG().nextFloat(), entity.posZ + radius * Math.cos(angle + deltaOmega));
            entity.world.spawnParticle(particleTypes, pos.x, pos.y, pos.z, 0,0,0);
        }
    }

    public static EnumFaction faction(EntityLivingBase creature)
    {
        if (isMoroonTeam(creature))
        {
            return EnumFaction.MOROON;
        } else if (isIdeallandTeam(creature))
        {
            return EnumFaction.IDEALLAND;
        }else if (creature instanceof EntityZombie)
        {
            return EnumFaction.MOB_VAN_ZOMBIE;
        }
        else if (creature instanceof IMob)
        {
            return EnumFaction.MOB_VANILLA;
        }else if (creature instanceof EntityPlayer)
        {
            return EnumFaction.PLAYER;
        }else
        {
            return EnumFaction.CRITTER;
        }
    }

    public static EnumAttitude getAttitude(EntityLivingBase subject, EntityLivingBase object)
    {
        if (subject == null || object == null)
        {
            return EnumAttitude.IGNORE;
        }

        if (subject.isOnSameTeam(object))
        {
            return EnumAttitude.FRIEND;
        }
        return getAttitude(faction(subject), faction(object));
    }

    public static EnumAttitude getAttitude(EnumFaction subject, EntityLivingBase object)
    {
        return getAttitude(subject, faction(object));
    }

    public static EnumAttitude getAttitude(EnumFaction subject, EnumFaction object)
    {
        if (subject == object)
        {
            return EnumAttitude.FRIEND;
        }

        if (subject == EnumFaction.CRITTER || object == EnumFaction.CRITTER)
        {
            return EnumAttitude.IGNORE;
        }

        switch (subject)
        {
            case PLAYER:
            case IDEALLAND:
                switch (object)
                {
                    case IDEALLAND:
                        return EnumAttitude.FRIEND;
                    case MOB_VANILLA:
                    case MOB_VAN_ZOMBIE:
                    case MOROON:
                        return EnumAttitude.HATE;
                    default:
                        return EnumAttitude.IGNORE;
                }
            case MOB_VANILLA:
                switch (object)
                {
                    case IDEALLAND:
                    case PLAYER:
                        return EnumAttitude.HATE;
                    case MOB_VAN_ZOMBIE:
                        return EnumAttitude.FRIEND;

                    default:
                        return EnumAttitude.IGNORE;
                }

            case MOB_VAN_ZOMBIE:
                switch (object)
                {
                    case IDEALLAND:
                    case PLAYER:
                        return EnumAttitude.HATE;

                    case MOB_VANILLA:
                        return EnumAttitude.FRIEND;

                    default:
                        return EnumAttitude.IGNORE;
                }

            case MOROON:
                switch (object)
                {
                    case IDEALLAND:
                    case MOB_VAN_ZOMBIE:
                    case PLAYER:
                        return EnumAttitude.HATE;

                    default:
                        return EnumAttitude.IGNORE;
                }
        }
        return EnumAttitude.IGNORE;
    }

    public static Vec3d GetRandomAround(EntityLivingBase entity, float radius)
    {
        float angle = entity.getRNG().nextFloat() * 6.282f;
        return new Vec3d(entity.posX + Math.sin(angle), entity.getEyeHeight() + entity.posY, entity.posZ + Math.cos(angle));
    }

    public static final Predicate<EntityLivingBase> InWater = new Predicate<EntityLivingBase>()
    {
        public boolean apply(@Nullable EntityLivingBase p_apply_1_)
        {
            return p_apply_1_ != null && p_apply_1_.isInWater();
        }
    };

    public static final Predicate<EntityLivingBase> FriendToIdl = new Predicate<EntityLivingBase>()
    {
        public boolean apply(@Nullable EntityLivingBase p_apply_1_)
        {
            return  p_apply_1_ != null && (getAttitude(EnumFaction.IDEALLAND, p_apply_1_)== EnumAttitude.FRIEND);
        }
    };

    public static final Predicate<EntityLivingBase> HostileToIdl = new Predicate<EntityLivingBase>()
    {
        public boolean apply(@Nullable EntityLivingBase p_apply_1_)
        {
            return  p_apply_1_ != null && (getAttitude(EnumFaction.IDEALLAND, p_apply_1_)== EnumAttitude.HATE) && (p_apply_1_).attackable();
        }
    };

    public static final Predicate<EntityLivingBase> HostileToIdl_AIR = new Predicate<EntityLivingBase>()
    {
        public boolean apply(@Nullable EntityLivingBase p_apply_1_)
        {
            return  p_apply_1_ != null && (getAttitude(EnumFaction.IDEALLAND, p_apply_1_)== EnumAttitude.HATE) && (p_apply_1_).attackable() && !p_apply_1_.onGround;
        }
    };

    public static final Predicate<EntityLivingBase> HostileToMor = new Predicate<EntityLivingBase>()
    {
        public boolean apply(@Nullable EntityLivingBase p_apply_1_)
        {
            return  p_apply_1_ != null && (getAttitude(EnumFaction.MOROON, p_apply_1_)== EnumAttitude.HATE) && (p_apply_1_).attackable();
        }
    };

    public static final Predicate<EntityLivingBase> IsVanilla = new Predicate<EntityLivingBase>()
    {
        public boolean apply(@Nullable EntityLivingBase p_apply_1_)
        {
            return  p_apply_1_ != null && isVanillaResident((p_apply_1_)) && (p_apply_1_).attackable();
        }
    };
    public static double getAttack(EntityLivingBase creature)
    {
        if (creature == null)
        {
            return 0;
        }

        IAttributeInstance attribute = creature.getEntityAttribute(ATTACK_DAMAGE);
        if (attribute == null)
        {
            return 0;
        }
        return attribute.getAttributeValue();
    }

    public static double getSight(EntityLivingBase creature)
    {
        if (creature == null)
        {
            return 0;
        }

        IAttributeInstance attribute = creature.getEntityAttribute(FOLLOW_RANGE);
        return attribute.getAttributeValue();
    }

    public static double getAtkSpeed(EntityLivingBase creature)
    {
        if (creature == null)
        {
            return 0;
        }

        IAttributeInstance attribute = creature.getEntityAttribute(ATTACK_SPEED);
        return attribute.getAttributeValue();
    }

    public static double getAttr(EntityLivingBase creature, IAttribute attr)
    {
        if (creature == null)
        {
            return 0;
        }

        IAttributeInstance attribute = creature.getEntityAttribute(attr);
        if (attribute == null)
        {
            return 0;
        }
        return attribute.getAttributeValue();
    }

    public static double getAttrBase(EntityLivingBase creature, IAttribute attr)
    {
        if (creature == null)
        {
            return 0;
        }

        IAttributeInstance attribute = creature.getEntityAttribute(attr);
        if (attribute == null)
        {
            return 0;
        }
        return attribute.getBaseValue();
    }

    public static boolean boostAttr(EntityLivingBase creature, IAttribute attrType, float amountFixed, UUID uuid)
    {
        float val = amountFixed;
        IAttributeInstance attribute = creature.getEntityAttribute(attrType);

        if (attribute == null)
        {
            //this happens on creatures with no attack.
            //will surely happen.
            creature.playSound(SoundEvents.BLOCK_DISPENSER_FAIL, 1f, 1f);
            return false;
        }

        double valueBefore = attribute.getAttributeValue();

        AttributeModifier modifier = attribute.getModifier(uuid);
        if (modifier != null)
        {
            //stack up
            val += modifier.getAmount();
            attribute.removeModifier(modifier);
        }
        attribute.applyModifier(new AttributeModifier(uuid, "pwr up",  val, 0));
        double valueAfter = attribute.getAttributeValue();

        if (modifier == null)
        {
            modifier = attribute.getModifier(uuid);
        }

        //Idealland.Log("Value:%s: %.2f->%.2f", modifier.getName(), valueBefore, valueAfter);
        return true;
    }

    public static boolean boostAttrRatio(EntityLivingBase creature, IAttribute attrType, float amountRatio, UUID uuid)
    {
        float val = amountRatio;
        IAttributeInstance attribute = creature.getEntityAttribute(attrType);

        if (attribute == null)
        {
            //this happens on creatures with no attack.
            //will surely happen.
            creature.playSound(SoundEvents.BLOCK_DISPENSER_FAIL, 1f, 1f);
            return false;
        }

        double valueBefore = attribute.getAttributeValue();

        AttributeModifier modifier = attribute.getModifier(uuid);
        if (modifier != null)
        {
            //stack up
            val += modifier.getAmount();
            attribute.removeModifier(modifier);
        }
        attribute.applyModifier(new AttributeModifier(uuid, "pwr up percent",  val, 1));
        double valueAfter = attribute.getAttributeValue();

        if (modifier == null)
        {
            modifier = attribute.getModifier(uuid);
        }

        //Idealland.Log("Value:%s: %.2f->%.2f", modifier.getName(), valueBefore, valueAfter);
        return true;
    }

    public enum EnumFaction {
        PLAYER((byte) 0),
        IDEALLAND((byte) 1, 1.0f, 1.0f, 0.7f),
        MOB_VANILLA((byte) 2, 1.0f, 0.5f, 0.5f),
        MOB_VAN_ZOMBIE((byte) 3, 0.4f, 1f, 0.4f),
        MOROON((byte) 4, 0.9f, 0.3f, 0.8f),
        CRITTER((byte) 5);

        public final byte index;
        float r = 1.0f, g = 1.0f, b = 1.0f;

        EnumFaction(byte index, float r, float g, float b) {
            this.index = index;
            this.r = r;
            this.g = g;
            this.b = b;
        }

        EnumFaction(byte index) {
            this.index = index;
        }

        public static EnumFaction fromIndex(byte index) {
            for (EnumFaction faction :
                    EnumFaction.values()) {
                if (faction.index == index) {
                    return faction;
                }
            }

            Idealland.LogWarning("Trying to parse non-existing faction : %s", index);
            return CRITTER;
        }

        public void applyColor() {
            GlStateManager.color(r, g, b);
        }
    }


    public enum EnumAttitude {
        HATE,
        IGNORE,
        FRIEND
    }

    public static Biome getBiomeForEntity(Entity entity)
    {
        World world = entity.getEntityWorld();
        return world.getBiomeForCoordsBody(entity.getPosition());
    }

    public static boolean isSunlit(Entity entity)
    {
        float f = entity.getBrightness();
        return  f > 0.5F && entity.world.canSeeSky(new BlockPos(entity.posX, entity.posY + (double)entity.getEyeHeight(), entity.posZ));
    }

    public static boolean isMoonlit(Entity entity)
    {
        int tickInDay = (int) (entity.getEntityWorld().getWorldTime() % 24000);
        if (tickInDay > 167 && tickInDay < 11834)
        {
            return false;
        }
        return entity.world.canSeeSky(new BlockPos(entity.posX, entity.posY + (double)entity.getEyeHeight(), entity.posZ));
    }

    @Nullable
    public static EntityEquipmentSlot findSlot(ItemStack stack, EntityLivingBase livingBase)
    {
        EntityEquipmentSlot slot = null;
        for (EntityEquipmentSlot _slot :
                EntityEquipmentSlot.values()) {
            if (stack == livingBase.getItemStackFromSlot(_slot))
            {
                slot = _slot;
            }
        }
        return slot;
    }

    //Note: this returns 0 if no buff.
    public static int getBuffLevelIDL(EntityLivingBase livingBase, Potion potion) {
        if (livingBase == null || potion == null) {
            Idealland.LogWarning("TRYING_TO_CHECK_ILLEGAL_POTION");
            return 0;
        }
        if (livingBase.getEntityWorld().isRemote && potion instanceof BaseSimplePotion)
        {
            return ((BaseSimplePotion) potion).hasPotion(livingBase) ? 1 : 0;
        }

        PotionEffect effect = livingBase.getActivePotionEffect(potion);
        if (effect == null) {
            return 0;
        } else {
            return effect.getAmplifier() + 1;
        }
    }
}

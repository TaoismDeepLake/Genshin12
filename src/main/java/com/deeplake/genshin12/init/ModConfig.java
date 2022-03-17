package com.deeplake.genshin12.init;

import com.deeplake.genshin12.item.artifact.ArtifactUtil;
import com.deeplake.genshin12.util.Reference;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.deeplake.genshin12.util.CommonDef.TICK_PER_SECOND;


@Config(modid = Reference.MOD_ID, category = "")
public class ModConfig {
    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    private static class EventHandler {

        private EventHandler() {
        }

        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(Reference.MOD_ID)) {
                ConfigManager.sync(Reference.MOD_ID, Config.Type.INSTANCE);
                ArtifactUtil.initValues();
            }
        }
    }

    @Config.LangKey("configgui.genshin12.category.Menu0.GeneralConf")
    @Config.Comment("Idealland general config.")
    public static final GeneralConf GeneralConf = new GeneralConf();

    public static class GeneralConf {
        @Config.LangKey("genshin12.conf.general.movie_mode")
        @Config.Comment("Movie Mode, greatly increases all kinds of effects to record videos.")
        public boolean MOVIE_MODE = false;

        @Config.LangKey("genshin12.conf.general.burst_req_shift")
        @Config.Comment("Elemental burst can only be cast while sneaking(hold shift).")
        public boolean BURST_REQ_SHIFT = false;

        @Config.LangKey("genshin12.conf.general.can_plunge_client")
        @Config.Comment("(ClientSide) Mid air attacks will cause you to plunge down. ")
        public boolean ENABLE_PLUNGE_CLIENT = true;

        @Config.LangKey("genshin12.conf.general.can_plunge_server")
        @Config.Comment("(ServerSide) Falling long launches a plunge attack automatically. Xiao Req this to work, so best not turn off.")
        public boolean ENABLE_PLUNGE_ATTACK = true;

        @Config.LangKey("genshin12.conf.general.dmg_atk_percent_genshin_to_mc")
        @Config.Comment("100% skill damage = ? damage in Minecraft?")
        public float DMG_ATK_PERCENT_GENSHIN_TO_MC = 2f; //100% dmg

        @Config.LangKey("genshin12.conf.general.disable_vanilla_crit")
        @Config.Comment("Disables the vanilla critical strike system.")
        public boolean DISABLE_VANILLA_CRIT = false;

        @Config.LangKey("genshin12.conf.general.override_crit_dmg")
        @Config.Comment("If critical, the damage is merely calculated by this mod")
        public boolean OVERRIDE_CRIT_DMG = false;

        @Config.LangKey("genshin12.conf.general.artifact_enhance_must_first_slot")
        @Config.Comment("When enhancing, the first slot must be the artifact, and destructing not.")
        public boolean ARTIFACT_ENHANCE_MUST_FIRST_SLOT = true;

        @Config.LangKey("genshin12.conf.general.safe_drop")
        @Config.Comment("Loot is directly given to players.")
        public boolean SAFE_DROP = true;
    }

    @Config.LangKey("configgui.genshin12.category.Menu0.GachaConf")
    @Config.Comment("Idealland general config.")
    public static final GachaConf GACHA_CONF = new GachaConf();

    public static class GachaConf {
        @Config.LangKey("genshin12.conf.gacha.allow_treasure_enchant")
        @Config.Comment("Weapon Pack can give treasure enchantments.")
        public boolean ALLOW_TREASUE_ENCHANT = false;

        @Config.LangKey("genshin12.conf.gacha.chara_chance")
        @Config.Comment("The chance of getting a character for interwined fate.")
        @Config.RangeDouble(min = 0, max = 1)
        public double CHARA_CHANCE = 0.1;

        @Config.LangKey("genshin12.conf.gacha.primo_per_chest")
        @Config.Comment("How many primo you get when you loot a chest.")
        @Config.RangeInt(min = 0)
        public int PRIMO_PER_CHEST = 1;

        @Config.LangKey("genshin12.conf.gacha.primo_per_advancement")
        @Config.Comment("How many primo you get when you get a lesser advancement.")
        @Config.RangeInt(min = 0)
        public int PRIMO_PER_ADVANCEMENT = 1;

        @Config.LangKey("genshin12.conf.gacha.primo_per_challenge")
        @Config.Comment("How many primo you get when you loot a challenge advancement.")
        @Config.RangeInt(min = 0)
        public int PRIMO_PER_CHALLENGE = 5;
    }

    @Config.LangKey("configgui.genshin12.category.Menu0.WorldGenConf")
    @Config.Comment("Idealland general config.")
    public static final WorldGenConf WorldGenConf = new WorldGenConf();

    public static class WorldGenConf {
        @Config.LangKey("genshin12.conf.worldgen.cor_lapis_count")
        @Config.Comment("Min count of Cor Lapis generation per chunk.")
        @Config.RangeInt(min = 0)
        @Config.RequiresMcRestart
        public int COR_LAPIS = 2;

        @Config.LangKey("genshin12.conf.worldgen.cor_lapis_count_delta")
        @Config.Comment("Max extra count of Cor Lapis generation per chunk.")
        @Config.RangeInt(min = 0)
        @Config.RequiresMcRestart
        public int COR_LAPIS_DELTA = 3;

        @Config.LangKey("genshin12.conf.worldgen.artifact_ore_count")
        @Config.Comment("Min count of Artifact Ore generation per chunk.")
        @Config.RangeInt(min = 0)
        @Config.RequiresMcRestart
        public int ARTIFACT_ORE_BASE = 24;

        @Config.LangKey("genshin12.conf.worldgen.artifact_ore_count_delta")
        @Config.Comment("Max extra count of Artifact Ore generation per chunk.")
        @Config.RangeInt(min = 0)
        @Config.RequiresMcRestart
        public int ARTIFACT_ORE_DELTA = 48;
    }

    @Config.LangKey("configgui.genshin12.category.Menu0.GuiConf")
    @Config.Comment("Genshin12 GUI config.")
    public static final GuiConf GUI_CONF = new GuiConf();

    public static class GuiConf {
        @Config.LangKey("genshin12.conf.gui.render_lv")
        @Config.Comment("Render Lv")
        public boolean RENDER_LV = true;

        @Config.LangKey("genshin12.conf.gui.max_render_lv_distance")
        @Config.Comment("If the creature is farther than this, don't render it's lv.")
        @Config.RangeDouble(min = 0)
        public double MAX_RENDER_LV_DISTANCE = 32;

        @Config.LangKey("genshin12.conf.gui.render_lv_y_offset")
        @Config.Comment("Where is the lv rendered")
        public double RENDER_LV_Y_OFFSET = 1f;

        @Config.LangKey("genshin12.conf.gui.render_lv_size")
        @Config.Comment("Size of Lv Text")
        public double RENDER_LV_SIZE = 1f;

        @Config.LangKey("genshin12.conf.gui.render_elem_y_offset")
        @Config.Comment("Where are the elements rendered")
        public double RENDER_ELEM_Y_OFFSET = 1f;
    }

    @Config.LangKey("configgui.genshin12.category.Menu0.DebugConf")
    @Config.Comment("Config for developers")
    public static final DebugConf DEBUG_CONF = new DebugConf();

    public static class DebugConf {

        public boolean TEST_1 = false;

        public int PARTICLE_COUNT = 40;
        public float PARTICLE_SPEED = 1f/TICK_PER_SECOND;
        public float PILLAR_LIFE = 30f;

        public boolean DEBUG_MODE = false;

        public float METEOR_LIFE = 2f;
        public float METEOR_SIZE = 9f;
        public float METEOR_HEIGHT = 9f;
        public float METEOR_OMEGA = 5f;

        public float KAEYA_OMEGA = 0.3f;//rad / tick

        public float XIAO_DASH_SPEED = 2f;// m/tick
        public int XIAO_DASH_DURA = 10;//tick
        public double XIAO_JUMP_FACTOR = Math.sqrt(6);//jump speed, meter per tick

        public float PLUNGE_MIN_HEIGHT = 1.5f;//m
        public float PLUNGE_MAX_HEIGHT = 2.4f;//m

        public float PLUNGE_MIN_DAMAGE_FACTOR = 1.3f;//100%atk
        public float PLUNGE_MAX_DAMAGE_FACTOR = 3.2f;//100%atk

        public float PLUNGE_SPEED = 3;

        @Config.Comment("Picking up orbs will cause players within this range to recharge.")
        public float RECHARGE_RADIUS = 16;

        public float ORB_SPAWN_RADIUS = 3;

        @Config.Comment("Level factor in the defense formula")
        public float DEF_LEVEL_FACTOR = 5;
        public float DEF_STATIC_PLUS = 500;

        @Config.Comment("1HP in MC = ?HP in Genshin?")
        public float HP_CONVERT_RATIO = 100;

        @Config.Comment("1ATK in MC = ?ATK in Genshin?")
        public float ATK_CONVERT_RATIO = 100;

        @Config.LangKey("genshin12.conf.worldgen.enable_xiao_jump_boost")
        @Config.Comment("(Client side)Xiao's jump height alter may confict with other jump-adjusting modes. If you are not playing xiao and have compatibility issues, turn this off client side.")
        public boolean ENABLE_XIAO_JUMP_BOOST = true;

        @Config.LangKey("genshin12.conf.debug.player_atk_scale_factor")
        @Config.Comment("Whether player scales with vanilla level.")
        public boolean PLAYER_LEVEL_SCALE_ENABLED = false;

        @Config.LangKey("genshin12.conf.debug.player_hp_scale_factor")
        @Config.Comment("Slows down or speeds up HP scaling of player")
        @Config.RangeDouble(min=0)
        public float PLAYER_HP_SCALE_FACTOR = 1f;

        @Config.LangKey("genshin12.conf.debug.player_atk_scale_factor")
        @Config.Comment("Slows down or speeds up ATK scaling of player")
        @Config.RangeDouble(min=0)
        public float PLAYER_ATK_SCALE_FACTOR = 1f;

        @Config.RangeDouble(min=1f)
        public float PLAYER_DETECT_RANGE = 256f;

        @Config.RangeDouble(min=0f, max =1f)
        public float LUMBER_CHANCE = 0.05f;

        @Config.RangeDouble(min=0f, max =1f)
        public float LUMBER_SET_CHANCE = 0.05f;

        @Config.RangeDouble(min=0f)
        public float MINER_BONUS = 0.3f;

        //https://genshin-impact.fandom.com/wiki/Shields/Enemy
        @Config.Comment("Damage Ratio is a ratio that shows how much a shield can be affected by character damage. The Damage Ratio is usually 0.2 or 0.")
        @Config.RangeDouble(min=0.000001f, max=10f)
        public float ENEMY_SHIELD_DAMAGE_RATIO = 0.2f;
    }

    @Config.LangKey("configgui.genshin12.category.Menu0.SpawnConf")
    @Config.Comment("Spawning")
    public static final SpawnConf SPAWN_CONF = new SpawnConf();

    public static class SpawnConf {
        @Config.LangKey("conf.spawn.enabled")
        @Config.Comment("Spawn mod creatures")
        @Config.RequiresMcRestart
        public boolean SPAWN = true;

        @Config.LangKey("conf.spawn.auto_level_vanilla_dim")
        @Config.Comment("Overworld, nether and the end uses auto leveling across X")
        public boolean AUTO_LEVEL = true;

        @Config.LangKey("conf.spawn.auto_level_block")
        @Config.Comment("Block per level")
        @Config.RangeDouble(min=1)
        public float BLOCK_PER_LEVEL = 64f;

        @Config.LangKey("conf.spawn.max_auto_level, 0 = unlimited")
        @Config.Comment("Max level")
        @Config.RangeInt(min=0)
        public int MAX_AUTO_LEVEL = 100;

        @Config.LangKey("conf.spawn.enemy_hp_scale_factor")
        @Config.Comment("Slows down or speeds up HP scaling of enemy")
        @Config.RangeDouble(min=0)
        public float ENEMY_HP_SCALE_FACTOR = 1f;

        @Config.LangKey("conf.spawn.enemy_atk_scale_factor")
        @Config.Comment("Slows down or speeds up ATK scaling of enemy")
        @Config.RangeDouble(min=0)
        public float ENEMY_ATK_SCALE_FACTOR = 1f;

        @Config.LangKey("conf.spawn.enemy_drop_scale_factor")
        @Config.Comment("Slows down or speeds up drop scaling of enemy")
        @Config.RangeDouble(min=0.000001f, max=10f)
        public float ENEMY_DROP_SCALE_FACTOR = 1f;


//        @Config.LangKey("entity.moroon_tainter.name")
//        @Config.Comment("Spawn Moroon Tainter")
//        @Config.RequiresMcRestart
//        public int SPAWN_TAINTER = 100;
    }

    @Config.LangKey("configgui.genshin12.category.Menu0.ElemConf")
    @Config.Comment("Elemental")
    public static final ElemConf ELEMCONF = new ElemConf();

    public static class ElemConf {
        @Config.LangKey("conf.elem.death_drop_orb")
        @Config.Comment("Creatures drop energy when killed. Only those with Atk Attr.")
        public boolean DEATH_DROP_ORB = true;

        public double R_STRONG_MELT = 2.5;
        public double R_STRONG_VAPORIZE = 2.5;
        public double R_WEAK_MELT = 0.625;
        public double R_WEAK_VAPORIZE = 0.625;
        public double R_OVERLOAD = 1.25;
        public double R_SUPERCONDUCT = 1.25;
        public double R_CRYTALLIZE = 0.625;
        public double R_SWIRL = 0.625;

        public double R_SHOCK = 0.625;

        public double GAUGE_PER_SHOCK = 0.4;

        public double AURA_TAX = 0.2;//20%will be removed

        public double DAMAGE_STRONG_MELT = 2;
        public double DAMAGE_STRONG_VAPORIZE = 2;
        public double DAMAGE_WEAK_MELT = 1.5;
        public double DAMAGE_WEAK_VAPORIZE = 1.5;

        @Config.LangKey("conf.elem.overload_explosion_grief")
        @Config.Comment("Overload explosion destroys terrain.")
        public boolean OVERLOAD_EXPLOSION_GRIEF = true;

        @Config.Comment("In seconds.")
        @Config.RangeDouble(min=0.01f)
        public double SUPERCONDUCT_DURA = 12;

    }
}

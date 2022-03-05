package com.deeplake.genshin12.item.artifact.set;

import com.deeplake.genshin12.item.artifact.ItemArtifactBase;
import com.deeplake.genshin12.util.CommonFunctions;
import com.deeplake.genshin12.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import scala.Int;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class ArtifactSetBase {
    public final String key;
    public final int minRarity;
    public final int maxRarity;
    public final UUID uuid;
    public HashMap<Integer, AttributeModifier> modifierMap = new HashMap<>();
    public HashMap<Integer, IAttribute> attrMap = new HashMap<>();
    public HashSet<Integer> suitList = new HashSet<>();
    public static final String NAME = "Set Bonus";

    static int MAX_SET_COUNT = 4;

    public ArtifactSetBase(String key, int minRarity, int maxRarity, UUID uuid) {
        this.key = key;
        this.minRarity = minRarity;
        this.maxRarity = maxRarity;
        this.uuid = uuid;
        ArtifactSetManager.list.add(this);
        CommonFunctions.addToEventBus(this);
    }

    public ArtifactSetBase(String key, int minRarity, int maxRarity, String uuid) {
        this(key,minRarity,maxRarity,UUID.fromString(uuid));
    }

    //Will conflict if the two shares the same attr.
    public ArtifactSetBase setAttrSet(int setCount, IAttribute attr, double value, int type)
    {
        modifierMap.put(setCount, new AttributeModifier(uuid, NAME, value, type));
        attrMap.put(setCount, attr);
        suitList.add(setCount);
        return this;
    }

    public ArtifactSetBase addSetEffectMark(int count)
    {
        if (!suitList.contains(count))
        {
            suitList.add(count);
        }
        return this;
    }

    public ArtifactSetBase(String key, UUID uuid) {
        this(key, 1, 5, uuid);
    }

    public boolean hasSetEffect(int count)
    {
        return count == 2 || count == 4;
    }

    public void updateSetCount(EntityPlayer player, int old, int now)
    {
        for (int count = 0; count <= MAX_SET_COUNT; count++) {
            if (count >= old && count < now) {
                updateSetBonus(count, player, true);
            }
            else if (count >= now && count < old)
            {
                updateSetBonus(count, player, false);
            }
        }
    }

    //can be overrided, but usually not.
    public void updateSetBonus(int count, EntityPlayer player, boolean remove)
    {
        updateSetBonusAttr(count, player, remove);
    }

    public void updateSetBonusAttr(int count, EntityPlayer player, boolean remove)
    {
        AttributeModifier modifier = modifierMap.get(count);
        IAttribute attribute = attrMap.get(count);
        if (modifier != null && attribute != null)
        {
            IAttributeInstance attributeInstance = player.getEntityAttribute(attribute);
            if (attributeInstance != null)
            {
                attributeInstance.removeModifier(modifier);
                if (!remove)
                {
                    attributeInstance.applyModifier(modifier);
                }
            }
        }
    }

    @SubscribeEvent
    public void onLogIn(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (!event.player.world.isRemote)
        {
            checkSetCount(event.player);
        }
    }

    @SubscribeEvent
    public void onEquipChange(LivingEquipmentChangeEvent event)
    {
        EntityLivingBase livingBase = event.getEntityLiving();
        if (livingBase instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) livingBase;
            checkSetCount(player);
        }
    }

    public void checkSetCount(EntityPlayer player) {
        int lastCount = IDLNBTUtil.getPlayerIdeallandIntSafe(player, key);
        int nowCount = getNowCount(player);

        if (nowCount != lastCount)
        {
            updateSetCount(player, lastCount, nowCount);
        }
    }

    public int getNowCount(EntityPlayer player) {
        int nowCount = 0;
        for (EntityEquipmentSlot slot :
                EntityEquipmentSlot.values()) {
            ItemStack stack = player.getItemStackFromSlot(slot);
            if (stack.getItem() instanceof ItemArtifactBase &&
                    stack.getItem().getEquipmentSlot(stack) == slot)
            {
                if (((ItemArtifactBase) stack.getItem()).getSet() == this)
                {
                    nowCount++;
                }
            }
        }
        return nowCount;
    }

    public static final String SUIT_COUNT_KEY = "genshin12.artifact.suit.count";
    static final String SUIT_DESC = "genshin12.artifact.suit.%s.%s";
    static final String NAME_KEY = "name";
    public String getSuitDescKey(int count)
    {
        return String.format(SUIT_DESC, key, count);
    }
    public String getSuitNameKey()
    {
        return String.format(SUIT_DESC, key, NAME_KEY);
    }
}

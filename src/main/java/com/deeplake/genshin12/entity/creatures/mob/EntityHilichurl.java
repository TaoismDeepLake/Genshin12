package com.deeplake.genshin12.entity.creatures.mob;

import com.deeplake.genshin12.entity.creatures.ai.hilichurl.EntityAIPanicOnFire;
import com.deeplake.genshin12.item.ModItems;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.*;

//Qiu Qiu Ren
public class EntityHilichurl extends EntityMobRanged {
    public EntityHilichurl(World worldIn) {
        super(worldIn);
        autoArmor = true;
    }

    @Override
    protected void applyEntityAI() {
        super.applyEntityAI();
        tasks.addTask(1, new EntityAIPanicOnFire(this, 1.5f));
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        setGenshinAttr(1f, 1f);
        return super.onInitialSpawn(difficulty, livingdata);
    }

    enum EnumHiliType
    {
        NORMAL,
        FIGHTER,//has a club
        BERSERKER,//has a fire club
        SHOOTER,//has a bow
        GUARD,//has a shield and a club
    }

    static List<HiliEntry> chanceSet = new ArrayList<>();
    static
    {
        chanceSet.add(new HiliEntry(EnumHiliType.NORMAL, 100));
        chanceSet.add(new HiliEntry(EnumHiliType.FIGHTER, 100));
        chanceSet.add(new HiliEntry(EnumHiliType.BERSERKER, 100));
        chanceSet.add(new HiliEntry(EnumHiliType.SHOOTER, 100));
        chanceSet.add(new HiliEntry(EnumHiliType.GUARD, 100));
    }

    public static class HiliEntry extends WeightedRandom.Item
    {
        public final EnumHiliType content;

        public HiliEntry(EnumHiliType biome, int weight)
        {
            super(weight);

            this.content = biome;
        }
    }

    static EnumHiliType getRandomClass(Random random)
    {
        //todo
        return WeightedRandom.getRandomItem(random, chanceSet).content;
    }

    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
        autoArmor = false;
        EnumHiliType type = getRandomClass(rand);
        switch (type)
        {
            case NORMAL:
                break;
            case FIGHTER:
                setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.WOODEN_SWORD));
                break;
            case BERSERKER:
                setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ModItems.FIRE_CLUB));
                break;
            case SHOOTER:
                setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
                break;
            case GUARD:
                autoArmor = true;
                setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.WOODEN_SWORD));
                setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
                break;
        }

        if (autoArmor)
        {
            super.setEquipmentBasedOnDifficulty(difficulty);
        }
    }
}

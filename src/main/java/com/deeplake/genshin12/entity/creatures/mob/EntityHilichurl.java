package com.deeplake.genshin12.entity.creatures.mob;

import com.deeplake.genshin12.item.ModItems;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Random;

//Qiu Qiu Ren
public class EntityHilichurl extends EntityMobRanged {
    public EntityHilichurl(World worldIn) {
        super(worldIn);
        autoArmor = true;
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
        SHOOTER,//has a bow
        GUARD,//has a shield and a club
    }

    static HashMap<EnumHiliType, Integer> chanceSet = new HashMap<>();
    static void initGroup()
    {
        //todo
        chanceSet.put(EnumHiliType.NORMAL, 100);
        chanceSet.put(EnumHiliType.FIGHTER, 100);
        chanceSet.put(EnumHiliType.SHOOTER, 100);
        chanceSet.put(EnumHiliType.GUARD, 100);
    }

    static EnumHiliType getRandomClass(Random random)
    {
        //todo
        return random.nextBoolean() ? EnumHiliType.GUARD : EnumHiliType.SHOOTER;
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
            case SHOOTER:
                setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
                break;
            case GUARD:
                autoArmor = true;
                setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.WOODEN_SWORD));
                setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.SHIELD));
                break;
        }

        if (autoArmor)
        {
            super.setEquipmentBasedOnDifficulty(difficulty);
        }
    }
}

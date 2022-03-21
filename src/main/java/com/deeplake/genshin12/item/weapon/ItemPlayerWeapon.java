package com.deeplake.genshin12.item.weapon;

import com.deeplake.genshin12.ILogNBT;
import com.deeplake.genshin12.Idealland;
import com.deeplake.genshin12.entity.creatures.attribute.ModAttributes;
import com.deeplake.genshin12.item.ILeveler;
import com.deeplake.genshin12.item.ItemBase;
import com.deeplake.genshin12.item.LevelingUtil;
import com.deeplake.genshin12.item.ModItems;
import com.deeplake.genshin12.util.IDLSkillNBT;
import com.google.common.collect.Multimap;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

//todo: weapon breakthrough
public class ItemPlayerWeapon extends ItemBase implements ILeveler, ILogNBT {
    public ItemPlayerWeapon(String name) {
        super(name);
    }

    public static float[] baseATK5 = {44.34f,45.94f,47.54f,49.13f};
    public static float[] baseATK4 = {41.07f,42.40f,43.73f,45.07f};
    public static float[] main_4_scale_t1 = new float[]
            {
                    1f, 1.077f, 1.154f, 1.23f, 1.306f, 1.382f, 1.457f, 1.533f, 1.607f, 1.682f, 1.757f, 1.831f, 1.905f, 1.979f, 2.052f, 2.126f, 2.199f, 2.272f, 2.345f, 2.417f, 2.49f, 2.562f, 2.634f, 2.707f, 2.778f, 2.85f, 2.922f, 2.993f, 3.065f, 3.136f, 3.207f, 3.278f, 3.349f, 3.42f, 3.49f, 3.561f, 3.632f, 3.702f, 3.772f, 3.842f, 3.913f, 3.983f, 4.053f, 4.122f, 4.192f, 4.262f, 4.332f, 4.401f, 4.471f, 4.54f, 4.609f, 4.679f, 4.748f, 4.817f, 4.886f, 4.955f, 5.024f, 5.093f, 5.162f, 5.231f, 5.3f, 5.368f, 5.437f, 5.506f, 5.574f, 5.643f, 5.711f, 5.78f, 5.848f, 5.916f, 5.985f, 6.053f, 6.121f, 6.189f, 6.257f, 6.326f, 6.394f, 6.462f, 6.53f, 6.598f, 6.665f, 6.733f, 6.801f, 6.869f, 6.937f, 7.005f, 7.072f, 7.14f, 7.208f, 7.275f};

    public static float[] main_5_scale_t1 = new float[]
            {
                    1f,1.079f,1.159f,1.238f,1.317f,1.395f,1.474f,1.552f,1.631f,1.709f,1.787f,1.865f,1.942f,2.02f,2.098f,2.175f,2.253f,2.33f,2.408f,2.485f,2.562f,2.639f,2.717f,2.794f,2.871f,2.948f,3.026f,3.103f,3.18f,3.257f,3.334f,3.412f,3.489f,3.566f,3.644f,3.721f,3.798f,3.876f,3.953f,4.031f,4.109f,4.186f,4.264f,4.342f,4.419f,4.497f,4.575f,4.653f,4.731f,4.81f,4.888f,4.966f,5.044f,5.123f,5.201f,5.28f,5.359f,5.437f,5.516f,5.595f,5.674f,5.753f,5.833f,5.912f,5.991f,6.071f,6.15f,6.23f,6.31f,6.39f,6.47f,6.55f,6.63f,6.71f,6.791f,6.871f,6.952f,7.033f,7.113f,7.194f,7.275f,7.357f,7.438f,7.519f,7.601f,7.682f,7.764f,7.846f,7.928f,8.01f
            };

    public static int getMaxLevel(int rarity)
    {
        switch (rarity)
        {
//            case 1:
//            case 2:
//                return 4;
//            case 3:
//                return 12;
//            case 4:
//                return 16;
//            case 5:
//                return 20;
            default:
                return 90;
        }
    }
    
    public float getATK(ItemStack stack)
    {
        //starts from Lv.1
        int level = LevelingUtil.getLevelForItem(stack) + 1;
        try {
            return main_4_scale_t1[level] * baseATK4[0];
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            e.printStackTrace();
        }
        return baseATK4[0];
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> result = super.getAttributeModifiers(slot, stack);
        if (slot == EntityEquipmentSlot.OFFHAND)
        {
            result.put(ModAttributes.GEN_ATK.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)getATK(stack), 0));
        }
        else if (slot == EntityEquipmentSlot.MAINHAND){
            result.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)getATK(stack), 0));
            result.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
        }

        return result;
    }

    public static int [] exp_weapon_4 =
            {
                    400,625,900,1200,1550,1950,2350,2800,3300,3800,4350,4925,5525,6150,6800,7500,8200,8950,9725,10500,11900,12775,13700,14650,15625,16625,17650,18700,19775,20900,22025,23200,24375,25600,26825,28100,29400,30725,32075,33425,36575,38075,39600,41150,42725,44325,45950,47600,49300,51000,55375,57225,59100,61025,62950,64925,66900,68925,70975,73050,78900,81125,83400,85700,88025,90375,92750,95150,97575,100050,107675,110325,113000,115700,118425,121200,124000,126825,129675,132575,156475,175875,197600,221975,249300,279950,314250,352700,395775};
    public static int[] exp_weapon_5 = {600,950,1350,1800,2325,2925,3525,4200,4950,5700,6525,7400,8300,9225,10200,11250,12300,13425,14600,15750,17850,19175,20550,21975,23450,24950,26475,28050,29675,31350,33050,34800,36575,38400,40250,42150,44100,46100,48125,50150,54875,57125,59400,61725,64100,66500,68925,71400,73950,76500,83075,85850,88650,91550,94425,97400,100350,103400,106475,109575,118350,121700,125100,128550,132050,135575,139125,142725,146375,150075,161525,165500,169500,173550,177650,181800,186000,190250,194525,198875,234725,263825,296400,332975,373950,419925,471375,529050,593675,};

    @Override
    public int[] levelupNeedXp(ItemStack stack) {
        return levelupNeedXp(LevelingUtil.getRarityArtifact(stack));
    }

    public static int[] levelupNeedXp(int rarity) {
        return exp_weapon_4;
    }

    @Override
    public int getMaxLevel(ItemStack stack) {
        return 90;
    }

    //level 2 = +2, the first 2 items in the array.
    public static int getTotalXP(int rarity, int level)
    {
        int[] table = levelupNeedXp(rarity);
        int total = 0;
        for (int i = 0; i < level; i++)
        {
            try {
                total += table[i];
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
                Idealland.LogWarning("Wrong level %s for rarity %s", level, rarity);
            }
        }
        return total;
    }

    public static int[] xp_worth = {0,600,1200,1800,5000,300000, 400,2000,10000};

    public static int getXPWorth(ItemStack stack)
    {
        if (stack.getItem() instanceof ItemPlayerWeapon)
        {
            ItemPlayerWeapon artifactBase = (ItemPlayerWeapon) stack.getItem();
            int rarity = LevelingUtil.getRarityArtifact(stack);
            int level = LevelingUtil.getLevelForItem(stack);
            int base = 100;
            try {
                base = xp_worth[rarity - 1];
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
                Idealland.LogWarning("Wrong rarity for weapon: %s", rarity);
            }

            return (int) (base + getTotalXP(rarity, level) * 0.8f + IDLSkillNBT.getXP(stack));
        }
        else if (stack.getItem() == ModItems.WEAPON_XP_STONE)
        {
            int meta = stack.getItemDamage();
            if (meta == 0)
            {
                return IDLSkillNBT.getXP(stack);
            }
            else {
                try {
                    return xp_worth[meta - 1];
                }
                catch (ArrayIndexOutOfBoundsException e)
                {
                    Idealland.LogWarning(e.toString());
                    return -1;
                }
            }
        }
        else {
            return -1;
        }

    }
}

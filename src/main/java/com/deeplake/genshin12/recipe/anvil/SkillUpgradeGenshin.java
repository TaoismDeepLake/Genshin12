package com.deeplake.genshin12.recipe.anvil;

import com.deeplake.genshin12.item.ModItems;
import com.deeplake.genshin12.item.skills.genshin.ItemGenshinSkillBase;
import com.deeplake.genshin12.util.EnumElemental;
import com.deeplake.genshin12.util.IDLSkillNBT;
import com.deeplake.genshin12.util.Reference;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class SkillUpgradeGenshin {

    static final int MAX_LV = 10;

    @SubscribeEvent
    public static void onAnvil(AnvilUpdateEvent event)
    {
        ItemStack leftIn = event.getLeft();
        ItemStack rightIn = event.getRight();


        if (leftIn.getItem() instanceof ItemGenshinSkillBase && rightIn.getItem() == ModItems.SKILL_UPGRADE)
        {
            ItemGenshinSkillBase skillBase = (ItemGenshinSkillBase) leftIn.getItem();
            int maxLevel = Math.min(MAX_LV, skillBase.maxLevel);
            int curLevel = skillBase.getLevel(leftIn);

            if (curLevel < maxLevel)
            {
                int nextLevel = curLevel + 1;

                EnumElemental enumElemental = skillBase.getElemental();
                if (enumElemental.ordinal() == rightIn.getMetadata())
                {
                    //type correct, now check amount
                    event.setMaterialCost(nextLevel);
                    event.setCost(nextLevel);
                    //fk mj. This cost will go ignored if the second material isn't enough
                    if (event.getRight().getCount() < event.getMaterialCost())
                    {
                        event.setOutput(ItemStack.EMPTY);
                        return;
                    }

                    ItemStack result = leftIn.copy();
                    IDLSkillNBT.setLevel(result, nextLevel);
                    event.setOutput(result);
                }
            }
        }
    }
}

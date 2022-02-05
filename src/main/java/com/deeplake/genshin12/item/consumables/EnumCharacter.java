package com.deeplake.genshin12.item.consumables;

import com.deeplake.genshin12.item.ModItems;
import com.deeplake.genshin12.item.skills.genshin.ItemGenshinBurstBase;
import com.deeplake.genshin12.item.skills.genshin.ItemGenshinSkillBase;
import com.deeplake.genshin12.util.PlayerUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public enum EnumCharacter {
    ZHONGLI(ModItems.ZHONGLI_E, ModItems.ZHONGLI_Q),
    KAEYA(ModItems.KAEYA_E, ModItems.KAEYA_Q);

    ItemGenshinSkillBase elemSkill;
    ItemGenshinBurstBase burst;
    EnumCharacter(ItemGenshinSkillBase elemSkill, ItemGenshinBurstBase burstBase)
    {
        this.elemSkill = elemSkill;
        this.burst = burstBase;
    }

    public void giveSkills(EntityPlayer player)
    {
        PlayerUtil.giveToPlayer(player, new ItemStack(elemSkill));
        PlayerUtil.giveToPlayer(player, new ItemStack(burst));
    }
}

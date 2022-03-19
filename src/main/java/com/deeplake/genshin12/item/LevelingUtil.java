package com.deeplake.genshin12.item;

import com.deeplake.genshin12.item.artifact.ArtifactUtil;
import com.deeplake.genshin12.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.item.ItemStack;

public class LevelingUtil {
    //usually starts from +0
    public static int getLevelForItem(ItemStack stack) {
        return IDLNBTUtil.GetInt(stack, ArtifactUtil.KEY_LEVEL, 0);
    }

    //returns 1,2,3,4,5,6
    public static int getRarityArtifact(ItemStack stack)
    {
        int rarity = IDLNBTUtil.GetInt(stack, ArtifactUtil.KEY_RARITY);
        if (rarity <= 0)
        {
            return 1;
        }
//        else if (rarity >= ArtifactUtil.MAX_RARITY) {
//            return ArtifactUtil.MAX_RARITY;
//        }
        else {
            return rarity;
        }
    }
}

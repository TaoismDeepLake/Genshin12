package com.deeplake.genshin12.item.artifact.set;

import net.minecraft.entity.SharedMonsterAttributes;

import java.util.ArrayList;
import java.util.List;

public class ArtifactSetManager {

    public static List<ArtifactSetBase> list = new ArrayList<>();

    public static ArtifactSetBase DEFAULT = new ArtifactSetBase("sDefault",1,1,"054b9ebc-56fb-49a0-a224-d744f9d5f455").addSetEffectMark(4).setAttrSet(2, SharedMonsterAttributes.MAX_HEALTH, 0.3, 1);

}

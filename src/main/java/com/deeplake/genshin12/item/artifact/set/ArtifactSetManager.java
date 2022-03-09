package com.deeplake.genshin12.item.artifact.set;

import com.deeplake.genshin12.entity.creatures.attribute.ModAttributes;
import net.minecraft.entity.SharedMonsterAttributes;

import java.util.ArrayList;
import java.util.List;

public class ArtifactSetManager {

    public static List<ArtifactSetBase> list = new ArrayList<>();

    public static ArtifactSetBase DEFAULT = new ArtifactSetBase("sDefault",1,5,"054b9ebc-56fb-49a0-a224-d744f9d5f455").addSetEffectMark(4).setAttrSet(2, SharedMonsterAttributes.MAX_HEALTH, 0.3, 1);

    public static ArtifactSetBase LUMBER = new ArtifactSetBase("sLumber",2,3,"b2e29d67-76b1-4ce2-a356-b88c6ee0035f").addSetEffectMark(4).addSetEffectMark(2);

    public static ArtifactSetBase MINER = new ArtifactSetBase("sMiner",2,3,"c38a9af1-291f-4cbd-8fa0-bedacc24f780").addSetEffectMark(4).addSetEffectMark(2);

    public static ArtifactSetBase GLADIATOR = new ArtifactSetBase("sGladiator",4,5,"93736326-b036-42e5-b64c-b9ea5239ad76").setAttrSet(2, SharedMonsterAttributes.ATTACK_DAMAGE, 0.18, 1).setAttrSet(4, ModAttributes.CRIT_RATE, ModAttributes.convert(0.20), 0);

//    public static ArtifactSetBase WANDERER = new ArtifactSetBase("sWanderer",4,5,"fc6413e0-e7ad-41e1-a49c-887a63a8562e").addSetEffectMark(4).setAttrSet(2, ModAttributes.ELEM_MASTERY, 80, 0);

    public static ArtifactSetBase BLANK = new ArtifactSetBase("sBlank",1,1,"fda02f58-ce32-4a65-8114-b833ae169b95");
}

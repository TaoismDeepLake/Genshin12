package com.deeplake.genshin12.designs;

import com.deeplake.genshin12.util.EnumElemental;

public class ReactionResult {
    public final EnumElemental enumElemental;
    public final EnumElemental enumElemental2;
    public final double amount;
    public final double amount2;
    public final int level;
    public final int level2;

    public final double factor;
    public final ElemTuple.EnumReaction reaction;

    public static ReactionResult NONE = new ReactionResult(ElemTuple.ZERO, ElemTuple.ZERO, ElemTuple.EnumReaction.NONE, 0, 0,0);

    public ReactionResult(EnumElemental enumElemental, EnumElemental enumElemental2, double amount, double amount2, ElemTuple.EnumReaction reaction, double factor, int level, int level2) {
        this.enumElemental = enumElemental;
        this.enumElemental2 = enumElemental2;
        this.amount = amount;
        this.amount2 = amount2;
        this.factor = factor;
        this.reaction = reaction;
        this.level = level;
        this.level2 = level2;
    }

    public ReactionResult(ElemTuple tuple1, ElemTuple tuple2, ElemTuple.EnumReaction reaction, double factor, int level, int level2) {
        this.enumElemental = tuple1.enumElemental;
        this.enumElemental2 = tuple2.enumElemental;
        this.amount = tuple1.amount;
        this.amount2 = tuple2.amount;
        this.factor = factor;
        this.reaction = reaction;
        this.level = level;
        this.level2 = level2;
    }

    public ReactionResult(ElemTuple tuple1, ElemTuple.EnumReaction reaction, double factor, int level, int level2) {
        this.enumElemental = tuple1.enumElemental;
        this.enumElemental2 = EnumElemental.PHYSICAL;
        this.amount = tuple1.amount;
        this.amount2 = 0;
        this.factor = factor;
        this.reaction = reaction;
        this.level = level;
        this.level2 = level2;
    }

    @Override
    public String toString() {
        return String.format("R-Result: (%s,%s,%s), (%s,%s,%s), type=%s, fac=%s",
                enumElemental,level,amount,
                enumElemental2,level2,amount2,
                reaction, factor);
    }


}

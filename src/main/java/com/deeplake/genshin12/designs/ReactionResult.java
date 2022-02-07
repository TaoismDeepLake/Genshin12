package com.deeplake.genshin12.designs;

import com.deeplake.genshin12.util.EnumElemental;

public class ReactionResult {
    final EnumElemental enumElemental;
    final EnumElemental enumElemental2;
    final double amount;
    final double amount2;
    final double factor;
    final ElemTuple.EnumReaction reaction;

    public static ReactionResult NONE = new ReactionResult(ElemTuple.ZERO, ElemTuple.ZERO, ElemTuple.EnumReaction.NONE, 0);

    public ReactionResult(EnumElemental enumElemental, EnumElemental enumElemental2, double amount, double amount2, ElemTuple.EnumReaction reaction, double factor) {
        this.enumElemental = enumElemental;
        this.enumElemental2 = enumElemental2;
        this.amount = amount;
        this.amount2 = amount2;
        this.factor = factor;
        this.reaction = reaction;
    }

    public ReactionResult(ElemTuple tuple1, ElemTuple tuple2, ElemTuple.EnumReaction reaction, double factor) {
        this.enumElemental = tuple1.enumElemental;
        this.enumElemental2 = tuple2.enumElemental;
        this.amount = tuple1.amount;
        this.amount2 = tuple2.amount;
        this.factor = factor;
        this.reaction = reaction;
    }

    public ReactionResult(ElemTuple tuple1, ElemTuple.EnumReaction reaction, double factor) {
        this.enumElemental = tuple1.enumElemental;
        this.enumElemental2 = EnumElemental.PHYSICAL;
        this.amount = tuple1.amount;
        this.amount2 = 0;
        this.factor = factor;
        this.reaction = reaction;
    }
}

package com.deeplake.genshin12.util;

public enum EnumElemental {
    NONE(0xcccccc),
    ANEMO(0x73bfa6),
    GEO(0xf5b32f),
    ELECTRO(0xb08fc2),
    DENDRO(0xa6c938),
    HYDRO(0x4bc3f1),
    PYRO(0xe37434),
    CYRO(0x9dd3e0),
    CHRONO(0xcccccc);

    int color;
    EnumElemental(int color)
    {
        this.color = color;
    }

    public int getR()
    {
        return color >> 16;
    }

    public int getG()
    {
        return (color >> 8) & 255;
    }

    public int getB()
    {
        return color & 255;
    }
}

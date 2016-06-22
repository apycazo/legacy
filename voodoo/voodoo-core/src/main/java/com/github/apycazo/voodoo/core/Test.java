package com.github.apycazo.voodoo.core;

import java.util.Collection;

/**
 * @author Andres Picazo
 */
public class Test
{

    public static boolean isEmpty(String str)
    {
        return str == null || str.isEmpty();
    }

    public static boolean isEmpty(Object[] obj)
    {
        return obj == null || obj.length == 0;
    }

    public static boolean isEmpty(Collection col)
    {
        return col == null || col.isEmpty();
    }

    public static boolean isBitSet(int value, int bitPosition)
    {
        return (value & (1 << bitPosition)) != 0;
    }
}

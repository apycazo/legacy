package com.github.apycazo.voodoo.core;

/**
 * @author Andres Picazo
 */
public class StringTools
{
    public static final String EMPTY = "".intern();

    public static String nullSafe(String string)
    {
        return nullSafe(string, EMPTY);
    }

    public static String nullSafe(String string, String defaultValue)
    {
        if (string == null) {
            return defaultValue;
        }
        else {
            return string;
        }
    }
}

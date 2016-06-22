package com.github.apycazo.voodoo.core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * @author Andres Picazo
 */
public class Dates
{

    public static long epoch()
    {
        return System.currentTimeMillis() / 1000;
    }

    public static long timestamp()
    {
        return System.currentTimeMillis();
    }

    public static DateFormat getISO8601DateFormat()
    {
        return getISO8601DateFormat(Constants.TIME_ZONE.UTC);
    }

    public static DateFormat getISO8601DateFormat(Constants.TIME_ZONE timeZone)
    {
        return getISO8601DateFormat(timeZone.name());
    }

    public static DateFormat getISO8601DateFormat(String timeZone)
    {
        DateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT_ISO_8601);
        format.setTimeZone(TimeZone.getTimeZone(timeZone));
        return format;
    }
}

package com.github.apycazo.voodoo.core;

/**
 * @author Andres Picazo
 */
public class Constants {

    // This class must never be instanced.
    private Constants()
    {
        throw new AssertionError("Class 'Constants' can not be instanced");
    }

    public static final String JSON_OK = "{\"ok\":true}";
    public static final String JSON_ERR = "{\"ok\":false}";
    public static final String JSON_NOT_AVAILABLE = "{\"result\":\"Not available\"}";
    public static final String JSON_SERIALIZATION_ERR = "{\"ok\":false, \"op\":\"serialization\"}";
    public static final String JSON_DESERIALIZATION_ERR = "{\"ok\":false, \"op\":\"deserialization\"}";

    public static final String DATE_FORMAT_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    public static final String NEW_LINE = System.getProperty("line.separator");
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String PATH_SEPARATOR = System.getProperty("path.separator");
    public static final String USER_HOME = System.getProperty("user.home");

    public static final String EMPTY_STRING = "";
    public static final String EMPTY_JSON_ARRAY_STRING = "[]";
    public static final String SPACE = " ";
    public static final String TAB = "\t";
    public static final String SINGLE_QUOTE = "'";
    public static final String PERIOD = ".";
    public static final String DOUBLE_QUOTE = "\"";

    public static final String ENABLED_STRING = "enabled";
    public static final String DISABLED_STRING = "disabled";

    public static enum TIME_ZONE {

        UTC, GMT
    }
}

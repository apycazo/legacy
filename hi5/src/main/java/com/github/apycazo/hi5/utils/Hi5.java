package com.github.apycazo.hi5.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created on 30/07/15.
 * @author Andres Picazo
 */
public class Hi5 {

    /**
     * Singleton to retrieve a jackson parser, built on demand.
     */
    public static final ObjectMapper jacksonParser = new ObjectMapper();

    /**
     * Singleton to retrieve a jackson parser with pretty printing on.
     */
    public static final ObjectMapper jacksonPrettyParser = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    /**
     * A static empty string, to avoid creating multiple objects.
     */
    public static final String EMPTY_STRING = "";

    /**
     * A serialization of an empty json object.
     */
    public static final String EMPTY_JSON_BRACES_STRING = "{}";

    /**
     * A serialization of an empty json array.
     */
    public static final String EMPTY_JSON_BRACKETS_STRING = "[]";

    /**
     * Safe get for Object.toString, if null, will return an empty string.
     * @param object The object to call toString method onto.
     * @return A not null string.
     */
    public static String stringValue (Object object) {

        return stringValue(object, EMPTY_STRING);
    }

    /**
     * Safe get for Object.toString, if null, will return an empty string.
     * @param object The object to call toString method onto.
     * @param defaultValue The default value to return when null, if null, will use an empty string instead.
     * @return A not null string.
     */
    public static String stringValue (Object object, String defaultValue) {

        defaultValue = defaultValue == null ? EMPTY_STRING : defaultValue;
        return object == null ? defaultValue : object.toString();
    }

    /**
     * Formats a string replacing each "{}" occurrence with the next value.toString.
     * @param format A string format, like "Hi, I am {}, nice to meet you".
     * @param values A value array, like ["Mike"].
     * @return A formatted String, like "Hi, I am Mike, nice to meet you".
     */
    public static String text (String format, Object ... values) {

        if (values == null) {
            return stringValue(format);
        }

        for (Object value : values) {
            format = format.replaceFirst("\\{}", stringValue(value));
        }

        return format;
    }

    /**
     * Null safe method get of an array size.
     * @param array The array to test for size.
     * @return The size of the array, 0 if null.
     */
    public static int sizeOf (Object [] array) {

        if (array == null) {
            return 0;
        }
        else {
            return array.length;
        }
    }

    /**
     * Null safe method get of a collection size.
     * @param collection The collection to test for size.
     * @return The size of the collection, 0 if null.
     */
    public static int sizeOf (Collection collection) {

        if (collection == null) {
            return 0;
        }
        else {
            return collection.size();
        }
    }

    /**
     * Safe instance for a collection iterator. Will create an empty LinkedList if null.
     * @param collection The collection to generate an iterator.
     * @param <T> The inner collection class.
     * @return An iterator of the appropriate class.
     */
    public static <T> Iterator<T> getIterator (Collection<T> collection) {

        if (collection == null) {
            collection = new LinkedList<>();
        }
        return collection.iterator();
    }

    /**
     * Exception safe conversion from Object to JSON String using static jacksonParser.
     * @param value The value to serialize.
     * @return The serialized value, or "{}" if an exception was thrown.
     */
    public static String toJson (Object value) {

        return toJson(value, EMPTY_JSON_BRACES_STRING);
    }

    /**
     * Exception safe conversion from Object to JSON String using static jacksonParser.
     * @param value The value to serialize.
     * @param defaultValue A value to return in case of error.
     * @return The serialized value, or the provided default value if an exception was thrown.
     */
    public static String toJson (Object value, String defaultValue) {

        try {
            return jacksonParser.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            return defaultValue;
        }
    }

    /**
     * Generates a quoted version of a string.
     * @param value The string to quote.
     * @return The value, with quoting signs, like 'test'.
     */
    public static String quote (String value) {

        return String.format("'%s'",value);
    }

}

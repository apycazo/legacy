package com.github.apycazo.voodoo.core;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Andres Picazo
 */
public class Converters
{

    private static class MapperHolder
    {
        private static final ObjectMapper MAPPER = new ObjectMapper().configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    public static ObjectMapper getMapper()
    {
        return MapperHolder.MAPPER;
    }

    public static String objectToJsonString(Object obj)
    {
        try {
            return getMapper().writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            // Silent exception
            return Constants.JSON_SERIALIZATION_ERR;
        }
    }

    public static String objectToPrettyJsonString(Object obj)
    {
        try {
            return getMapper().writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            // Silent exception
            return Constants.JSON_SERIALIZATION_ERR;
        }
    }

    public static String exceptionToJsonMap(Throwable th)
    {
        return exceptionToJsonMap(th, false);
    }

    public static String exceptionToJsonMap(Throwable th, boolean pretty)
    {
        LinkedHashMap<String, Object> map = exceptionToMap(th);
        if (pretty) {
            return objectToPrettyJsonString(map);
        } else {
            return objectToJsonString(map);
        }

    }

    public static LinkedHashMap<String, Object> exceptionToMap(Throwable th)
    {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("name", th.getClass().getSimpleName());
        map.put("class", th.getClass().getCanonicalName());
        map.put("message", th.getMessage());
        if (th.getCause() != null) {
            Throwable cause = th.getCause();
            LinkedHashMap<String, Object> causeMap = new LinkedHashMap<>();
            causeMap.put("class", cause.getClass().getCanonicalName());
            causeMap.put("message", cause.getMessage());
            map.put("cause", causeMap);
        }
        if (false == Test.isEmpty(th.getStackTrace())) {
            Map<String, String> stack = new LinkedHashMap<>();
            for (StackTraceElement ste : th.getStackTrace()) {
                stack.put(
                        String.format("%s(%s)", ste.getMethodName(), ste.getLineNumber()),
                        ste.getClassName());

                // Add a human readable reason for the exception
                if (false == map.containsKey("reason")) {
                    map.put(
                            "reason",
                            String.format(
                                    "%s method of %s has failed at line %d",
                                    ste.getMethodName(),
                                    ste.getClassName(),
                                    ste.getLineNumber())
                    );
                }
            }
            map.put("stack", stack);
        }

        return map;
    }

    /**
     * This method should be able to convert any of this sources to String:
     * <ul>
     *     <li>BufferedReader</li>
     *     <li>CharArrayReader</li>
     *     <li>CharBuffer</li>
     *     <li>FileReader</li>
     *     <li>FilterReader</li>
     *     <li>InputStreamReader</li>
     *     <li>LineNumberReader</li>
     *     <li>PipedReader</li>
     *     <li>PushBackReader</li>
     *     <li>Reader</li>
     *     <li>StringReader</li>
     * </ul>
     * @param source
     * @return
     */
    public static String toString(Readable source)
    {
        return new Scanner(source).useDelimiter("\\A").next();
    }

    public String inputStreamToString(InputStream is) throws IOException
    {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }

        return sb.toString();
    }
}

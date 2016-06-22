package com.github.apycazo.voodoo.core;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;
import org.junit.Test;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Andres Picazo
 */
public class ConvertersTest
{

    @org.junit.Test
    public void testGetMapper() throws Exception
    {
        ObjectMapper mapper = Converters.getMapper();
        Assert.assertNotNull("Mapper returned by " + Converters.class.getCanonicalName() + " is null", mapper);
    }

    @Test
    public void testObjectToJsonString() throws Exception
    {
        String source;
        Map<String, Object> map;
        TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>()
        {
        };

        // Double quote is standard
        source = "{\"result\":\"success\", \"id\":1, \"exception\": false}";
        map = Converters.getMapper().readValue(source, typeRef);
        Assert.assertNotNull("Parsed map is null", map);
        Assert.assertEquals("success", map.get("result"));
        Assert.assertEquals(1, map.get("id"));
        Assert.assertEquals(false, map.get("exception"));

        // Single quote must be valid
        source = "{'result':'success', 'id':1, 'exception': false}";
        map = Converters.getMapper().readValue(source, typeRef);
        Assert.assertNotNull("Parsed map is null", map);
        Assert.assertEquals("success", map.get("result"));
        Assert.assertEquals(1, map.get("id"));
        Assert.assertEquals(false, map.get("exception"));
    }

    @Test
    public void testToString()
    {
        String expected = "test";
        StringReader sr = new StringReader(expected);
        String result = Converters.toString(sr);

        Assert.assertNotNull("Returned value is null", result);
        Assert.assertEquals(expected, result);

    }
}
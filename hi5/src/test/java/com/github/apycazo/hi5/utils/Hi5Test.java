package com.github.apycazo.hi5.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by manager on 30/07/15.
 */
public class Hi5Test {

    @Test
    public void testStringValueOneArgument() throws Exception {

        Assert.assertNotNull(Hi5.stringValue(null));
        Assert.assertEquals("test", Hi5.stringValue("test"));
    }

    @Test
    public void testStringValueTwoArguments() throws Exception {

        final String defaultValue = "default";
        Assert.assertNotNull(Hi5.stringValue(null, defaultValue));
        Assert.assertEquals(defaultValue, Hi5.stringValue(null, defaultValue));
    }

    @Test
    public void testText() throws Exception {

        final String expected = "Value 1 and value two : true";
        String format = "Value {} and {}value {} : {}";

        String result = Hi5.text(format, 1, null, "two", true);
        Assert.assertEquals(expected, result);

    }

    @Test
    public void testSizeOf () {

        final String content = "X";

        String [] nullArray = null;
        Collection<String> nullCollection = null;

        String [] validArray = new String [] { content };
        Collection<String> validCollection = new LinkedList<>();
        validCollection.add( content );

        Assert.assertEquals(0, Hi5.sizeOf(nullArray));
        Assert.assertEquals(1, Hi5.sizeOf(validArray));

        Assert.assertEquals(0, Hi5.sizeOf(nullCollection));
        Assert.assertEquals(1, Hi5.sizeOf(validCollection));
    }

    @Test
    public void testGetIterator () {

        Collection<String> nullCollection = null;
        Collection<String> validCollection = new LinkedList<>();

        Iterator<String> it;

        it = Hi5.getIterator(validCollection);
        Assert.assertNotNull(it);

        it = Hi5.getIterator(nullCollection);
        Assert.assertNotNull(it);
    }
}
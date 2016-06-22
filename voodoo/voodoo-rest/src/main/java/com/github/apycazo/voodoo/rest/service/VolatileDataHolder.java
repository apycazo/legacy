package com.github.apycazo.voodoo.rest.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

/**
 * @author Andres Picazo
 */
@Service("voodoo-rest:volatile-data-holder")
public class VolatileDataHolder
{

    private Map<String, Object> map = new WeakHashMap<>();

    public synchronized String save(Object obj)
    {
        String key = UUID.randomUUID().toString();
        map.put(key, obj);
        return key;
    }

    public synchronized String save(String key, Object obj)
    {
        map.put(key, obj);
        return key;
    }

    public synchronized Object read(String key)
    {
        return map.get(key);
    }

    public synchronized boolean containsKey(String key)
    {
        return map.containsKey(key);
    }

    public synchronized Object remove (String key)
    {
        Object obj = map.get(key);
        map.remove(key);
        return obj;
    }

    public synchronized int clear ()
    {
        int count = map.size();
        map.clear();
        return count;

    }

}

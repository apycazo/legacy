package com.github.apycazo.hi5.optional.directory;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Andres Picazo
 */
public class DirectoryService {

    private Map<String, DirectoryEntry> resources;

    public DirectoryService () {

        resources = new ConcurrentHashMap<>();
    }

    public DirectoryEntry getResource (String id) {

        return resources.get(id);
    }

    public List<DirectoryEntry> getResourcesByName (String name) {

        List<DirectoryEntry> results = new LinkedList<>();
        Iterator<DirectoryEntry> it = resources.values().iterator();
        while (it.hasNext()) {
            DirectoryEntry re = it.next();
            if (re.getName().equals(name)) {
                results.add(re);
            }
        }
        return results;
    }

    public List<DirectoryEntry> getResourcesByGroupId (String groupId) {

        List<DirectoryEntry> results = new LinkedList<>();
        Iterator<DirectoryEntry> it = resources.values().iterator();
        while (it.hasNext()) {
            DirectoryEntry re = it.next();
            if (re.getGroupId().equals(groupId)) {
                results.add(re);
            }
        }
        return results;
    }

    public void putResourceEntry (DirectoryEntry re) {

        resources.put(re.getId(), re);
    }

    public void removeResourceEntryById (String id) {

        resources.remove(id);
    }

    public void removeResourceEntryByGroupId (String groupId) {

        Iterator<DirectoryEntry> it = resources.values().iterator();
        while (it.hasNext()) {
            DirectoryEntry re = it.next();
            if (re.getGroupId().equals(groupId)) {
                it.remove();
            }
        }
    }
    public void removeResourceEntryByName (String name) {

        Iterator<DirectoryEntry> it = resources.values().iterator();
        while (it.hasNext()) {
            DirectoryEntry re = it.next();
            if (re.getName().equals(name)) {
                it.remove();
            }
        }
    }
}

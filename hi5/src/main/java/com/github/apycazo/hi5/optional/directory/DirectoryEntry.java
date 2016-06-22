package com.github.apycazo.hi5.optional.directory;

import com.github.apycazo.hi5.shared.interfaces.GroupIdentifiable;
import lombok.Data;

/**
 * @author Andres Picazo
 */
@Data
public class DirectoryEntry implements GroupIdentifiable<String> {

    public static final String DEFAULT_GROUP = "DEFAULT";

    private String id, groupId;
    private String name;
    private String locator;
    private String heartbeat;

    @Override
    public String getId() {

        return id;
    }

    @Override
    public String getGroupId() {

        return groupId;
    }
}

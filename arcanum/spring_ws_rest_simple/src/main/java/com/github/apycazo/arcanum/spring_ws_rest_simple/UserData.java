

package com.github.apycazo.arcanum.spring_ws_rest_simple;

import java.util.HashMap;
import java.util.Set;
import org.springframework.stereotype.Component;

/**
 * Simple data manager store mock
 * @author Andres Picazo
 */


@Component("data")
public class UserData {
    
    private final HashMap<Integer, User> users;
    private int nextId;
    public static final String defaultPermisions = "R";
     
    public UserData () {
        users = new HashMap();
        nextId = 0;
    }
    
    public int createUser (String name) {
        User user = new User(name, nextId++, defaultPermisions);
        users.put(user.getId(), user);
        return user.getId();
    }
    
    public User getUser (Integer id) {
        return users.get(id);
    }
    
    public User updateUser(Integer id, String field, String value) {
        User user = users.get(id);
        if (user == null) return null;
        switch (field) {
            case "name":
                user.setName(value);
                break;
            case "permisions":
                user.setPermisions(value);
                break;
        }
        return user;
    }
    
    public int deleteUser (Integer id) {
        if (users.containsKey(id)) {
            users.remove(id);
            return id;
        }
        else return -1;
    }
    
    // An extra method, to list all
    public String getAllUsersAsString () {
        StringBuilder sb = new StringBuilder("Users: ");
        sb.append(users.size()).append("\n");
        Set<Integer> keys = users.keySet();
        for (Integer key : keys) {
            String value = users.get(key).toString();
            sb.append("\t").append(value).append("\n");
        }
        
        return sb.toString();
    }
    
    public Integer getUserCount () {
        return users.size();
    }    
    
    // Only for easing testing
    public void reset () {
        users.clear();
        nextId = 0;
    }
}

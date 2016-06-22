
package com.github.apycazo.arcanum.spring_ws_rest_simple;

import java.util.StringTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Andres Picazo
 */

@Service
@RequestMapping(value = "/users", headers="Accept=*/*")
public class SimpleService {
    
    @Autowired
    private UserData data;
    
    private final String version = "0.1";
  
    @RequestMapping(value = "/version", method = RequestMethod.GET)
    public @ResponseBody String getVersion () {
        return this.version;
    }
    
    // CRUD Operation: CREATE
    @RequestMapping(value = "", method = RequestMethod.POST, consumes="text/plain")
    public ResponseEntity<String> createUser (@RequestBody String name) {
        System.out.println("Trying to create user with name : " + name);
        Integer userid = data.createUser(name);
        return new ResponseEntity(userid.toString(), HttpStatus.CREATED);
    }
    
    // CRUD Operation: READ
    @RequestMapping(value = "/{userid}", method = RequestMethod.GET)
    public ResponseEntity<String> readUser (@PathVariable Integer userid) {
        User user = data.getUser(userid);
        if (user == null) {
            return new ResponseEntity("No user found", HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity(user.toString(), HttpStatus.OK);
        }
    }
    
    // CRUD Operation: UPDATE    
    @RequestMapping(value = "/{userid}", method = RequestMethod.PUT)
    public @ResponseBody String updateUser (@PathVariable Integer userid, @RequestBody String body) {
        StringTokenizer st = new StringTokenizer(body.trim(),":");
        String param = st.nextToken().trim();
        String value = st.nextToken().trim();
        User user = data.getUser(userid);
        if (user == null) return "-1";
        else {
            user = data.updateUser(userid, param, value);
            return ""+user.getId();
        }
    }
    
    // CRUD Operation: DELETE
    @RequestMapping(value = "/{userid}", method = RequestMethod.DELETE)
    public @ResponseBody String deleteUser (@PathVariable Integer userid) {
        User user = data.getUser(userid);
        if (user == null) return "-1";
        else {
            Integer res = data.deleteUser(userid);
            return res.toString();
        }
    }
    
    // Some extra methods
    
    // Extra : List all
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody String listAllUsers () {
        return data.getAllUsersAsString();
    }
    
    @RequestMapping(value = "/{userid}/{parameter}", method = RequestMethod.GET)
    public ResponseEntity<String> getUserParameter (@PathVariable Integer userid, @PathVariable String parameter) {
        User user = data.getUser(userid);
        if (user == null) {
            return new ResponseEntity("User ID not found: " + userid, HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity(user.getParameter(parameter), HttpStatus.OK);
        }
    }
    
    @RequestMapping (value = "/count", method = RequestMethod.GET, produces="text/plain")
    public ResponseEntity<String> getUserCount () {
        return new ResponseEntity(""+data.getUserCount(), HttpStatus.OK);
    }
    
    // Only to help testing
    public void reset () {
        data.reset();
    }
    
}

package com.github.apycazo.arcanum.struts_basic_xml;

import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;

/**
 *
 * @author Andres Picazo
 */

public class IteratedModelAction extends ActionSupport {

    private ArrayList<String> users;
    private ArrayList<UserDomain> userdomains;

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    public ArrayList<UserDomain> getUserdomains() {
        return userdomains;
    }

    public void setUserdomains(ArrayList<UserDomain> userdomains) {
        this.userdomains = userdomains;
    }
    
    // Required, put struts logic here (and return where to redirect)
    @Override
    public String execute() {
        
        users = new ArrayList();
        users.add("Alfred Hitchcock");
        users.add("Stanley Kubrick");
        users.add("Martin Scorsese");
        users.add("Steven Spielberg");
        users.add("Charlie Chaplin");
        users.add("Clint Eastwood");
        
        userdomains = new ArrayList();
        for (String username : users) {
            UserDomain ud = new UserDomain();
            ud.setUsername(username);
            ud.setUserid(username.replace(" ", "").toLowerCase());
            userdomains.add(ud);
        }
        
        // Just tell which page should load (see corresponding action in struts.cfg)
        return "success";
    }
    
}

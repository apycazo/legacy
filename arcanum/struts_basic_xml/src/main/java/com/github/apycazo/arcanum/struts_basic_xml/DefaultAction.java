package com.github.apycazo.arcanum.struts_basic_xml;

/**
 *
 * @author Andres Picazo
 */

public class DefaultAction {

    private String username;
    
    public DefaultAction () {
        
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Required, put struts logic here (and return where to redirect)
    public String execute() {
        this.username = "John";
        // Just tell which page should load (see corresponding action in struts.cfg)
        return "redirect_home";
    }
}

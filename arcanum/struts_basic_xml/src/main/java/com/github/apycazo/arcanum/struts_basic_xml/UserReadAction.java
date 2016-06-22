
package com.github.apycazo.arcanum.struts_basic_xml;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 *
 * @author Andres Picazo
 */

public class UserReadAction extends ActionSupport implements ModelDriven {

    private final UserDomain userdomain = new UserDomain();
    
    // Required, put struts logic here (and return where to redirect)
    @Override
    public String execute() {
        // Just tell which page should load (see corresponding action in struts.cfg)
        return "success";
    }
    
    @Override
    public Object getModel() {
        return userdomain;
    }
    
}

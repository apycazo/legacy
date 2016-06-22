
package com.github.apycazo.arcanum.jsf_basic;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Andres Picazo
 */

@ManagedBean
@SessionScoped
public class UserDomain implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserid () {        
        if (name == null || name.equals("")) return "";
        else return "id_" + name.replace(" ", "").toLowerCase().trim();
    }
    
}

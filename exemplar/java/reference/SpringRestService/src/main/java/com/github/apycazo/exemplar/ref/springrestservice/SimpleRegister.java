
package com.github.apycazo.exemplar.ref.springrestservice;

import java.util.ArrayList;

/**
 *
 * @author Andres Picazo
 */

public class SimpleRegister {
    public String id;
    public ArrayList<String> names;
    
    public SimpleRegister () {
        id = "sample";
        names = new ArrayList();
        names.add("John");
        names.add("Daniel");
    } 
    
    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"id\":").append(this.id).append(",\"names\":");
        if (!names.isEmpty()) {
            sb.append("[");
            for (String s : names) sb.append(s).append(" ");
            sb.append("]");
        }
        sb.append("}");
        return sb.toString();
    }
}

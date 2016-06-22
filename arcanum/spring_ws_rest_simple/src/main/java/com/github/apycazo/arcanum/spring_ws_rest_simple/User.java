package com.github.apycazo.arcanum.spring_ws_rest_simple;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Very simple user data model
 *
 * @author Andres Picazo
 */
public class User {

    public String name;
    private final int id;
    public Date creationTime, lastUpdate;
    public String permisions;
    private final SimpleDateFormat dateFormat;
    

    public User(String name, int id, String permisions) {
        this.name = name;
        this.id = id;
        this.permisions = permisions;
        this.creationTime = new Date();
        this.lastUpdate = new Date();
        dateFormat = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss z");
    }

    public int getId() {
        return this.id;
    }
    
    public String setName (String name) {
        this.name = name;
        this.lastUpdate = new Date();
        return this.name;
    }
    
    public String setPermisions (String permisions) {
        this.permisions = permisions;
        this.lastUpdate = new Date();
        return this.permisions;
    }
    
    private String date2String (Date date) {
        return dateFormat.format(date);
    }
    
    public String getParameter (String parameter) {
        String response = "";
        switch (parameter) {
            case "permisions":
                response =  this.permisions;
                break;
            case "name":
                response = this.name;
                break;
        }
        return response;
    }
    
    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder("[");
        sb.append(this.id)
                .append("] Name:").append(this.name)
                .append(" Permisions: ").append(this.permisions)
                .append(" Created: ").append(date2String(this.creationTime))
                .append(" Last modified: ").append(date2String(this.lastUpdate));
        return sb.toString();
                
    }
    
}

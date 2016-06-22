
package com.github.apycazo.quinielator2.domain.model;

public class ConfigRequest {

    String request;

    public ConfigRequest() {
        this.request = "unset";
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getRequest() {
        return this.request;
    }
    
    @Override
    public String toString () {
        return String.format("Request : %s", this.request);
    }
}

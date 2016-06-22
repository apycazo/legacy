
package com.github.apycazo.arcanum.spring_mvc.homepage.domain;

/**
 *
 * @author Andres Picazo
 */
public class HomePagePostRequest {
    
    private String username;
    private String text;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
}

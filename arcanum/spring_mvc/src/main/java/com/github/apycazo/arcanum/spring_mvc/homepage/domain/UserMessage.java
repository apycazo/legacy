
package com.github.apycazo.arcanum.spring_mvc.homepage.domain;

/**
 *
 * @author Andres Picazo
 */

public class UserMessage {

    public String username, text;

    public UserMessage(String username, String text) {
        this.username = username;
        this.text = text;
    }

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
    
    
    
    @Override
    public String toString () {
        return String.format("User: %s, Message: %s", username, text);
    }

}

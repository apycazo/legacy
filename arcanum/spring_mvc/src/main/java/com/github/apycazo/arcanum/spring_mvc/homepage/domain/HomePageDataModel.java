
package com.github.apycazo.arcanum.spring_mvc.homepage.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andres Picazo
 */

public class HomePageDataModel {    
    
    private ArrayList<UserMessage> messages;
    private String selectedUser;
    private String selectedUserText;
    
    public HomePageDataModel () {
        messages = new ArrayList();
    }
    
    public List<UserMessage> getMessages () {
        return this.messages;
    }
    
    public void setMessages (ArrayList<UserMessage> messages) {
        this.messages = messages;
    }
    
    public void addMessage (String user, String text) {
        UserMessage um = new UserMessage(user,text);
        this.messages.add(um);
    }
    
    public int getMessageCount () {
        return this.messages.size();
    }
    
    public String getMessageFromUser (String user) {
        String text = "";
        int i = 0;
        while (text.isEmpty() && i < this.messages.size()) {
           if (messages.get(i).username.equals(user)) {
               text = messages.get(i).text;
           }
           else i++;
        }
        return text;
    }

    public String getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(String selectedUser) {
        this.selectedUser = selectedUser;
    }
    
    public String getSelectedUserText () {
        return this.selectedUserText;
    }
    
    public void setSelectedUserText (String text) {
        this.selectedUserText = text;
    }
    
    public void selectUser (String user) {
        setSelectedUser(user);
        setSelectedUserText(getMessageFromUser(user));
    }

}


package com.github.apycazo.arcanum.spring_mvc.homepage.controller;

import com.github.apycazo.arcanum.spring_mvc.homepage.domain.HomePageDataModel;
import com.github.apycazo.arcanum.spring_mvc.homepage.domain.HomePagePostRequest;
import com.github.apycazo.arcanum.spring_mvc.homepage.domain.UserMessage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * This controller will handle view described at /WEB-INF/views/main.jsp.
 * (Will be called as /home, actually)
 * @author Andres Picazo
 */

@Controller ("homePageController")
@RequestMapping (value="/home")
public class HomePageController {
    
    private HomePageDataModel _model;
    private String title;
    
    // =========================================================================
    // Page loads
    // =========================================================================
    @RequestMapping(method = RequestMethod.GET)
    public String initForm(ModelMap model) {
        // return form view (actually /WEB-INF/views/main.jsp, check spring-cfg)
        return "main";
    }
    
    // =========================================================================
    // Submit handler for HomePagePostRequest
    // =========================================================================
    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(
            @ModelAttribute("HomePagePostRequest") HomePagePostRequest model,
            BindingResult result, SessionStatus status) {
        
        // Update model, according to the selected username
        if (!model.getUsername().isEmpty()) {
            _model.selectUser(model.getUsername());
        }
        
        status.setComplete();
        
        return "main";
    }
    
    // =========================================================================
    // Handler to test exception
    // =========================================================================
    @RequestMapping(value = "/fail", method = RequestMethod.GET)
    public ModelAndView testExceptionHandler () throws Exception {
        throw new Exception("Forced test exception from main.jsp");
    }
    
    // =========================================================================
    // For ajax requests (test)
    // =========================================================================
    @RequestMapping(value = "/ajax/{user}", method = RequestMethod.GET)
    public @ResponseBody String getUserMessage (@PathVariable String user) {
        return _model.getMessageFromUser(user);
    }
    // =========================================================================
    // Model attribute initializers
    // =========================================================================
    @ModelAttribute("userlist")
    public List<String> getUserList () {
        ArrayList<String> users = new ArrayList();        
        List<UserMessage> messages = _model == null ? getDataModel().getMessages() : _model.getMessages();
        Iterator<UserMessage> it = messages.iterator();
        while (it.hasNext()) {
            users.add(it.next().username);
        }
        return users;
    }
    
    @ModelAttribute("HomePageDataModel")
    public HomePageDataModel getDataModel () {
        _model = new HomePageDataModel();
        _model.addMessage("John", "John's message");
        _model.addMessage("Paul", "Paul's message");
        _model.addMessage("Daniel", "Daniel's message");
        return _model;
    }
    
    @ModelAttribute("Title")
    public String getTitle () {
        this.title = "Spring MVC Reference Home Page";
        return this.title;
    }
    
    @ModelAttribute("HomePagePostRequest")
    public HomePagePostRequest getPostRequestModel () {
        return new HomePagePostRequest();
    }
    
}


package com.github.apycazo.arcanum.spring_mvc.about.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Andres Picazo
 */

@Controller ("aboutPageController")
@RequestMapping (value="/about")
public class AboutController {
    
    private String message;
    
    /**
     Uses model and view instead of a String
     * @param request
     * @param response
     * @return ModelAndView
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        this.message = "Sample additional page to demostrate navigation.";
        ModelAndView mav = new ModelAndView("about","message",this.message);
        return mav;
    }
    
}

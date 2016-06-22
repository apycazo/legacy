
package com.github.apycazo.quinielator2.web;

import com.github.apycazo.quinielator2.domain.model.ConfigRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

/*
 * http://docs.spring.io/spring/docs/3.0.0.RELEASE/spring-framework-reference/htmlsingle/spring-framework-reference.html#mvc-ann-requestmapping-advanced
 */

@Controller
@RequestMapping(value="/config/*")
public class ConfigController {
    
    @RequestMapping(method = RequestMethod.GET, value="/config.htm")
    public String initForm(ModelMap model) {

        System.out.println("Opening config view");
        model.addAttribute("config", new ConfigRequest());

        return "config";
    }
    
    @RequestMapping(method = RequestMethod.GET, value="/config.htm", params="update=true")
    public String updateDB (@RequestParam("update") Boolean updateDB) {
        System.out.println("Same htm update request: " + updateDB);
        // Freeze page for a few seconds
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
        }
        return "config";
    }
    
    @RequestMapping(method = RequestMethod.GET, value="/config.updatedb.htm")
    public String update (@RequestParam("update") Boolean updateDB) {
        System.out.println("Update!: " + updateDB);
        return "config";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String processFormSubmit(
            @ModelAttribute("config") ConfigRequest cr,
            @RequestParam("update") Boolean updateDB,
            BindingResult result, SessionStatus status) {
        System.out.println("Requested config: " + cr.toString());
        System.out.println("Update : " + updateDB);
        return "config";
    }

}

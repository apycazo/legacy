package nox.apycazo.springRest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/service")
public class RestService {
    
    // Note :: 'SpringRest' is due to the application context path (see project properties).
    // For testing :: http://localhost:8080/SpringRest/service/echo/works!

    @RequestMapping(value = "/echo/{name}", method = RequestMethod.GET)
    public @ResponseBody String echo (@PathVariable String name) {
        System.out.println("Running method 'echo'");
        return "Echo :: " + name;
    }
    
    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    public @ResponseBody String hi () {
        System.out.println("Running method 'hi'");
        return "Howdy!";
    }
}

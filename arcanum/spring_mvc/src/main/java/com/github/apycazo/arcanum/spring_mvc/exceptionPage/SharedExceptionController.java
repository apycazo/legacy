
package com.github.apycazo.arcanum.spring_mvc.exceptionPage;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * This controller will be shared with all others (thanks to @ControllerAdvice).
 * (Applies to all @RequestMapping methods).
 * @author Andres Picazo
 */

@ControllerAdvice
public class SharedExceptionController {
    
    @ExceptionHandler(Exception.class)
    public ModelAndView exception(Exception e) {
         
        ModelAndView mav = new ModelAndView("exception");
        mav.addObject("name", e.getClass().getSimpleName());
        mav.addObject("message", e.getMessage());
 
        return mav;
    }
    
}

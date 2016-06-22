
package com.github.apycazo.quinielator2.web;

import com.github.apycazo.quinielator2.domain.model.PredictionRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping(value="/main.htm")
public class MainController {
    
    /*
    @RequestMapping(value="/main.htm")
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//    	String now = (new Date()).toString();
//        logger.info("Returning hello view with " + now);
//
//        Map<String, Object> myModel = new HashMap<String, Object>();
//        myModel.put("now", now);
//        myModel.put("products", this.productManager.getProducts());

//        return new ModelAndView("hello", "model", myModel);
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("request",new PredictionRequest());
        return new ModelAndView("main", "model", model);
    }
    */
    
    @RequestMapping(method = RequestMethod.GET)
    public String initForm(ModelMap model) {

        PredictionRequest pr = new PredictionRequest();
        //command object
        model.addAttribute("predictionRequest", pr);
        
        Iterator<String> it = model.keySet().iterator();
        while (it.hasNext()) {
            String next = it.next();
            System.out.println("Model entry : " + next);
        }

        //return form view
        return "main";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(
            @ModelAttribute("predictionRequest") PredictionRequest pr,
            BindingResult result, SessionStatus status) {

        System.out.println("predictionRequest :: " + pr.toString());
        pr.setStringValue(pr.toString());
        //reqStr = pr.toString();
        return "main";
    }
    /*
    @RequestMapping(method = RequestMethod.POST)
    public String onSubmit(PredictionRequest request, BindingResult result) {
        
        System.out.println(request.toString());
        
        if (result.hasErrors()) {
            return "main";
        }
        return "main";
//        return "redirect:/hello.htm";
    }
    */

    
    @ModelAttribute("yearList")
    public List<Integer> populateYearList () {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 2000; i < 2013; i++) {
            list.add(i+1);
        }
        return list;
    }
    
    @ModelAttribute("dayList")
    public List<Integer> populateDayList () {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 38; i++) {
            list.add(i+1);
        }
        return list;
    }
    
    @ModelAttribute("predictorList")
    public List<String> populatePredictorList () {
        List<String> list = new ArrayList<String>();
        list.add("Default");
        list.add("Naive-1");
        list.add("Naive-2");
        return list;
    }
    
}

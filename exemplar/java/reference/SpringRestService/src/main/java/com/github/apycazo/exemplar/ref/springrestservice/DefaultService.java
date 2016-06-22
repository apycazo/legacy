

package com.github.apycazo.exemplar.ref.springrestservice;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Andres Picazo
 */

@Service
@RequestMapping("/")
public class DefaultService {
    
    @RequestMapping(method = RequestMethod.GET, produces = "text/html")
    public @ResponseBody String getDefault () {
        return "<h2>Spring Rest Service Reference loaded</h2>";
    }
}


package com.github.apycazo.exemplar.ref.springrestservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Andres Picazo
 */

@Service
@RequestMapping(value = "/simple-service", headers="Accept=*/*")
public class SimpleService {
    
    // Wiring the object builder, only as an example
    @Autowired
    private SampleObjectBuilder builder;
    
    public static final String defaultRootResponse = "<h2>Simple rest service</h2><hr>Version : 0.1";
    public static final String getEchoDefaultValue = "<empty>";
    public static final String contentFoundHeader = "<h2>Content found</h2><hr><p>";
    public static final String contentFoundTail = "</p>";
    public static final String noContentFound = "<h2>No content found</h2>";
    
    // At root, show version number
    @RequestMapping(value = "", method = RequestMethod.GET)
    public @ResponseBody String getDefault() {
        return defaultRootResponse;
    }
    
    // Another easy one, return the last path value
    // http://localhost:8080/SpringRestService/simple-service/get-echo/test
    @RequestMapping(value = "/get-echo/{text}", method = RequestMethod.GET)
    public @ResponseBody String getEchoVar (@PathVariable String text) {
        return text;
    }
    
    // Same echo, using a requested parameter
    // http://localhost:8080/SpringRestService/simple-service/get-echo?text=test
    @RequestMapping(value = "/get-echo", method = RequestMethod.GET, produces = "text/plain")
    public @ResponseBody String getEchoPath (
            @RequestParam(value = "text", required = false, defaultValue = getEchoDefaultValue) String text
    ) {
        return text;
    }
    
    // Get some content
    @RequestMapping(value = "/post-text", method = RequestMethod.POST, produces = "text/html")
    public @ResponseBody String postText (@RequestBody String content) {
        if (content != null && !content.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append(contentFoundHeader).append(content).append(contentFoundTail);
            return sb.toString();
        }
        else return noContentFound;
    }
    
    // Get some content
    @RequestMapping(value = "/post-json", method = RequestMethod.POST, consumes = "application/json", produces="text/html")
    public @ResponseBody String postJson (@RequestBody String content) {
        if (content != null && !content.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append(contentFoundHeader).append(content).append(contentFoundTail);
            return sb.toString();
        }
        else return noContentFound;
    }
    
    @RequestMapping(value = "/get-json", method = RequestMethod.GET, produces = "application/json")    
    public @ResponseBody SimpleRegister getJson () {
        return builder.createDefaultRegister();
    }
    
    // Secured service
    @RequestMapping(value = "/secure/get-json", produces="application/json", method = RequestMethod.GET)
    public @ResponseBody SimpleRegister getSimpleRegisterForAuth () {
        return builder.createSecuredRegister();
    }
}

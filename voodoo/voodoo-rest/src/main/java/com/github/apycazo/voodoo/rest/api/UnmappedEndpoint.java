package com.github.apycazo.voodoo.rest.api;

import com.github.apycazo.voodoo.core.Dates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
// Static imports
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Andres Picazo
 */
@RestController("voodoo.rest:catch-unmapped-api")
@CrossOrigin(origins = "*")
@ConditionalOnProperty(prefix = "voodoo.rest.api.unmapped", name = "enable", matchIfMissing = false)
public class UnmappedEndpoint
{
    @Autowired(required = false)
    protected HttpServletRequest httpRequest;

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @RequestMapping(method = {GET,POST,PUT,DELETE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,Object> unmappedHandler(@RequestBody(required = false) String requestBody)
    {

        Map<String,Object> map = new LinkedHashMap<>();

        if (httpRequest != null) {
            map.put("description", "Handler not found for URI " + httpRequest.getRequestURI());
        }
        else {
            map.put("description", "Handler not found for this request");
        }

        if (requestBody != null) {
            map.put("received-content", requestBody);
        }

        map.put("epoch", Dates.epoch());
        map.put("success", Boolean.FALSE);

        return map;
    }
}

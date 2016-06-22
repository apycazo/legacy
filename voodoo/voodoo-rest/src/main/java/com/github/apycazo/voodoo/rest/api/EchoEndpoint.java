package com.github.apycazo.voodoo.rest.api;

import com.github.apycazo.voodoo.rest.etc.VooDooRestConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andres Picazo
 */
@RestController("voodoo-rest:echo-api")
@RequestMapping(value = "${voodoo.rest.api.echo.mapping:/echo}")
@ConditionalOnProperty(prefix = "voodoo.rest", name = "api.echo.enable", matchIfMissing = false)
public class EchoEndpoint
{

    protected final Logger logger = LoggerFactory.getLogger(EchoEndpoint.class);

    @Autowired
    protected HttpServletRequest request;

    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.POST})
    public String echoPutAndPost(@RequestBody String content)
    {
        if (request != null) logger.info(VooDooRestConstants.MARKER, "Received request", request);
        return content;
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.DELETE})
    public String echoGetAndDelete(@RequestParam(value = "content", required = true) String content)
    {
        if (request != null) logger.info(VooDooRestConstants.MARKER, "Received request", request);
        return content;
    }
}

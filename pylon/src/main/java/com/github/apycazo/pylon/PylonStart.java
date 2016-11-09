package com.github.apycazo.pylon;

import com.github.apycazo.pylon.core.data.Outcome;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Andres Picazo
 */
@Slf4j
@SpringBootApplication
public class PylonStart
{
    public static void main(String[] args)
    {
        SpringApplication app = new SpringApplication(PylonStart.class);
        app.setAdditionalProfiles("local","config","dev");
        app.run(args);
    }

    @RestController
    @RequestMapping(value = "beat", produces = MediaType.APPLICATION_JSON_VALUE)
    public static class RestInfo
    {
        @GetMapping
        public Outcome beat (HttpServletRequest req)
        {
            Enumeration<String> headers = req.getHeaderNames();
            while (headers.hasMoreElements()) {
                String header = headers.nextElement();
                log.info("Got header: {}={}", header, req.getHeader(header));
            }
            return Outcome.success();
        }
    }

    @RestController
    @RequestMapping(value = "beat-echo", produces = MediaType.APPLICATION_JSON_VALUE)
    public static class RestInfoEcho
    {
        @Autowired
        private RestTemplate rt;

        @GetMapping
        public Outcome beatEcho ()
        {
            String result = rt.getForObject("http://localhost:8080/beat", String.class);
            return Outcome.success(result);
        }
    }
}

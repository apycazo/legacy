package com.github.apycazo.pylon;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "test")
public class Tester
{
    ObjectMapper mapper = new ObjectMapper();

    @PostMapping(value = "map", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void receive (@RequestBody LinkedHashMap<String,Object> content)
    {
        try {
            String json = mapper.writeValueAsString(content);
            log.info("Received json:\n{}",json);
            log.info("params: {}", content.get("data"));
        }
        catch (Exception e) {
            log.error("Unable to map", e);
        }
    }

    @PostMapping(value = "string", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void receiveAsString (@RequestBody String content) throws Exception
    {
        log.info("Received:\n{}", Optional.ofNullable(content).orElse("null"));

        Data data = mapper.readValue(content, Data.class);
    }
}

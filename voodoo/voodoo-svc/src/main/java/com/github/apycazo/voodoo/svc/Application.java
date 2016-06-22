package com.github.apycazo.voodoo.svc;

import com.github.apycazo.voodoo.svc.x.TestLambda;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Andres Picazo
 */
@SpringBootApplication
public class Application
{
    private static Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args)
    {
        log.info("Starting Spring Boot using loader: {}", Application.class.getCanonicalName());
        SpringApplication.run(Application.class, args);
        TestLambda.runTest();
    }

    /*
    @Bean
    public PropertySourcesPlaceholderConfigurer yamlPropertyLoader ()
    {
        return YamlPropertyLoader.properties(new String[] {
            "application.yml"
        });

    }*/

}

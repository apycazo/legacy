package com.github.apycazo.voodoo.rest.etc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;

/**
 * @author Andres Picazo
 */
@Configuration
@ComponentScan(value = {"com.github.apycazo.voodoo.rest"})
public class VoodooRestAutoConfig
{

    protected static final Logger log = LoggerFactory.getLogger(VoodooRestAutoConfig.class);

    @PostConstruct
    protected void $init()
    {
        log.info("Using VoodooRestAutoConfig");
    }

    /**
     * Global CORS mapping configuration.
     *
     * @return WebMvcConfigurer with the mapping allowing all origins
     */
    @Bean
    @ConditionalOnProperty(prefix = "voodoo.rest", name = "cors", matchIfMissing = true)
    public WebMvcConfigurer corsConfigurer()
    {
        return new WebMvcConfigurerAdapter()
        {
            @Override
            public void addCorsMappings(CorsRegistry registry)
            {
                registry.addMapping("/").allowedOrigins("*");
            }
        };
    }
}

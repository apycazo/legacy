package com.github.apycazo.voodoo.svc.etc;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Andres Picazo
 */
@Configuration
@ConfigurationProperties(prefix = "voodoo", ignoreUnknownFields = true)
public class Settings
{
    private String[] debugOptions = new String[0];

    public String[] getDebugOptions()
    {
        return debugOptions;
    }

    public void setDebugOptions(String[] debugOptions)
    {
        this.debugOptions = debugOptions;
    }
}

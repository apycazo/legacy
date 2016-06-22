package com.github.apycazo.hi5.optional.directory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * @author Andres Picazo
 */
@Configuration
@ConditionalOnProperty(value = DirectoryConfig.ACTIVATION_PROPERTY, havingValue = "true", matchIfMissing = false)
public class DirectoryConfig {

    public static final String ACTIVATION_PROPERTY = "hi5.optional.directory";
    public static final String MAPPING_PATH = "/opt/directory";
}

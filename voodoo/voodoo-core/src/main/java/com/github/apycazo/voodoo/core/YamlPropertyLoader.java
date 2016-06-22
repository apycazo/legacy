package com.github.apycazo.voodoo.core;

import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


/**
 * @author Andres Picazo
 */
public class YamlPropertyLoader
{
    protected static final Logger log = LoggerFactory.getLogger(YamlPropertyLoader.class);

    protected static final String defaultLocator = "classpath:application.yml";
    protected static final String markerName = "yaml-loader";
    protected static final Marker marker = MarkerFactory.getMarker(markerName);
    protected static String[] resourceLocations = new String[]{defaultLocator};

    /**
     * Returns a property source configurer.
     * @return The configurer instanced.
     */
    public static PropertySourcesPlaceholderConfigurer properties()
    {
        return properties(null);
    }

    /**
     * Returns a property source configurer. To load default properties leave
     * locations to null. To avoid loading anything use an empty array.
     * Note that only 'classpath:{filename}' locations are supported right now.
     * Default locator is 'classpath:application.yml'.
     * @param locations The locations to search for yaml files.
     * @return The configurer instanced.
     */
    public static PropertySourcesPlaceholderConfigurer properties(String[] locations)
    {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer
                = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();

        if (locations == null) {
            locations = resourceLocations;
        }

        if (locations != null) {
            List<Resource> resources = new LinkedList<>();
            for (String locator : locations) {
                if (locator != null && locator.isEmpty() == false) {
                    if (locator.startsWith("classpath:")) {
                        locator = locator.replace("classpath:", "");
                        ClassPathResource cpr = new ClassPathResource(locator);
                        if (cpr.exists()) {
                            resources.add(cpr);
                            log.info(marker, "Loaded yaml: {}", locator);
                        } else {
                            log.warn(
                                    marker,
                                    "Yaml file not found on classpath: {}",
                                    locator);
                        }
                    } else {
                        log.warn(marker,
                                "Only classpath values can be loaded: {}",
                                locator);
                    }
                }
            }
            if (resources.isEmpty() == false) {
                yaml.setResources(resources.toArray(new Resource[resources.size()]));
                try {
                    propertySourcesPlaceholderConfigurer.setProperties(yaml.getObject());
                } catch (Exception e) {
                    log.warn(
                            marker,
                            "Unable to load yaml properties ({}:{})",
                            e.getClass().getSimpleName(),
                            e.getMessage());
                }
            }
        }

        return propertySourcesPlaceholderConfigurer;
    }


}

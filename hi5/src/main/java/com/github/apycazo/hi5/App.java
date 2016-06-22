package com.github.apycazo.hi5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.context.ApplicationContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.util.Assert;

/**
 *
 * @author Andres Picazo
 */
@Slf4j
@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
@PropertySources(value = {
    @PropertySource(value = "classpath:hi5-defaults.properties", ignoreResourceNotFound = false),
    @PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
})
public class App {

    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(App.class, args);
        Assert.notNull(ctx, "Unable to load application context");
        String[] beanNames = ctx.getBeanDefinitionNames();
        log.info("App ready ({} beans loaded)", beanNames.length);
    }

}

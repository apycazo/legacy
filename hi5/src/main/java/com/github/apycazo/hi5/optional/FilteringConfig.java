package com.github.apycazo.hi5.optional;

import com.github.apycazo.hi5.shared.filters.LoggingFilter;
import com.github.apycazo.hi5.shared.filters.OriginFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Andres Picazo
 */
@Slf4j
@Configuration
public class FilteringConfig {

    public static final String LOAD_LOGGING_FILTER = "hi5.optional.loggingFilter";
    public static final String LOAD_ORIGIN_FILTER = "hi5.optional.originFilter";
    public static final String ALLOWED_ORIGINS = "${hi5.optional.originFilter.allowedOrigins:*}";

    @Value(ALLOWED_ORIGINS)
    protected String allowedOrigins;

    @Bean
    @ConditionalOnProperty(value = LOAD_LOGGING_FILTER, havingValue = "true", matchIfMissing = false)
    public LoggingFilter instanceLoggingFilter () {

        return new LoggingFilter();
    }

    @Bean
    @ConditionalOnProperty(value = LOAD_ORIGIN_FILTER, havingValue = "true", matchIfMissing = false)
    public OriginFilter instanceOriginFilter () {

        return new OriginFilter(allowedOrigins);
    }

}

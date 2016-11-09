package com.github.apycazo.pylon.core.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

/**
 * If enabled, this interceptor will include two additional headers on every RestTemplate request:
 * <ul>
 * <li>X-Request-Origin : Indicating the service sending the request</li>
 * <li>X-Request-Id : A randomly created id for the request</li>
 * </ul>
 * <p>
 * To disable user property: <strong>forge.outbound-interceptor.enable: false</strong>.
 *
 * @author Andres Picazo
 */
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutboundInterceptor implements ClientHttpRequestInterceptor
{
    @Autowired
    private ApplicationContext applicationContext;

    private String origin = "rest-service";
    private String requestOriginHeader = "X-Request-Origin";
    private String requestIdHeader = "X-Request-Id";

    @Override
    public ClientHttpResponse intercept(
            HttpRequest httpRequest,
            byte[] bytes,
            ClientHttpRequestExecution clientHttpRequestExecution)
            throws IOException
    {
        log.info("Intercepting headers");
        HttpHeaders headers = httpRequest.getHeaders();

        if (!headers.containsKey(requestOriginHeader)) {
            headers.add(requestOriginHeader, origin);
        }
        if (!headers.containsKey(requestIdHeader)) {
            headers.add(requestIdHeader, UUID.randomUUID().toString());
        }

        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }
}

package com.github.apycazo.hi5.shared.factories;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author Andres Picazo
 */
public class RestClientFactory {

    public RestTemplate createRestTemplate () {

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        RequestConfig config = RequestConfig.custom().build();

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(config)
                .build();

        ClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(factory);

    }

    public RestTemplate createRestTemplate (int max_total_conn, int max_per_channel, int conn_timeout) {

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(max_total_conn);
        connectionManager.setDefaultMaxPerRoute(max_per_channel);
        RequestConfig config = RequestConfig.custom().setConnectTimeout(conn_timeout).build();

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(config)
                .build();

        ClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(factory);
    }
}

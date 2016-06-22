
package com.github.apycazo.exemplar.ref.springrestservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.springframework.web.client.RestTemplate;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author Andres Picazo
 */

public class SimpleServiceTest {
    
    private RestTemplate template;
    
    @Before
    public void init () {
        template = new RestTemplate();
    }
    
    @Test
    public void echoAsPathIsWorking () {        
        String expectedResponse = "test-echo";
        ResponseEntity<String> response = template.getForEntity(
                "http://localhost:8080/SpringRestService/simple-service/get-echo/"+expectedResponse, 
                String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }
    
    @Test
    public void echoAsParamIsWorking () {        
        String expectedResponse = "test-echo";
        ResponseEntity<String> response = template.getForEntity(
                "http://localhost:8080/SpringRestService/simple-service/get-echo?text="+expectedResponse, 
                String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        
        // Now we test the default value
        response = template.getForEntity(
                "http://localhost:8080/SpringRestService/simple-service/get-echo",
                String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(SimpleService.getEchoDefaultValue, response.getBody());
    }
    
    @Test
    public void postTextIsWorking () {
        String payload = "some text";
        StringBuilder sb = new StringBuilder();
        sb.append(SimpleService.contentFoundHeader).append(payload).append(SimpleService.contentFoundTail);
        String expectedResponseOk = sb.toString();
        String expectedResponseErr = SimpleService.noContentFound;
        
        HttpEntity<String> content = new HttpEntity(payload);
        ResponseEntity<String> response = template.postForEntity(
                "http://localhost:8080/SpringRestService/simple-service/post-text", 
                content, 
                String.class);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponseOk, response.getBody());
        
        // What if we send an empty post?
        response = template.postForEntity(
                "http://localhost:8080/SpringRestService/simple-service/post-text", 
                null, 
                String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponseErr, response.getBody());
        assertEquals(true, response.getHeaders().containsKey("content-type"));
        assertEquals("text/html", response.getHeaders().get("content-type").get(0));
    }
    
    @Test
    public void postJsonIsWorking () {
        String payload = "{\"type\":\"test\"}";
        StringBuilder sb = new StringBuilder();
        sb.append(SimpleService.contentFoundHeader).append(payload).append(SimpleService.contentFoundTail);
        String expectedResponseOk = sb.toString();
        String expectedResponseErr = SimpleService.noContentFound;
        
        
        HttpEntity<String> content = new HttpEntity(payload);
        
        // ---------------------------------------------------------------------
        // This service requires headers, this should fail
        // ---------------------------------------------------------------------
        try {
            template.postForEntity(
                "http://localhost:8080/SpringRestService/simple-service/post-json", 
                content, 
                String.class);
        } catch (RestClientException e) {
            assertTrue(e.getMessage().contains("415 Unsupported Media Type"));
        }
        
        // ---------------------------------------------------------------------
        // This service requires headers, let put them in place
        // ---------------------------------------------------------------------
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        
        content = new HttpEntity(payload, headers);
        
        ResponseEntity<String> responseValid = template.postForEntity(
                "http://localhost:8080/SpringRestService/simple-service/post-json", 
                content, 
                String.class);
        
        assertEquals(HttpStatus.OK, responseValid.getStatusCode());
        assertEquals(expectedResponseOk, responseValid.getBody());
        
        // ---------------------------------------------------------------------
        // What if I send content-type, but no payload?
        // ---------------------------------------------------------------------
        content = new HttpEntity(null, headers);
        
        responseValid = template.postForEntity(
                "http://localhost:8080/SpringRestService/simple-service/post-json", 
                content, 
                String.class);
        
        assertEquals(HttpStatus.OK, responseValid.getStatusCode());
        assertEquals(expectedResponseErr, responseValid.getBody());
        
    }
    
    @Test
    public void publicGetJsonIsWorking() {
        getJsonWorks(false);
    }
    
    @Test
    public void secureGetJsonIsWorking() {
        getJsonWorks(true);
    }
    
    // GetJson has two methods, one is secured and the other is not
    // TODO: Missing equals check!!
    public void getJsonWorks (boolean secured) {        
        
        if (!secured) {
            ResponseEntity<String> response = template.getForEntity(
                    "http://localhost:8080/SpringRestService/simple-service/get-json",
                    String.class);

            assertEquals(HttpStatus.OK, response.getStatusCode());
        }
        else {
            
            // Secure, no headers
            try {
                template.getForEntity(
                    "http://localhost:8080/SpringRestService/simple-service/secure/get-json",
                    String.class);
            } catch (RestClientException e) {
                assertTrue(e.getMessage().contains("401 Unauthorized"));
            }
            
            // Secure, with headers
            HttpHeaders headers = new HttpHeaders();
            headers.add(BasicSecurityFilter.AUTH_HEADER, "user@pass");
            
            HttpEntity<String> entity = new HttpEntity(null, headers);
            
            ResponseEntity<String> response = template.exchange(
                    "http://localhost:8080/SpringRestService/simple-service/secure/get-json", 
                    HttpMethod.GET, 
                    entity, 
                    String.class);

            assertEquals(HttpStatus.OK, response.getStatusCode());
        }        
    }
    
}

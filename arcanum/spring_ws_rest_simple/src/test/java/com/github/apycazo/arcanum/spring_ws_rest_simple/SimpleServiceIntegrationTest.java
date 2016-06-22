
package com.github.apycazo.arcanum.spring_ws_rest_simple;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Some basic testing, using MockMvc.
 * Note: rest-service-servlet.xml has been moved from WEB-INF to resources to avoid
 * problems when testing with maven.
 * @author Andres Picazo
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:/spring-config/rest-service-servlet.xml"})
public class SimpleServiceIntegrationTest {

    @Autowired
    private WebApplicationContext wac;
    
    @Autowired
    private SimpleService service;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        this.service.reset();
    }
    
    @Test
    public void getCount() throws Exception {
        this.service.reset();
        mockMvc
                .perform(get("/users/count/"))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
        // Create an user
        createUser();
        // Now count should be 1
        mockMvc
                .perform(get("/users/count/"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    public void createUser() throws Exception {
        this.service.reset();
        String username = "John Doe";
        Integer userid = new Integer(-1);
        MvcResult result = mockMvc
                .perform(post("/users").contentType(MediaType.TEXT_PLAIN).content(username))
                .andExpect(status().isCreated())
                .andReturn();        
        try {
            userid = Integer.parseInt(result.getResponse().getContentAsString());
            assertTrue(userid >= 0);
        } catch (Exception e) {
            fail("Response should be an integer value: " + e.getMessage());
        }
        
        // Recover created user
        result = mockMvc.perform(get("/users/"+userid.toString()))
                .andExpect(status().isOk())
                .andReturn();
        
        assertTrue(!result.getResponse().getContentAsString().isEmpty());
    }
    
    @Test
    public void updateUser () throws Exception {
        // First, create user must be valid
        createUser();
        // Get user current permisions
        MvcResult result = mockMvc
                .perform(get("/users/0/permisions"))
                .andExpect(status().isOk())
                .andReturn();
        String currentPermisions = result.getResponse().getContentAsString();
        String newPermisions = "RW";
        System.out.println("Current permisions: " + currentPermisions);
        assertTrue(currentPermisions.equals("R"));
        assertTrue(!currentPermisions.equals(newPermisions));
        
        // Do update
        mockMvc
                .perform(put("/users/0").content("permisions:RW"))
                .andExpect(status().isOk());
        // Check result
        result = mockMvc
                .perform(get("/users/0/permisions"))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println("New permisions are: " + result.getResponse().getContentAsString());
        assertTrue(result.getResponse().getContentAsString().equals(newPermisions));
    }
    
    @Test
    public void deleteUser () throws Exception {
        createUser();
        // Get count now
        mockMvc
                .perform(get("/users/count/"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
        // Delete user 0
        mockMvc
                .perform(delete("/users/0"))
                .andExpect(status().isOk());
        // Now count should be 0
        mockMvc
                .perform(get("/users/count/"))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

}

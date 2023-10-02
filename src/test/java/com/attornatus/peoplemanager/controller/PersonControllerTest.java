package com.attornatus.peoplemanager.controller;

import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    private MockMvc mvc;
    
    @Test
    void shouldReturnPersonWithStartNameC() throws Exception {
    	mvc.perform(MockMvcRequestBuilders.get("/api/v1/persons/search?name=c")
			    .accept(MediaType.APPLICATION_JSON))
    	.andExpect(MockMvcResultMatchers.status().isOk())
    	.andExpect(MockMvcResultMatchers.jsonPath("$[0]").exists())
    	.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNotEmpty())
    	.andExpect(MockMvcResultMatchers.jsonPath("$[0].name", startsWith("C")))
    	.andExpect(MockMvcResultMatchers.jsonPath("$[0].birthDate").isNotEmpty());
    }
    
}

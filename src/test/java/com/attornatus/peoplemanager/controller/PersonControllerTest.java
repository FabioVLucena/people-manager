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

import com.attornatus.peoplemanager.dto.PersonAddressRequestDTO;
import com.attornatus.peoplemanager.dto.PersonRequestDTO;
import com.attornatus.peoplemanager.enums.MainEnum;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    
    @Test
    void shouldntCreatePersonWithInvalidParameters() throws Exception {
    	PersonRequestDTO personRequestDTO = new PersonRequestDTO();
    	personRequestDTO.setName("");
    	personRequestDTO.setBirthDateStr("5661");

    	mvc.perform(MockMvcRequestBuilders.post("/api/v1/persons")
    			   .contentType(MediaType.APPLICATION_JSON)
    			   .content(asJsonString(personRequestDTO)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldntUpdatePersonWithInvalidParameters() throws Exception {
    	Long personId = 1L;
    	
    	PersonRequestDTO personRequestDTO = new PersonRequestDTO();
    	personRequestDTO.setName("");
    	personRequestDTO.setBirthDateStr("5661");

    	mvc.perform(MockMvcRequestBuilders.put("/api/v1/persons/" + personId)
    			   .contentType(MediaType.APPLICATION_JSON)
    			   .content(asJsonString(personRequestDTO)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test
    void shouldntCreateAddressWithInvalidParameters() throws Exception {
    	Long personId = 1L;
    	
    	PersonAddressRequestDTO personAddressRequestDTO = new PersonAddressRequestDTO();
    	personAddressRequestDTO.setAddress("");
    	personAddressRequestDTO.setMain(5);

    	mvc.perform(MockMvcRequestBuilders.post("/api/v1/persons/" + personId + "/addresses")
    			   .contentType(MediaType.APPLICATION_JSON)
    			   .content(asJsonString(personAddressRequestDTO)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldReturnAllPersonAddress() throws Exception {
    	Long personId = 1L;
    	
    	mvc.perform(MockMvcRequestBuilders.get("/api/v1/persons/" + personId +"/addresses")
			    .accept(MediaType.APPLICATION_JSON))
    	.andExpect(MockMvcResultMatchers.status().isOk())
    	.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
    	.andExpect(MockMvcResultMatchers.jsonPath("$[0].mainStr").value(MainEnum.YES_STR));
    }
    
    @Test
    void shouldFindMainPersonAddress() throws Exception {
    	Long personId = 2L;
    	
    	mvc.perform(MockMvcRequestBuilders.get("/api/v1/persons/" + personId +"/addresses/main")
			    .accept(MediaType.APPLICATION_JSON))
    	.andExpect(MockMvcResultMatchers.status().isOk())
    	.andExpect(MockMvcResultMatchers.jsonPath("$").exists())
    	.andExpect(MockMvcResultMatchers.jsonPath("$.mainStr").value(MainEnum.YES_STR));
    	
    }
    
    public static String asJsonString(Object obj) {
    	try {
    		return new ObjectMapper().writeValueAsString(obj);
    	} catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    }
    
}

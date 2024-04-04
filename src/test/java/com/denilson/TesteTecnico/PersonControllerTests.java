package com.denilson.TesteTecnico;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.denilson.TesteTecnico.Controllers.PersonController;
import com.denilson.TesteTecnico.Entities.Person;
import com.denilson.TesteTecnico.Servicies.PersonService;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;
    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Throwable {
        Person person = new Person();
        person.setId(1L);
        person.setFullName("John Doe");
        person.setDateOfBirth(LocalDate.of(1990, 5, 15));
        given(personService.getPersonByid(1L)).willReturn(person);
    }

    @Test
    public void testGetPersonById() throws Exception {
        mockMvc.perform(get("/api/people/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("John Doe"))
                .andExpect(jsonPath("$.dateOfBirth").value("1990-05-15"));
    }


    @Test
    public void testCreatePerson() throws Exception {
        Person newPerson = new Person();
        newPerson.setFullName("Alice Johnson");
        newPerson.setDateOfBirth(LocalDate.of(1985, 8, 25));

        given(personService.createPerson(any(Person.class))).willReturn(newPerson);

        mockMvc.perform(post("/api/people")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPerson)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Alice Johnson"))
                .andExpect(jsonPath("$.dateOfBirth").value("1985-08-25"));
    }


    @Test
    public void testUpdatePerson() throws Throwable {
        // Criar uma pessoa existente
        Person existingPerson = new Person();
        existingPerson.setId(1L);
        existingPerson.setFullName("John Doe");
        existingPerson.setDateOfBirth(LocalDate.of(1990, 5, 15));

        given(personService.getPersonByid(1L)).willReturn(existingPerson);

        Person updatedPerson = new Person();
        updatedPerson.setFullName("Jane Smith");
        updatedPerson.setDateOfBirth(LocalDate.of(1990, 6, 30));

        given(personService.updatePerson(1L, updatedPerson)).willReturn(updatedPerson);

        mockMvc.perform(put("/api/people/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPerson)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Jane Smith"))
                .andExpect(jsonPath("$.dateOfBirth").value("1990-06-30"));
    }

    @Test
    public void testDeletePerson() throws Throwable {
        mockMvc.perform(delete("/api/people/1"))
                .andExpect(status().isOk());
        verify(personService).deletePerson(1L);
    }

}

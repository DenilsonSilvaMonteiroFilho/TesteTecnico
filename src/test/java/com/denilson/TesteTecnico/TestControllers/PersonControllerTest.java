package com.denilson.TesteTecnico.TestControllers;

import com.denilson.TesteTecnico.Controllers.PersonController;
import com.denilson.TesteTecnico.Entities.Person;
import com.denilson.TesteTecnico.Exceptions.InvalidFieldException;
import com.denilson.TesteTecnico.Exceptions.ResourceNotFoundException;
import com.denilson.TesteTecnico.Servicies.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Testes para o PersonController")
public class PersonControllerTest {

    @InjectMocks
    private PersonController personController;

    @Mock
    private PersonService personService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Obter todas as pessoas.")
    void getAllPeople() {
        List<Person> expectedPeople = new ArrayList<>();
        when(personService.getAllPeople()).thenReturn(expectedPeople);

        ResponseEntity<List<Person>> people = personController.getAllPeople();

        assertThat(people.getBody()).isSameAs(expectedPeople);
    }

    @Test
    @DisplayName("Obter pessoa por ID existente.")
    void getExistingPersonById() throws Throwable {
        Long id = 1L;
        Person expectedPerson = new Person();
        expectedPerson.setId(id);
        when(personService.getPersonByid(id)).thenReturn(expectedPerson);

        ResponseEntity<Person> response = personController.getPersonById(id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isSameAs(expectedPerson);
    }

    @Test
    @DisplayName("Obter pessoa por ID inexistente.")
    void getNonExistingPersonById() throws Throwable {
        Long id = 1L;
        when(personService.getPersonByid(id)).thenThrow(new ResourceNotFoundException("Person not found with id: " + id));

        ResponseEntity<Person> response = personController.getPersonById(id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Criar pessoa com sucesso.")
    void createPersonWithSuccess() {
        Person person = new Person();
        when(personService.createPerson(person)).thenReturn(person);

        ResponseEntity<Person> createdPerson = personController.createPerson(person);

        assertThat(createdPerson.getBody()).isSameAs(person);
    }

    @Test
    @DisplayName("Atualizar pessoa com ID existente.")
    void updateExistingPerson() throws Throwable {
        Long id = 1L;
        Person personDetails = new Person();
        when(personService.updatePerson(id, personDetails)).thenReturn(personDetails);

        ResponseEntity<Person> updatedPerson = personController.updatePerson(id, personDetails);

        assertThat(updatedPerson.getBody()).isSameAs(personDetails);
    }

    @Test
    @DisplayName("Tentar atualizar pessoa com ID inexistente.")
    void updateNonExistingPerson() throws Throwable {
        Long id = 1L;
        Person personDetails = new Person();
        when(personService.updatePerson(id, personDetails)).thenThrow(new ResourceNotFoundException("Person not found with id: " + id));

        Throwable e = catchThrowable(() -> personService.updatePerson(id, personDetails));

        assertThat(e).isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Person not found with id: " + id);
    }

    @Test
    @DisplayName("Tentar atualizar pessoa com dados inválidos.")
    void updatePersonWithInvalidData() throws Throwable {
        Long id = 1L;
        Person personDetails = new Person();

        when(personService.updatePerson(id, personDetails)).thenThrow(new InvalidFieldException("Invalid data"));

        assertThatThrownBy(() -> personController.updatePerson(id, personDetails))
                .isInstanceOf(InvalidFieldException.class);
    }

    @Test
    @DisplayName("Deletar pessoa com ID existente.")
    void deleteExistingPerson() throws Throwable {
        Long id = 1L;

        personController.delete(id);

        verify(personService, times(1)).deletePerson(id);
    }

    @Test
    @DisplayName("Tentar deletar pessoa com ID inexistente.")
    void deleteNonExistingPerson() throws Throwable {
        Long id = 1L;
        doThrow(new ResourceNotFoundException("Person not found with id: " + id)).when(personService).deletePerson(id);

        Throwable e = catchThrowable(() -> personService.deletePerson(id));

        assertThat(e).isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Person not found with id: " + id);
    }
}
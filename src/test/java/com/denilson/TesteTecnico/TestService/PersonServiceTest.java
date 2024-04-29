package com.denilson.TesteTecnico.TestService;

import com.denilson.TesteTecnico.Entities.Person;
import com.denilson.TesteTecnico.Exceptions.InvalidFieldException;
import com.denilson.TesteTecnico.Exceptions.ResourceNotFoundException;
import com.denilson.TesteTecnico.Repositories.PersonRepository;
import com.denilson.TesteTecnico.Servicies.PersonService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;


import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DisplayName("Testes para o PersonService")
public class PersonServiceTest {

    @InjectMocks
    private PersonService personService;
    @Mock
    private PersonRepository personRepository;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("cadastra pessoa com sucesso.")
    void savePersonWithSuccess(){
        Person person = new Person();
        person.setFullName("Person 1");
        person.setDateOfBirth(LocalDate.of(1999,8,21));

        when(personRepository.save(person)).thenReturn(person);

        Person personSaved = personService.createPerson(person);

        assertThat(personSaved).isNotNull();
        assertThat(personSaved).isEqualTo(person);
    }

    @Test
    @DisplayName("Tenta cadastrar sem nome.")
    void savePersonWithoutName(){
        Person person = new Person();
        person.setDateOfBirth(LocalDate.of(1999,8,21));

        when(personService.createPerson(person)).thenThrow(new InvalidFieldException("Invalid Name or Date"));

        Throwable e = Assertions.catchThrowable(() -> personService.createPerson(person));
        assertThat(e).isInstanceOf(InvalidFieldException.class).hasMessageContaining("Invalid Name or Date");
    }

    @Test
    @DisplayName("Tenta cadastrar sem data nascimento.")
    void savePersonWithoutDateOfBirth(){
        Person person = new Person();
        person.setFullName("person 1");

        when(personService.createPerson(person)).thenThrow(new InvalidFieldException("Invalid Name or Date"));

        Throwable e = Assertions.catchThrowable(() -> personService.createPerson(person));
        assertThat(e).isInstanceOf(InvalidFieldException.class).hasMessageContaining("Invalid Name or Date");
    }

    @Test
    @DisplayName("Buscar pessoa por ID existente.")
    void getExistingPersonById() throws Throwable {
        Long id = 1L;
        Person person = new Person();
        person.setId(id);
        person.setFullName("Person 1");
        person.setDateOfBirth(LocalDate.of(1999,8,21));

        when(personRepository.findById(id)).thenReturn(Optional.of(person));

        Person retrievedPerson = personService.getPersonByid(id);

        assertThat(retrievedPerson).isNotNull();
        assertThat(retrievedPerson).isEqualTo(person);
    }

    @Test
    @DisplayName("Buscar pessoa por ID inexistente.")
    void getNonExistingPersonById(){
        Long id = 1L;

        when(personRepository.findById(id)).thenReturn(Optional.empty());

        Throwable e = catchThrowable(() -> personService.getPersonByid(id));

        assertThat(e).isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Person not found with id: " + id);
    }

    @Test
    @DisplayName("Atualizar pessoa existente.")
    void updateExistingPerson() throws Throwable {
        Long id = 1L;
        Person existingPerson = new Person();
        existingPerson.setId(id);
        existingPerson.setFullName("Person 1");
        existingPerson.setDateOfBirth(LocalDate.of(1999, 8, 21));

        when(personRepository.findById(id)).thenReturn(Optional.of(existingPerson));
        when(personRepository.save(any(Person.class))).thenReturn(existingPerson);

        Person updatedPerson = personService.updatePerson(id, existingPerson);

        assertThat(updatedPerson).isNotNull();
        assertThat(updatedPerson).isEqualTo(existingPerson);
    }

    @Test
    @DisplayName("Tentar atualizar pessoa inexistente.")
    void updateNonExistingPerson() {
        Long id = 1L;

        when(personRepository.findById(id)).thenReturn(Optional.empty());

        Throwable e = catchThrowable(() -> personService.updatePerson(id, new Person()));

        assertThat(e).isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Person not found with id: " + id);
    }

    @Test
    @DisplayName("Deletar pessoa existente.")
    void deleteExistingPerson() throws Throwable {
        Long id = 1L;
        Person existingPerson = new Person();
        existingPerson.setId(id);
        existingPerson.setFullName("Person 1");
        existingPerson.setDateOfBirth(LocalDate.of(1999, 8, 21));

        when(personRepository.findById(id)).thenReturn(Optional.of(existingPerson));

        personService.deletePerson(id);

        verify(personRepository, times(1)).delete(existingPerson);
    }

    @Test
    @DisplayName("Tentar deletar pessoa inexistente.")
    void deleteNonExistingPerson() {
        Long id = 1L;

        when(personRepository.findById(id)).thenReturn(Optional.empty());

        Throwable e = catchThrowable(() -> personService.deletePerson(id));

        assertThat(e).isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Person not found with id: " + id);
    }
}

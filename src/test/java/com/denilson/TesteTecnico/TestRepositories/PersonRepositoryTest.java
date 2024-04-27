package com.denilson.TesteTecnico.TestRepositories;

import com.denilson.TesteTecnico.Entities.Person;
import com.denilson.TesteTecnico.Repositories.PersonRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@DataJpaTest
@ActiveProfiles("test")
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    @DisplayName("Persistencia com sucesso")
    void savePersonWithSuccess(){
        Person person = new Person();
        person.setFullName("Person 1");
        person.setDateOfBirth(LocalDate.of(1999,8,21));

        Person personSaved = personRepository.save(person);

        assertThat(personSaved).isNotNull();
        assertThat(personSaved.getId()).isNotNull();
        assertThat(personSaved).isEqualTo(person);
    }

    @Test
    @DisplayName("Buscar pessoa por ID com sucesso.")
    void findByIdWithSuccess() {
        Person person = new Person();
        person.setFullName("Person 1");
        person.setDateOfBirth(LocalDate.of(1999,8,21));
        Person personSaved = personRepository.save(person);

        Person personFind = personRepository.findById(personSaved.getId()).get();

        assertThat(personFind).isNotNull();
        assertThat(personFind).isEqualTo(personSaved);
    }

    @Test
    @DisplayName("Deleta pessoa por ID com sucesso")
    void DeletePersonByIdWithSuccess(){
        Person person = new Person();
        person.setFullName("Person 1");
        person.setDateOfBirth(LocalDate.of(1999,8,21));

        personRepository.delete(person);
        Throwable e = Assertions.catchThrowable(() -> personRepository.findById(0l).get());
        assertThat(e).isInstanceOf(NoSuchElementException.class).hasMessageContaining("No value present");

    }

    @Test
    @DisplayName("Busca pessoa por ID que não existe.")
    void findByIdNotExist() {
        Throwable e = Assertions.catchThrowable(() -> personRepository.findById(0l).get());
        assertThat(e).isInstanceOf(NoSuchElementException.class).hasMessageContaining("No value present");
    }

}

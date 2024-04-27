package com.denilson.TesteTecnico.TestRepositories;

import com.denilson.TesteTecnico.Entities.Address;
import com.denilson.TesteTecnico.Entities.Person;
import com.denilson.TesteTecnico.Repositories.AddressRepository;
import com.denilson.TesteTecnico.Repositories.PersonRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PersonRepository personRepository;

    @Test
    @DisplayName("Persistência de endereço com sucesso")
    void saveAddressWithSuccess() {
        Person person = new Person();
        person.setFullName("Person 1");
        person.setDateOfBirth(LocalDate.of(1999, 8, 21));
        personRepository.save(person);

        Address address = new Address("Street 1", "12345-678", "100", "City", "State");
        address.setPerson(person);

        Address savedAddress = addressRepository.save(address);

        assertThat(savedAddress).isNotNull();
        assertThat(savedAddress.getId()).isNotNull();
        assertThat(savedAddress).isEqualTo(address);
    }

    @Test
    @DisplayName("Buscar endereço por ID com sucesso.")
    void findByIdWithSuccess() {
        Person person = new Person();
        person.setFullName("Person 1");
        person.setDateOfBirth(LocalDate.of(1999, 8, 21));
        personRepository.save(person);

        Address address = new Address("Street 1", "12345-678", "100", "City", "State");
        address.setPerson(person);
        Address savedAddress = addressRepository.save(address);

        Address addressFind = addressRepository.findById(savedAddress.getId()).get();
        assertThat(addressFind).isNotNull();
        assertThat(addressFind).isEqualTo(savedAddress);
    }

    @Test
    @DisplayName("Deletar endereço por ID com sucesso")
    void deleteAddressByIdWithSuccess() {
        Person person = new Person();
        person.setFullName("Person 1");
        person.setDateOfBirth(LocalDate.of(1999, 8, 21));
        personRepository.save(person);

        Address address = new Address("Street 1", "12345-678", "100", "City", "State");
        address.setPerson(person);
        Address savedAddress = addressRepository.save(address);

        addressRepository.delete(savedAddress);
        Throwable e = Assertions.catchThrowable(() -> addressRepository.findById(0l).get());
        assertThat(e).isInstanceOf(NoSuchElementException.class).hasMessageContaining("No value present");
    }

    @Test
    @DisplayName("Buscar endereço por ID que não existe.")
    void findByIdNotExist() {
        Throwable e = Assertions.catchThrowable(() -> addressRepository.findById(0l).get());
        assertThat(e).isInstanceOf(NoSuchElementException.class).hasMessageContaining("No value present");
    }
}

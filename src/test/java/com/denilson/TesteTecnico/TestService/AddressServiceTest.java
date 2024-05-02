package com.denilson.TesteTecnico.TestService;

import com.denilson.TesteTecnico.Entities.Address;
import com.denilson.TesteTecnico.Entities.Person;
import com.denilson.TesteTecnico.Exceptions.ResourceNotFoundException;
import com.denilson.TesteTecnico.Repositories.AddressRepository;
import com.denilson.TesteTecnico.Repositories.PersonRepository;
import com.denilson.TesteTecnico.Servicies.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Testes para o AddressService")
public class AddressServiceTest {
    @InjectMocks
    private AddressService addressService;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private PersonRepository personRepository;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Obter endereços por ID de pessoa existente.")
    void getAddressesByExistingPersonId() {
        Long personId = 1L;
        List<Address> expectedAddresses = new ArrayList<>();
        when(addressRepository.findByPersonId(personId)).thenReturn(expectedAddresses);

        List<Address> addresses = addressService.getAddressesByPersonId(personId);

        assertThat(addresses).isSameAs(expectedAddresses);
    }

    @Test
    @DisplayName("Criar endereço para pessoa existente.")
    void createAddressForExistingPerson() throws Throwable {
        Long personId = 1L;
        Person person = new Person();
        person.setId(personId);

        Address address = new Address();
        address.setId(1L);

        when(personRepository.findById(personId)).thenReturn(Optional.of(person));
        when(addressRepository.save(any(Address.class))).thenReturn(address);

        Address createdAddress = addressService.createAddressForPerson(personId, address);

        assertThat(createdAddress).isNotNull();
        assertThat(createdAddress).isEqualTo(address);
        assertThat(createdAddress.getPerson()).isEqualTo(person);
    }

    @Test
    @DisplayName("Tentar criar endereço para pessoa inexistente.")
    void createAddressForNonExistingPerson() {
        Long personId = 1L;

        when(personRepository.findById(personId)).thenReturn(Optional.empty());

        Throwable e = catchThrowable(() -> addressService.createAddressForPerson(personId, new Address()));

        assertThat(e).isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Person not found with id: " + personId);
    }

    @Test
    @DisplayName("Atualizar endereço para pessoa existente.")
    void updateAddressForExistingPerson() throws Throwable {
        Long personId = 1L;
        Long addressId = 1L;

        Person person = new Person();
        person.setId(personId);

        Address existingAddress = new Address();
        existingAddress.setId(addressId);

        Address addressDetails = new Address();
        addressDetails.setStreet("New Street");

        when(personRepository.findById(personId)).thenReturn(Optional.of(person));
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(existingAddress));
        when(addressRepository.save(any(Address.class))).thenReturn(existingAddress);

        Address updatedAddress = addressService.updateAddressForPerson(personId, addressId, addressDetails);

        assertThat(updatedAddress).isNotNull();
        assertThat(updatedAddress.getId()).isEqualTo(addressId);
        assertThat(updatedAddress.getStreet()).isEqualTo(addressDetails.getStreet());
    }

    @Test
    @DisplayName("Tentar atualizar endereço para pessoa inexistente.")
    void updateAddressForNonExistingPerson() {
        Long personId = 1L;
        Long addressId = 1L;

        when(personRepository.findById(personId)).thenReturn(Optional.empty());

        Throwable e = catchThrowable(() -> addressService.updateAddressForPerson(personId, addressId, new Address()));

        assertThat(e).isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Person not found with id: " + personId);
    }

    @Test
    @DisplayName("Definir endereço principal para pessoa existente.")
    void setMainAddressForExistingPerson() throws Throwable {
        Long personId = 1L;
        Long addressId = 1L;

        Person person = new Person();
        person.setId(personId);

        Address existingAddress = new Address();
        existingAddress.setId(addressId);

        List<Address> addresses = new ArrayList<>();
        addresses.add(existingAddress);

        when(personRepository.findById(personId)).thenReturn(Optional.of(person));
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(existingAddress));
        when(addressRepository.findByPersonId(personId)).thenReturn(addresses);
        when(addressRepository.save(any(Address.class))).thenReturn(existingAddress);

        Address mainAddress = addressService.setMainAddress(personId, addressId);

        assertThat(mainAddress).isNotNull();
        assertThat(mainAddress.getId()).isEqualTo(addressId);
        assertThat(mainAddress.getMainAddress()).isTrue();
    }

    @Test
    @DisplayName("Deletar endereço para pessoa existente.")
    void deleteAddressForExistingPerson() {
        Long personId = 1L;
        Long addressId = 1L;

        Person person = new Person();
        person.setId(personId);

        Address existingAddress = new Address();
        existingAddress.setId(addressId);

        when(addressRepository.findByIdAndPersonId(addressId, personId)).thenReturn(Optional.of(existingAddress));

        assertThatCode(() -> addressService.deleteAddressForPerson(personId, addressId))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Tentar deletar endereço para pessoa inexistente.")
    void deleteAddressForNonExistingPerson() {
        Long personId = 1L;
        Long addressId = 1L;

        when(addressRepository.findByIdAndPersonId(addressId, personId)).thenReturn(Optional.empty());

        Throwable e = catchThrowable(() -> addressService.deleteAddressForPerson(personId, addressId));

        assertThat(e).isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Address not found with id " + addressId + " and personId " + personId);
    }

}

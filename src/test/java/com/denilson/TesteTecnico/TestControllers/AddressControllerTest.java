package com.denilson.TesteTecnico.TestControllers;

import com.denilson.TesteTecnico.Controllers.AddressController;
import com.denilson.TesteTecnico.Entities.Address;
import com.denilson.TesteTecnico.Entities.Person;
import com.denilson.TesteTecnico.Exceptions.InvalidAddressExcetion;
import com.denilson.TesteTecnico.Exceptions.InvalidFieldException;
import com.denilson.TesteTecnico.Exceptions.ResourceNotFoundException;
import com.denilson.TesteTecnico.Servicies.AddressService;
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
@DisplayName("Testes para o AddressController")
public class AddressControllerTest {

    @InjectMocks
    private AddressController addressController;

    @Mock
    private AddressService addressService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Obter endereços por ID de pessoa.")
    void getAddressesByPersonId() {
        Long personId = 1L;
        List<Address> expectedAddresses = new ArrayList<>();
        when(addressService.getAddressesByPersonId(personId)).thenReturn(expectedAddresses);

        ResponseEntity<List<Address>> addresses = addressController.getAddressesByPersonId(personId);

        assertThat(addresses.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(addresses.getBody()).isSameAs(expectedAddresses);
    }

    @Test
    @DisplayName("Obter endereço por ID inexistente.")
    void getNonExistingPersonById() throws Throwable {
        Long id = 1L;
        when(addressService.getAddressesByPersonId(id)).thenThrow(new ResourceNotFoundException("Address not found with id: " + id));

        ResponseEntity<List<Address>> response = addressController.getAddressesByPersonId(id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Criar endereço com sucesso.")
    void createAddressForPersonWithSuccess() throws Throwable {
        Long personId = 1L;
        Address address = new Address();
        when(addressService.createAddressForPerson(personId, address)).thenReturn(address);

        ResponseEntity<Address> createdAddress = addressController.createAddressForPerson(personId, address);

        assertThat(createdAddress.getBody()).isSameAs(address);
    }

    @Test
    @DisplayName("Atualizar endereço para pessoa com ID existente.")
    void updateAddressForPersonWithExistingId() throws Throwable {
        Long personId = 1L;
        Long addressId = 1L;
        Address addressDetails = new Address();

        when(addressService.updateAddressForPerson(personId, addressId, addressDetails)).thenReturn(addressDetails);

        ResponseEntity<Address> updatedAddress = addressController.updateAddressForPerson(personId, addressId, addressDetails);

        assertThat(updatedAddress.getBody()).isSameAs(addressDetails);
    }

    @Test
    @DisplayName("Tentar atualizar endereço com ID inexistente.")
    void updateNonExistingPerson() throws Throwable {
        Long personId = 1L;
        Long addressId = 1L;
        Address addressDetails = new Address();
        when(addressService.updateAddressForPerson(personId, addressId, addressDetails)).thenThrow(new ResourceNotFoundException("Address not found with id: " + addressId));

        Throwable e = catchThrowable(() -> addressService.updateAddressForPerson(personId, addressId, addressDetails));

        assertThat(e).isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Address not found with id: " + addressId);
    }

    @Test
    @DisplayName("Tentar atualizar endereço com dados inválidos.")
    void updatePersonWithInvalidData() throws Throwable {
        Long personId = 1L;
        Long addressId = 1L;
        Address addressDetails = new Address();
        when(addressService.updateAddressForPerson(personId, addressId, addressDetails)).thenThrow(new InvalidAddressExcetion("Invalid data"));

        assertThatThrownBy(() -> addressController.updateAddressForPerson(personId, addressId, addressDetails))
                .isInstanceOf(InvalidAddressExcetion.class);
    }

    @Test
    @DisplayName("Tentar atualizar endereço para pessoa com ID inexistente.")
    void updateAddressForPersonWithNonExistingId() throws Throwable {
        Long personId = 1L;
        Long addressId = 1L;
        Address addressDetails = new Address();

        when(addressService.updateAddressForPerson(personId, addressId, addressDetails))
                .thenThrow(new InvalidAddressExcetion("Address not found with id: " + addressId));

        assertThatThrownBy(() -> addressController.updateAddressForPerson(personId, addressId, addressDetails))
                .isInstanceOf(InvalidAddressExcetion.class);
    }

    @Test
    @DisplayName("Definir endereço principal para pessoa com IDs existentes.")
    void setMainAddressWithExistingIds() throws Throwable {
        Long personId = 1L;
        Long addressId = 1L;
        Address address = new Address();

        when(addressService.setMainAddress(personId, addressId)).thenReturn(address);

        ResponseEntity<Address> mainAddress = addressController.setMainAddress(personId, addressId);

        assertThat(mainAddress.getBody()).isSameAs(address);
    }

    @Test
    @DisplayName("Tentar definir endereço principal para pessoa com IDs inexistentes.")
    void setMainAddressWithNonExistingIds() throws Throwable {
        Long personId = 1L;
        Long addressId = 1L;

        when( addressService.setMainAddress(personId, addressId)).thenThrow(new ResourceNotFoundException("Address not found with id: " + addressId));

        Throwable e = catchThrowable(() -> addressService.setMainAddress(personId, addressId));

        assertThat(e).isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Address not found with id: " + addressId);
    }

    @Test
    @DisplayName("Deletar endereço para pessoa com IDs existentes.")
    void deleteAddressForPersonWithExistingIds() throws Throwable {
        Long personId = 1L;
        Long addressId = 1L;

        addressController.deleteAddressForPerson(personId, addressId);

        verify(addressService, times(1)).deleteAddressForPerson(personId, addressId);
    }

    @Test
    @DisplayName("Tentar deletar endereço para pessoa com IDs inexistentes.")
    void deleteAddressForPersonWithNonExistingIds() throws Throwable {
        Long personId = 1L;
        Long addressId = 1L;

        when( addressService.deleteAddressForPerson(personId, addressId))
                .thenThrow(new ResourceNotFoundException("Address not found with id " + addressId + " or personId " + personId));

        Throwable e = catchThrowable(() -> addressService.deleteAddressForPerson(personId, addressId));

        assertThat(e).isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Address not found with id " + addressId + " or personId " + personId);
    }
}

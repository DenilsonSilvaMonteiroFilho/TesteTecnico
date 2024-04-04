package com.denilson.TesteTecnico;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.denilson.TesteTecnico.Controllers.AddressController;
import com.denilson.TesteTecnico.Entities.Address;
import com.denilson.TesteTecnico.Servicies.AddressService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AddressController.class)
public class AddressControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;

    @Test
    public void testCreateAddressForPerson() throws Throwable {
        Address address = new Address();
        address.setId(1L);
        address.setStreet("123 Main St");
        address.setZipCode("12345");
        address.setNumber("123");
        address.setCity("Cityville");
        address.setState("ST");

        given(addressService.createAddressForPerson(Mockito.anyLong(), Mockito.any(Address.class))).willReturn(address);

        mockMvc.perform(post("/api/people/1/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(address)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.street").value("123 Main St"))
                .andExpect(jsonPath("$.zipCode").value("12345"))
                .andExpect(jsonPath("$.number").value("123"))
                .andExpect(jsonPath("$.city").value("Cityville"))
                .andExpect(jsonPath("$.state").value("ST"));
    }

    @Test
    public void testUpdateAddressForPerson() throws Throwable {
        Address address = new Address();
        address.setId(1L);
        address.setStreet("123 Main St");
        address.setZipCode("12345");
        address.setNumber("123");
        address.setCity("Cityville");
        address.setState("ST");

        given(addressService.updateAddressForPerson(Mockito.anyLong(), Mockito.anyLong(), Mockito.any(Address.class))).willReturn(address);

        mockMvc.perform(put("/api/people/1/addresses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(address)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.street").value("123 Main St"))
                .andExpect(jsonPath("$.zipCode").value("12345"))
                .andExpect(jsonPath("$.number").value("123"))
                .andExpect(jsonPath("$.city").value("Cityville"))
                .andExpect(jsonPath("$.state").value("ST"));
    }

    @Test
    public void testSetMainAddress() throws Throwable {
        Address address = new Address();
        address.setId(1L);
        address.setStreet("123 Main St");
        address.setZipCode("12345");
        address.setNumber("123");
        address.setCity("Cityville");
        address.setState("ST");

        given(addressService.setMainAddress(Mockito.anyLong(), Mockito.anyLong())).willReturn(address);

        mockMvc.perform(put("/api/people/1/addresses/mainAddress/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.street").value("123 Main St"))
                .andExpect(jsonPath("$.zipCode").value("12345"))
                .andExpect(jsonPath("$.number").value("123"))
                .andExpect(jsonPath("$.city").value("Cityville"))
                .andExpect(jsonPath("$.state").value("ST"));
    }

    @Test
    public void testDeleteAddressForPerson() throws Exception {
        mockMvc.perform(delete("/api/people/1/addresses/1"))
                .andExpect(status().isOk());

        verify(addressService).deleteAddressForPerson(1L, 1L);
    }

    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

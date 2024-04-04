package com.denilson.TesteTecnico.Controllers;

import com.denilson.TesteTecnico.Entities.Address;
import com.denilson.TesteTecnico.Servicies.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/people/{personId}/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping
    public List<Address> getAddressesByPersonId(@PathVariable Long personId) {
        return addressService.getAddressesByPersonId(personId);
    }

    @PostMapping
    public Address createAddressForPerson(@PathVariable Long personId, @RequestBody Address address) throws Throwable {
        return addressService.createAddressForPerson(personId, address);
    }

    @PutMapping("/{addressId}")
    public Address updateAddressForPerson(@PathVariable Long personId, @PathVariable Long addressId,
                                          @RequestBody Address addressDetails) throws Throwable {
        return addressService.updateAddressForPerson(personId, addressId, addressDetails);
    }

    @PutMapping("/mainAddress/{addressId}")
    public Address setMainAddress(@PathVariable Long personId, @PathVariable Long addressId) throws Throwable {
        return addressService.setMainAddress(personId, addressId);
    }

    @DeleteMapping("/{addressId}")
    public Object deleteAddressForPerson(@PathVariable Long personId, @PathVariable Long addressId) {
        return addressService.deleteAddressForPerson(personId, addressId);
    }
}

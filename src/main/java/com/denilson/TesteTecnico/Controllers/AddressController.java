package com.denilson.TesteTecnico.Controllers;

import com.denilson.TesteTecnico.Entities.Address;
import com.denilson.TesteTecnico.Exceptions.ResourceNotFoundException;
import com.denilson.TesteTecnico.Servicies.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Address>> getAddressesByPersonId(@PathVariable Long personId) {
        try {
            return ResponseEntity.ok(addressService.getAddressesByPersonId(personId));
        } catch (Throwable e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Address> createAddressForPerson(@PathVariable Long personId, @RequestBody Address address) throws Throwable {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(addressService.createAddressForPerson(personId, address));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<Address> updateAddressForPerson(@PathVariable Long personId, @PathVariable Long addressId,
                                          @RequestBody Address addressDetails) throws Throwable {
        try {
            return ResponseEntity.ok(addressService.updateAddressForPerson(personId, addressId, addressDetails));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/mainAddress/{addressId}")
    public ResponseEntity<Address> setMainAddress(@PathVariable Long personId, @PathVariable Long addressId) throws Throwable {
       try {
           return ResponseEntity.ok(addressService.setMainAddress(personId, addressId));
       }catch (ResourceNotFoundException e){
           return ResponseEntity.notFound().build();
       }
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddressForPerson(@PathVariable Long personId, @PathVariable Long addressId) {
        try {
            addressService.deleteAddressForPerson(personId, addressId);
            return ResponseEntity.noContent().build();
        }catch (Throwable e){
            return ResponseEntity.notFound().build();
        }
    }
}

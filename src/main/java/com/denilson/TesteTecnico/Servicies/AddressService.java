package com.denilson.TesteTecnico.Servicies;

import com.denilson.TesteTecnico.Entities.Address;
import com.denilson.TesteTecnico.Entities.Person;
import com.denilson.TesteTecnico.Exceptions.InvalidAddressExcetion;
import com.denilson.TesteTecnico.Exceptions.ResourceNotFoundException;
import com.denilson.TesteTecnico.Repositories.AddressRepository;
import com.denilson.TesteTecnico.Repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PersonRepository personRepository;

    public List<Address> getAddressesByPersonId(Long personId) {
        return addressRepository.findByPersonId(personId);
    }

    public Address createAddressForPerson(Long personId,Address address) throws Throwable {
        Person person = (Person) personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + personId));

        if(!address.equals(null)) {
            address.setMainAddress(false);
            address.setPerson(person);
        }else {
            new InvalidAddressExcetion("Address null or invalid");
        }
        return addressRepository.save(address);
    }
    public Address updateAddressForPerson(Long personId, Long addressId, Address addressDetails) throws Throwable {
        Person person = (Person) personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + personId));

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + addressId));

        if(!addressDetails.equals(null)) {
            address.setStreet(addressDetails.getStreet());
            address.setZipCode(addressDetails.getZipCode());
            address.setNumber(addressDetails.getNumber());
            address.setCity(addressDetails.getCity());
            address.setState(addressDetails.getState());
        }
        personRepository.saveAndFlush(person);
        return addressRepository.save(address);
    }
    
    public Address setMainAddress(Long personId, Long addressId) throws Throwable {
        Person person = (Person) personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + personId));

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + addressId));
        
        List<Address> addressList = getAddressesByPersonId(personId);
        for (Address address1: addressList){
            if (address1.getMainAddress()){
                address1.setMainAddress(false);
            }
        }

        address.setMainAddress(true);
        return addressRepository.save(address);
    }

    public Object deleteAddressForPerson(Long personId, Long addressId) {
        return addressRepository.findByIdAndPersonId(addressId, personId).map(address -> {
            addressRepository.delete((Address) address);
            return address;
        }).orElseThrow(() -> new ResourceNotFoundException(
                "Address not found with id " + addressId + " and personId " + personId));
    }
}

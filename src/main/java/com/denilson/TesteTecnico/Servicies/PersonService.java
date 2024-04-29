package com.denilson.TesteTecnico.Servicies;

import com.denilson.TesteTecnico.Entities.Address;
import com.denilson.TesteTecnico.Entities.Person;
import com.denilson.TesteTecnico.Exceptions.InvalidFieldException;
import com.denilson.TesteTecnico.Exceptions.ResourceNotFoundException;
import com.denilson.TesteTecnico.Repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public List<Person> getAllPeople() {
        return personRepository.findAll();
    }

    public Person createPerson(Person person) {
        if(person.getAddresses()!=null) {
            for (Address address : person.getAddresses()) {
                address.setMainAddress(false);
                address.setPerson(person);
            }
        }
        if((person.getFullName()!=null && person.getFullName().isEmpty())
                ||(person.getDateOfBirth()!=null && person.getDateOfBirth().equals(null))) {
            throw new InvalidFieldException("Invalid Name or Date");
        }
        return personRepository.save(person);
    }

    public Person getPersonByid(Long id) throws Throwable {
        return personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));
    }

    public Person updatePerson(Long id, Person personDetails) throws Throwable {
        Person person =  personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));
        if(personDetails == null){
            throw new ResourceNotFoundException("Invalid data");
        }
        if (personDetails.getFullName() != null && !personDetails.getFullName().isEmpty())  {
            person.setFullName(personDetails.getFullName());
        }else{
            throw new InvalidFieldException("Invalid Name");
        }

        if(personDetails.getDateOfBirth() !=null) {
            person.setDateOfBirth(personDetails.getDateOfBirth());
        }else {
            throw new InvalidFieldException("Invalid Date");
        }
        return personRepository.save(person);
    }

    public void deletePerson(Long id) throws Throwable {
        Person person = (Person) personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));
        personRepository.delete(person);
    }
}

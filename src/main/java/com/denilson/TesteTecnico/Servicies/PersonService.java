package com.denilson.TesteTecnico.Servicies;

import com.denilson.TesteTecnico.Entities.Person;
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
        return personRepository.saveAndFlush(person);
    }

    public Person getPersonByid(Long id){
        return personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));
    }

    public Person updatePerson(Long id, Person personDetails) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));

        person.setFullName(personDetails.getFullName());
        person.setDateOfBirth(personDetails.getDateOfBirth());

        return personRepository.saveAndFlush(person);
    }

    public void deletePerson(Long id){
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));

        personRepository.delete(person);
    }
}

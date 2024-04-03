package com.denilson.TesteTecnico.Controllers;

import com.denilson.TesteTecnico.Entities.Person;
import com.denilson.TesteTecnico.Servicies.PersonService;
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
@RequestMapping("/api/people")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public List<Person> getAllPeople() {
        return personService.getAllPeople();
    }

    @GetMapping("/{id}")
    public Person getPersonById(@PathVariable Long id) {
        return personService.getPersonByid(id);
    }

    @PostMapping
    public Person createPerson(@RequestBody Person person) {
        return personService.createPerson(person);
    }

    @PutMapping("/{id}")
    public Person updatePerson(@PathVariable Long id, @RequestBody Person personDetails) {
        return personService.updatePerson(id,personDetails);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        personService.deletePerson(id);
    }
}

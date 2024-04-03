package com.denilson.TesteTecnico.Repositories;

import com.denilson.TesteTecnico.Entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}

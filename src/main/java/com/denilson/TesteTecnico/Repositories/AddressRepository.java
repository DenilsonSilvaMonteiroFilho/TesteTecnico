package com.denilson.TesteTecnico.Repositories;

import com.denilson.TesteTecnico.Entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
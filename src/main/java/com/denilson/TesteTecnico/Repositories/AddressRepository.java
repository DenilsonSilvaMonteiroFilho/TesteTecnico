package com.denilson.TesteTecnico.Repositories;

import com.denilson.TesteTecnico.Entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByPersonId(Long personId);
    Optional<Object> findByIdAndPersonId(Long addressId, Long personId);
}
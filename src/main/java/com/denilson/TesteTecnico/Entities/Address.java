package com.denilson.TesteTecnico.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private String zipCode;
    private String number;
    private String city;
    private String state;
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    // Getters and setters
}
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

    public Address(){

    }
    public Address(String street, String zipCode, String number, String city, String state, Person person) {
        this.street = street;
        this.zipCode = zipCode;
        this.number = number;
        this.city = city;
        this.state = state;
        this.person = person;
    }
}
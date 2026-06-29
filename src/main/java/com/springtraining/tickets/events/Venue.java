package com.springtraining.tickets.events;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// @Entity tells JPA/Hibernate this class maps to a database table.
// @Table picks the table name ("venues"). Each field below becomes a column.
// JPA needs a normal (mutable) class with a no-argument constructor, which is why
// this is no longer a "record" - records are final and immutable.
@Entity
@Table(name = "venues")
public class Venue {

    // @Id marks the primary key. @GeneratedValue(IDENTITY) lets the database
    // generate the id automatically (an auto-increment column).
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column
    private String street;

    @Column
    private String city;

    @Column
    private String country;

    // setter and getter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

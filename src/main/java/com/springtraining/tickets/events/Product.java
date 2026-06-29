package com.springtraining.tickets.events;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// A product is something you can buy for an event (e.g. a ticket type).
// We use BigDecimal for price because it is accurate for money (double is not).
// @Entity maps this class to the "products" table.
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // The id of the Event this product belongs to.
    // @Column(name = "event_id") names the column explicitly so it reads nicely in SQL.
    @Column(name = "event_id")
    private int eventId;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private BigDecimal price;

    // setter and getter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

package com.springtraining.tickets.events;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

// Spring Data repository for products (the things you can buy for an event).
public interface ProductRepository extends JpaRepository<Product, Integer> {

    // findByEventId -> "find every Product whose eventId column equals eventId".
    // Spring builds the SQL from the method name; we never write it ourselves.
    List<Product> findByEventId(int eventId);

}

package com.springtraining.tickets.events;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

// Stores and looks up Products (the things you can buy for an event).
@Repository
public class ProductRepository {

    private final List<Product> products = List.of(
            new Product(401, 301, "General Admission", "Standard entry ticket", new BigDecimal("99.00")),
            new Product(402, 301, "VIP Pass", "VIP entry with premium seating", new BigDecimal("249.00")),
            new Product(403, 302, "Day Pass", "Single day access", new BigDecimal("149.00")),
            new Product(404, 303, "Workshop Ticket", "Hands-on developer workshop", new BigDecimal("79.00"))
    );

    // Find one product by its own id.
    public Optional<Product> findById(int id) {
        return products.stream()
                .filter(product -> product.id() == id)
                .findAny();
    }

    // Find ALL products that belong to a given event.
    // toList() collects every match into a new list.
    public List<Product> findByEventId(int eventId) {
        return products.stream()
                .filter(product -> product.eventId() == eventId)
                .toList();
    }

    // Return every product.
    public List<Product> findAll() {
        return products;
    }

}

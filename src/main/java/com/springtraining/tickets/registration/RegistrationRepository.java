package com.springtraining.tickets.registration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

// Stores Registrations.
// Unlike the other repositories (which hold a fixed List.of(...) that never changes),
// this one must let new registrations be ADDED at runtime. So we use a mutable ArrayList.
@Repository
public class RegistrationRepository {

    // A growable list we can add to. Starts empty.
    private final List<Registration> registrations = new ArrayList<>();

    // Add a new registration to our store and return it back to the caller.
    public Registration save(Registration registration) {
        registrations.add(registration);
        return registration;
    }

    // Find one registration by its id (empty Optional if none match).
    public Optional<Registration> findById(String id) {
        return registrations.stream()
                .filter(registration -> registration.id().equals(id)) // id is a String, so use .equals()
                .findAny();
    }

    // Return every registration.
    public List<Registration> findAll() {
        // Wrap in a new list so callers can't modify our internal list directly.
        return List.copyOf(registrations);
    }

}

package com.springtraining.tickets.events;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

// @Repository tells Spring this class manages data access.
// Spring creates ONE instance of it (a "bean") and gives it to anyone who asks for it.
// For now our data is just a hard-coded in-memory list instead of a real database.
@Repository
public class OrganizerRepository {

    private final List<Organizer> organizers = List.of(
            new Organizer(101, "Wisetech Global", "Technology solutions provider"),
            new Organizer(102, "Tech Solutions", "Technology solutions specialist"),
            new Organizer(103, "Event Pro", "Event management company")
    );

    // Find one organizer by its id.
    // Optional means "maybe a value, maybe nothing" - it avoids null pointer errors.
    public Optional<Organizer> findById(int id) {
        return organizers.stream()                          // go through the list one by one
                .filter(organizer -> organizer.id() == id)  // keep only the matching id
                .findAny();                                 // return the first match (or empty)
    }

    // Return every organizer.
    public List<Organizer> findAll() {
        return organizers;
    }

}

package com.springtraining.tickets.events;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

// Stores and looks up Venues. Works the same way as OrganizerRepository.
@Repository
public class VenueRepository {

    private final List<Venue> venues = List.of(
            new Venue(201, "Olympic Park", "1 Olympic Boulevard", "Sydney", "Australia"),
            new Venue(202, "Convention Centre", "14 Darling Drive", "Sydney", "Australia"),
            new Venue(203, "Tech Arena", "88 Innovation Way", "Melbourne", "Australia")
    );

    // Find one venue by id (empty Optional if none match).
    public Optional<Venue> findById(int id) {
        return venues.stream()
                .filter(venue -> venue.id() == id)
                .findAny();
    }

    // Return every venue.
    public List<Venue> findAll() {
        return venues;
    }

}

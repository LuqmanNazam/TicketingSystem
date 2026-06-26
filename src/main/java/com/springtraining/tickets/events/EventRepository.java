package com.springtraining.tickets.events;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

// Stores and looks up Events.
// An Event needs an Organizer and a Venue, so this repository asks Spring for the
// other two repositories (this is called "dependency injection") and uses them to
// build its sample data in the constructor.
@Repository
public class EventRepository {

    private final List<Event> events;

    // Spring automatically passes in the OrganizerRepository and VenueRepository beans.
    public EventRepository(OrganizerRepository organizerRepository, VenueRepository venueRepository) {
        // orElseThrow() unwraps the Optional, or fails loudly if the id was not found.
        Organizer wisetech = organizerRepository.findById(101).orElseThrow();
        Organizer eventPro = organizerRepository.findById(103).orElseThrow();
        Venue olympicPark = venueRepository.findById(201).orElseThrow();
        Venue conventionCentre = venueRepository.findById(202).orElseThrow();

        this.events = List.of(
                new Event(301, "Spring Conference", wisetech, conventionCentre,
                        LocalDate.of(2026, 9, 10), LocalDate.of(2026, 9, 12)),
                new Event(302, "Tech Summit", eventPro, olympicPark,
                        LocalDate.of(2026, 10, 5), LocalDate.of(2026, 10, 7)),
                new Event(303, "Developer Day", wisetech, olympicPark,
                        LocalDate.of(2026, 11, 1), LocalDate.of(2026, 11, 1))
        );
    }

    // Find one event by id.
    public Optional<Event> findById(int id) {
        return events.stream()
                .filter(event -> event.id() == id)
                .findAny();
    }

    // Return every event.
    public List<Event> findAll() {
        return events;
    }

    // Find all events run by a given organizer.
    // event.organizer().id() reads the id of the nested Organizer record.
    public List<Event> findByOrganizerId(int organizerId) {
        return events.stream()
                .filter(event -> event.organizer().id() == organizerId)
                .toList();
    }

}

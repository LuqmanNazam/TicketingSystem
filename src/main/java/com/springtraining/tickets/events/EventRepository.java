package com.springtraining.tickets.events;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

// Spring Data repository for events.
// Besides the built-in methods (findById, findAll, save, ...), we can declare
// "derived query methods": Spring reads the METHOD NAME and writes the query for us.
public interface EventRepository extends JpaRepository<Event, Integer> {

    // findByOrganizerId -> "find every Event whose organizer's id equals organizerId".
    // Spring follows the Event.organizer relationship down to Organizer.id automatically.
    List<Event> findByOrganizerId(int organizerId);

}

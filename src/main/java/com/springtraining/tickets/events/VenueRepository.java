package com.springtraining.tickets.events;

import org.springframework.data.jpa.repository.JpaRepository;

// Spring Data repository for venues. Works the same way as OrganizerRepository:
// extending JpaRepository gives us findById, findAll, save, delete, etc. for free.
public interface VenueRepository extends JpaRepository<Venue, Integer> {

}

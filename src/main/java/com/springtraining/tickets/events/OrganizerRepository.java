package com.springtraining.tickets.events;

import org.springframework.data.jpa.repository.JpaRepository;

// With Spring Data JPA we no longer WRITE the data-access code ourselves.
// We just declare an interface that extends JpaRepository<EntityType, IdType>
// and Spring generates a working implementation at runtime.
//
// JpaRepository<Organizer, Integer> already gives us, for free:
//   findById(id) -> Optional<Organizer>
//   findAll()    -> List<Organizer>
//   save(...), deleteById(...), count(), and many more.
public interface OrganizerRepository extends JpaRepository<Organizer, Integer> {

}

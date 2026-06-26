package com.springtraining.tickets.events;

import java.time.LocalDate;

// An event has an organizer and a venue (nested records), plus the dates it runs.
public record Event(int id, String name, Organizer organizer, Venue venue, LocalDate startDate, LocalDate endDate) {

}

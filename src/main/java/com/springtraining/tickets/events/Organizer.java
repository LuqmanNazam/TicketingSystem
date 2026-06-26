package com.springtraining.tickets.events;

// A "record" is a short way to create an immutable data class.
// Java automatically gives us a constructor, getters (id(), name(), description()),
// equals(), hashCode() and toString() - so we don't write any boilerplate.
public record Organizer(int id, String name, String description) {

}

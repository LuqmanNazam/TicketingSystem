package com.springtraining.tickets.events;

// A record holding the details of a place where an event happens.
public record Venue(int id, String name, String street, String city, String country) {

}

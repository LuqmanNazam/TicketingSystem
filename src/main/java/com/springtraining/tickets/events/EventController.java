package com.springtraining.tickets.events;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// @RestController means this class handles web requests and returns data (JSON),
// not HTML pages. Spring turns the objects we return into JSON automatically.
@RestController
public class EventController {

    // The controller does not create repositories itself - Spring "injects" them
    // through this constructor. We just store them in final fields to use later.
    private final OrganizerRepository organizerRepository;
    private final EventRepository eventRepository;
    private final ProductRepository productRepository;

    public EventController(OrganizerRepository organizerRepository, EventRepository eventRepository, ProductRepository productRepository) {
        this.organizerRepository = organizerRepository;
        this.eventRepository = eventRepository;
        this.productRepository = productRepository;
    }

    // GET /organizers  ->  list of all organizers
    @GetMapping(path = "/organizers")
    public List<Organizer> getOrganizers() {
        return organizerRepository.findAll();
    }

    // GET /events?organizerId=101  ->  events for that organizer
    // @RequestParam reads a value from the URL's query string (after the "?").
    @GetMapping(path = "/events")
    public List<Event> getEventsByOrganizer(@RequestParam("organizerId") int organizerId) {
        return eventRepository.findByOrganizerId(organizerId);
    }

    // GET /events/5  ->  the single event with id 5
    // @PathVariable reads a value out of the URL path itself.
    @GetMapping(path = "/events/{id}")
    public Event getEventById(@PathVariable("id") int eventId) {
        // .get() throws NoSuchElementException if the event is missing.
        // The @ExceptionHandler method below catches that and turns it into a clean 404.
        return eventRepository.findById(eventId).get();
    }

    @GetMapping(path = "/products")
    public List<Product> getProductsByEvent(@RequestParam("eventId") int eventId) {
        return productRepository.findByEventId(eventId);
    }

    @ExceptionHandler(NoSuchElementException.class)
    // If a NoSuchElementException is thrown anywhere in this controller, Spring will call this method to handle it.
    // We return an ErrorResponse object (which Spring turns into JSON) and set the HTTP status to 404 Not Found.
    public ErrorResponse notFound(NoSuchElementException e) {
        return ErrorResponse.create(e, HttpStatus.NOT_FOUND, e.getMessage());
    }
}

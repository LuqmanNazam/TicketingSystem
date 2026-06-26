package com.springtraining.tickets.registration;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.springtraining.tickets.events.ProductRepository;

import jakarta.validation.Valid;

// Handles web requests for registrations (creating a ticket registration and reading them back).
@RestController
public class RegistrationController {

    // Spring injects these two beans through the constructor.
    // We need ProductRepository so we can check the product the client asked for really exists.
    private final RegistrationRepository registrationRepository;
    private final ProductRepository productRepository;

    public RegistrationController(RegistrationRepository registrationRepository, ProductRepository productRepository) {
        this.registrationRepository = registrationRepository;
        this.productRepository = productRepository;
    }

    // POST /registrations  with a JSON body like: { "productId": 401, "attendeeName": "Sam" }
    // @RequestBody tells Spring to read the JSON body and turn it into a RegistrationRequest.
    // @ResponseStatus(CREATED) makes a successful call return HTTP 201 (the standard "resource created").
    // @Valid tells Spring to check the @NotNull / @NotBlank rules on RegistrationRequest
    // BEFORE this method runs. If any rule fails, Spring rejects it with a 400 Bad Request
    // and this method body is never reached.
    @PostMapping(path = "/registrations")
    @ResponseStatus(HttpStatus.CREATED)
    public Registration register(@Valid @RequestBody RegistrationRequest request) {
        // Make sure the product exists before we register anyone for it.
        // If it doesn't, fail with a clear 400 Bad Request instead of saving bad data.
        productRepository.findById(request.productId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown product id"));

        // The SERVER generates the id and ticket code - never trust the client to set these.
        String id = UUID.randomUUID().toString();          // a unique, random identifier
        String ticketCode = "TKT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        // Build the full Registration from the request plus our generated values, then save it.
        Registration registration = new Registration(id, request.productId(), ticketCode, request.attendeeName());
        return registrationRepository.save(registration);
    }

    // GET /registrations  ->  list of every registration
    @GetMapping(path = "/registrations")
    public List<Registration> getRegistrations() {
        return registrationRepository.findAll();
    }

    // GET /registrations/{id}  ->  the single registration with that id
    @GetMapping(path = "/registrations/{id}")
    public Registration getRegistrationById(@PathVariable("id") String id) {
        // orElseThrow returns a clean 404 Not Found if there is no registration with this id.
        return registrationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registration not found"));
    }
}

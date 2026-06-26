package com.springtraining.tickets.events;

import java.math.BigDecimal;

// A product is something you can buy for an event (e.g. a ticket type).
// We use BigDecimal for price because it is accurate for money (double is not).
// eventId links this product back to the Event it belongs to.
public record Product(int id, int eventId, String name, String description, BigDecimal price) {

}

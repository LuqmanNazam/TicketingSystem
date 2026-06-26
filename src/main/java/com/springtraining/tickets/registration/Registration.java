package com.springtraining.tickets.registration;

// Represents one person's registration for a product (ticket).
// productId links back to the Product that was bought.
public record Registration(String id, int productId, String ticketCode, String attendeeName) {

}

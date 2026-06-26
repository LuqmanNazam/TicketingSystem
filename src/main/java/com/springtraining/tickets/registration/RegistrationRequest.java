package com.springtraining.tickets.registration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// This is the data the CLIENT sends us when registering for a ticket (the request body).
// It is kept separate from the Registration record on purpose:
//   - the client only tells us WHICH product and WHO is attending,
//   - the server decides the id and ticketCode (the client must not choose those).
// A "DTO" (Data Transfer Object) like this protects us from clients setting fields they shouldn't.
//
// The annotations below are Bean Validation rules. They are checked automatically
// when the controller method uses @Valid - if a rule fails, Spring returns 400 Bad Request.
public record RegistrationRequest(

        // productId is an Integer (not int) on purpose: only an object type can be null,
        // and @NotNull means "the client MUST send this field".
        @NotNull(message = "productId is required")
        Integer productId,

        // @NotBlank means the value must be present AND not empty / not just spaces.
        // (Use @NotBlank for text; it implies @NotNull plus a "not only whitespace" check.)
        @NotBlank(message = "attendeeName must not be blank")
        String attendeeName

) {

}

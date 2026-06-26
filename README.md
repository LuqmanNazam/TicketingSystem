# Tickets — Spring Boot Training Project

A small **Spring Boot** REST API for managing events and ticket registrations.
This is a learning project, so the code is heavily commented for beginners and the
data is stored **in memory** (no database yet) — everything resets when the app restarts.

## Tech stack

| Thing | Version / Tool |
|-------|----------------|
| Language | Java 25 |
| Framework | Spring Boot 4.1.0 (Spring Web MVC) |
| Validation | Spring Boot Starter Validation (Jakarta Bean Validation) |
| Build tool | Maven (use the included `mvnw` wrapper) |

## How to run

```bash
# Windows (PowerShell / CMD)
mvnw.cmd spring-boot:run

# macOS / Linux / Git Bash
./mvnw spring-boot:run
```

The app starts on **http://localhost:8081** (configured in
`src/main/resources/application.properties`).

> **Note:** the build needs a JDK that can target Java 25. If `mvnw` complains about
> `JAVA_HOME`, point it at a JDK 25+ install before running.

## Project structure

```
src/main/java/com/springtraining/tickets/
├── TicketsApplication.java          # App entry point (@SpringBootApplication)
├── events/                          # Everything about events
│   ├── Organizer.java               # record: who runs an event
│   ├── Venue.java                   # record: where an event happens
│   ├── Event.java                   # record: an event (has an Organizer + Venue)
│   ├── Product.java                 # record: a buyable item for an event (a ticket type)
│   ├── OrganizerRepository.java     # in-memory store of organizers
│   ├── VenueRepository.java         # in-memory store of venues
│   ├── EventRepository.java         # in-memory store of events
│   ├── ProductRepository.java       # in-memory store of products
│   └── EventController.java         # REST endpoints for organizers/events/products
└── registration/                    # Everything about ticket registrations
    ├── Registration.java            # record: a saved registration (id + ticketCode)
    ├── RegistrationRequest.java     # record: the validated POST body from the client
    ├── RegistrationRepository.java  # in-memory store (mutable — registrations are added)
    └── RegistrationController.java  # REST endpoints for creating/reading registrations
```

### Key concepts used (for learning)

- **`record`** — a concise, immutable data class (auto-generates constructor, accessors, `equals`, etc.).
- **`@RestController`** — handles web requests and returns data as JSON.
- **`@Repository`** — a Spring-managed bean responsible for data access.
- **Dependency injection** — controllers/repositories receive the beans they need through their constructor; Spring wires them automatically.
- **`Optional`** — represents "a value or nothing", avoiding null-pointer bugs.
- **Bean Validation** — `@NotNull` / `@NotBlank` on `RegistrationRequest`, enforced by `@Valid` in the controller.

## API endpoints

### Events module

| Method | Path | Description |
|--------|------|-------------|
| `GET` | `/organizers` | List all organizers |
| `GET` | `/events?organizerId={id}` | List events for an organizer |
| `GET` | `/events/{id}` | Get one event (404 if not found) |
| `GET` | `/products?eventId={id}` | List products (ticket types) for an event |

### Registration module

| Method | Path | Description |
|--------|------|-------------|
| `POST` | `/registrations` | Create a registration (validated; returns **201 Created**) |
| `GET` | `/registrations` | List all registrations |
| `GET` | `/registrations/{id}` | Get one registration (404 if not found) |

## Example requests

```bash
# List organizers
curl http://localhost:8081/organizers

# Events for organizer 101
curl "http://localhost:8081/events?organizerId=101"

# Products for event 301
curl "http://localhost:8081/products?eventId=301"

# Register for a product (returns 201 with a generated id + ticketCode)
curl -X POST http://localhost:8081/registrations \
  -H "Content-Type: application/json" \
  -d '{"productId": 401, "attendeeName": "Sam"}'

# Validation failures return 400 Bad Request:
curl -X POST http://localhost:8081/registrations \
  -H "Content-Type: application/json" \
  -d '{"attendeeName": "Sam"}'          # missing productId

# List registrations
curl http://localhost:8081/registrations
```

## Sample data (IDs to try)

- **Organizers:** `101` Wisetech Global, `102` Tech Solutions, `103` Event Pro
- **Venues:** `201` Olympic Park, `202` Convention Centre, `203` Tech Arena
- **Events:** `301` Spring Conference, `302` Tech Summit, `303` Developer Day
- **Products:** `401`/`402` (event 301), `403` (event 302), `404` (event 303)

## Roadmap / not done yet

- Persist data in a real database (currently in-memory only).
- Friendlier JSON error body listing which fields failed validation.
- Tests for the controllers and repositories.

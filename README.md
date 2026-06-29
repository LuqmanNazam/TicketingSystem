# Tickets — Spring Boot Training Project

A small **Spring Boot** REST API for managing events and ticket registrations.
This is a learning project, so the code is heavily commented for beginners.

The **events module** now persists to a database using **Spring Data JPA** backed by an
in-memory **H2** database — Hibernate creates the tables from the `@Entity` classes on
startup and `data.sql` seeds them. Because H2 runs in memory, the data still resets when
the app restarts. The **registration module** is still a plain in-memory store (not yet
migrated to JPA).

## Tech stack

| Thing | Version / Tool |
|-------|----------------|
| Language | Java 25 |
| Framework | Spring Boot 4.1.0 (Spring Web MVC) |
| Persistence | Spring Data JPA / Hibernate |
| Database | H2 (in-memory), with the H2 web console enabled |
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
├── events/                          # Everything about events (JPA-backed)
│   ├── Organizer.java               # @Entity: who runs an event
│   ├── Venue.java                   # @Entity: where an event happens
│   ├── Event.java                   # @Entity: an event (@ManyToOne Organizer + Venue)
│   ├── Product.java                 # @Entity: a buyable item for an event (a ticket type)
│   ├── OrganizerRepository.java     # JpaRepository for organizers
│   ├── VenueRepository.java         # JpaRepository for venues
│   ├── EventRepository.java         # JpaRepository for events (derived findByOrganizerId)
│   ├── ProductRepository.java       # JpaRepository for products (derived findByEventId)
│   └── EventController.java         # REST endpoints for organizers/events/products
└── registration/                    # Everything about ticket registrations (in-memory)
    ├── Registration.java            # record: a saved registration (id + ticketCode)
    ├── RegistrationRequest.java     # record: the validated POST body from the client
    ├── RegistrationRepository.java  # in-memory store (mutable — registrations are added)
    └── RegistrationController.java  # REST endpoints for creating/reading registrations

src/main/resources/
├── application.properties           # Spring Data JPA / H2 / H2-console settings
└── data.sql                         # seed data, loaded into H2 on every startup
```

### Key concepts used (for learning)

- **`@Entity` / `@Table`** — maps a class to a database table; each field becomes a column.
- **`@Id` / `@GeneratedValue`** — marks the primary key and lets the database generate it.
- **`@ManyToOne` / `@JoinColumn`** — models a foreign-key relationship (e.g. many `Event`s share one `Organizer`/`Venue`).
- **`JpaRepository<Entity, Id>`** — extend this interface and Spring Data *generates* the data-access code at runtime (`findById`, `findAll`, `save`, `deleteById`, …).
- **Derived query methods** — Spring builds the SQL from the method name, e.g. `findByOrganizerId(...)` / `findByEventId(...)`.
- **`data.sql` + `ddl-auto=create-drop`** — Hibernate creates the tables from the entities, then `data.sql` seeds them (`defer-datasource-initialization=true` makes the seed run *after* the tables exist).
- **`record`** — a concise, immutable data class, still used in the registration module and for request bodies. (Note: JPA entities can't be records — they need a mutable, no-arg-constructor class.)
- **`@RestController`** — handles web requests and returns data as JSON.
- **Dependency injection** — controllers receive the repository beans they need through their constructor; Spring wires them automatically.
- **`Optional`** — represents "a value or nothing", avoiding null-pointer bugs (returned by `findById`).
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

The seed data lives in `src/main/resources/data.sql` and is loaded into H2 on every startup.

- **Organizers:** `101` Wisetech Global, `102` Tech Solutions, `103` Event Pro
- **Venues:** `201` Olympic Park, `202` Convention Centre, `203` Tech Arena
- **Events:** `301` Spring Conference, `302` Tech Summit, `303` Developer Day
- **Products:** `401`/`402` (event 301), `403` (event 302), `404` (event 303)

## H2 database console

A web console for inspecting the in-memory database is enabled at
**http://localhost:8081/h2-console**. Connect with:

| Field | Value |
|-------|-------|
| JDBC URL | `jdbc:h2:mem:tickets` |
| User | `sa` |
| Password | *(leave empty)* |

The SQL Hibernate runs is also printed to the console (`spring.jpa.show-sql=true`).

## Roadmap / not done yet

- Migrate the **registration module** to Spring Data JPA (still in-memory).
- Swap H2 for a persistent database (e.g. PostgreSQL) so data survives restarts.
- Friendlier JSON error body listing which fields failed validation.
- Tests for the controllers and repositories.

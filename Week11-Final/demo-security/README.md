# Campus Events API — JWT & RBAC Demo

A simple Spring Boot API for teaching **JSON Web Tokens**, **role-based access control (RBAC)**, and **JPA relationships** (`@OneToOne`, `@ManyToOne`, `@ManyToMany`).

Students browse campus events, register for workshops, and organizers manage their events.

## Prerequisites

- Java 17

No database install required — the app uses an **in-memory H2 database** that starts empty and is recreated on each run.

## Run the app

```bash
./mvnw spring-boot:run
```

Server starts on `http://localhost:8080`.

## Insomnia collection

Import [`insomnia_collection.json`](insomnia_collection.json):

1. **Application** → **Import** → **From File**
2. Run **Login as organizer1**
3. Copy the `token` from the response
4. Open a protected request → **Auth** tab → **Bearer Token** → paste the token
5. Send the request

No environment variables — just copy/paste the token manually. See [`explanation.md`](explanation.md) for users, roles, and what each can do.

## Seed data

On every startup, [`DataSeeder`](src/main/java/com/example/demo/config/DataSeeder.java) automatically loads dummy data. You will see this in the console:

```
Seeding database with demo data...
Seed complete — 6 users, 4 tags, 5 events, 5 registrations
```

Because the database is in-memory, **restarting the app gives you a fresh database with seed data** — no SQL scripts needed.

### Optional: browse the database

Open [http://localhost:8080/h2-console](http://localhost:8080/h2-console) while the app is running:

| Setting   | Value                      |
|-----------|----------------------------|
| JDBC URL  | `jdbc:h2:mem:campus_events` |
| Username  | `sa`                       |
| Password  | *(leave blank)*            |

### Seeded users

All demo users use password: **`Password123!`**

| Username     | Roles                          | Display name    |
|--------------|--------------------------------|-----------------|
| `admin`      | ADMIN, ATTENDEE                | Admin User      |
| `organizer1` | ORGANIZER, ATTENDEE            | Alex Organizer  |
| `organizer2` | ORGANIZER, ATTENDEE            | Jordan Events   |
| `student1`   | ATTENDEE                       | Sam Student     |
| `student2`   | ATTENDEE                       | Riley Johnson   |
| `student3`   | ATTENDEE                       | Casey Lee       |

### Seeded events

| Event                  | Published | Organizer   |
|------------------------|-----------|-------------|
| Spring Campus Fair     | yes       | organizer1  |
| Git Basics Workshop    | yes       | organizer1  |
| Outdoor Movie Night    | yes       | organizer2  |
| Tech Career Panel      | yes       | organizer2  |
| Resume Review Session  | no (draft)| organizer1  |

Tags: `workshop`, `networking`, `social`, `career`

## Demo accounts

Same as seeded users above — password **`Password123!`** for all.

## Classroom demo: JWT step by step

### 1. Browse events without a token (public)

```bash
curl http://localhost:8080/api/events
```

No login required — these endpoints are public.

### 2. Log in and get a JWT

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d "{\"username\":\"organizer1\",\"password\":\"Password123!\"}"
```

Response:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "username": "organizer1",
  "roles": ["ROLE_ORGANIZER", "ROLE_ATTENDEE"]
}
```

Copy the `token` value.

### 3. Paste the token into jwt.io

Open [https://jwt.io](https://jwt.io) and paste the token.

Point out the three parts:

- **Header** — algorithm (`HS256`)
- **Payload** — `sub` (username), `roles`, `iat`, `exp`
- **Signature** — proves the token was issued by our server

The token is **self-contained**: the server does not store sessions. Everything needed to identify the user travels in the token.

### 4. Try a protected endpoint without the token

```bash
curl -X POST http://localhost:8080/api/events \
  -H "Content-Type: application/json" \
  -d "{\"title\":\"Test\",\"startsAt\":\"2026-12-01T10:00:00\",\"location\":\"Room 1\",\"capacity\":10}"
```

Result: **401 Unauthorized**

### 5. Same request with the Bearer token

```bash
curl -X POST http://localhost:8080/api/events \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -d "{\"title\":\"Test Event\",\"description\":\"Demo\",\"startsAt\":\"2026-12-01T10:00:00\",\"location\":\"Room 1\",\"capacity\":10,\"tagNames\":[\"workshop\"]}"
```

Spring Security reads the `Authorization: Bearer …` header, validates the JWT, and allows the request.

### 6. Register for an event (any logged-in user)

```bash
curl -X POST http://localhost:8080/api/events/2/registrations \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

---

## Public vs protected endpoints

| Endpoint | Access |
|----------|--------|
| `POST /api/auth/register` | Public |
| `POST /api/auth/login` | Public |
| `GET /api/events` | Public |
| `GET /api/events/{id}` | Public |
| Everything else | Valid JWT required |
| Create/edit/delete events | JWT + ORGANIZER or ADMIN role |

---

## JPA relationships in this project

| Relationship | Example |
|--------------|---------|
| `@OneToOne` | `User` ↔ `AttendeeProfile` |
| `@ManyToOne` | `Event` → `User` (organizer), `Registration` → `Event` |
| `@ManyToMany` | `User` ↔ `Role`, `Event` ↔ `Tag` |

---

## Project structure

```
controller/   REST endpoints
service/      business logic
repository/   Spring Data JPA
model/        JPA entities
dto/          request/response objects
mapper/       static DTO ↔ entity mappers
security/     JWT service, auth filter
config/       SecurityConfig, seed data
```

# Campus Events API — User & Role Guide

This document explains the demo users, their passwords, what each role can do, and how access control works in this project.

---

## Demo users

All seeded users share the same password:

**Password: `Password123!`**

| Username     | Email                  | Display name    | Roles                          |
|--------------|------------------------|-----------------|--------------------------------|
| `admin`      | admin@campus.edu       | Admin User      | ADMIN, ATTENDEE                |
| `organizer1` | organizer@campus.edu   | Alex Organizer  | ORGANIZER, ATTENDEE            |
| `organizer2` | events@campus.edu      | Jordan Events   | ORGANIZER, ATTENDEE            |
| `student1`   | student1@campus.edu    | Sam Student     | ATTENDEE                       |
| `student2`   | student2@campus.edu    | Riley Johnson   | ATTENDEE                       |
| `student3`   | student3@campus.edu    | Casey Lee       | ATTENDEE                       |

These users are created automatically by `DataSeeder` when the app starts.

You can also register a new user via `POST /api/auth/register`. New accounts always receive the **ATTENDEE** role.

---

## Roles explained

| Role        | Stored in DB as   | Purpose                                      |
|-------------|-------------------|----------------------------------------------|
| **ADMIN**   | `ROLE_ADMIN`      | Full access — can manage any event           |
| **ORGANIZER** | `ROLE_ORGANIZER` | Can create and manage their own events       |
| **ATTENDEE**  | `ROLE_ATTENDEE`  | Can browse events, register, manage profile  |

Spring Security expects roles to be prefixed with `ROLE_` in the database. In `@PreAuthorize` annotations we write `hasRole('ADMIN')` — Spring adds the prefix automatically.

A user can have **more than one role**. For example, `organizer1` is both an ORGANIZER and an ATTENDEE, so they can create events *and* register for other people's events.

---

## What each user can and cannot do

### ADMIN (`admin`)

**Can:**
- Log in and receive a JWT
- Browse published events (public, no token needed)
- Register for events
- View and update their own profile
- View their own registrations
- **Create, update, publish, and delete any event** (not just their own)
- **View registrations for any event**

**Cannot:**
- Access protected endpoints without a valid JWT (same as everyone)

---

### ORGANIZER (`organizer1`, `organizer2`)

**Can:**
- Log in and receive a JWT
- Browse published events
- Register for events (they also have ATTENDEE)
- View and update their own profile
- View their own registrations
- **Create new events** (start as unpublished drafts)
- **Update, publish/unpublish, and delete their own events**
- **View registrations for events they organized**

**Cannot:**
- Update, publish, or delete **another organizer's** event → `403 Forbidden`
- View registrations for another organizer's event → `403 Forbidden`

---

### ATTENDEE (`student1`, `student2`, `student3`)

**Can:**
- Log in and receive a JWT
- Browse published events (public)
- Register for published events (if not full and not already registered)
- View their own registrations
- View and update their own profile

**Cannot:**
- Create events → `403 Forbidden`
- Update, publish, or delete events → `403 Forbidden`
- View who registered for an event → `403 Forbidden`

---

## Endpoint access summary

| Endpoint | Public? | Who can access? |
|----------|---------|-----------------|
| `POST /api/auth/register` | Yes | Anyone |
| `POST /api/auth/login` | Yes | Anyone |
| `GET /api/events` | Yes | Anyone (no token) |
| `GET /api/events/{id}` | Yes | Anyone (no token) |
| `GET /api/profile/me` | No | Any logged-in user |
| `PUT /api/profile/me` | No | Any logged-in user |
| `POST /api/events/{id}/registrations` | No | Any logged-in user |
| `GET /api/registrations/me` | No | Any logged-in user |
| `POST /api/events` | No | ORGANIZER or ADMIN |
| `PUT /api/events/{id}` | No | Owner of event or ADMIN |
| `PATCH /api/events/{id}/publish` | No | Owner of event or ADMIN |
| `DELETE /api/events/{id}` | No | Owner of event or ADMIN |
| `GET /api/events/{id}/registrations` | No | Owner of event or ADMIN |

**Public** = no `Authorization` header required.

**No** = valid JWT required (`Authorization: Bearer <token>`). Without a token → `401 Unauthorized`. With a token but wrong role → `403 Forbidden`.

---

## JWT claims (what's inside the token)

When a user logs in, the response includes a JWT. Paste it into [jwt.io](https://jwt.io) to inspect the payload.

| Claim   | Meaning                          | Example                          |
|---------|----------------------------------|----------------------------------|
| `sub`   | Username (subject)               | `"organizer1"`                   |
| `roles` | User's roles                     | `["ROLE_ORGANIZER","ROLE_ATTENDEE"]` |
| `iat`   | Issued at (timestamp)              | When the token was created       |
| `exp`   | Expires (timestamp)              | 24 hours after login by default  |

The token is **self-contained** — the server does not store sessions. Each protected request must send the token in the header:

```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

---

## Suggested classroom experiments

1. **Public vs protected** — Call `GET /api/events` with no token (works). Call `GET /api/profile/me` with no token (`401`).

2. **Wrong role** — Login as `student1`, try `POST /api/events` with the token (`403`).

3. **Right role** — Login as `organizer1`, create an event (`201`).

4. **Ownership** — Login as `organizer2`, try to update an event created by `organizer1` (`403`).

5. **Admin override** — Login as `admin`, update any event (works).

6. **JWT inspection** — Login, copy the token, paste into jwt.io, point out `sub` and `roles`.

---

## Seeded sample data

**Tags:** `workshop`, `networking`, `social`, `career`

**Published events (visible in public listing):**
- Spring Campus Fair (organizer1)
- Git Basics Workshop (organizer1)
- Outdoor Movie Night (organizer2)
- Tech Career Panel (organizer2)

**Draft event (not in public listing):**
- Resume Review Session (organizer1, unpublished)

**Sample registrations:**
- student1 and student2 → Git Basics Workshop
- student3 → Spring Campus Fair
- student1 → Tech Career Panel
- student2 → Outdoor Movie Night

---

## Tools

- **Insomnia collection:** import `insomnia_collection.json` — login, copy the token, paste it into the Auth tab on protected requests
- **H2 console:** [http://localhost:8080/h2-console](http://localhost:8080/h2-console) — JDBC URL: `jdbc:h2:mem:campus_events`, user: `sa`, password: *(blank)*

package com.example.demo.config;

import com.example.demo.model.AttendeeProfile;
import com.example.demo.model.Event;
import com.example.demo.model.Registration;
import com.example.demo.model.Role;
import com.example.demo.model.Tag;
import com.example.demo.model.User;
import com.example.demo.repository.AttendeeProfileRepository;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.RegistrationRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.TagRepository;
import com.example.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Set;

@Configuration
public class DataSeeder {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    @Bean
    CommandLineRunner seedDatabase(
            RoleRepository roleRepository,
            UserRepository userRepository,
            AttendeeProfileRepository attendeeProfileRepository,
            TagRepository tagRepository,
            EventRepository eventRepository,
            RegistrationRepository registrationRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            if (roleRepository.count() > 0) {
                log.info("Database already has data — skipping seed.");
                return;
            }

            log.info("Seeding database with demo data...");

            Role adminRole = roleRepository.save(new Role("ROLE_ADMIN"));
            Role organizerRole = roleRepository.save(new Role("ROLE_ORGANIZER"));
            Role attendeeRole = roleRepository.save(new Role("ROLE_ATTENDEE"));

            String encodedPassword = passwordEncoder.encode("Password123!");

            User admin = userRepository.save(createUser("admin", "admin@campus.edu", encodedPassword, Set.of(adminRole, attendeeRole)));
            User organizer1 = userRepository.save(createUser("organizer1", "organizer@campus.edu", encodedPassword, Set.of(organizerRole, attendeeRole)));
            User organizer2 = userRepository.save(createUser("organizer2", "events@campus.edu", encodedPassword, Set.of(organizerRole, attendeeRole)));
            User student1 = userRepository.save(createUser("student1", "student1@campus.edu", encodedPassword, Set.of(attendeeRole)));
            User student2 = userRepository.save(createUser("student2", "student2@campus.edu", encodedPassword, Set.of(attendeeRole)));
            User student3 = userRepository.save(createUser("student3", "student3@campus.edu", encodedPassword, Set.of(attendeeRole)));

            saveProfile(attendeeProfileRepository, admin, "Admin User", "IT");
            saveProfile(attendeeProfileRepository, organizer1, "Alex Organizer", "Student Affairs");
            saveProfile(attendeeProfileRepository, organizer2, "Jordan Events", "Student Affairs");
            AttendeeProfile samProfile = saveProfile(attendeeProfileRepository, student1, "Sam Student", "Computer Science");
            AttendeeProfile rileyProfile = saveProfile(attendeeProfileRepository, student2, "Riley Johnson", "Business");
            AttendeeProfile caseyProfile = saveProfile(attendeeProfileRepository, student3, "Casey Lee", "Design");

            Tag workshop = tagRepository.save(new Tag("workshop"));
            Tag networking = tagRepository.save(new Tag("networking"));
            Tag social = tagRepository.save(new Tag("social"));
            Tag career = tagRepository.save(new Tag("career"));

            Event springFair = saveEvent(eventRepository, organizer1,
                    "Spring Campus Fair",
                    "Meet student clubs and local organizations on the main lawn.",
                    LocalDateTime.now().plusDays(14), "Main Lawn", 100, true,
                    Set.of(social, networking));

            Event gitWorkshop = saveEvent(eventRepository, organizer1,
                    "Git Basics Workshop",
                    "Hands-on intro to branches, commits, and pull requests.",
                    LocalDateTime.now().plusDays(7), "Room 204", 30, true,
                    Set.of(workshop));

            Event movieNight = saveEvent(eventRepository, organizer2,
                    "Outdoor Movie Night",
                    "Free popcorn and a classic film on the quad.",
                    LocalDateTime.now().plusDays(10), "Central Quad", 200, true,
                    Set.of(social));

            Event careerPanel = saveEvent(eventRepository, organizer2,
                    "Tech Career Panel",
                    "Alumni share tips on portfolios, interviews, and first jobs.",
                    LocalDateTime.now().plusDays(5), "Auditorium B", 80, true,
                    Set.of(career, networking));

            saveEvent(eventRepository, organizer1,
                    "Resume Review Session",
                    "Draft event — not visible in public listing until published.",
                    LocalDateTime.now().plusDays(21), "Career Center", 20, false,
                    Set.of(workshop, career));

            saveRegistration(registrationRepository, samProfile, gitWorkshop, LocalDateTime.now().minusDays(1));
            saveRegistration(registrationRepository, rileyProfile, gitWorkshop, LocalDateTime.now().minusHours(12));
            saveRegistration(registrationRepository, caseyProfile, springFair, LocalDateTime.now().minusDays(2));
            saveRegistration(registrationRepository, samProfile, careerPanel, LocalDateTime.now().minusHours(6));
            saveRegistration(registrationRepository, rileyProfile, movieNight, LocalDateTime.now().minusDays(3));

            log.info("Seed complete — 6 users, 4 tags, 5 events, 5 registrations (password for all users: Password123!)");
        };
    }

    private User createUser(String username, String email, String password, Set<Role> roles) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setRoles(roles);
        return user;
    }

    private AttendeeProfile saveProfile(
            AttendeeProfileRepository repository,
            User user,
            String displayName,
            String department
    ) {
        AttendeeProfile profile = new AttendeeProfile();
        profile.setDisplayName(displayName);
        profile.setDepartment(department);
        profile.setUser(user);
        return repository.save(profile);
    }

    private Event saveEvent(
            EventRepository repository,
            User organizer,
            String title,
            String description,
            LocalDateTime startsAt,
            String location,
            int capacity,
            boolean published,
            Set<Tag> tags
    ) {
        Event event = new Event();
        event.setTitle(title);
        event.setDescription(description);
        event.setStartsAt(startsAt);
        event.setLocation(location);
        event.setCapacity(capacity);
        event.setPublished(published);
        event.setOrganizer(organizer);
        event.setTags(tags);
        return repository.save(event);
    }

    private void saveRegistration(
            RegistrationRepository repository,
            AttendeeProfile profile,
            Event event,
            LocalDateTime registeredAt
    ) {
        Registration registration = new Registration();
        registration.setRegisteredAt(registeredAt);
        registration.setAttendeeProfile(profile);
        registration.setEvent(event);
        repository.save(registration);
    }
}

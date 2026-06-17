package com.example.demo.repository;

import com.example.demo.model.Registration;
import com.example.demo.model.AttendeeProfile;
import com.example.demo.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    List<Registration> findByAttendeeProfile(AttendeeProfile attendeeProfile);

    List<Registration> findByEvent(Event event);

    long countByEvent(Event event);

    Optional<Registration> findByAttendeeProfileAndEvent(AttendeeProfile attendeeProfile, Event event);
}

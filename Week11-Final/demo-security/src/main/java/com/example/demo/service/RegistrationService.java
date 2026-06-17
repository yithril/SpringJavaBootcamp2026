package com.example.demo.service;

import com.example.demo.dto.RegistrationResponse;
import com.example.demo.exception.ConflictException;
import com.example.demo.exception.ForbiddenException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.RegistrationMapper;
import com.example.demo.model.AttendeeProfile;
import com.example.demo.model.Event;
import com.example.demo.model.Registration;
import com.example.demo.repository.AttendeeProfileRepository;
import com.example.demo.repository.RegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final AttendeeProfileRepository attendeeProfileRepository;
    private final EventService eventService;

    public RegistrationService(
            RegistrationRepository registrationRepository,
            AttendeeProfileRepository attendeeProfileRepository,
            EventService eventService
    ) {
        this.registrationRepository = registrationRepository;
        this.attendeeProfileRepository = attendeeProfileRepository;
        this.eventService = eventService;
    }

    @Transactional
    public RegistrationResponse registerForEvent(Long eventId, String username) {
        Event event = eventService.findEvent(eventId);

        if (!event.isPublished()) {
            throw new ResourceNotFoundException("Event not found");
        }

        AttendeeProfile profile = attendeeProfileRepository.findByUserUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Attendee profile not found"));

        if (registrationRepository.findByAttendeeProfileAndEvent(profile, event).isPresent()) {
            throw new ConflictException("Already registered for this event");
        }

        if (registrationRepository.countByEvent(event) >= event.getCapacity()) {
            throw new ConflictException("Event is full");
        }

        Registration registration = new Registration();
        registration.setRegisteredAt(LocalDateTime.now());
        registration.setAttendeeProfile(profile);
        registration.setEvent(event);

        return RegistrationMapper.toResponse(registrationRepository.save(registration));
    }

    public List<RegistrationResponse> getMyRegistrations(String username) {
        AttendeeProfile profile = attendeeProfileRepository.findByUserUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Attendee profile not found"));

        return registrationRepository.findByAttendeeProfile(profile).stream()
                .map(RegistrationMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<RegistrationResponse> getRegistrationsForEvent(Long eventId, String username, boolean isAdmin) {
        Event event = eventService.findEvent(eventId);

        if (!isAdmin && !event.getOrganizer().getUsername().equals(username)) {
            throw new ForbiddenException("You can only view registrations for your own events");
        }

        return registrationRepository.findByEvent(event).stream()
                .map(RegistrationMapper::toResponse)
                .collect(Collectors.toList());
    }
}

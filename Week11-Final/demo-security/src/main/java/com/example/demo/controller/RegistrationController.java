package com.example.demo.controller;

import com.example.demo.dto.RegistrationResponse;
import com.example.demo.service.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/events/{eventId}/registrations")
    public ResponseEntity<RegistrationResponse> registerForEvent(
            @PathVariable Long eventId,
            Authentication authentication
    ) {
        RegistrationResponse response = registrationService.registerForEvent(eventId, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/registrations/me")
    public ResponseEntity<List<RegistrationResponse>> getMyRegistrations(Authentication authentication) {
        return ResponseEntity.ok(registrationService.getMyRegistrations(authentication.getName()));
    }

    @GetMapping("/events/{eventId}/registrations")
    @PreAuthorize("hasAnyRole('ORGANIZER', 'ADMIN')")
    public ResponseEntity<List<RegistrationResponse>> getEventRegistrations(
            @PathVariable Long eventId,
            Authentication authentication
    ) {
        return ResponseEntity.ok(registrationService.getRegistrationsForEvent(
                eventId,
                authentication.getName(),
                isAdmin(authentication)
        ));
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));
    }
}

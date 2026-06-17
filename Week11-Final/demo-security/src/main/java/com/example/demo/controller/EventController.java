package com.example.demo.controller;

import com.example.demo.dto.EventCreateRequest;
import com.example.demo.dto.EventResponse;
import com.example.demo.dto.EventUpdateRequest;
import com.example.demo.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<EventResponse>> getPublishedEvents() {
        return ResponseEntity.ok(eventService.getPublishedEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ORGANIZER', 'ADMIN')")
    public ResponseEntity<EventResponse> createEvent(
            @Valid @RequestBody EventCreateRequest request,
            Authentication authentication
    ) {
        EventResponse response = eventService.createEvent(request, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ORGANIZER', 'ADMIN')")
    public ResponseEntity<EventResponse> updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody EventUpdateRequest request,
            Authentication authentication
    ) {
        return ResponseEntity.ok(eventService.updateEvent(
                id,
                request,
                authentication.getName(),
                isAdmin(authentication)
        ));
    }

    @PatchMapping("/{id}/publish")
    @PreAuthorize("hasAnyRole('ORGANIZER', 'ADMIN')")
    public ResponseEntity<EventResponse> togglePublished(
            @PathVariable Long id,
            Authentication authentication
    ) {
        return ResponseEntity.ok(eventService.togglePublished(
                id,
                authentication.getName(),
                isAdmin(authentication)
        ));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ORGANIZER', 'ADMIN')")
    public ResponseEntity<Void> deleteEvent(
            @PathVariable Long id,
            Authentication authentication
    ) {
        eventService.deleteEvent(id, authentication.getName(), isAdmin(authentication));
        return ResponseEntity.noContent().build();
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));
    }
}

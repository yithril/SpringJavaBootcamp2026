package com.example.demo.mapper;

import com.example.demo.dto.EventCreateRequest;
import com.example.demo.dto.EventResponse;
import com.example.demo.dto.EventUpdateRequest;
import com.example.demo.model.Event;
import com.example.demo.model.Tag;
import com.example.demo.model.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class EventMapper {

    private EventMapper() {
    }

    public static Event toEntity(EventCreateRequest request, User organizer, Set<Tag> tags) {
        Event event = new Event();
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setStartsAt(request.getStartsAt());
        event.setLocation(request.getLocation());
        event.setCapacity(request.getCapacity());
        event.setOrganizer(organizer);
        event.setTags(tags);
        return event;
    }

    public static void updateEntity(Event event, EventUpdateRequest request, Set<Tag> tags) {
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setStartsAt(request.getStartsAt());
        event.setLocation(request.getLocation());
        event.setCapacity(request.getCapacity());
        event.setTags(tags);
    }

    public static EventResponse toResponse(Event event) {
        EventResponse response = new EventResponse();
        response.setId(event.getId());
        response.setTitle(event.getTitle());
        response.setDescription(event.getDescription());
        response.setStartsAt(event.getStartsAt());
        response.setLocation(event.getLocation());
        response.setCapacity(event.getCapacity());
        response.setPublished(event.isPublished());
        response.setOrganizerUsername(event.getOrganizer().getUsername());
        response.setTags(event.getTags().stream()
                .map(Tag::getName)
                .sorted()
                .collect(Collectors.toList()));
        return response;
    }
}

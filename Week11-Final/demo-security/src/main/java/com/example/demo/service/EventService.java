package com.example.demo.service;

import com.example.demo.dto.EventCreateRequest;
import com.example.demo.dto.EventResponse;
import com.example.demo.dto.EventUpdateRequest;
import com.example.demo.exception.ForbiddenException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.EventMapper;
import com.example.demo.model.Event;
import com.example.demo.model.Tag;
import com.example.demo.model.User;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.TagRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    public EventService(EventRepository eventRepository, UserRepository userRepository, TagRepository tagRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
    }

    public List<EventResponse> getPublishedEvents() {
        return eventRepository.findByPublishedTrue().stream()
                .map(EventMapper::toResponse)
                .collect(Collectors.toList());
    }

    public EventResponse getEventById(Long id) {
        Event event = findEvent(id);
        if (!event.isPublished()) {
            throw new ResourceNotFoundException("Event not found");
        }
        return EventMapper.toResponse(event);
    }

    @Transactional
    public EventResponse createEvent(EventCreateRequest request, String username) {
        User organizer = findUser(username);
        Set<Tag> tags = resolveTags(request.getTagNames());
        Event event = EventMapper.toEntity(request, organizer, tags);
        return EventMapper.toResponse(eventRepository.save(event));
    }

    @Transactional
    public EventResponse updateEvent(Long id, EventUpdateRequest request, String username, boolean isAdmin) {
        Event event = findEvent(id);
        checkOwnership(event, username, isAdmin);
        Set<Tag> tags = resolveTags(request.getTagNames());
        EventMapper.updateEntity(event, request, tags);
        return EventMapper.toResponse(eventRepository.save(event));
    }

    @Transactional
    public EventResponse togglePublished(Long id, String username, boolean isAdmin) {
        Event event = findEvent(id);
        checkOwnership(event, username, isAdmin);
        event.setPublished(!event.isPublished());
        return EventMapper.toResponse(eventRepository.save(event));
    }

    @Transactional
    public void deleteEvent(Long id, String username, boolean isAdmin) {
        Event event = findEvent(id);
        checkOwnership(event, username, isAdmin);
        eventRepository.delete(event);
    }

    public Event findEvent(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
    }

    private User findUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private void checkOwnership(Event event, String username, boolean isAdmin) {
        if (!isAdmin && !event.getOrganizer().getUsername().equals(username)) {
            throw new ForbiddenException("You can only manage your own events");
        }
    }

    private Set<Tag> resolveTags(List<String> tagNames) {
        Set<Tag> tags = new HashSet<>();
        if (tagNames == null) {
            return tags;
        }
        for (String name : tagNames) {
            Tag tag = tagRepository.findByName(name)
                    .orElseThrow(() -> new ResourceNotFoundException("Tag not found: " + name));
            tags.add(tag);
        }
        return tags;
    }
}

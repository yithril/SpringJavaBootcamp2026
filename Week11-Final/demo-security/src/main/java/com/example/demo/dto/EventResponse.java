package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.List;

public class EventResponse {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime startsAt;
    private String location;
    private int capacity;
    private boolean published;
    private String organizerUsername;
    private List<String> tags;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(LocalDateTime startsAt) {
        this.startsAt = startsAt;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public String getOrganizerUsername() {
        return organizerUsername;
    }

    public void setOrganizerUsername(String organizerUsername) {
        this.organizerUsername = organizerUsername;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}

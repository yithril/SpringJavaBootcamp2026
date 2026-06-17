package com.example.demo.dto;

import java.time.LocalDateTime;

public class RegistrationResponse {

    private Long id;
    private LocalDateTime registeredAt;
    private String status;
    private Long eventId;
    private String eventTitle;
    private String attendeeDisplayName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getAttendeeDisplayName() {
        return attendeeDisplayName;
    }

    public void setAttendeeDisplayName(String attendeeDisplayName) {
        this.attendeeDisplayName = attendeeDisplayName;
    }
}

package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "registrations",
        uniqueConstraints = @UniqueConstraint(columnNames = {"attendee_profile_id", "event_id"})
)
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime registeredAt;

    @Column(nullable = false)
    private String status = "CONFIRMED";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attendee_profile_id", nullable = false)
    private AttendeeProfile attendeeProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

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

    public AttendeeProfile getAttendeeProfile() {
        return attendeeProfile;
    }

    public void setAttendeeProfile(AttendeeProfile attendeeProfile) {
        this.attendeeProfile = attendeeProfile;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}

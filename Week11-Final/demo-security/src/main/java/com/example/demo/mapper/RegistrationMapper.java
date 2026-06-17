package com.example.demo.mapper;

import com.example.demo.dto.RegistrationResponse;
import com.example.demo.model.Registration;

public final class RegistrationMapper {

    private RegistrationMapper() {
    }

    public static RegistrationResponse toResponse(Registration registration) {
        RegistrationResponse response = new RegistrationResponse();
        response.setId(registration.getId());
        response.setRegisteredAt(registration.getRegisteredAt());
        response.setStatus(registration.getStatus());
        response.setEventId(registration.getEvent().getId());
        response.setEventTitle(registration.getEvent().getTitle());
        response.setAttendeeDisplayName(registration.getAttendeeProfile().getDisplayName());
        return response;
    }
}

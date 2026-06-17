package com.example.demo.mapper;

import com.example.demo.dto.ProfileResponse;
import com.example.demo.model.AttendeeProfile;

public final class ProfileMapper {

    private ProfileMapper() {
    }

    public static ProfileResponse toResponse(AttendeeProfile profile) {
        ProfileResponse response = new ProfileResponse();
        response.setId(profile.getId());
        response.setUsername(profile.getUser().getUsername());
        response.setEmail(profile.getUser().getEmail());
        response.setDisplayName(profile.getDisplayName());
        response.setDepartment(profile.getDepartment());
        return response;
    }
}

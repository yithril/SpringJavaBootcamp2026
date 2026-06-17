package com.example.demo.service;

import com.example.demo.dto.ProfileResponse;
import com.example.demo.dto.ProfileUpdateRequest;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.ProfileMapper;
import com.example.demo.model.AttendeeProfile;
import com.example.demo.repository.AttendeeProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileService {

    private final AttendeeProfileRepository attendeeProfileRepository;

    public ProfileService(AttendeeProfileRepository attendeeProfileRepository) {
        this.attendeeProfileRepository = attendeeProfileRepository;
    }

    public ProfileResponse getMyProfile(String username) {
        AttendeeProfile profile = findProfile(username);
        return ProfileMapper.toResponse(profile);
    }

    @Transactional
    public ProfileResponse updateMyProfile(String username, ProfileUpdateRequest request) {
        AttendeeProfile profile = findProfile(username);
        profile.setDisplayName(request.getDisplayName());
        profile.setDepartment(request.getDepartment());
        return ProfileMapper.toResponse(attendeeProfileRepository.save(profile));
    }

    private AttendeeProfile findProfile(String username) {
        return attendeeProfileRepository.findByUserUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Attendee profile not found"));
    }
}

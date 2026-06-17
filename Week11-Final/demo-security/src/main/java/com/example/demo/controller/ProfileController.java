package com.example.demo.controller;

import com.example.demo.dto.ProfileResponse;
import com.example.demo.dto.ProfileUpdateRequest;
import com.example.demo.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/me")
    public ResponseEntity<ProfileResponse> getMyProfile(Authentication authentication) {
        return ResponseEntity.ok(profileService.getMyProfile(authentication.getName()));
    }

    @PutMapping("/me")
    public ResponseEntity<ProfileResponse> updateMyProfile(
            @Valid @RequestBody ProfileUpdateRequest request,
            Authentication authentication
    ) {
        return ResponseEntity.ok(profileService.updateMyProfile(authentication.getName(), request));
    }
}

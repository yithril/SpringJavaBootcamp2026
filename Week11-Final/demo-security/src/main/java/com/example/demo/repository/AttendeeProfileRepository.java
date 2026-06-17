package com.example.demo.repository;

import com.example.demo.model.AttendeeProfile;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttendeeProfileRepository extends JpaRepository<AttendeeProfile, Long> {

    Optional<AttendeeProfile> findByUser(User user);

    Optional<AttendeeProfile> findByUserUsername(String username);
}

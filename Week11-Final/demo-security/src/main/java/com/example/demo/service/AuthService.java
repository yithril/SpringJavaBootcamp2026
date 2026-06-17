package com.example.demo.service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.exception.ConflictException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.AttendeeProfile;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.AttendeeProfileRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.CustomUserDetailsService;
import com.example.demo.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AttendeeProfileRepository attendeeProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public AuthService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            AttendeeProfileRepository attendeeProfileRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            CustomUserDetailsService userDetailsService
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.attendeeProfileRepository = attendeeProfileRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Transactional
    public void register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ConflictException("Username already taken");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email already in use");
        }

        Role attendeeRole = roleRepository.findByName("ROLE_ATTENDEE")
                .orElseThrow(() -> new ResourceNotFoundException("Default role not found"));

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(attendeeRole));
        userRepository.save(user);

        AttendeeProfile profile = new AttendeeProfile();
        profile.setDisplayName(request.getDisplayName());
        profile.setDepartment(request.getDepartment());
        profile.setUser(user);
        attendeeProfileRepository.save(profile);
    }

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtService.generateToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(auth -> auth.getAuthority())
                .collect(Collectors.toList());

        return new LoginResponse(token, request.getUsername(), roles);
    }
}

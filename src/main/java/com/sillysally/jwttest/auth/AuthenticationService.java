package com.sillysally.jwttest.auth;

import com.sillysally.jwttest.config.JwtService;
import com.sillysally.jwttest.user.Role;
import com.sillysally.jwttest.user.User;
import com.sillysally.jwttest.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public void deleteUser(String email) {
        repository.deleteByEmail(email);
    }

    public UserResponse updateUser(String email, UpdateRequest request) {
        var user = repository.findByEmail(email)
                .orElseThrow();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        var updatedUser = repository.save(user);
        return UserResponse.builder()
                .firstname(updatedUser.getFirstname())
                .lastname(updatedUser.getLastname())
                .email(updatedUser.getEmail())
                .build();
    }

    public List<UserResponse> getUsers() {
        List<User> users = repository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : users) {
            UserResponse userResponse = UserResponse.builder()
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .email(user.getEmail())
                    .build();
            userResponses.add(userResponse);
        }

        return userResponses;
    }
}

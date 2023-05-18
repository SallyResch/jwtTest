package com.sillysally.jwttest.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }


    @GetMapping("/getUsersCredentials")
    public ResponseEntity<List<UserResponse>> getUsers() {
        List<UserResponse> users = service.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/getUserInfo")
    public ResponseEntity<UserResponse> getUserInfo(@AuthenticationPrincipal UserDetails userDetails) {
        UserResponse userResponse = service.getUserInfo(userDetails.getUsername());
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUserByEmail(@RequestParam String email) {
        try {
            service.deleteUserByEmail(email);
            return ResponseEntity.ok().body("User with email " + email + " was deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not delete user with email " + email + ": " + e.getMessage());
        }
    }
}

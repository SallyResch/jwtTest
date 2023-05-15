package com.sillysally.jwttest.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUser(
            @AuthenticationPrincipal UserDetails userDetails
    ){
        service.deleteUser(userDetails.getUsername());
        System.out.println("User is deleted");
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/update")
    public ResponseEntity<UserResponse> updateUser(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UpdateRequest request
    ){
        UserResponse userResponse = service.updateUser(userDetails.getUsername(), request);
        return ResponseEntity.ok(userResponse);
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

}

package com.sillysally.jwttest.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@PreAuthorize("hasRole('USER')")
public class UserController {
    @GetMapping
    @PreAuthorize("hasAuthority('user:read')")
    public String get() {
        return "GET:: user controller";
    }
    @PostMapping
    @PreAuthorize("hasAuthority('user:create')")
    public String post() {
        return "POST:: user controller";
    }
    @PutMapping
    @PreAuthorize("hasAuthority('user:update')")
    public String put() {
        return "PUT:: user controller";
    }
    @DeleteMapping
    @PreAuthorize("hasAuthority('user:delete')")
    public String delete() {
        return "DELETE:: user controller";
    }

}

package com.sillysally.jwttest.demo;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin-controller")
public class AdminController {
    @GetMapping
    public String get(){
        return "GET:: admin controller";
    }
    @PostMapping
    public String post(){
        return "POST:: admin controller";
    }
    @PutMapping
    public String put(){
        return "PUT:: admin controller";
    }
    @DeleteMapping
    public String delete(){
        return "DELETE:: admin controller";
    }
}

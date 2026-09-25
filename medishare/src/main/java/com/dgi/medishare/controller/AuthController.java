package com.dgi.medishare.controller;

import com.dgi.medishare.dto.LoginRequest;
import com.dgi.medishare.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.dgi.medishare.dto.RegisterNgoRequest;
import com.dgi.medishare.entity.Ngo;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return authService.login(request.getEmail(), request.getPassword());
    }

    @PostMapping("/register/ngo")
    public Ngo registerNgo(@RequestBody RegisterNgoRequest request) {
        return authService.registerNgo(request);
    }
}
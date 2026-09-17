package com.dgi.medishare.service;

import com.dgi.medishare.entity.Admin;
import com.dgi.medishare.entity.Ngo;
import com.dgi.medishare.repository.AdminRepository;
import com.dgi.medishare.repository.NgoRepository;
import com.dgi.medishare.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dgi.medishare.entity.VerificationStatus;

@Service
public class AuthService {

    @Autowired
    private NgoRepository ngoRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private JwtUtil jwtUtil;

    // Returns a JWT token if login is valid, otherwise throws an error
    public String login(String email, String password) {

        // Check Admin first
        Admin admin = adminRepository.findAll().stream()
                .filter(a -> a.getEmail().equals(email) && a.getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if (admin != null) {
            return jwtUtil.generateToken(admin.getEmail(), admin.getRole().name());
        }

        // Check Ngo
        Ngo ngo = ngoRepository.findAll().stream()
                .filter(n -> n.getEmail().equals(email) && n.getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if (ngo != null) {
            if (ngo.getVerificationStatus() != VerificationStatus.APPROVED) {
                throw new IllegalArgumentException("NGO account not yet verified by admin");
            }
            return jwtUtil.generateToken(ngo.getEmail(), ngo.getRole().name());
        }

        throw new IllegalArgumentException("Invalid email or password");
    }
}
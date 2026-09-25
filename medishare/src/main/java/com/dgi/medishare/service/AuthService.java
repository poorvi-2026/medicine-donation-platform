package com.dgi.medishare.service;

import com.dgi.medishare.entity.Admin;
import com.dgi.medishare.entity.Donor;
import com.dgi.medishare.entity.Ngo;
import com.dgi.medishare.entity.VerificationStatus;
import com.dgi.medishare.repository.AdminRepository;
import com.dgi.medishare.repository.NgoRepository;
import com.dgi.medishare.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private NgoRepository ngoRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String login(String email, String password) {

        Admin admin = adminRepository.findAll().stream()
                .filter(a -> a.getEmail().equals(email))
                .findFirst()
                .orElse(null);

        if (admin != null && passwordEncoder.matches(password, admin.getPassword())) {
            return jwtUtil.generateToken(admin.getEmail(), admin.getRole().name());
        }

        Ngo ngo = ngoRepository.findAll().stream()
                .filter(n -> n.getEmail().equals(email))
                .findFirst()
                .orElse(null);

        if (ngo != null && passwordEncoder.matches(password, ngo.getPassword())) {
            if (ngo.getVerificationStatus() != VerificationStatus.APPROVED) {
                throw new IllegalArgumentException("NGO account not yet verified by admin");
            }
            return jwtUtil.generateToken(ngo.getEmail(), ngo.getRole().name());
        }

        com.dgi.medishare.entity.Donor donor = donorRepository.findAll().stream()
                .filter(d -> d.getEmail().equals(email))
                .findFirst()
                .orElse(null);

        if (donor != null && passwordEncoder.matches(password, donor.getPassword())) {
            return jwtUtil.generateToken(donor.getEmail(), donor.getRole().name());
        }

        throw new IllegalArgumentException("Invalid email or password");
    }

    public Ngo registerNgo(com.dgi.medishare.dto.RegisterNgoRequest request) {

        boolean emailExists = ngoRepository.findAll().stream()
                .anyMatch(n -> n.getEmail().equals(request.getEmail()));

        if (emailExists) {
            throw new IllegalArgumentException("Email already registered");
        }

        Ngo ngo = new Ngo();
        ngo.setName(request.getName());
        ngo.setEmail(request.getEmail());
        ngo.setPassword(passwordEncoder.encode(request.getPassword()));
        ngo.setPhone(request.getPhone());
        ngo.setAddress(request.getAddress());
        ngo.setLicenseNumber(request.getLicenseNumber());
        return ngoRepository.save(ngo);
    }


    @Autowired
    private com.dgi.medishare.repository.DonorRepository donorRepository;
    public Donor registerDonor(com.dgi.medishare.dto.RegisterDonorRequest request) {

        boolean emailExists = donorRepository.findAll().stream()
                .anyMatch(d -> d.getEmail().equals(request.getEmail()));

        if (emailExists) {
            throw new IllegalArgumentException("Email already registered");
        }

        Donor donor = new Donor();
        donor.setName(request.getName());
        donor.setEmail(request.getEmail());
        donor.setPassword(passwordEncoder.encode(request.getPassword()));
        donor.setPhone(request.getPhone());
        donor.setAddress(request.getAddress());
        return donorRepository.save(donor);
    }
}
package com.dgi.medishare.service;

import com.dgi.medishare.entity.Ngo;
import com.dgi.medishare.repository.NgoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private NgoRepository ngoRepository;

    // Admin approval workflow — verify an NGO
    public Ngo verifyNgo(Long ngoId) {
        Ngo ngo = ngoRepository.findById(ngoId)
                .orElseThrow(() -> new IllegalArgumentException("NGO not found with id: " + ngoId));
        ngo.setVerified(true);
        return ngoRepository.save(ngo);
    }

    // Reject an NGO (e.g. fake license)
    public Ngo rejectNgo(Long ngoId) {
        Ngo ngo = ngoRepository.findById(ngoId)
                .orElseThrow(() -> new IllegalArgumentException("NGO not found with id: " + ngoId));
        ngo.setVerified(false);
        return ngoRepository.save(ngo);
    }

    // Get all NGOs waiting for verification
    public List<Ngo> getPendingNgos() {
        return ngoRepository.findAll().stream()
                .filter(ngo -> !ngo.isVerified())
                .toList();
    }

    // Get all verified NGOs
    public List<Ngo> getVerifiedNgos() {
        return ngoRepository.findAll().stream()
                .filter(Ngo::isVerified)
                .toList();
    }
}
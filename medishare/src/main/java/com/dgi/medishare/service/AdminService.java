package com.dgi.medishare.service;
import com.dgi.medishare.entity.Ngo;
import com.dgi.medishare.entity.VerificationStatus;
import com.dgi.medishare.repository.NgoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    private NgoRepository ngoRepository;

    public Ngo verifyNgo(Long ngoId) {
        Ngo ngo = ngoRepository.findById(ngoId)
                .orElseThrow(() -> new IllegalArgumentException("NGO not found with id: " + ngoId));
        ngo.setVerificationStatus(VerificationStatus.APPROVED);
        return ngoRepository.save(ngo);
    }

    public Ngo rejectNgo(Long ngoId) {
        Ngo ngo = ngoRepository.findById(ngoId)
                .orElseThrow(() -> new IllegalArgumentException("NGO not found with id: " + ngoId));
        ngo.setVerificationStatus(VerificationStatus.REJECTED);
        return ngoRepository.save(ngo);
    }

    public List<Ngo> getPendingNgos() {
        return ngoRepository.findAll().stream()
                .filter(ngo -> ngo.getVerificationStatus() == VerificationStatus.PENDING)
                .toList();
    }

    public List<Ngo> getVerifiedNgos() {
        return ngoRepository.findAll().stream()
                .filter(ngo -> ngo.getVerificationStatus() == VerificationStatus.APPROVED)
                .toList();
    }
}
package com.dgi.medishare.service;

import com.dgi.medishare.entity.Donor;
import com.dgi.medishare.exception.ResourceNotFoundException;
import com.dgi.medishare.repository.DonorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonorService {

    @Autowired
    private DonorRepository donorRepository;

    public Donor registerDonor(Donor donor) {
        if (donorRepository.findByEmail(donor.getEmail()).isPresent()) {
            throw new IllegalArgumentException("A donor with this email already exists");
        }
        return donorRepository.save(donor);
    }

    public Donor getDonorById(Long id) {
        return donorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donor not found with id: " + id));
    }

    public List<Donor> getAllDonors() {
        return donorRepository.findAll();
    }

    public Donor updateDonor(Long id, Donor updatedDonor) {
        Donor donor = getDonorById(id);
        donor.setName(updatedDonor.getName());
        donor.setPhone(updatedDonor.getPhone());
        donor.setAddress(updatedDonor.getAddress());
        return donorRepository.save(donor);
    }

    public void deleteDonor(Long id) {
        Donor donor = getDonorById(id);
        donorRepository.delete(donor);
    }
}
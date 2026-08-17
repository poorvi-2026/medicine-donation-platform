package com.dgi.medishare.controller;

import com.dgi.medishare.entity.Donor;
import com.dgi.medishare.service.DonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donors")
public class DonorController {

    @Autowired
    private DonorService donorService;

    @PostMapping("/register")
    public Donor registerDonor(@RequestBody Donor donor) {
        return donorService.registerDonor(donor);
    }

    @GetMapping("/{id}")
    public Donor getDonor(@PathVariable Long id) {
        return donorService.getDonorById(id);
    }

    @GetMapping
    public List<Donor> getAllDonors() {
        return donorService.getAllDonors();
    }

    @PutMapping("/{id}")
    public Donor updateDonor(@PathVariable Long id, @RequestBody Donor donor) {
        return donorService.updateDonor(id, donor);
    }

    @DeleteMapping("/{id}")
    public void deleteDonor(@PathVariable Long id) {
        donorService.deleteDonor(id);
    }
}
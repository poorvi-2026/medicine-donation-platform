package com.dgi.medishare.controller;

import com.dgi.medishare.entity.DonationRequest;
import com.dgi.medishare.entity.RequestStatus;
import com.dgi.medishare.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donation")
public class DonationController {

    @Autowired
    private DonationService donationService;

    // NGO requests a medicine
    @PostMapping("/request")
    public DonationRequest createRequest(@RequestParam Long medicineId, @RequestParam Long ngoId) {
        return donationService.createRequest(medicineId, ngoId);
    }

    // Donor accepts the request
    @PutMapping("/{id}/accept")
    public DonationRequest acceptRequest(@PathVariable Long id) {
        return donationService.acceptRequest(id);
    }

    // Donor rejects the request
    @PutMapping("/{id}/reject")
    public DonationRequest rejectRequest(@PathVariable Long id) {
        return donationService.rejectRequest(id);
    }

    // Update status (PICKED_UP, DELIVERED, etc.)
    @PutMapping("/{id}/status")
    public DonationRequest updateStatus(@PathVariable Long id, @RequestParam RequestStatus status) {
        return donationService.updateStatus(id, status);
    }

    // Get all requests for a specific NGO
    @GetMapping("/ngo/{ngoId}")
    public List<DonationRequest> getRequestsByNgo(@PathVariable Long ngoId) {
        return donationService.getRequestsByNgo(ngoId);
    }
}
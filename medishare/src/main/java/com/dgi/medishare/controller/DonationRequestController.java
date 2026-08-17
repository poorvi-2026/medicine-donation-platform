package com.dgi.medishare.controller;

import com.dgi.medishare.entity.DonationRequest;
import com.dgi.medishare.service.DonationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class DonationRequestController {

    @Autowired
    private DonationRequestService requestService;

    @PostMapping
    public DonationRequest createRequest(@RequestParam Long medicineId, @RequestParam Long ngoId) {
        return requestService.createRequest(medicineId, ngoId);
    }

    @PutMapping("/{id}/approve")
    public DonationRequest approveRequest(@PathVariable Long id) {
        return requestService.approveRequest(id);
    }

    @PutMapping("/{id}/reject")
    public DonationRequest rejectRequest(@PathVariable Long id) {
        return requestService.rejectRequest(id);
    }

    @PutMapping("/{id}/picked-up")
    public DonationRequest markPickedUp(@PathVariable Long id) {
        return requestService.markPickedUp(id);
    }

    @PutMapping("/{id}/delivered")
    public DonationRequest markDelivered(@PathVariable Long id) {
        return requestService.markDelivered(id);
    }

    @GetMapping("/ngo/{ngoId}")
    public List<DonationRequest> getByNgo(@PathVariable Long ngoId) {
        return requestService.getRequestsByNgo(ngoId);
    }

    @GetMapping
    public List<DonationRequest> getAllRequests() {
        return requestService.getAllRequests();
    }
}
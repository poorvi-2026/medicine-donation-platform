package com.dgi.medishare.service;

import com.dgi.medishare.entity.*;
import com.dgi.medishare.repository.DonationRequestRepository;
import com.dgi.medishare.repository.MedicineRepository;
import com.dgi.medishare.repository.NgoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DonationService {

    @Autowired
    private DonationRequestRepository donationRequestRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private NgoRepository ngoRepository;

    // NGO requests a medicine
    public DonationRequest createRequest(Long medicineId, Long ngoId) {
        Medicine medicine = medicineRepository.findById(medicineId)
                .orElseThrow(() -> new IllegalArgumentException("Medicine not found"));

        if (medicine.getStatus() != MedicineStatus.AVAILABLE) {
            throw new IllegalArgumentException("Medicine is not available for donation");
        }

        Ngo ngo = ngoRepository.findById(ngoId)
                .orElseThrow(() -> new IllegalArgumentException("NGO not found"));

        DonationRequest request = new DonationRequest();
        request.setMedicine(medicine);
        request.setNgo(ngo);
        request.setStatus(RequestStatus.PENDING);
        request.setRequestedAt(LocalDateTime.now());

        medicine.setStatus(MedicineStatus.REQUESTED);
        medicineRepository.save(medicine);

        return donationRequestRepository.save(request);
    }

    // Donor accepts the request
    public DonationRequest acceptRequest(Long requestId) {
        DonationRequest request = donationRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        request.setStatus(RequestStatus.APPROVED);
        return donationRequestRepository.save(request);
    }

    // Donor rejects the request
    public DonationRequest rejectRequest(Long requestId) {
        DonationRequest request = donationRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        request.setStatus(RequestStatus.REJECTED);

        Medicine medicine = request.getMedicine();
        medicine.setStatus(MedicineStatus.AVAILABLE); // dubara available ho jaye
        medicineRepository.save(medicine);

        return donationRequestRepository.save(request);
    }

    // Update status (e.g. PICKED_UP, DELIVERED)
    public DonationRequest updateStatus(Long requestId, RequestStatus status) {
        DonationRequest request = donationRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        request.setStatus(status);

        if (status == RequestStatus.DELIVERED) {
            Medicine medicine = request.getMedicine();
            medicine.setStatus(MedicineStatus.DONATED);
            medicineRepository.save(medicine);
        }

        return donationRequestRepository.save(request);
    }

    public List<DonationRequest> getRequestsByNgo(Long ngoId) {
        return donationRequestRepository.findAll().stream()
                .filter(r -> r.getNgo().getId().equals(ngoId))
                .toList();
    }
}
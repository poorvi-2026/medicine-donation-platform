package com.dgi.medishare.service;

import com.dgi.medishare.entity.*;
import com.dgi.medishare.exception.ResourceNotFoundException;
import com.dgi.medishare.repository.DonationRequestRepository;
import com.dgi.medishare.repository.MedicineRepository;
import com.dgi.medishare.repository.NgoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DonationRequestService {

    @Autowired
    private DonationRequestRepository requestRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private NgoRepository ngoRepository;

    // NGO requests a medicine
    public DonationRequest createRequest(Long medicineId, Long ngoId) {
        Medicine medicine = medicineRepository.findById(medicineId)
                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found with id: " + medicineId));

        Ngo ngo = ngoRepository.findById(ngoId)
                .orElseThrow(() -> new ResourceNotFoundException("NGO not found with id: " + ngoId));

        if (ngo.getVerificationStatus() != VerificationStatus.APPROVED) {
            throw new IllegalArgumentException("Only verified NGOs can request medicines");
        }

        if (medicine.getStatus() != MedicineStatus.AVAILABLE) {
            throw new IllegalArgumentException("This medicine is not available for request");
        }

        DonationRequest request = new DonationRequest();
        request.setMedicine(medicine);
        request.setNgo(ngo);
        request.setStatus(RequestStatus.PENDING);
        request.setRequestedAt(LocalDateTime.now());

        medicine.setStatus(MedicineStatus.REQUESTED);
        medicineRepository.save(medicine);

        return requestRepository.save(request);
    }

    // Admin/Donor approves a request
    public DonationRequest approveRequest(Long requestId) {
        DonationRequest request = getRequestById(requestId);
        request.setStatus(RequestStatus.APPROVED);

        Medicine medicine = request.getMedicine();
        medicine.setStatus(MedicineStatus.DONATED);
        medicineRepository.save(medicine);

        return requestRepository.save(request);
    }

    // Reject a request — medicine goes back to AVAILABLE
    public DonationRequest rejectRequest(Long requestId) {
        DonationRequest request = getRequestById(requestId);
        request.setStatus(RequestStatus.REJECTED);

        Medicine medicine = request.getMedicine();
        medicine.setStatus(MedicineStatus.AVAILABLE);
        medicineRepository.save(medicine);

        return requestRepository.save(request);
    }

    public DonationRequest markPickedUp(Long requestId) {
        DonationRequest request = getRequestById(requestId);
        request.setStatus(RequestStatus.PICKED_UP);
        return requestRepository.save(request);
    }

    public DonationRequest markDelivered(Long requestId) {
        DonationRequest request = getRequestById(requestId);
        request.setStatus(RequestStatus.DELIVERED);
        return requestRepository.save(request);
    }

    public DonationRequest getRequestById(Long id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found with id: " + id));
    }

    public List<DonationRequest> getRequestsByNgo(Long ngoId) {
        return requestRepository.findByNgoId(ngoId);
    }

    public List<DonationRequest> getAllRequests() {
        return requestRepository.findAll();
    }
}
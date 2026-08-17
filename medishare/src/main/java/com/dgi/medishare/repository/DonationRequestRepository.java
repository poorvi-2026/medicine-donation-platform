package com.dgi.medishare.repository;

import com.dgi.medishare.entity.DonationRequest;
import com.dgi.medishare.entity.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRequestRepository extends JpaRepository<DonationRequest, Long> {
    List<DonationRequest> findByNgoId(Long ngoId);
    List<DonationRequest> findByStatus(RequestStatus status);
}
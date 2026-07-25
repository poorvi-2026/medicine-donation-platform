package com.dgi.medishare.repository;

import com.dgi.medishare.entity.DonationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationRequestRepository extends JpaRepository<DonationRequest, Long> {
}
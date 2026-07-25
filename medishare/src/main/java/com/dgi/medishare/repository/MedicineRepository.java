package com.dgi.medishare.repository;

import com.dgi.medishare.entity.Medicine;
import com.dgi.medishare.entity.MedicineStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    List<Medicine> findByStatus(MedicineStatus status);
    List<Medicine> findByDonorId(Long donorId);
}
package com.dgi.medishare.service;

import com.dgi.medishare.entity.Medicine;
import com.dgi.medishare.entity.MedicineStatus;
import com.dgi.medishare.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    // Donor listing
    public Medicine addMedicine(Medicine medicine) {
        validateExpiry(medicine);
        medicine.setStatus(MedicineStatus.AVAILABLE);
        return medicineRepository.save(medicine);
    }

    // Expiry validation
    private void validateExpiry(Medicine medicine) {
        if (medicine.getExpiryDate() == null || !((LocalDate) medicine.getExpiryDate()).isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Cannot list an expired or invalid medicine");
        }
    }

    public List<Medicine> getAvailableMedicines() {
        return medicineRepository.findByStatus(MedicineStatus.AVAILABLE);
    }

    public List<Medicine> getMedicinesByDonor(Long donorId) {
        return medicineRepository.findByDonorId(donorId);
    }
}
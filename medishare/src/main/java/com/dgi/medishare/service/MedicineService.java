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

    public Medicine updateMedicine(Long id, Medicine updatedData) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Medicine not found"));

        if (medicine.getStatus() != MedicineStatus.AVAILABLE) {
            throw new IllegalArgumentException("Cannot edit a medicine that is already requested or donated");
        }

        medicine.setName(updatedData.getName());
        medicine.setCategory(updatedData.getCategory());
        medicine.setQuantity(updatedData.getQuantity());
        medicine.setExpiryDate(updatedData.getExpiryDate());

        return medicineRepository.save(medicine);
    }

    public void deleteMedicine(Long id) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Medicine not found"));

        if (medicine.getStatus() != MedicineStatus.AVAILABLE) {
            throw new IllegalArgumentException("Cannot delete a medicine that is already requested or donated");
        }

        medicineRepository.delete(medicine);
    }

    @org.springframework.scheduling.annotation.Scheduled(cron = "0 0 0 * * *")
    public void markExpiredMedicines() {
        medicineRepository.findAll().stream()
                .filter(m -> m.getStatus() == MedicineStatus.AVAILABLE
                        && m.getExpiryDate() != null
                        && m.getExpiryDate().isBefore(LocalDate.now()))
                .forEach(m -> { m.setStatus(MedicineStatus.EXPIRED); medicineRepository.save(m); });
    }
}
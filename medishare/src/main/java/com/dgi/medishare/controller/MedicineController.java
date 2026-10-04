package com.dgi.medishare.controller;

import com.dgi.medishare.entity.Medicine;
import com.dgi.medishare.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicines")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @PostMapping
    public Medicine addMedicine(@RequestBody Medicine medicine) {
        return medicineService.addMedicine(medicine);
    }

    @GetMapping("/available")
    public List<Medicine> getAvailableMedicines() {
        return medicineService.getAvailableMedicines();
    }

    @GetMapping("/donor/{donorId}")
    public List<Medicine> getMedicinesByDonor(@PathVariable Long donorId) {
        return medicineService.getMedicinesByDonor(donorId);
    }

    @PutMapping("/{id}")
    public Medicine updateMedicine(@PathVariable Long id, @RequestBody Medicine updatedData) {
        return medicineService.updateMedicine(id, updatedData);
    }

    @DeleteMapping("/{id}")
    public String deleteMedicine(@PathVariable Long id) {
        medicineService.deleteMedicine(id);
        return "Medicine deleted successfully";
    }
}
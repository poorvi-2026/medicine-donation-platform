package com.dgi.medishare.controller;

import com.dgi.medishare.entity.Ngo;
import com.dgi.medishare.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/ngos/pending")
    public List<Ngo> getPendingNgos() {
        return adminService.getPendingNgos();
    }

    @GetMapping("/ngos/verified")
    public List<Ngo> getVerifiedNgos() {
        return adminService.getVerifiedNgos();
    }

    @PutMapping("/ngos/{id}/verify")
    public Ngo verifyNgo(@PathVariable Long id) {
        return adminService.verifyNgo(id);
    }

    @PutMapping("/ngos/{id}/reject")
    public Ngo rejectNgo(@PathVariable Long id) {
        return adminService.rejectNgo(id);
    }
}
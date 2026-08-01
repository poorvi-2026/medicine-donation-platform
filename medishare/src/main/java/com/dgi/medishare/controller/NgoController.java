package com.dgi.medishare.controller;

import com.dgi.medishare.entity.Ngo;
import com.dgi.medishare.repository.NgoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ngos")
public class NgoController {

    @Autowired
    private NgoRepository ngoRepository;

    @PostMapping("/register")
    public Ngo registerNgo(@RequestBody Ngo ngo) {
        return ngoRepository.save(ngo);
    }
}
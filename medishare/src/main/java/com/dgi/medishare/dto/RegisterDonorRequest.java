package com.dgi.medishare.dto;

import lombok.Data;

@Data
public class RegisterDonorRequest {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
}
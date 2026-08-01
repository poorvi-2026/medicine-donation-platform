package com.dgi.medishare.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "ngos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ngo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    private String phone;
    private String address;
    private String licenseNumber;
    @Enumerated(EnumType.STRING)
    private Role role = Role.NGO;
    @Enumerated(EnumType.STRING)
    private VerificationStatus verificationStatus = VerificationStatus.PENDING;
}

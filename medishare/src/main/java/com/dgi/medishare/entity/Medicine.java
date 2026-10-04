package com.dgi.medishare.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "medicines")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;
    private String batchNumber;
    private LocalDate manufactureDate;
    private LocalDate expiryDate;
    private int quantity;
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private MedicineStatus status = MedicineStatus.AVAILABLE;

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private Donor donor;
}
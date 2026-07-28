package com.dgi.medishare.repository;

import com.dgi.medishare.entity.Ngo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NgoRepository extends JpaRepository<Ngo, Long> {
}

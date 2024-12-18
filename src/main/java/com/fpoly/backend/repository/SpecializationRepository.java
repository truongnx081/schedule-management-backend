package com.fpoly.backend.repository;

import com.fpoly.backend.entities.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization,Integer> {
    Optional<Specialization> findByName(String name);
}

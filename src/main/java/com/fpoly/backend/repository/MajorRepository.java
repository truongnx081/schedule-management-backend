package com.fpoly.backend.repository;

import com.fpoly.backend.entities.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MajorRepository extends JpaRepository<Major,Integer> {
    Major findByName(String name);
}

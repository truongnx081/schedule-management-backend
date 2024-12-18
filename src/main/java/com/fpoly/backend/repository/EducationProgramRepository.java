package com.fpoly.backend.repository;

import com.fpoly.backend.entities.EducationProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationProgramRepository extends JpaRepository<EducationProgram,Integer> {
    EducationProgram findByName(String name);
}

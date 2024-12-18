package com.fpoly.backend.repository;

import com.fpoly.backend.entities.StudyResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkRepository extends JpaRepository<StudyResult,Integer> {


}

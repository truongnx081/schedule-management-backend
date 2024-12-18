package com.fpoly.backend.repository;

import com.fpoly.backend.entities.PrivateMajor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface PrivateMajorRepository extends JpaRepository<PrivateMajor,Integer> {
    @Query("SELECT pm.id as id, pm.name as name, m.specialization.id as specializationId  " +
            "FROM PrivateMajor pm " +
            "JOIN pm.major m")
    List<Map<String, Object>> findAllPrivateMajor ();
}

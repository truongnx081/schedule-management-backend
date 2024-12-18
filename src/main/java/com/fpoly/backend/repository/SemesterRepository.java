package com.fpoly.backend.repository;

import com.fpoly.backend.entities.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SemesterRepository extends JpaRepository<Semester,String> {

    @Query("SELECT s.semester as semester FROM  Semester s")
    List<Map<String, Object>> findAllSemester();
}

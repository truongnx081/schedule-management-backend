package com.fpoly.backend.repository;

import com.fpoly.backend.entities.Year;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface YearRepository extends JpaRepository<Year,Integer> {
    Optional<Year> findByYear(Integer year);

    @Query("SELECT y.year as year FROM Year y")
    List<Map<String, Object>> findAllYear();
}

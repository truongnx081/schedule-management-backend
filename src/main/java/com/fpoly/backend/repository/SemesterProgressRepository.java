package com.fpoly.backend.repository;

import com.fpoly.backend.entities.SemesterProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SemesterProgressRepository extends JpaRepository<SemesterProgress,Integer> {

    Optional<SemesterProgress> findByIsActive(Boolean isActive);


    @Query("SELECT sp " +
            "FROM SemesterProgress sp " +
            "WHERE sp.isActive = true")
    SemesterProgress findActivedProgress();

    @Query(" SELECT sp.id as id ,sp.block.block as block, sp.semester.semester as semester, sp.year.year as year, " +
            "sp.isActive as isActive,  sp.createDateStart as createDateStart, " +
            "sp.createDateEnd as createDateEnd , sp.repaireDateStart as repaireDateStart , " +
            " sp.repaireDateEnd as repaireDateEnd ," +
            "sp.firstPartStart as firstPartStart,  sp.firstPartEnd  as firstPartEnd, "+
            "sp.secondPartStart as secondPartStart, sp.secondPartEnd as secondPartEnd " +
            "FROM SemesterProgress sp " +
            "JOIN sp.block bl " +
            "JOIN sp.year yr " +
            "JOIN sp.semester se ")
    List<Map<String, Object>> getAllSemesterProgress();

    @Transactional
    @Modifying
    @Query("UPDATE SemesterProgress sp SET sp.isActive = false")
    void updateAllStatusOfSemesterProgressIsFalse();
}

package com.fpoly.backend.repository;

import com.fpoly.backend.entities.ArrangeBatch;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArrangeBatchRepository extends JpaRepository<ArrangeBatch,Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM ArrangeBatch ab WHERE ab.clazz.id = :clazzId")
    void deleteByClazzId(@Param("clazzId") Integer clazzId);
    List<ArrangeBatch> findAllByClazzId(Integer clazzId);

    @Query("SELECT a FROM ArrangeBatch a WHERE a.student.id = :studentId AND a.clazz.id = :clazzId")
    ArrangeBatch findArrangeBatchByStudentIdAndClazzId (@Param("studentId") Integer studentId,
                                                        @Param("clazzId") Integer clazzId);
}

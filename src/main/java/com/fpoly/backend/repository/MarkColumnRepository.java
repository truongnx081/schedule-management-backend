package com.fpoly.backend.repository;

import com.fpoly.backend.entities.MarkColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkColumnRepository extends JpaRepository<MarkColumn,Integer> {

    @Query("SELECT mc.id " +
            "FROM MarkColumn mc " +
            "JOIN mc.subjectMarks sm " +
            "WHERE sm.subject.id = :subjectId")
    List<Integer> findMarkColumnsBySubjectId(@Param("subjectId") Integer subjectId);

    @Query("SELECT mc.id " +
            "FROM MarkColumn mc " +
            "JOIN mc.subjectMarks sm " +
            "WHERE sm.subject.id = :subjectId " +
            "AND mc.finalMarks = true")
    Integer findFinalMarkColumnBySubjectId (@Param("subjectId") Integer subjectId);
}

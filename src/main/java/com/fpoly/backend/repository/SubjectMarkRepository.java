package com.fpoly.backend.repository;

import com.fpoly.backend.entities.Subject;
import com.fpoly.backend.entities.SubjectMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface SubjectMarkRepository extends JpaRepository<SubjectMark, Integer> {

    @Query("SELECT mc.id as mark_column_id," +
            "mc.name as mark_column_name," +
            "sm.percentage as percentage," +
            "sm.part as part " +
            "FROM MarkColumn mc " +
            "JOIN mc.subjectMarks sm " +
            "WHERE sm.subject.id = :subjectId " +
            "AND mc.manageByInstructor = true")
    List<Map<String, Object>> findMarkColumnBySubjectId (@Param("subjectId") Integer subjectId);

    @Query("SELECT SUM(sm.percentage) " +
            "FROM SubjectMark sm " +
            "WHERE sm.subject.id = :subjectId " +
            "AND sm.markColumn.finalMarks = false " +
            "AND sm.markColumn.qualify = true")
    Double findProgressPercentageBySubjectId(@Param("subjectId") Integer subjectId);

    @Query("SELECT sm " +
            "FROM SubjectMark sm " +
            "WHERE sm.subject.id = :subjectId " +
            "AND sm.markColumn.id = :markColumnId")
    SubjectMark findSubjectMarkBySubjectIdAndAndMarkColumnId(@Param("subjectId") Integer subjectId,
                                                             @Param("markColumnId") Integer markColumnId);

    List<SubjectMark> findAllBySubject (Subject subject);

    @Query("SELECT mc.id as mark_column_id," +
            "mc.name as mark_column_name," +
            "sm.percentage as percentage," +
            "sm.part as part " +
            "FROM MarkColumn mc " +
            "JOIN mc.subjectMarks sm " +
            "WHERE sm.subject.id = :subjectId ")
    List<Map<String, Object>> findMarkColumnBySubject(@Param("subjectId") Integer subjectId);
}

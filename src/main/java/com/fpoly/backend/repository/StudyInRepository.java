package com.fpoly.backend.repository;

import com.fpoly.backend.dto.StudyResultProjection;
import com.fpoly.backend.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
public interface StudyInRepository extends JpaRepository<StudyIn,Integer> {
    List<StudyIn> findAllByClazzBlockAndClazzSemesterAndClazzYear(Block block, Semester semester, Year year);

    @Query("SELECT s.id as id " +
            "FROM StudyIn s JOIN s.clazz c " +
            "WHERE c.block.block = :blockId " +
            "AND c.semester.semester = :semesterId " +
            "AND c.year.year = :yearId" )
    List<Map<String, Object>> getAllIdOfStudyInByBlockAndSemesterAndYearOfStudent(
            @Param("blockId") Integer blockId,
            @Param("semesterId") String semesterId,
            @Param("yearId") Integer yearId
    );

    List<StudyIn> findByClazz_Instructor_Id(Integer instructorId);

    @Query("SELECT clz.id as clazz_id ,clz.code as clazz_code, stu.code as student_code ,CONCAT(stu.lastName ,' ', stu.firstName) as fullname,\n" +
            "sub.name as subject_name,sum(srs.marked * sms.percentage)/100 as AVG " +
            "FROM StudyIn sti " +
            "JOIN sti.clazz clz " +
            "JOIN sti.student stu " +
            "JOIN sti.studyResults srs " +
            "JOIN srs.markColumn mcs " +
            "JOIN mcs.subjectMarks sms " +
            "JOIN clz.instructor ins " +
            "JOIN clz.subject sub " +
            "WHERE clz.id = :clazzId " +
            "group by clz.id, stu.code, CONCAT(stu.lastName ,' ', stu.firstName), clz.code, sub.name " +
            "order by CONCAT(stu.lastName ,' ', stu.firstName)")
    List<Map<String, Object>> getAllMarkAverageStudentsByClazzId(@Param("clazzId")Integer clazzId);


    boolean existsByStudentIdAndClazzId(Integer studentId, Integer clazzId);


    @Query("SELECT COUNT(si) FROM StudyIn si WHERE si.clazz.id = :clazzId")
    Integer countStudentByClazzId(@Param("clazzId") Integer clazzId);

    @Query("SELECT si FROM  StudyIn si WHERE si.student.id = :studentId AND si.clazz.id =:clazzId")
    StudyIn findByStudentIdAndClazzId(@Param("studentId") Integer studentId,
                                      @Param("clazzId") Integer clazzId);

    @Query("SELECT si.id FROM StudyIn si JOIN si.clazz c WHERE si.student.id = :studentId AND c.subject.id = :subjectId AND si.paid = :paid")
    List<Integer> findByStudentIdAndSubjectId (@Param("studentId") Integer studentId,
                                               @Param("subjectId") Integer subjectId,
                                               @Param("paid") Boolean paid);
    @Transactional
    @Modifying
    @Query("UPDATE StudyIn si SET si.paid = true where si.student = :student")
    void updateAllStudyInIsTrueByStudent(@Param("student") Student student);

    @Query("SELECT sj.id as subjectId, sj.code as subjectCode, sj.name as subjectName, c.code as clazzCode, c.id as classId, sj.credits as credits," +
            "ins.code as instructorCode, ins.firstName as firstName, ins.lastName as lastName, c.shift.id as shift " +
            "FROM StudyIn si JOIN si.clazz c " +
            "JOIN c.subject sj " +
            "JOIN c.instructor ins " +
            "WHERE c.block.block = :blockId " +
            "AND c.semester.semester = :semesterId " +
            "AND c.year.year = :yearId " +
            "AND si.student = :student " )
    List<Map<String, Object>> getAllIdOfStudyInByBlockAndSemesterAndYearOfStudent2(
            @Param("blockId") Integer blockId,
            @Param("semesterId") String semesterId,
            @Param("yearId") Integer yearId,
            @Param("student") Student student
    );

    @Query("SELECT si FROM StudyIn si WHERE si.clazz.year.year = :year")
    List<StudyIn> findStudyInsByYear(@Param("year") Integer year);

    @Query("SELECT si.id AS studyInId, " +
            "       si.clazz.subject.id AS subjectId, " +
            "       COALESCE(SUM(sr.marked * sm.percentage) / 100, NULL) AS averageMark, " +
            "       mc.id AS finalMarkColumnId, " +
            "       srFinal.marked AS finalMark " +
            "FROM StudyIn si " +
            "LEFT JOIN si.clazz.subject.subjectMarks sm " +
            "LEFT JOIN sm.markColumn mc " +
            "LEFT JOIN StudyResult sr ON sr.studyIn.id = si.id AND sr.markColumn.id = mc.id " +
            "LEFT JOIN StudyResult srFinal ON srFinal.studyIn.id = si.id AND srFinal.markColumn.id = mc.id AND mc.finalMarks = true " +
            "WHERE si.clazz.year.year = :year " +
            "GROUP BY si.id, si.clazz.subject.id, mc.id, srFinal.marked")
    List<StudyResultProjection> findStudyResultsByYear(@Param("year") Integer year);

}

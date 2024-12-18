package com.fpoly.backend.repository;

import com.fpoly.backend.entities.StudyResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;


public interface StudyResultRepository extends JpaRepository<StudyResult,Integer> {

//    @Query("SELECT " +
//            "    s.code AS subject_code, " +
//            "    s.name AS subject_name, " +
//            "    s.credits AS credits, " +
//            "    COALESCE(SUM(str.marked * str.percentage) / 100, NULL) AS mark_average, " +
//            "    s.id AS subject_id, " +
//            "    MAX(CASE " +
//            "        WHEN sti.clazz.id IS NOT NULL THEN COALESCE(cl.code, null) " + // COALESCE thay cho null để tránh VARBINARY
//            "        ELSE null " +
//            "    END) AS clazz_code, " +
//            "MAX(CASE WHEN sti.clazz.id IS NOT NULL THEN CAST(cl.id AS INTEGER) ELSE NULL END) AS clazz_id, "+
//            "    MAX(CASE " +
//            "        WHEN sti.clazz.id IS NOT NULL THEN COALESCE(bl.block, null) " + // COALESCE để đảm bảo kiểu so sánh
//            "        ELSE null " +
//            "    END) AS block, " +
//            "    MAX(CASE " +
//            "        WHEN sti.clazz.id IS NOT NULL THEN COALESCE(se.semester, null) " + // COALESCE để đảm bảo kiểu so sánh
//            "        ELSE null " +
//            "    END) AS semester, " +
//            "    MAX(CASE " +
//            "        WHEN sti.clazz.id IS NOT NULL THEN COALESCE(yr.year, null) " + // COALESCE để đảm bảo kiểu so sánh
//            "        ELSE null " +
//            "    END) AS year " +
//            "FROM Subject s " +
//            "JOIN s.applyFors af " +
//            "JOIN af.educationProgram ep " +
//            "JOIN ep.students st " +
//            "LEFT JOIN s.clazzes cl " +
//            "JOIN cl.studyIns sti " +
//            "LEFT JOIN sti.studyResults str " +
//            "LEFT JOIN cl.block bl " +
//            "LEFT JOIN cl.semester se " +
//            "LEFT JOIN cl.year yr " +
//            "WHERE st.id = :studentId " +
//            "GROUP BY s.id, s.code, s.name, s.credits ")
//    List<Map<String, Object>> getAllStudyResultByStudentId(@Param("studentId") Integer studentId);
//
//




    @Query("SELECT sr.id as id, sr.marked as marked, sm.percentage as percentage, sr.studyIn.id as studyIn " +
            "FROM StudyResult sr " +
            "JOIN sr.markColumn mc " +
            "JOIN mc.subjectMarks sm " +
            "JOIN sr.studyIn s " +
            "WHERE sr.studyIn.id = :studyInId")
    List<Map<String, Object>> findAllByStudyInIdOfStudent(
            @Param("studyInId") Integer studyInId
    );


    @Query("SELECT CONCAT(std.lastName, ' ', std.firstName, ' - ', std.code) AS studentName, " +
            "mcs.name AS markColumnName, " +
            "srs.marked AS mark " +
            "FROM StudyResult srs " +
            "JOIN srs.studyIn sis " +
            "JOIN srs.markColumn mcs " +
            "JOIN sis.clazz czs " +
            "JOIN sis.student std " +
            "JOIN czs.subject sus " +
            "JOIN czs.instructor ins " +
            "WHERE czs.id = :clazzId AND std.id = :studentId")
    List<Map<String, Object>> getAllMarkColumn(@Param("clazzId") Integer clazzId,
                                               @Param("studentId") Integer studentId);

    @Query("SELECT COUNT(DISTINCT s.subject.id ) " +
            "FROM StudyResult str " +
            "JOIN str.studyIn st " +
            "JOIN str.markColumn m " +
            "JOIN m.subjectMarks s " +
            "WHERE st.student.id = :studentId " +
            "GROUP BY s.subject.id " +
            "HAVING SUM(str.marked * s.percentage) / 100 >= 5")
    Integer countSubjectPassByStudent(@Param("studentId") Integer studentId);


    boolean existsByMarkColumnIdAndStudyInId(Integer markColumnId, Integer studyInId);

    @Query("SELECT  " +
                  "mcs.name AS markColumnName, " +
                  "srs.marked AS mark, sms.percentage AS percentage " +
                  "FROM StudyResult srs " +
                  "JOIN srs.studyIn sis " +
                  "JOIN srs.markColumn mcs " +
                  "JOIN sis.clazz czs " +
                  "JOIN sis.student std " +
                  "JOIN czs.subject sus " +
                  "JOIN czs.instructor ins " +
                  "JOIN mcs.subjectMarks sms " +
                  "WHERE czs.id = :clazzId AND sus.id = :subjectId AND std.id = :studentId")
    List<Map<String, Object>> getAllMarkDetail(@Param("clazzId")Integer clazzId,
                                               @Param("subjectId")Integer subjectId,
                                               @Param("studentId")Integer studentId);


    @Query("SELECT s.student.id as studentId, " +
            "s.student.code as studentCode, " +
            "CONCAT(s.student.lastName, ' ', s.student.firstName) as studentFullName, " +
            "sr.marked as studentMark, " +
            "mc.name as markName, " +
            "s.id as studyInId " +
            "FROM Clazz c " +
            "JOIN c.studyIns s " +
            "LEFT JOIN s.studyResults sr " +
            "LEFT JOIN sr.markColumn mc " +
            "WHERE c.id = :clazzId")
    List<Map<String, Object>> getMarkByClazzId(@Param("clazzId") Integer clazzId);



    @Query("SELECT COALESCE(SUM(sr.marked * sm.percentage) / 100, NULL) AS mark_average " +
            "FROM StudyResult sr " +
            "JOIN sr.markColumn mc " +
            "JOIN mc.subjectMarks sm " +
            "WHERE sr.studyIn.id = :studyInId " +
            "AND sm.subject.id = :subjectId")
    Double findAverangeMarkByStudyInId (@Param("studyInId") Integer studyInId,
                                        @Param("subjectId") Integer subjectId);

    @Query("SELECT mc.name as mark_column_name, sm.percentage as percentage, sr.marked as marked " +
            "FROM StudyResult sr " +
            "JOIN sr.markColumn mc " +
            "JOIN mc.subjectMarks sm " +
            "WHERE sr.studyIn.id = :studyInId " +
            "AND sm.subject.id = :subjectId")
    List<Map<String, Object>> findAllMarkDetailByStudyInId (@Param("studyInId") Integer studyInId,
                                                            @Param("subjectId") Integer subjectId);

    @Query("SELECT mc.id as mark_column_id," +
            "mc.name as mark_column_name," +
            "sr.marked as marked " +
            "FROM StudyResult sr " +
            "JOIN sr.studyIn si " +
            "JOIN sr.markColumn mc " +
            "WHERE si.id = :studyInId")
    List<Map<String, Object>>findStudyResultByStudyInId(@Param("studyInId") Integer studyInId);


    @Query("SELECT SUM(sr.marked * sm.percentage) " +
            "FROM StudyResult sr " +
            "JOIN sr.markColumn mc " +
            "JOIN mc.subjectMarks sm " +
            "WHERE sr.studyIn.id = :studyInId " +
            "AND mc.finalMarks = false " +
            "AND mc.qualify = false")
    Double findProgressMarkByStudyInId(@Param("studyInId") Integer studyInId);

    @Query("SELECT sr " +
            "FROM StudyResult sr " +
            "WHERE sr.studyIn.id = :studyInId " +
            "AND sr.markColumn.id = :markColumnId")
    StudyResult findByStudyInIdAndMarkColumnId (@Param("studyInId") Integer studyInId,
                                                @Param("markColumnId") Integer markColumnId);

    @Query("SELECT sr.marked " +
            "FROM StudyResult sr " +
            "WHERE sr.markColumn.id = :markColumnId " +
            "AND sr.studyIn.id = :studyInId")
    Double findMarkedByMarkColumnIdAndStudyInId(@Param("markColumnId") Integer markColumnId,
                                                @Param("studyInId") Integer studyInId);
}

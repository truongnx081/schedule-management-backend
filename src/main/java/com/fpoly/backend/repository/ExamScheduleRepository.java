package com.fpoly.backend.repository;

import com.fpoly.backend.entities.ExamSchedule;
import com.fpoly.backend.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface ExamScheduleRepository extends JpaRepository<ExamSchedule,Integer> {

    @Query("SELECT ex.date AS examDate, ex.batch AS examBatch, ex.room.name AS roomName, " +
            "subj.code AS subjectCode, subj.name AS subjectName, " +
            "c.code AS clazzCode, i.code AS instructorCode, " +
            "ex.shift.id AS shiftId, ex.shift.startTime AS startTime, ex.shift.endTime AS endTime " +
            "FROM ExamSchedule ex " +
            "JOIN ex.clazz c " +
            "JOIN StudyIn sti ON sti.clazz.id = c.id " +
            "JOIN sti.student st " +
            "JOIN ArrangeBatch ab ON ab.student.id = st.id " +
            "JOIN c.subject subj " +
            "JOIN ex.room r " +
            "JOIN c.instructor i " +
            "JOIN ex.shift sh " +
            "WHERE st.id = :studentId " +
            "AND ab.batch = ex.batch " +
            "AND ex.date BETWEEN :startDate AND :endDate " +
            "GROUP BY ex.date, ex.batch, r.name, subj.code, subj.name, " +
            "c.code, i.code, sh.id, sh.startTime, sh.endTime")
    List<Map<String, Object>> getExamScheduleByDateRange(@Param("studentId") Integer studentId,
                                                         @Param("startDate") LocalDate startDate,
                                                         @Param("endDate") LocalDate endDate);

    @Query("SELECT ex.date as exam_date, ex.batch as exam_batch, stu.code as student_code, " +
            "CONCAT(stu.lastName, ' ', stu.firstName) AS student_fullname " +
            "FROM ExamSchedule ex " +
            "JOIN ex.clazz cs " +
            "JOIN ArrangeBatch ar ON ar.clazz.id = cs.id " +
            "JOIN ar.student stu " +
            "WHERE cs.id = :clazzId " +
            "  AND cs.instructor.id = :instructorId " +
            "  AND ex.batch = ar.batch " +
            "order by ex.batch")
    List<Map<String, Object>> getAllBathByClazzInstructor(@Param("clazzId") Integer clazzId,
                                                          @Param("instructorId") Integer instructorId);
    @Query("SELECT " +
            "exm.id AS exam_id," +
            "exm.date as exam_date,   " +
            "clz.code AS clazz_code, " +
            "sub.code AS subject_code, " +
            "sub.name AS subject_name, " +
            "sif.id AS shift_id, " +
            "sif.startTime AS start_time, " +
            "sif.endTime AS end_time, " +
            "ins.firstName as first_name,  " +
            "ins.lastName AS last_name, " +
            "ins.code AS instructor_code, " +
            "bls.block AS block, " +
            "ses.semester AS semester, " +
            "yrs.year AS year, " +
            "ros.name AS room_name, " +
            "bld.name AS building_name, " +
            "exm.batch AS batch_exam, " +
            "spe.id AS specialization_id " +
            "FROM ExamSchedule exm " +
            "JOIN exm.clazz clz " +
            "JOIN clz.subject sub " +
            "JOIN clz.shift sif " +
            "JOIN clz.block bls " +
            "JOIN clz.instructor ins " +
            "JOIN clz.semester ses " +
            "JOIN clz.room ros " +
            "JOIN ros.building bld " +
            "JOIN clz.year yrs " +
            "JOIN sub.specialization spe")
    List<Map<String, Object>> getAllExamScheduleOfAdmin();
    @Query("SELECT " +
            "exm.id AS exam_id, " +
            "exm.date as exam_date, " +
            "clz.code AS clazz_code, clz.id AS clazzId, " +
            "sub.code AS subject_code, " +
            "sub.name AS subject_name, " +
            "exm.shift.id AS shift_id, " +
            "sif.startTime AS start_time, " +
            "sif.endTime AS end_time, " +
            "ins.firstName as first_name,  " +
            "ins.lastName AS last_name, " +
            "ins.code AS instructor_code, " +
            "bls.block AS block, " +
            "ses.semester AS semester, " +
            "yrs.year AS year, " +
            "exm.room.name AS room_name, exm.room.id AS roomId, " +
            "bld.name AS building_name, " +
            "exm.batch AS batch_exam, " +
            "spe.id AS specialization_id " +
            "FROM ExamSchedule exm " +
            "JOIN exm.clazz clz " +
            "JOIN clz.subject sub " +
            "JOIN clz.shift sif " +
            "JOIN clz.block bls " +
            "JOIN clz.instructor ins " +
            "JOIN clz.semester ses " +
            "JOIN clz.room ros " +
            "JOIN ros.building bld " +
            "JOIN clz.year yrs " +
            "JOIN sub.specialization spe " +
            "WHERE bls.block = :block and ses.semester = :semester and yrs.year = :year and spe.id = :specializationId")
    List<Map<String, Object>> getAllExamByBlockAndSemesterAndYearAndSpecializationIdOfAdmin(@Param("block") Integer block,
                                                                                            @Param("semester") String semester,
                                                                                            @Param("year") Integer year,
                                                                                            @Param("specializationId") Integer specializationId);

    @Query("SELECT es " +
            "FROM ExamSchedule es " +
            "JOIN es.clazz c " +
            "JOIN c.studyIns si " +
            "WHERE es.date = :date " +
            "AND es.shift.id = :shift " +
            "AND si.student.id = :studentId")
    List<ExamSchedule> findExamSchedulesByDateAndShiftAndStudentId (@Param("date") LocalDate date,
                                                                    @Param("shift") Integer shift,
                                                                    @Param("studentId") Integer studentId);
}

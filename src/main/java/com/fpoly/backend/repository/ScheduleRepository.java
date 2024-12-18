package com.fpoly.backend.repository;

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
public interface ScheduleRepository extends JpaRepository<Schedule,Integer> {
    @Query("SELECT s.date AS studyDate, " +
            "r.name AS roomName, " +
            "subj.code AS subjectCode, " +
            "subj.name AS subjectName, " +
            "c.code AS clazzCode, " +
            "i.code AS instructorCode, " +
            "sh.id AS shiftId, " +
            "sh.startTime AS start_time, " +
            "sh.endTime AS end_time " +
            "FROM Schedule s " +
            "JOIN s.clazz c " +
            "JOIN c.room r " +
            "JOIN c.subject subj " +
            "JOIN c.instructor i " +
            "JOIN c.shift sh " +
            "JOIN c.studyDays stday " +
            "JOIN stday.weekDay wd " +
            "JOIN c.studyIns si " +
            "JOIN si.student stu " +
            "WHERE stu.id = :studentId " +
            "AND s.date BETWEEN :startDate AND :endDate AND s.status = true and si.paid = true " +
            "GROUP BY s.date, r.name, subj.code, subj.name, c.code, i.code, sh.id, sh.startTime, sh.endTime " +
            "ORDER BY s.date ASC")
    List<Map<String, Object>> getScheduleByDateRange(@Param("studentId") Integer studentId,
                                                     @Param("startDate") LocalDate startDate,
                                                     @Param("endDate")LocalDate endDate);

    @Query("SELECT " +
            "c.id as clazzId, " +
            "sc.id as scheduleId, " +
            "c.code as clazzCode, " +
            "c.subject.code as codeSubject, " +
            "c.subject.name as subjectName, " +
            "sc.date as date, " +
            "c.room.name as roomName, " +
            "c.shift.id as shiftId, " +
            "CASE WHEN rs.id IS NOT NULL THEN true ELSE false END as isRetake " +
            "FROM Clazz c " +
            "JOIN c.instructor i " +
            "JOIN c.schedules sc " +
            "LEFT JOIN RetakeSchedule rs ON rs.schedule.id = sc.id " +
            "WHERE i.id = :instructorId " +
            "AND sc.status = false")
    List<Map<String, Object>> getScheduleByScheduleStatus(@Param("instructorId") Integer instructorId);


    @Query ("SELECT s.date FROM Schedule s WHERE s.clazz.id = :clazzId ORDER BY s.date ASC")
    List<LocalDate> findDateByClazzId(@Param("clazzId") Integer clazzId);

    @Query("SELECT sch.date as date_schedule, sch.clazz.code as clazz_code, clz.instructor.code as instructor_code, " +
            "sif.id as shift_id, rom.name as room_name, sub.name as subject_name, sub.code as subject_code," +
            "spe.name as specialization_name, sch.status as status " +
            "FROM Schedule sch " +
            "JOIN sch.clazz clz " +
            "JOIN clz.shift sif " +
            "JOIN clz.room rom " +
            "JOIN clz.instructor ins " +
            "JOIN clz.subject sub " +
            "JOIN sub.specialization spe " +
            "JOIN clz.block blk " +
            "JOIN clz.semester ses " +
            "JOIN clz.year yrs")
    List<Map<String, Object>> getAllScheduleByAdmin();
    @Query("SELECT sch.id as id, sch.date as date_schedule, sch.clazz.code as clazz_code, sch.clazz.id as clazzId, clz.instructor.code as instructor_code, " +
            "clz.instructor.lastName as instructor_lastName, clz.instructor.firstName as instructor_firstName, sif.id as shift_id, rom.name as room_name, sub.name as subject_name, sub.code as subject_code," +
            "spe.name as specialization_name, sch.status as status, rom.building.name as building_name, sif.startTime as start_time, sif.endTime as end_time " +
            "FROM Schedule sch " +
            "JOIN sch.clazz clz " +
            "JOIN clz.shift sif " +
            "JOIN clz.room rom " +
            "JOIN clz.instructor ins " +
            "JOIN clz.subject sub " +
            "JOIN sub.specialization spe " +
            "JOIN clz.block blk " +
            "JOIN clz.semester ses " +
            "JOIN clz.year yrs " +
            "WHERE  blk.block = :block and ses.semester = :semester and yrs.year = :year")
    List<Map<String, Object>> getAllSchedulesByBlockSemesterYearByAdmin(@Param("block")Integer block,
                                                                        @Param("semester")String semester,
                                                                        @Param("year")Integer year);
    @Query("SELECT re.date AS studyDate, " +
            "re.room.name AS roomName, " +
            "subj.code AS subjectCode, " +
            "subj.name AS subjectName, " +
            "c.code AS clazzCode, " +
            "i.code AS instructorCode, " +
            "re.shift.id AS shiftId, " +
            "re.shift.startTime AS start_time, " +
            "re.shift.endTime AS end_time " +
            "FROM RetakeSchedule re " +
            "JOIN re.schedule s " +
            "JOIN s.clazz c " +
            "JOIN c.room r " +
            "JOIN c.subject subj " +
            "JOIN c.instructor i " +
            "JOIN c.shift sh " +
            "JOIN c.studyDays stday " +
            "JOIN stday.weekDay wd " +
            "JOIN c.studyIns si " +
            "JOIN si.student stu " +
            "WHERE stu.id = :studentId " +
            "AND re.date BETWEEN :startDate AND :endDate " +
            "GROUP BY re.date, re.room.name, subj.code, subj.name, c.code, i.code, re.shift.id, re.shift.startTime, re.shift.endTime " +
            "ORDER BY re.date ASC")
    List<Map<String, Object>> getScheduleFromRetakeSchedulesByDateRange(@Param("studentId") Integer studentId,
                                                                        @Param("startDate") LocalDate startDate,
                                                                        @Param("endDate")LocalDate endDate);

    @Query("SELECT s " +
            "FROM Schedule s " +
            "JOIN s.clazz c " +
            "JOIN c.studyIns si " +
            "WHERE s.date = :date " +
            "AND c.shift.id = :shift " +
            "AND si.student.id = :studentId")
    List<Schedule> findSchedulesByDateAndShiftAndStudentId (@Param("date") LocalDate date,
                                                            @Param("shift") Integer shift,
                                                            @Param("studentId") Integer studentId);

    @Query("SELECT COUNT (s.id) " +
            "FROM Schedule s " +
            "WHERE  s.clazz.id = :clazzId")
    Integer countScheduleByClazzId(@Param("clazzId") Integer clazzId);
}

package com.fpoly.backend.repository;

import com.fpoly.backend.entities.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Integer> {

    @Query("SELECT sch.clazz.shift.id " +
            "FROM Schedule sch " +
            "WHERE sch.clazz.id = :clazzId " +
            "AND sch.clazz.instructor.id = :instructorId " +
            "AND sch.date = :date")
    List<Integer> findInstructorShiftsFromSchedule(@Param("clazzId") Integer clazzId,
                                                   @Param("instructorId") Integer instructorId,
                                                   @Param("date") LocalDate date);

    @Query("SELECT ex.clazz.shift.id " +
            "FROM ExamSchedule ex " +
            "WHERE ex.clazz.id = :clazzId " +
            "AND ex.clazz.instructor.id = :instructorId " +
            "AND ex.date = :date")
    List<Integer> findInstructorShiftsFromExamSchedule(@Param("clazzId") Integer clazzId,
                                                       @Param("instructorId") Integer instructorId,
                                                       @Param("date") LocalDate date);

    @Query("SELECT re.shift.id " +
            "FROM RetakeSchedule re " +
            "WHERE re.schedule.clazz.id = :clazzId " +
            "AND re.schedule.clazz.instructor.id = :instructorId " +
            "AND re.date = :date")
    List<Integer> findInstructorShiftsFromRetakeSchedule(@Param("clazzId") Integer clazzId,
                                                         @Param("instructorId") Integer instructorId,
                                                         @Param("date") LocalDate date);

    @Query("SELECT DISTINCT c.shift.id " +
            "FROM Schedule s " +
            "JOIN s.clazz c " +
            "JOIN c.studyIns si " +
            "WHERE c.id = :clazzId " +
            "AND s.date = :date")
    List<Integer> findStudentShiftFromSchedule(@Param("clazzId") Integer clazzId,
                                               @Param("date") LocalDate date);

    @Query("SELECT DISTINCT ex.shift.id " +
            "FROM ExamSchedule ex " +
            "JOIN ex.clazz c " +
            "JOIN c.studyIns " +
            "WHERE c.id = :clazzId " +
            "AND ex.date = :date")
    List<Integer> findStudentShiftFromExamSchedule(@Param("clazzId") Integer clazzId,
                                                   @Param("date") LocalDate date);

    @Query("SELECT DISTINCT rs.shift.id " +
            "FROM RetakeSchedule rs " +
            "JOIN rs.schedule s " +
            "WHERE rs.schedule.clazz.id = :clazzId " +
            "AND rs.date = :date")
    List<Integer> findStudentShiftFromRetakeSchedule(@Param("clazzId") Integer clazzId,
                                                     @Param("date") LocalDate date);


    @Query("SELECT c.shift.id " +
            "FROM Clazz c " +
            "JOIN c.schedules s " +
            "WHERE c.instructor.id = :instructorId " +
            "AND s.date = :date " +
            "AND s.status=true ")
    List<Integer> findBusyShitftsFromScheduleByInstructorIdAndDate(@Param("instructorId") Integer instructorId,
                                                                   @Param("date") LocalDate date);


    @Query("SELECT rs.shift.id " +
            "FROM Clazz c " +
            "JOIN c.schedules s " +
            "JOIN s.retakeSchedules rs " +
            "WHERE c.instructor.id = :instructorId " +
            "AND rs.date = :date")
    List<Integer> findBusyShitftsFromRetakeScheduleByInstructorIdAndDate(@Param("instructorId") Integer instructorId,
                                                                        @Param("date") LocalDate date);

    @Query("SELECT c.shift.id " +
            "FROM Clazz c " +
            "JOIN c.examSchedules es " +
            "WHERE c.instructor.id = :instructorId " +
            "AND es.date = :date")
    List<Integer> findBusyShitftsFromExamScheduleByInstructorIdAndDate(@Param("instructorId") Integer instructorId,
                                                                       @Param("date") LocalDate date);
}

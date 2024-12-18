package com.fpoly.backend.repository;

import com.fpoly.backend.entities.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor,Integer> {
    boolean existsBySchoolEmail(String schoolEmail);
    Optional<Instructor> findBySchoolEmail(String schoolEmail);


    @Query("SELECT " +
            "c.id as clazzId, " +
            "s.id as scheduleId, " +
            "s.date as date, " +
            "c.code as code, " +
            "c.subject.name as subjectName, " +
            "c.subject.code as subjectCode, " +
            "c.room.name as roomName, " +
            "sh.id as shiftId " +
            "FROM Instructor i " +
            "JOIN i.clazzes c " +
            "JOIN c.schedules s " +
            "JOIN c.shift sh " +
            "WHERE i.id = :instructorId " +
            "AND s.date BETWEEN :startDate AND :endDate " +
            "AND s.status=true ")
    List<Map<String, Object>> getAllTeachingScheduleByInstructor(
            @Param("instructorId") Integer instructorId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    List<Instructor> findAllBySpecializationId(Integer specializationId);

    Optional<Instructor> findByCode(String code);

    boolean existsByCode(String code);

    @Query("SELECT COUNT(i) FROM Instructor i WHERE i.status = true")
    Integer countActiveInstructor();
}

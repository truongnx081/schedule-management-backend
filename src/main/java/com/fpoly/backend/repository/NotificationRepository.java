package com.fpoly.backend.repository;

import com.fpoly.backend.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Integer> {
    @Query("SELECT ins.code as instructor_code_send, ads.code as admin_code_send, sts.code as student_code_send, " +
            "nts.date as date_notification, nts.content as content " +
            "FROM  Notification nts " +
            "LEFT JOIN nts.sendAdmin ads " +
            "LEFT JOIN nts.sendInstructor ins " +
            "LEFT JOIN nts.sendStudent sts " +
            "WHERE nts.receiveStudent.id   = :studentId")
    List<Map<String, Object>> getAllNotificationsByStudentId(@Param("studentId") Integer studentId);

    @Query("SELECT ins.code as instructor_code_send, ads.code as admin_code_send, sts.code as student_code_send, " +
            "nts.date as date_notification, nts.content as content " +
            "FROM  Notification nts " +
            "LEFT JOIN nts.sendAdmin ads " +
            "LEFT JOIN nts.sendInstructor ins " +
            "LEFT JOIN nts.sendStudent sts " +
            "WHERE nts.receiveAdmin.id = :adminId")
    List<Map<String, Object>> getAllNotificationsByAdminId(@Param("adminId") Integer adminId);

    @Query("SELECT ins.code as instructor_code_send, ads.code as admin_code_send, sts.code as student_code_send, " +
            "nts.date as date_notification, nts.content as content " +
            "FROM  Notification nts " +
            "LEFT JOIN nts.sendAdmin ads " +
            "LEFT JOIN nts.sendInstructor ins " +
            "LEFT JOIN nts.sendStudent sts " +
            "WHERE nts.receiveInstructor.id = :instructorId")
    List<Map<String, Object>> getAllNotificationsByInstructorId(@Param("instructorId") Integer instructorId);
}

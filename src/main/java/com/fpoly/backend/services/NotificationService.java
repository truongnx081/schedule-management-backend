package com.fpoly.backend.services;

import com.fpoly.backend.dto.NotificationDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface NotificationService {
    List<Map<String, Object>> getAllNotificationByStudentId();

    List<Map<String, Object>> getAllNotificationsByAdminId();

    List<Map<String, Object>> getAllNotificationsByInstructorId();

    NotificationDTO createNotificationFromInstructorToStudent(String content,
                                                              Integer recieve_student_Id,
                                                              Integer send_instructor_id);
}

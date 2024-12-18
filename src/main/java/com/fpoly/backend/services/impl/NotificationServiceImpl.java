package com.fpoly.backend.services.impl;

import com.fpoly.backend.dto.NotificationDTO;
import com.fpoly.backend.dto.StudentDTO;
import com.fpoly.backend.entities.Instructor;
import com.fpoly.backend.entities.Notification;
import com.fpoly.backend.entities.Student;
import com.fpoly.backend.exception.AppUnCheckedException;
import com.fpoly.backend.mapper.NotificationMapper;
import com.fpoly.backend.repository.InstructorRepository;
import com.fpoly.backend.repository.NotificationRepository;
import com.fpoly.backend.repository.StudentRepository;
import com.fpoly.backend.services.IdentifyUserAccessService;
import com.fpoly.backend.services.NotificationService;
import com.fpoly.backend.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    IdentifyUserAccessService identifyUserAccessService;
    @Autowired
    StudentService studentService;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    InstructorRepository instructorRepository;
    @Autowired
    NotificationMapper notificationMapper;
    @Override
    public List<Map<String, Object>> getAllNotificationByStudentId() {
        Integer studentId = identifyUserAccessService.getStudent().getId();
        return notificationRepository.getAllNotificationsByStudentId(studentId);
    }

    @Override
    public List<Map<String, Object>> getAllNotificationsByAdminId() {
        Integer adminId = identifyUserAccessService.getAdmin().getId();
        return notificationRepository.getAllNotificationsByAdminId(adminId);
    }

    @Override
    public List<Map<String, Object>> getAllNotificationsByInstructorId() {
        Integer instructorId = identifyUserAccessService.getInstructor().getId();
        return notificationRepository.getAllNotificationsByInstructorId(instructorId);
    }

    @Override
    public NotificationDTO createNotificationFromInstructorToStudent(String content, Integer recieve_student_Id, Integer send_instructor_id) {
        Student receiveStudent = studentRepository.findById(recieve_student_Id)
                .orElseThrow(() -> new AppUnCheckedException("Student not found", HttpStatus.NOT_FOUND));

        Instructor sendInstructor = instructorRepository.findById(send_instructor_id)
                .orElseThrow(() -> new AppUnCheckedException("Instructor not found", HttpStatus.NOT_FOUND));

        Notification notification = Notification.builder()
                .content(content)
                .date(LocalDate.now())
                .receiveStudent(receiveStudent)
                .sendInstructor(sendInstructor)
                .build();

        notification = notificationRepository.save(notification);
        return notificationMapper.toDTO(notification);
    }
}



package com.fpoly.backend.services.impl;

import com.fpoly.backend.dto.AttendanceDTO;
import com.fpoly.backend.entities.*;
import com.fpoly.backend.mapper.AttendanceMapper;
import com.fpoly.backend.repository.*;
import com.fpoly.backend.services.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {
    private final StudentRepository studentRepository;
    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;
    private final ScheduleRepository scheduleRepository;
    private final RetakeScheduleRepository retakeScheduleRepository;
    private final ExamScheduleRepository examScheduleRepository;

    @Override
    public List<AttendanceDTO> getAttendanceByStudentId(int studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(()->
                new RuntimeException("Student not found"));

        return attendanceRepository.findAllByStudent(student).stream()
                .map(attendanceMapper::toDTO).toList();
    }

    @Override
    public List<AttendanceDTO> markAttendance(List<AttendanceDTO> request) {
        List<Attendance> savedAttendances = new ArrayList<>();

        for(AttendanceDTO attendanceDTO:request ){
            Attendance attendance = attendanceMapper.toEntity(attendanceDTO);

            attendance.setSchedule(scheduleRepository.findById(attendanceDTO.getScheduleId())
                    .orElseThrow(() -> new RuntimeException("Schedule not found")));
            attendance.setStudent(studentRepository.findById(attendanceDTO.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Student not found")));
            if (attendanceDTO.getRetakeScheduleId() != null) {
                attendance.setRetakeSchedule(retakeScheduleRepository.findById(attendanceDTO.getRetakeScheduleId())
                        .orElseThrow(() -> new RuntimeException("Retake Schedule not found")));
            }else {
                attendance.setRetakeSchedule(null);
            }

            savedAttendances.add(attendanceRepository.save(attendance));
        }
        return savedAttendances.stream()
                .map(attendanceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Map<String,Object>> getAttendanceByClazzId(Integer clazzId, Integer scheduleId){
        return attendanceRepository.findStudentIdsByScheduleIdAndClazzId(clazzId,scheduleId);
    }

    @Override
    public List<AttendanceDTO> updateAttendance(List<AttendanceDTO> attendanceDTOs) {
        List<Attendance> updatedAttendances = new ArrayList<>();

        for (AttendanceDTO attendanceDTO : attendanceDTOs) {
            Attendance attendance = attendanceMapper.toEntity(attendanceDTO);

            Attendance existingAttendance = attendanceRepository.findByStudentIdAndScheduleId(attendanceDTO.getStudentId(), attendanceDTO.getScheduleId())
                    .orElseThrow(() -> new RuntimeException("Attendance not found"));

            existingAttendance.setPresent(attendanceDTO.getPresent());

            updatedAttendances.add(attendanceRepository.save(existingAttendance));
        }

        return updatedAttendances.stream()
                .map(attendanceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Map<String,Object>> findRetakeAttendanceByClazzIdAndRetakeScheduleId(Integer clazzId, Integer retakeScheduleId){
        RetakeSchedule retakeSchedule = retakeScheduleRepository.findById(retakeScheduleId).orElseThrow(() ->
                new RuntimeException("Không tìm thấy lịch dạy bù"));
        LocalDate date = retakeSchedule.getDate();
        Integer shift = retakeSchedule.getShift().getId();
        List<Map<String, Object>> students = studentRepository.findStudentForAttendanceByClazzId(clazzId);
        if (!students.isEmpty()){
            for (int i = 0; i < students.size(); i++){
                Map<String, Object> student = new HashMap<>(students.get(i));
                Integer studentId = (Integer) student.get("studentId");
                List<Schedule> duplicatedSchedule = scheduleRepository.findSchedulesByDateAndShiftAndStudentId(date, shift, studentId);
                List<RetakeSchedule> duplicatedRetakeSchedule = retakeScheduleRepository.findRetakeSchedulesByDateAndShiftAndStudentId(date, shift, studentId, retakeScheduleId);
                List<ExamSchedule> duplicatedExamSchedule = examScheduleRepository.findExamSchedulesByDateAndShiftAndStudentId(date, shift, studentId);
                if (duplicatedSchedule.isEmpty() && duplicatedRetakeSchedule.isEmpty() && duplicatedExamSchedule.isEmpty()){
                    student.put("presentable", true);
                    Attendance attendance = attendanceRepository.findAttendanceByStudentIdAndRetakeScheduleId(studentId,retakeScheduleId);
                    if (attendance == null){
                        student.put("present", false);
                    } else{
                        student.put("present", attendance.getPresent());
                    }
                } else {
                    student.put("presentable", false);
                    student.put("present", true);
                }
                students.set(i, student);
            }
        }
        return students;
    }

    @Override
    public List<AttendanceDTO> doAttendanceForRetake(List<AttendanceDTO> attendanceDTOS) {
        for (AttendanceDTO attendanceDTO : attendanceDTOS ){
            Integer studentId = attendanceDTO.getStudentId();
            Integer retakeScheduleId = attendanceDTO.getRetakeScheduleId();
            Boolean present = attendanceDTO.getPresent();
            Attendance attendance = attendanceRepository.findAttendanceByStudentIdAndRetakeScheduleId(studentId,retakeScheduleId);
            if (attendance != null){
                attendance.setPresent(present);
            } else {
                attendance = new Attendance();
                attendance.setRetakeSchedule(retakeScheduleRepository.findById(retakeScheduleId)
                        .orElseThrow(()-> new RuntimeException("Không tìm thấy lịch học bù")));
                attendance.setStudent(studentRepository.findById(studentId)
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên")));
                attendance.setPresent(present);
            }
            attendanceRepository.save(attendance);
        }
        return attendanceDTOS;
    }

    @Override
    public Boolean checkExistByRetakeScheduleId(Integer retakeScheduleId) {
        List<Attendance> attendances = attendanceRepository.findAttendancesByRetakeScheduleId(retakeScheduleId);
        if (attendances.isEmpty()){
            return false;
        }
        return true;
    }


}

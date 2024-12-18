package com.fpoly.backend.services;

import com.fpoly.backend.dto.AttendanceDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public interface AttendanceService {
    List<AttendanceDTO> getAttendanceByStudentId(int studentId);

    List<AttendanceDTO> markAttendance(List<AttendanceDTO> request);

    List<Map<String,Object>> getAttendanceByClazzId(Integer clazzId, Integer scheduleId);

    List<AttendanceDTO> updateAttendance(List<AttendanceDTO> attendanceDTOs) ;

    List<Map<String,Object>> findRetakeAttendanceByClazzIdAndRetakeScheduleId(Integer clazzId, Integer retakeScheduleId);

    List<AttendanceDTO> doAttendanceForRetake (List<AttendanceDTO> attendanceDTOS);

    Boolean checkExistByRetakeScheduleId (Integer retakeScheduleId);
}

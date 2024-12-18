package com.fpoly.backend.services;
import com.fpoly.backend.dto.ClazzDTO;

import com.fpoly.backend.dto.ScheduleDTO;
import com.fpoly.backend.entities.Schedule;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public interface ScheduleService {
    Schedule findById(Integer id);

    List<Map<String, Object>> getScheduleByDateRange(LocalDate startDate, LocalDate endDate);

    ScheduleDTO putScheduleStatus(ScheduleDTO request, Integer scheduleId);

    List<Map<String,Object>>getScheduleByScheduleStatus();

    ScheduleDTO create(ScheduleDTO request);
    ScheduleDTO update(ScheduleDTO request, Integer id);
    void delete(Integer id);
    ScheduleDTO getOne(Integer id);
    List<ScheduleDTO> getAll();
    void importStudySchedule(MultipartFile file);

    List<Map<String, Object>> getAllSchedulesByAdmin();


    List<Map<String, Object>> getAllSchedulesByBlockSemesterYearByAdmin(Integer block, String semester, Integer year);

    List<Map<String, Object>> getSchedulesFromRetakeSchedules(LocalDate startDate, LocalDate endDate);
}

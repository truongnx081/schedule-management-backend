package com.fpoly.backend.services;

import com.fpoly.backend.dto.ClazzDTO;
import com.fpoly.backend.dto.ExamScheduleDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public interface ExamScheduleService {
    List<Map<String, Object>> getExameScheduleByDateRange(LocalDate startDate, LocalDate endDate);
    ExamScheduleDTO create(ExamScheduleDTO request);
    ExamScheduleDTO update(ExamScheduleDTO request, Integer id);
    void delete(Integer id);
    ExamScheduleDTO getOne(Integer id);
    
    void importExamSchedule(MultipartFile file);

    List<Map<String, Object>> getAllBathByClazzInstructor(Integer clazzId);

    List<Map<String, Object>> getAllExamOfAdmin();

    List<Map<String, Object>> getAllExamByBlockAndSemesterAndYearAndSpecializationIdOfAdmin(Integer block, String semester, Integer year, Integer specializationId);
}

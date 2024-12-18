package com.fpoly.backend.services;

import com.fpoly.backend.dto.StudyInDTO;
import com.fpoly.backend.entities.Student;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface StudyInService {
    List<StudyInDTO> findAllByBlockAndSemesterAnhYear();

    List<Map<String, Object>> getAllIdOfStudyInByBlockAndSemesterAndYearOfStudent(
            Integer blockId, String semesterId, Integer yearId
    );

    List<Map<String, Object>> getAllMarkAverageStudentsByClazzId(Integer clazzId);

    StudyInDTO create (Integer clazzId);

    StudyInDTO update(Integer oldClazzId, Integer newClazzId);

    void delete(Integer id);

    void updateAllStudyInIsTrueByStudent();

    List<Map<String, Object>> getAllIdOfStudyInByBlockAndSemesterAndYearOfStudent2();
}

package com.fpoly.backend.services;

import com.fpoly.backend.dto.StudyResultDTO;
import com.fpoly.backend.entities.StudyResult;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public interface StudyResultService {

//    List<Map<String, Object>> getAllStudyResultByStudentId();
    List<Map<String, Object>> findAllByStudyInId(Integer studyInId);
    Map<String, Object> countPassAndFalseByBlockAndSemesterAndYearOfStudent(
            Integer blockId, String semesterId, Integer yearId
    );


//    List<Map<String, Object>> getAllMarkColumn(Integer clazzId, Integer studentId);

    public Map<String, Integer> getreportLearningProgressByStudentId();

//    StudyResultDTO createStudyResult(Integer studentId, StudyResultDTO studyResultDTO);
//
//    StudyResultDTO updateStudyResult(Integer studyResultId, StudyResultDTO studyResultDTO);
//
//    List<Map<String, Object>> getAllMarkDetail(Integer clazzId, Integer subjectId);

    List<Map<String, Object>>getMarkByClazzId(Integer clazzId);

    List<Map<String, Object>> findStudyHistoryByStudentId();

    List<Map<String, Object>> findMarkTableByStudentId();

    List<Map<String, Object>> findMarkDetailByStudyInId(Integer studyInId);

    List<Map<String, Object>> findStudyResultByStudentIdAndClazzId (Integer studentId, Integer clazzId);

    List<StudyResultDTO> update(Integer studentId, Integer clazzId, List<StudyResultDTO> studyResultDTOS);



}

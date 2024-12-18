package com.fpoly.backend.services;

import com.fpoly.backend.dto.SubjectDTO;
import com.fpoly.backend.dto.SubjectMarkColumnDTO;
import com.fpoly.backend.entities.Subject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public interface SubjectService {
    Subject findById(Integer id);

    List<Map<String, Object>> getAllSubjectBySpecializationId(Integer specializationId);

    List<Map<String, Object>> findAllSubject();

    List<Map<String, Object>> getSubjectDetail(Integer subjectId);
    List<Map<String, Object>> findAllSubjectByEducationProgramId(Integer educationProgramId);
    void create(SubjectMarkColumnDTO request);
    void update(SubjectMarkColumnDTO request, Integer subjectId);
    void delete(Integer subjectId);
    void importSubject(MultipartFile file);
}

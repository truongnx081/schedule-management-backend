package com.fpoly.backend.services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface SubjectMarkService {

    List<Map<String, Object>> findSubjectMarkByClazzId(Integer clazzId);
    List<Map<String, Object>> findSubjectMarkBySubjectId(Integer subjectId);
}

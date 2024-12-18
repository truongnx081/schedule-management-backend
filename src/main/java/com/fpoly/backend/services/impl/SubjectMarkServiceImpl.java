package com.fpoly.backend.services.impl;

import com.fpoly.backend.entities.Clazz;
import com.fpoly.backend.exception.AppUnCheckedException;
import com.fpoly.backend.repository.ClazzRepository;
import com.fpoly.backend.repository.SubjectMarkRepository;
import com.fpoly.backend.services.SubjectMarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SubjectMarkServiceImpl implements SubjectMarkService {
    @Autowired
    ClazzRepository clazzRepository;
    @Autowired
    SubjectMarkRepository subjectMarkRepository;

    @Override
    public List<Map<String, Object>> findSubjectMarkByClazzId(Integer clazzId) {
        Clazz clazz = clazzRepository.findById(clazzId).orElseThrow(() -> new AppUnCheckedException("Lớp học không tồn tại", HttpStatus.NOT_FOUND));
        Integer subjectId = clazz.getSubject().getId();
        return subjectMarkRepository.findMarkColumnBySubjectId(subjectId);
    }

    @Override
    public List<Map<String, Object>> findSubjectMarkBySubjectId(Integer subjectId) {
        return subjectMarkRepository.findMarkColumnBySubject(subjectId);
    }
}

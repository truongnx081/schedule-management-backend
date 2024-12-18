package com.fpoly.backend.services.impl;

import com.fpoly.backend.dto.SemesterDTO;
import com.fpoly.backend.entities.Semester;
import com.fpoly.backend.entities.SemesterProgress;
import com.fpoly.backend.mapper.SemesterMapper;
import com.fpoly.backend.repository.SemesterProgressRepository;
import com.fpoly.backend.repository.SemesterRepository;
import com.fpoly.backend.services.SemesterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SemesterServiceImpl implements SemesterService {

    @Autowired
    SemesterRepository semesterRepository;
    @Autowired
    private SemesterMapper semesterMapper;
    @Autowired
    private SemesterProgressRepository semesterProgressRepository;

    @Override
    public Semester findById(String semester) {
        return semesterRepository.findById(semester).orElse(null);
    }

    @Override
    public List<SemesterDTO> getAllSemester() {
        return semesterRepository.findAll().stream().map(semesterMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> findAllSemestersWithDefault() {
        SemesterProgress semesterProgress = semesterProgressRepository.findActivedProgress();
        String activedSemester = semesterProgress.getSemester().getSemester();
        List<Map<String, Object>> semesters = semesterRepository.findAllSemester();

        for (int i = 0; i < semesters.size(); i++){
            HashMap<String, Object> semester = new HashMap<>(semesters.get(i));
            if (semester.get("semester").equals(activedSemester)){
                semester.put("default", true);
            } else{
                semester.put("default", false);
            }
            semesters.set(i, semester);
        }

        return semesters;
    }


}

package com.fpoly.backend.services.impl;

import com.fpoly.backend.dto.YearDTO;
import com.fpoly.backend.entities.SemesterProgress;
import com.fpoly.backend.entities.Year;
import com.fpoly.backend.mapper.YearMapper;
import com.fpoly.backend.repository.SemesterProgressRepository;
import com.fpoly.backend.repository.YearRepository;
import com.fpoly.backend.services.YearService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class YearServiceImpl implements YearService {

    @Autowired
    YearRepository yearRepository;
    @Autowired
    private YearMapper yearMapper;
    @Autowired
    SemesterProgressRepository semesterProgressRepository;


    @Override
    public Year findById(Integer year) {
        return yearRepository.findById(year).orElse(null);
    }

    @Override
    public List<YearDTO> findAll() {
        return yearRepository.findAll()
                .stream()
                .map(yearMapper::toDTO).toList();
    }

    @Override
    public List<Map<String, Object>> findAllYearsWithDefault() {
        SemesterProgress activedSemesterProgress  = semesterProgressRepository.findActivedProgress();
        Integer activedYear = activedSemesterProgress.getYear().getYear();
        List<Map<String, Object>> years = yearRepository.findAllYear();
        for (int i = 0; i < years.size(); i++){
            Map<String, Object> year = new HashMap<>(years.get(i));
            if (year.get("year").equals(activedYear)){
                year.put("default", true);
            } else{
                year.put("default", false);
            }
            years.set(i,year);
        }
        return years;
    }
}

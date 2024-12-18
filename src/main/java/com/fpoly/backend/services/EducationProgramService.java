package com.fpoly.backend.services;

import com.fpoly.backend.dto.ApplyForEducationDTO;
import com.fpoly.backend.dto.EducationProgramDTO;
import com.fpoly.backend.entities.EducationProgram;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EducationProgramService {
    EducationProgram findById(Integer id);
    List<EducationProgramDTO> findAll();
    boolean create(ApplyForEducationDTO request);
    boolean update(ApplyForEducationDTO request, Integer educationProgramId);
}

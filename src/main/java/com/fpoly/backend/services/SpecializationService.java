package com.fpoly.backend.services;

import com.fpoly.backend.dto.SpecializationDTO;
import com.fpoly.backend.entities.Specialization;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SpecializationService {
    Specialization findById(Integer id);

    List<SpecializationDTO> getAllSpecializations();
}

package com.fpoly.backend.services.impl;

import com.fpoly.backend.dto.SpecializationDTO;
import com.fpoly.backend.entities.Specialization;
import com.fpoly.backend.mapper.SpecializationMapper;
import com.fpoly.backend.repository.SpecializationRepository;
import com.fpoly.backend.services.SpecializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpecializationServiceImpl implements SpecializationService {
    @Autowired
    SpecializationRepository specializationRepository;

    private final SpecializationMapper specializationMapper;


    @Override
    public Specialization findById(Integer id) {
        return specializationRepository.findById(id).orElse(null);
    }

    @Override
    public List<SpecializationDTO> getAllSpecializations() {
        return specializationRepository.findAll().stream().map(specializationMapper::toDTO).collect(Collectors.toList());
    }
}

package com.fpoly.backend.services.impl;

import com.fpoly.backend.dto.MajorDTO;
import com.fpoly.backend.entities.Major;
import com.fpoly.backend.mapper.MajorMapper;
import com.fpoly.backend.repository.MajorRepository;
import com.fpoly.backend.services.MajorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MajorServiceImpl implements MajorService {

    @Autowired
    MajorRepository majorRepository;
    @Autowired
    private MajorMapper majorMapper;

    @Override
    public Major findById(Integer id) {
        return majorRepository.findById(id).orElse(null);
    }

    @Override
    public List<MajorDTO> getAllMajor() {
        return majorRepository.findAll().stream().map(majorMapper::toDTO).collect(Collectors.toList());
    }
}

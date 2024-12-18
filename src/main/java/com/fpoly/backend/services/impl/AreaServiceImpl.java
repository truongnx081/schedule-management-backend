package com.fpoly.backend.services.impl;

import com.fpoly.backend.dto.AreaDTO;
import com.fpoly.backend.entities.Area;
import com.fpoly.backend.mapper.AreaMapper;
import com.fpoly.backend.repository.AreaRepository;
import com.fpoly.backend.services.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {

    @Autowired
    AreaRepository areaRepository;
    @Autowired
    private AreaMapper areaMapper;

    @Override
    public Area findById(Integer id) {
        return areaRepository.findById(id).orElse(null);
    }

    @Override
    public List<AreaDTO> getAll() {
        return areaRepository.findAll().stream().map(areaMapper::toDTO).toList();
    }
}

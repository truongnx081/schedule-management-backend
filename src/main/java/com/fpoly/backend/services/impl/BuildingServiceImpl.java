package com.fpoly.backend.services.impl;

import com.fpoly.backend.entities.Building;
import com.fpoly.backend.repository.BuildingRepository;
import com.fpoly.backend.services.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuildingServiceImpl implements BuildingService {
    @Autowired
    BuildingRepository buildingRepository;

    @Override
    public Building findById(Integer id) {
        return buildingRepository.findById(id).orElse(null);
    }
}

package com.fpoly.backend.services;

import com.fpoly.backend.entities.Building;
import org.springframework.stereotype.Service;

@Service
public interface BuildingService {
    Building findById(Integer id);
}

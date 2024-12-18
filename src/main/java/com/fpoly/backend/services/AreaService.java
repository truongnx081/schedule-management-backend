package com.fpoly.backend.services;

import com.fpoly.backend.dto.AreaDTO;
import com.fpoly.backend.entities.Area;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AreaService {
    Area findById(Integer id);
    List<AreaDTO> getAll();
}

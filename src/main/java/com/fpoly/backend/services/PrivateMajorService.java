package com.fpoly.backend.services;

import com.fpoly.backend.entities.PrivateMajor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface PrivateMajorService {
    PrivateMajor findById(Integer id);
    List<Map<String, Object>> findAll();
}

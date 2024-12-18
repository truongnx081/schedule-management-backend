package com.fpoly.backend.services;

import com.fpoly.backend.entities.NoteType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface NoteTypeService {
    NoteType findById(Integer id);

    List<Map<String, Object>> getAllNotType();
}

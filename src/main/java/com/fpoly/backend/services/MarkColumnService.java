package com.fpoly.backend.services;

import com.fpoly.backend.dto.MarkColumnDTO;
import com.fpoly.backend.entities.MarkColumn;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MarkColumnService {
    MarkColumn findById(Integer id);
    List<MarkColumnDTO> getAll();
}

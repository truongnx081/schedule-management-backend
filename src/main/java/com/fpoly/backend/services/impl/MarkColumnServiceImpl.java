package com.fpoly.backend.services.impl;

import com.fpoly.backend.dto.MarkColumnDTO;
import com.fpoly.backend.entities.MarkColumn;
import com.fpoly.backend.mapper.MarkColumnMapper;
import com.fpoly.backend.repository.MarkColumnRepository;
import com.fpoly.backend.services.MarkColumnService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarkColumnServiceImpl implements MarkColumnService {

    @Autowired
    MarkColumnRepository markColumnRepository;
    @Autowired
    private MarkColumnMapper markColumnMapper;

    @Override
    public MarkColumn findById(Integer id) {
        return markColumnRepository.findById(id).orElse(null);
    }

    @Override
    public List<MarkColumnDTO> getAll() {
        return markColumnRepository.findAll().stream()
                .map(markColumnMapper::toDTO).toList();
    }
}

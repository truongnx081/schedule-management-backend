package com.fpoly.backend.services;

import com.fpoly.backend.dto.MajorDTO;
import com.fpoly.backend.entities.Major;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MajorService {
    Major findById(Integer id);

    List<MajorDTO> getAllMajor();
}

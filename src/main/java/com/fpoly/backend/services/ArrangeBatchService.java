package com.fpoly.backend.services;

import com.fpoly.backend.dto.ArrangeBatchDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ArrangeBatchService {
    ArrangeBatchDTO createArrangeBatch(ArrangeBatchDTO arrangeBatchDTO);

    ArrangeBatchDTO updateArrangeBatch(Integer arrangeBatchId, ArrangeBatchDTO arrangeBatchDTO);

    void deleteArrangeBatch(Integer arrangeBatchId);

    List<ArrangeBatchDTO> doArrangeBatch(List<ArrangeBatchDTO> arrangeBatchDTOS);
}

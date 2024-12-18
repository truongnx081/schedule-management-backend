package com.fpoly.backend.services;

import com.fpoly.backend.dto.BlockDTO;
import com.fpoly.backend.entities.Block;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface BlockService {
    Block findById (Integer block);

    List<BlockDTO> getAllBlocks();

    List<Map<String,Object>> findAllBlocksWithDefault();

}

package com.fpoly.backend.services.impl;

import com.fpoly.backend.dto.BlockDTO;
import com.fpoly.backend.entities.Block;
import com.fpoly.backend.entities.SemesterProgress;
import com.fpoly.backend.mapper.BlockMapper;
import com.fpoly.backend.repository.BlockRepository;
import com.fpoly.backend.repository.SemesterProgressRepository;
import com.fpoly.backend.services.BlockService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlockServiceImpl implements BlockService {

    @Autowired
    BlockRepository blockRepository;

    @Autowired
    BlockMapper blockMapper;

    @Autowired
    SemesterProgressRepository semesterProgressRepository;

    @Override
    public Block findById(Integer block) {
        return blockRepository.findById(block).orElse(null);
    }

    @Override
    public List<BlockDTO> getAllBlocks() {
        return blockRepository.findAll().stream().map(blockMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> findAllBlocksWithDefault() {
        SemesterProgress semesterProgres = semesterProgressRepository.findActivedProgress();
        Integer activedBlock = semesterProgres.getBlock().getBlock();

        List<Map<String,Object>> blocks = blockRepository.findAllBlocks();
        for (int i = 0; i < blocks.size(); i++){
            HashMap<String,Object> block = new HashMap<>(blocks.get(i));
            if (block.get("block").equals(activedBlock)){
                block.put("default", true);
            }else{
                block.put("default", false);
            }
            blocks.set(i,block);
        }
        return blocks;
    }


}

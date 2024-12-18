package com.fpoly.backend.mapper;

import com.fpoly.backend.dto.BlockDTO;
import com.fpoly.backend.entities.Block;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class BlockMapper {

    public abstract BlockDTO toDTO (Block block);

    public abstract  void updateBlock(@MappingTarget Block block, BlockDTO request);


    public abstract Block toEntity(BlockDTO blockDTO);
}

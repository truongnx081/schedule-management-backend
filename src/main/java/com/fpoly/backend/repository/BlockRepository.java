package com.fpoly.backend.repository;

import com.fpoly.backend.entities.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface BlockRepository extends JpaRepository<Block,Integer> {

    @Query("SELECT b.block as block FROM Block b")
    List<Map<String, Object>> findAllBlocks();
}

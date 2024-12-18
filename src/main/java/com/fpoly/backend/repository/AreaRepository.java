package com.fpoly.backend.repository;

import com.fpoly.backend.entities.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AreaRepository extends JpaRepository<Area,Integer> {
    Optional<Area> findByName(String name);
}

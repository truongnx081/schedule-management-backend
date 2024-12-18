package com.fpoly.backend.repository;

import com.fpoly.backend.entities.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRepository extends JpaRepository<Building,Integer> {
}

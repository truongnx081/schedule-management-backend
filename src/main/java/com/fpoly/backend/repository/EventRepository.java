package com.fpoly.backend.repository;

import com.fpoly.backend.entities.Area;
import com.fpoly.backend.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Integer> {
    List<Event> findAllByArea(Area area);
}

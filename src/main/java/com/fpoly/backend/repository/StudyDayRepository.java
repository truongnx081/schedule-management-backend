package com.fpoly.backend.repository;

import com.fpoly.backend.entities.Clazz;
import com.fpoly.backend.entities.StudyDay;
import com.fpoly.backend.entities.WeekDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyDayRepository extends JpaRepository<StudyDay,Integer> {
    List<StudyDay> findAllByClazz(Clazz clazz);
}

package com.fpoly.backend.repository;

import com.fpoly.backend.entities.WeekDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeekDayRepository extends JpaRepository<WeekDay,Integer> {

    @Query("SELECT wd.day FROM WeekDay wd JOIN wd.studyDays sd WHERE sd.clazz.id = :clazzId")
    List<String> findWeekDayByClazzId(@Param("clazzId") Integer clazzId);

    @Query("SELECT wd.id FROM WeekDay wd JOIN wd.studyDays sd WHERE sd.clazz.id = :clazzId")
    List<Integer> findWeekDayIdByClazzId(@Param("clazzId") Integer clazzId);
}

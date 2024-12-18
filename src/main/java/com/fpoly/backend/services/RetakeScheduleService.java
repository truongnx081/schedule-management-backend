package com.fpoly.backend.services;

import com.fpoly.backend.dto.RetakeScheduleDTO;
import com.fpoly.backend.dto.RoomDTO;
import com.fpoly.backend.dto.ShiftDTO;
import com.fpoly.backend.entities.RetakeSchedule;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public interface RetakeScheduleService {

    public RetakeScheduleDTO createRetakeSchedule(RetakeScheduleDTO dto);
    List<Map<String, Object>> getRetakeScheduleByInstructor(LocalDate startDate, LocalDate endDate);
}

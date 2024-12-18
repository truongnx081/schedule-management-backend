package com.fpoly.backend.services.impl;

import com.fpoly.backend.dto.RetakeScheduleDTO;
import com.fpoly.backend.dto.RoomDTO;
import com.fpoly.backend.dto.ShiftDTO;
import com.fpoly.backend.entities.Instructor;
import com.fpoly.backend.entities.RetakeSchedule;
import com.fpoly.backend.entities.Room;
import com.fpoly.backend.entities.Student;
import com.fpoly.backend.mapper.RetakeScheduleMapper;
import com.fpoly.backend.repository.*;
import com.fpoly.backend.services.IdentifyUserAccessService;
import com.fpoly.backend.services.RetakeScheduleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RetakeScheduleServiceImpl implements RetakeScheduleService {

    @Autowired
    RetakeScheduleMapper retakeScheduleMapper;

    @Autowired
    RetakeScheduleRepository retakeScheduleRepository;
    @Autowired
    RoomRepository roomRepository;

    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    ShiftRepository shiftRepository;
    @Autowired
    IdentifyUserAccessService identifyUserAccessService;

    @Override
    public RetakeScheduleDTO createRetakeSchedule(RetakeScheduleDTO dto) {
        RetakeSchedule retakeSchedule = retakeScheduleMapper.toEntity(dto);

        retakeSchedule.setCreatedBy(identifyUserAccessService.getInstructor().getCode());

        retakeSchedule.setSchedule(scheduleRepository.findById(dto.getScheduleId())
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found")));

        Room room = roomRepository.findByName(dto.getRoomName())
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));
        retakeSchedule.setRoom(room);


        retakeSchedule = retakeScheduleRepository.save(retakeSchedule);

        retakeSchedule.setShift(shiftRepository.findById(dto.getShiftId())
                .orElseThrow(() -> new IllegalArgumentException("Shift not found")));
        retakeSchedule = retakeScheduleRepository.save(retakeSchedule);

        return retakeScheduleMapper.toDTO(retakeSchedule);
    }

    @Override
    public  List<Map<String, Object>> getRetakeScheduleByInstructor(LocalDate startDate, LocalDate endDate) {
        Integer instructorId =identifyUserAccessService.getInstructor().getId();
        return retakeScheduleRepository.getRetakeScheduleByInstructor(instructorId,startDate,endDate);
    }
}

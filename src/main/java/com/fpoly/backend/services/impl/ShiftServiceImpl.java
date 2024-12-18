package com.fpoly.backend.services.impl;

import com.fpoly.backend.dto.ShiftDTO;
import com.fpoly.backend.entities.Instructor;
import com.fpoly.backend.mapper.ShiftMapper;
import com.fpoly.backend.repository.ShiftRepository;
import com.fpoly.backend.services.IdentifyUserAccessService;
import com.fpoly.backend.services.ShiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ShiftServiceImpl implements ShiftService {
    @Autowired
    ShiftRepository shiftRepository;
    @Autowired
    ShiftMapper shiftMapper;
    @Autowired
    IdentifyUserAccessService identifyUserAccessService;

    @Override
    public List<ShiftDTO> getAllShift() {
        return shiftRepository.findAll()
                .stream().map(shiftMapper::toDTO).toList();
    }

    @Override
    public List<ShiftDTO> getAvailableShifts(Integer clazzId, LocalDate date){
        Integer instructorId = identifyUserAccessService.getInstructor().getId();
        Set<Integer> bookedShifts = new HashSet<>();

        List<Integer> instructorShiftsFromSchedule = shiftRepository.findInstructorShiftsFromSchedule(clazzId, instructorId,date);
        List<Integer> instructorShiftsFromExamSchedule = shiftRepository.findInstructorShiftsFromExamSchedule(clazzId, instructorId,date);
        List<Integer> instructorShiftsFromRetakeSchedule = shiftRepository.findInstructorShiftsFromRetakeSchedule(clazzId, instructorId,date);

        List<Integer> studentShiftsFromSchedule = shiftRepository.findStudentShiftFromSchedule(clazzId,date);
        List<Integer> studentShiftsFromExamSchedule = shiftRepository.findStudentShiftFromExamSchedule(clazzId,date);
        List<Integer> studentShiftsFromRetakeSchedule = shiftRepository.findStudentShiftFromRetakeSchedule(clazzId,date);

        bookedShifts.addAll(instructorShiftsFromSchedule);
        bookedShifts.addAll(instructorShiftsFromExamSchedule);
        bookedShifts.addAll(instructorShiftsFromRetakeSchedule);
        bookedShifts.addAll(studentShiftsFromSchedule);
        bookedShifts.addAll(studentShiftsFromExamSchedule);
        bookedShifts.addAll(studentShiftsFromRetakeSchedule);

        List<ShiftDTO> allShiftsAvailable = getAllShift();

        return allShiftsAvailable.stream()
                .filter(shift -> !bookedShifts.contains(shift.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ShiftDTO> findAvailableShiftsByInstructorIdAndDate(LocalDate date) {
        Instructor instructor = identifyUserAccessService.getInstructor();
        Integer instructorId = instructor.getId();

        Set<Integer> bookedShifts = new HashSet<>();

        List<Integer> instructorShiftsFromSchedule = shiftRepository.findBusyShitftsFromScheduleByInstructorIdAndDate(instructorId,date);
        List<Integer> instructorShiftsFromExamSchedule = shiftRepository.findBusyShitftsFromExamScheduleByInstructorIdAndDate(instructorId,date);
        List<Integer> instructorShiftsFromRetakeSchedule = shiftRepository.findBusyShitftsFromRetakeScheduleByInstructorIdAndDate(instructorId,date);

        bookedShifts.addAll(instructorShiftsFromSchedule);
        bookedShifts.addAll(instructorShiftsFromExamSchedule);
        bookedShifts.addAll(instructorShiftsFromRetakeSchedule);

        List<ShiftDTO> allShiftsAvailable = getAllShift();

        return allShiftsAvailable.stream()
                .filter(shift -> !bookedShifts.contains(shift.getId()))
                .collect(Collectors.toList());
    }
}

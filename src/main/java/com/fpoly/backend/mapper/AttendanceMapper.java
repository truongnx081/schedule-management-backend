package com.fpoly.backend.mapper;

import com.fpoly.backend.dto.AttendanceDTO;
import com.fpoly.backend.entities.Attendance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class AttendanceMapper {

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "schedule.id", target = "scheduleId")
    @Mapping(source = "retakeSchedule.id", target = "retakeScheduleId")
    public abstract AttendanceDTO toDTO (Attendance attendance);

    @Mapping(target = "student", ignore = true)
    @Mapping(target = "schedule", ignore = true)
    @Mapping(target = "retakeSchedule", ignore = true)
    public abstract  void updateAttendance(@MappingTarget Attendance attendance, AttendanceDTO request);


    @Mapping(target = "student", ignore = true)
    @Mapping(target = "schedule", ignore = true)
    @Mapping(target = "retakeSchedule", ignore = true)
    public abstract Attendance toEntity(AttendanceDTO attendanceDTO);

}

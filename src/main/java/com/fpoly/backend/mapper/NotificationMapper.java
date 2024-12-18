package com.fpoly.backend.mapper;

import com.fpoly.backend.dto.NotificationDTO;
import com.fpoly.backend.entities.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class NotificationMapper {

    @Mapping(source = "receiveStudent.id", target = "receiveStudentId")
    @Mapping(source = "receiveAdmin.id", target = "receiveAdminId")
    @Mapping(source = "receiveInstructor.id", target = "receiveInstructorId")
    @Mapping(source = "sendStudent.id", target = "sendStudentId")
    @Mapping(source = "sendAdmin.id", target = "sendAdminId")
    @Mapping(source = "sendInstructor.id", target = "sendInstructorId")
    public abstract NotificationDTO toDTO (Notification notification);

    @Mapping(target = "receiveStudent", ignore = true)
    @Mapping(target = "receiveAdmin", ignore = true)
    @Mapping(target = "receiveInstructor", ignore = true)
    @Mapping(target = "sendStudent", ignore = true)
    @Mapping(target = "sendAdmin", ignore = true)
    @Mapping(target = "sendInstructor", ignore = true)
    public abstract Notification toEntity(NotificationDTO notificationDTO);

    @Mapping(target = "receiveStudent", ignore = true)
    @Mapping(target = "receiveAdmin", ignore = true)
    @Mapping(target = "receiveInstructor", ignore = true)
    @Mapping(target = "sendStudent", ignore = true)
    @Mapping(target = "sendAdmin", ignore = true)
    @Mapping(target = "sendInstructor", ignore = true)
    public abstract void updateNotification(@MappingTarget Notification notification, NotificationDTO request);
}

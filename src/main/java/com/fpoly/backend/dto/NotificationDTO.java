package com.fpoly.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationDTO {
    private Integer id;

    private String content;

    private Date date;

    private Integer receiveStudentId;

    private Integer receiveAdminId;

    private Integer receiveInstructorId;

    private Integer sendStudentId;

    private Integer sendAdminId;

    private Integer sendInstructorId;
}

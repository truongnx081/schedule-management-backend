package com.fpoly.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class InstructorDTO {
    private Integer id;

    private  String code;

    private String lastName;

    private String firstName;

    private boolean gender;

    private LocalDate birthday;

    private String phone;

    private String address;

    private String schoolEmail;

    private String privateEmail;

    private String avatar;

    private String description;

    private Integer specializationId;
}

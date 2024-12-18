package com.fpoly.backend.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentDTO {
    private Integer id;

    private String code;

    private String firstName;

    private String lastName;

    private String email;

    private Boolean gender;

//    @JsonFormat(pattern = "yyyy-MM-dd")
     private LocalDate birthday;

    private String phone;

    private String address;

    private String description;

    private String avatar;



    private String course;

    private Integer majorId;

    private String semester;

    private Integer year;

    private Integer educationProgramId;
}


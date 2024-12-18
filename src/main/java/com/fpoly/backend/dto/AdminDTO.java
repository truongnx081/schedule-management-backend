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

public class AdminDTO {
    private Integer id;

    private String code;

    private String firstName;

    private String lastName;

    private Boolean gender;

    private Date birthday;

    private String phoneNumber;

    private String email;

    private String description;

    private String address;

    private String avatar;

    private Boolean status;
}

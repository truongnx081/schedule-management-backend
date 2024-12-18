package com.fpoly.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class SemesterProgressDTO{
    private Integer id;

    private Date createDateStart;

    private Date createDateEnd;

    private Date repaireDateStart;

    private Date repaireDateEnd;

    private Date firstPartStart;

    private Date firstPartEnd;

    private Date secondPartStart;

    private Date secondPartEnd;

    private Boolean isActive;

    private Integer block;

    private String semester;

    private Integer year;
}

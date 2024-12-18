package com.fpoly.backend.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BuildingDTO {

    private Integer id;

    private String name;

    private String place;

    private String note;

    private boolean status;

    private Integer areaId;
}

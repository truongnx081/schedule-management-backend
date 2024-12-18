package com.fpoly.backend.dto;

import com.fpoly.backend.entities.Clazz;
import com.fpoly.backend.entities.Room;
import com.fpoly.backend.entities.Shift;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ExamScheduleDTO {
    private Integer id;

    private LocalDate date;

    private Integer clazzId;

    private Integer batch;

    private Integer roomId;

    private Integer shiftId;

}

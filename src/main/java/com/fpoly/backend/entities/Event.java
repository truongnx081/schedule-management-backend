package com.fpoly.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Setter
@Getter
@Table(name = "events")
public class Event extends AbstractEntity<Integer>{
    @Column(name = "name", columnDefinition = "NVARCHAR(255)")
    private String name;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "place", columnDefinition = "NVARCHAR(255)")
    private String place;

    @Column(name = "content", columnDefinition = "NVARCHAR(255)")
    private String content;

    @Column(name = "image", columnDefinition = "VARCHAR(255)")
    private String image;

    @Column(name = "status")
    private boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    private Area area;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;
}

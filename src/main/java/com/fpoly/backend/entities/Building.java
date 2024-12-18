package com.fpoly.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Setter
@Getter
@Table(name = "buildings")
public class Building extends AbstractEntity<Integer> {
    @Column (name = "name", columnDefinition = "NVARCHAR(30)")
    private String name;

    @Column(name = "place", columnDefinition = "NVARCHAR(255)")
    private String place;

    @Column(name = "note", columnDefinition = "NVARCHAR(255)")
    private String note;

    @Column(name = "status")
    private boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    private Area area;

    @JsonIgnore
    @OneToMany(mappedBy = "building", fetch = FetchType.LAZY)
    private List<Room> rooms;
}

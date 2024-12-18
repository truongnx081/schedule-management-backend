package com.fpoly.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "areas")
public class Area extends AbstractEntity<Integer>{
    @Column(name = "name", columnDefinition = "NVARCHAR(255)", unique = true)
    private String name;

    @Column(name = "status")
    private boolean status;

    @JsonIgnore
    @OneToMany(mappedBy = "area", fetch = FetchType.LAZY)
    private List<Specialization> specializations;

    @JsonIgnore
    @OneToMany(mappedBy = "area", fetch = FetchType.LAZY)
    private List<Building> buildings;

    @JsonIgnore
    @OneToMany(mappedBy = "area", fetch = FetchType.LAZY)
    private List<Event> events;

    @JsonIgnore
    @OneToMany(mappedBy = "area", fetch = FetchType.LAZY)
    private List<Admin> admins;

}

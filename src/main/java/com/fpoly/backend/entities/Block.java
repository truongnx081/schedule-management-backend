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
@Table(name = "blocks")
public class Block {
    @Id
    @Column(name = "block")
    private Integer block;

    @JsonIgnore
    @OneToMany(mappedBy = "block", fetch = FetchType.LAZY)
    private List<Clazz> clazzes;

    @JsonIgnore
    @OneToMany(mappedBy = "block", fetch = FetchType.LAZY)
    private List<SemesterProgress> semesterProgresses;

}

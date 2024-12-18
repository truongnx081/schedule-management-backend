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
@Table(name = "note_types")
public class NoteType extends AbstractEntity<Integer>{
    @Column(name = "name", columnDefinition = "NVARCHAR(255)")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "noteType", fetch = FetchType.LAZY)
    private List<Note> notes;
}

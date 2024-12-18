package com.fpoly.backend.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Setter
@Getter
@Table(name = "students")
public class Student extends AbstractEntity<Integer> {
    @Column(name = "code", columnDefinition = "VARCHAR(20)", unique = true)
    private String code;

    @Column(name = "first_name", columnDefinition = "NVARCHAR(255)")
    private String firstName;

    @Column(name = "last_name", columnDefinition = "NVARCHAR(255)")
    private String lastName;

    @Column(name = "email", columnDefinition = "VARCHAR(255)",unique = true)
    private String email;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "birthday")
//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDate birthday;

    @Column(name = "phone", columnDefinition = "VARCHAR(20)")
    private String phone;

    @Column(name = "address", columnDefinition = "NVARCHAR(255)")
    private String address;

    @Column(name = "description", columnDefinition = "NVARCHAR(255)")
    private String description;

    @Column(name = "avatar", columnDefinition = "VARCHAR(255)")
    private String avatar;

    @Column(name = "course", columnDefinition = "VARCHAR(255)")
    private String course;

    @Column(name = "status")
    private boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "major_id")
    private Major major;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester")
    private Semester semester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "year")
    private Year year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "education_program_id")
    private EducationProgram educationProgram;

    @JsonIgnore
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<Note> notes;

    @JsonIgnore
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<StudyIn> studyIns;


    @JsonIgnore
    @OneToMany(mappedBy = "receiveStudent", fetch = FetchType.LAZY)
    private List<Notification> receiveNotifications;

    @JsonIgnore
    @OneToMany(mappedBy = "sendStudent", fetch = FetchType.LAZY)
    private List<Notification> sendNotifications;

    @JsonIgnore
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<Attendance> attendances;

    @JsonIgnore
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<ArrangeBatch> arrangeBatches;

}

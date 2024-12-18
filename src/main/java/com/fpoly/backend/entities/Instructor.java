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
@Table(name = "instructors")
public class Instructor extends  AbstractEntity<Integer> {
    @Column(name = "code", columnDefinition = "VARCHAR(30)",unique = true)
    private  String code;

    @Column(name = "last_name", columnDefinition = "NVARCHAR(255)")
    private String lastName;

    @Column(name = "first_name", columnDefinition = "NVARCHAR(255)")
    private String firstName;

    @Column(name = "gender")
    private boolean gender;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "phone", columnDefinition = "VARCHAR(20)")
    private String phone;

    @Column(name = "address", columnDefinition = "NVARCHAR(255)")
    private String address;

    @Column(name = "school_email", columnDefinition = "VARCHAR(255)",unique = true)
    private String schoolEmail;

    @Column(name = "private_email", columnDefinition = "VARCHAR(255)",unique = true)
    private String privateEmail;

    @Column(name = "avatar", columnDefinition = "VARCHAR(255)")
    private String avatar;

    @Column(name = "description", columnDefinition = "NVARCHAR(255)")
    private String description;

    @Column(name = "status")
    private boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialization_id")
    private Specialization specialization;

    @JsonIgnore
    @OneToMany(mappedBy = "instructor", fetch = FetchType.LAZY)
    private List<Clazz> clazzes;

    @JsonIgnore
    @OneToMany(mappedBy = "receiveInstructor", fetch = FetchType.LAZY)
    private List<Notification> receiveNotifications;

    @JsonIgnore
    @OneToMany(mappedBy = "sendInstructor", fetch = FetchType.LAZY)
    private List<Notification> sendNotifications;

}

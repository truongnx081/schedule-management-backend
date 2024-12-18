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
@Table(name = "admins", uniqueConstraints = @UniqueConstraint(columnNames = "code"))
public class Admin extends AbstractEntity<Integer>{
    @Column(name = "code", columnDefinition = "VARCHAR(10)", unique = true)
    private String code;

    @Column(name = "first_name", columnDefinition = "NVARCHAR(255)")
    private String firstName;

    @Column(name = "last_name", columnDefinition = "NVARCHAR(255)")
    private String lastName;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email", columnDefinition = "VARCHAR(255)", unique = true)
    private String email;

    @Column(name = "description", columnDefinition = "NVARCHAR(255)")
    private String description;

    @Column(name = "address", columnDefinition = "NVARCHAR(255)")
    private String address;

    @Column(name = "avatar", columnDefinition = "VARCHAR(30)")
    private String avatar;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    private Area area;

    @JsonIgnore
    @OneToMany(mappedBy = "admin", fetch = FetchType.LAZY)
    private List<Event> events;

    @JsonIgnore
    @OneToMany(mappedBy = "admin", fetch = FetchType.LAZY)
    private List<Clazz> clazzes;

    @JsonIgnore
    @OneToMany(mappedBy = "receiveAdmin", fetch = FetchType.LAZY)
    private List<Notification> receiveNotifications;

    @JsonIgnore
    @OneToMany(mappedBy = "sendAdmin", fetch = FetchType.LAZY)
    private List<Notification> sendNotifications;

}

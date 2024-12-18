package com.fpoly.backend.services.impl;

import com.fpoly.backend.entities.Admin;
import com.fpoly.backend.entities.Instructor;
import com.fpoly.backend.entities.Student;
import com.fpoly.backend.repository.AdminRepository;
import com.fpoly.backend.repository.InstructorRepository;
import com.fpoly.backend.repository.StudentRepository;
import com.fpoly.backend.services.IdentifyUserAccessService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class IdentifyUserAccessServiceImpl  implements IdentifyUserAccessService {

    StudentRepository studentRepository;
    AdminRepository adminRepository;
    InstructorRepository instructorRepository;

    @Override
    public Admin getAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email=auth.getName();

        return adminRepository.findByEmail(email).orElseThrow(()->
                new RuntimeException("Admin not found")) ;
    }

    @Override
    public Student getStudent() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email=auth.getName();

        return studentRepository.findByEmail(email).orElseThrow(()->
                new RuntimeException("Admin not found")) ;
    }

    @Override
    public Instructor getInstructor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email=auth.getName();

        return instructorRepository.findBySchoolEmail(email).orElseThrow(()->
                new RuntimeException("Admin not found")) ;
    }
}

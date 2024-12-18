package com.fpoly.backend.services;

import com.fpoly.backend.entities.Admin;
import com.fpoly.backend.entities.Instructor;
import com.fpoly.backend.entities.Student;

public interface IdentifyUserAccessService {

    public Admin getAdmin();

    public Student getStudent();

    public Instructor getInstructor();
}

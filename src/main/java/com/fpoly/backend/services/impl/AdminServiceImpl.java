package com.fpoly.backend.services.impl;

import com.fpoly.backend.dto.AdminDTO;
import com.fpoly.backend.dto.StudentDTO;
import com.fpoly.backend.dto.StudyResultProjection;
import com.fpoly.backend.entities.Admin;
import com.fpoly.backend.entities.SemesterProgress;
import com.fpoly.backend.entities.Student;
import com.fpoly.backend.entities.StudyIn;
import com.fpoly.backend.mapper.AdminMapper;
import com.fpoly.backend.repository.*;
import com.fpoly.backend.services.AdminService;
import com.fpoly.backend.services.IdentifyUserAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminRepository adminRepository;
    @Autowired
    private IdentifyUserAccessService identifyUserAccessService;
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private StudyInRepository studyInRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private ClazzRepository clazzRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private SubjectMarkRepository subjectMarkRepository;

    @Autowired
    private StudyResultRepository studyResultRepository;

    @Autowired
    private MarkColumnRepository markColumnRepository;

    @Autowired
    private SemesterProgressRepository semesterProgressRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Admin findById(Integer id) {
        return adminRepository.findById(id).orElse(null);
    }

    @Override
    public AdminDTO getAdminInfor() {
        Admin admin = identifyUserAccessService.getAdmin();
        return adminMapper.toDTO(admin);
    }

    @Override
    public Map<String, Object> findStatistisByYear(Integer year) {
        SemesterProgress semesterProgress = semesterProgressRepository.findActivedProgress();
        Integer currentYear = semesterProgress.getYear().getYear();


        Map<String, Object> statistic = new HashMap<>();

        List<StudyIn> studyIns = studyInRepository.findStudyInsByYear(year);

        statistic.put("registed_students", studyIns.size());

        statistic.put("active_instructor", instructorRepository.countActiveInstructor());

        statistic.put("clazz_amount", clazzRepository.countClazzByYear(year));

        statistic.put("student_amout", studentRepository.countStudentsByYear(year));

        Integer pass = 0;
        Integer fail = 0;

        // Lấy tất cả kết quả trong một truy vấn
        List<StudyResultProjection> results = studyInRepository.findStudyResultsByYear(year);

        for (StudyResultProjection result : results) {
            Double averageMark = result.getAverageMark();
            Double finalMark = result.getFinalMark();

            if (averageMark != null) {
                if (averageMark < 5) {
                    fail++;
                } else {
                    if (finalMark != null && finalMark < 5) {
                        fail++;
                    } else {
                        pass++;
                    }
                }
            }
        }

        statistic.put("pass", pass);
        statistic.put("fail", fail);

        return statistic;
    }
}

package com.fpoly.backend.services.impl;

import com.fpoly.backend.dto.ApplyForDTO;
import com.fpoly.backend.dto.ApplyForEducationDTO;
import com.fpoly.backend.dto.EducationProgramDTO;
import com.fpoly.backend.entities.*;
import com.fpoly.backend.mapper.ApplyForMapper;
import com.fpoly.backend.mapper.ApplyForMapperImpl;
import com.fpoly.backend.mapper.EducationProgramMapper;
import com.fpoly.backend.mapper.EducationProgramMapperImpl;
import com.fpoly.backend.repository.*;
import com.fpoly.backend.services.EducationProgramService;
import com.fpoly.backend.services.IdentifyUserAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationProgramServiceImpl implements EducationProgramService {

    @Autowired
    EducationProgramRepository educationProgramRepository;
    @Autowired
    private EducationProgramMapper educationProgramMapper;
    @Autowired
    private SemesterRepository semesterRepository;
    @Autowired
    private YearRepository yearRepository;
    @Autowired
    private PrivateMajorRepository privateMajorRepository;
    @Autowired
    private ApplyForMapper applyForMapper;
    @Autowired
    private ApplyForRepository applyForRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private IdentifyUserAccessService identifyUserAccessService;

    @Override
    public EducationProgram findById(Integer id) {
        return educationProgramRepository.findById(id).orElse(null);
    }

    @Override
    public List<EducationProgramDTO> findAll() {
        return educationProgramRepository.findAll().stream()
                .map(educationProgramMapper::toDTO).toList();
    }

    @Override
    public boolean create(ApplyForEducationDTO request) {
        try {
            EducationProgram educationProgram = educationProgramMapper.toEntity(request.getEducationProgramDTO());

            Semester semester = semesterRepository.findById(request.getEducationProgramDTO().getSemester()).orElseThrow(()->
                    new RuntimeException("Học kỳ này không tồn tại"));

            Year year = yearRepository.findById(request.getEducationProgramDTO().getYear()).orElseThrow(()->
                    new RuntimeException("Năm học này không tồn tại"));

            if(request.getEducationProgramDTO().getPrivateMajorId() != 0){
                PrivateMajor privateMajor = privateMajorRepository.findById(request.getEducationProgramDTO().getPrivateMajorId()).orElseThrow(()->
                        new RuntimeException("Chuyên ngành hẹp này không tồn tại"));
                educationProgram.setPrivateMajor(privateMajor);
            }
            else
                throw new RuntimeException("Vui lòng chọn chuyên ngành đào tạo!");

            educationProgram.setId(null);
            educationProgram.setSemester(semester);
            educationProgram.setYear(year);
            educationProgram.setCreatedBy(identifyUserAccessService.getAdmin().getCode());

            EducationProgram educationProgram1 = educationProgramRepository.save(educationProgram);

            List<ApplyFor> applyFors = new ArrayList<>();

            for (String id : request.getIds()) {
                ApplyFor applyFor = new ApplyFor();
                Subject subject = subjectRepository.findById(Integer.parseInt(id)).orElseThrow(()->
                        new RuntimeException("Môn học này không tồn tại"));

                applyFor.setSubject(subject);
                applyFor.setEducationProgram(educationProgram1);

                applyFors.add(applyFor);
            }
            applyForRepository.saveAll(applyFors);

            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean update(ApplyForEducationDTO request, Integer educationProgramId) {
        try {
            EducationProgram educationProgram = educationProgramRepository.findById(educationProgramId).orElseThrow(()->
                    new RuntimeException("Chương trình đào tạo này không tồn tại"));

            educationProgramMapper.updateEducationProgram(educationProgram, request.getEducationProgramDTO());

            Semester semester = semesterRepository.findById(request.getEducationProgramDTO().getSemester()).orElseThrow(()->
                    new RuntimeException("Học kỳ này không tồn tại"));

            Year year = yearRepository.findById(request.getEducationProgramDTO().getYear()).orElseThrow(()->
                    new RuntimeException("Năm học này không tồn tại"));

            if(request.getEducationProgramDTO().getPrivateMajorId() != 0){
                PrivateMajor privateMajor = privateMajorRepository.findById(request.getEducationProgramDTO().getPrivateMajorId()).orElseThrow(()->
                        new RuntimeException("Chuyên ngành hẹp này không tồn tại"));
                educationProgram.setPrivateMajor(privateMajor);
            }
            else
                throw new RuntimeException("Vui lòng chọn chuyên ngành đào tạo!");

            educationProgram.setSemester(semester);
            educationProgram.setYear(year);
            educationProgram.setUpdatedBy(identifyUserAccessService.getAdmin().getCode());
            educationProgram.setUpdatedAt(new Date());

            educationProgramRepository.save(educationProgram);

            // Tìm kiếm tất cả appyfor của educationprogram
            List<ApplyFor> applyForsList = applyForRepository.findAllByEducationProgram(educationProgram);

            // Xóa tất cả
            applyForRepository.deleteAll(applyForsList);

            List<ApplyFor> applyFors = new ArrayList<>();

            for (String id : request.getIds()) {
                ApplyFor applyFor = new ApplyFor();
                Subject subject = subjectRepository.findById(Integer.parseInt(id)).orElseThrow(()->
                        new RuntimeException("Môn học này không tồn tại"));

                applyFor.setSubject(subject);
                applyFor.setEducationProgram(educationProgram);

                applyFors.add(applyFor);
            }
            applyForRepository.saveAll(applyFors);

            return true;
        }catch (Exception e){
            return false;
        }
    }
}

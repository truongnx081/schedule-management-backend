package com.fpoly.backend.services.impl;

import com.fpoly.backend.dto.InstructorDTO;
import com.fpoly.backend.entities.Instructor;
import com.fpoly.backend.entities.Specialization;
import com.fpoly.backend.entities.Student;
import com.fpoly.backend.mapper.InstructorMapper;
import com.fpoly.backend.repository.InstructorRepository;
import com.fpoly.backend.repository.SpecializationRepository;
import com.fpoly.backend.services.IdentifyUserAccessService;
import com.fpoly.backend.services.InstructorService;
import com.fpoly.backend.until.ExcelUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {
    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    IdentifyUserAccessService identifyUserAccessService;
    @Autowired
    private InstructorMapper instructorMapper;
    @Autowired
    private SpecializationRepository specializationRepository;
    @Autowired
    private ExcelUtility excelUtility;

    @Override
    public Instructor findById(Integer id) {
        return instructorRepository.findById(id).orElse(null);
    }

    @Override
    public List<Map<String, Object>> getAllTeachingScheduleByInstructor(LocalDate startDate, LocalDate endDate) {
        Integer instructorId = identifyUserAccessService.getInstructor().getId();
        return instructorRepository.getAllTeachingScheduleByInstructor(instructorId, startDate, endDate);
    }

    @Override
    public InstructorDTO getInstructorInfor() {
        Instructor instructor = identifyUserAccessService.getInstructor();
        return instructorMapper.toDTO(instructor);
    }

    @Override
    public List<InstructorDTO> getAllInstructorBySpecialization(Integer specializationId) {
        return instructorRepository.findAllBySpecializationId(specializationId).stream()
                .map(instructorMapper::toDTO).toList();
    }

    @Override
    public InstructorDTO create(InstructorDTO request) {
        Instructor instructor = instructorMapper.toEntity(request);

        Specialization specialization = specializationRepository.findById(request.getSpecializationId()).orElseThrow(()
        -> new RuntimeException("Bộ môn này không tồn tại"));

        if(instructorRepository.existsByCode(request.getCode()))
            throw new RuntimeException("Mã giảng viên này đã tồn tại");

        instructor.setSpecialization(specialization);
        return instructorMapper.toDTO(instructorRepository.save(instructor));
    }

    @Override
    public InstructorDTO update(InstructorDTO request, Integer id) {
        Instructor instructor = instructorRepository.findById(id).orElseThrow(()->
                new RuntimeException("Giảng viên này không tồn tại"));

        instructorMapper.updateInstructor(instructor, request);

        Specialization specialization = specializationRepository.findById(request.getSpecializationId()).orElseThrow(()
                -> new RuntimeException("Bộ môn này không tồn tại"));

        instructor.setSpecialization(specialization);
        return instructorMapper.toDTO(instructorRepository.save(instructor));
    }

    @Override
    public void delete(Integer id) {
        instructorRepository.deleteById(id);
    }

    @Override
    public void importInstructor(MultipartFile file) {
        try {
            List<Instructor> instructorsList = excelUtility.excelToInstructorList(file.getInputStream());
            instructorRepository.saveAll(instructorsList);
        } catch (IOException ex) {
            throw new RuntimeException("Xuất hiện lỗi trong Excel: " + ex.getMessage());
        }
    }

}

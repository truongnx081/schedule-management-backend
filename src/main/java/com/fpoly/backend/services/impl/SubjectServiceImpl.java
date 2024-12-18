package com.fpoly.backend.services.impl;

import com.fpoly.backend.dto.SubjectDTO;
import com.fpoly.backend.dto.SubjectMarkColumn2DTO;
import com.fpoly.backend.dto.SubjectMarkColumnDTO;
import com.fpoly.backend.entities.*;
import com.fpoly.backend.mapper.SubjectMapper;
import com.fpoly.backend.repository.MarkColumnRepository;
import com.fpoly.backend.repository.SpecializationRepository;
import com.fpoly.backend.repository.SubjectMarkRepository;
import com.fpoly.backend.repository.SubjectRepository;
import com.fpoly.backend.services.IdentifyUserAccessService;
import com.fpoly.backend.services.SubjectService;
import com.fpoly.backend.until.ExcelUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    private SubjectMapper subjectMapper;
    @Autowired
    private SpecializationRepository specializationRepository;
    @Autowired
    private IdentifyUserAccessService identifyUserAccessService;
    @Autowired
    private ExcelUtility excelUtility;
    @Autowired
    private SubjectMarkRepository subjectMarkRepository;
    @Autowired
    private MarkColumnRepository markColumnRepository;

    @Override
    public Subject findById(Integer id) {
        return subjectRepository.findById(id).orElse(null);
    }

    @Override
    public List<Map<String, Object>> getAllSubjectBySpecializationId(Integer specializationId) {
        return subjectRepository.getAllSubjectBySpecializationId(specializationId);
    }

    @Override
    public List<Map<String, Object>> findAllSubject() {
        return subjectRepository.findAllSubject();
    }

    @Override
    public List<Map<String, Object>> getSubjectDetail(Integer subjectId) {
        return subjectRepository.getSubjectDetailById(subjectId);
    }

    @Override
    public List<Map<String, Object>> findAllSubjectByEducationProgramId(Integer educationProgramId) {
        return subjectRepository.findAllSubjectByEducationProgramId(educationProgramId);
    }

    @Override
    public void create(SubjectMarkColumnDTO request) {
//        try {
            Subject subject = subjectMapper.toEntity(request.getSubjectDTO());

            if(subjectRepository.existsByCode(request.getSubjectDTO().getCode()))
                throw new RuntimeException("Mã môn " +request.getSubjectDTO().getCode()+" này đã tồn tại");

            if(request.getSubjectDTO().getRequiredId() != 0){
                Subject subjectRequired = subjectRepository.findById(request.getSubjectDTO().getRequiredId()).orElseThrow(()->
                        new RuntimeException("Môn học này không tồn tại"));
                subject.setRequired(subjectRequired);
            }

            Specialization specialization = specializationRepository.findById(request.getSubjectDTO().getSpecializationId()).orElseThrow(()->
                    new RuntimeException("Bộ môn này không tồn tại"));

            subject.setSpecialization(specialization);
            subject.setId(null);
            subject.setStatus(true);
            subject.setOffline(true);
            subject.setCreatedBy(identifyUserAccessService.getAdmin().getCode());

            Subject subject1 = subjectRepository.save(subject);

            List<SubjectMark> subjectMarks = new ArrayList<>();

            for (SubjectMarkColumn2DTO subjectMarrkColumn : request.getItems()) {
                SubjectMark subjectMark = new SubjectMark();

                MarkColumn markColumn = markColumnRepository.findById(subjectMarrkColumn.getId()).orElseThrow(()->
                        new RuntimeException("Cột điểm này không tồn tại"));

                subjectMark.setSubject(subject1);
                subjectMark.setMarkColumn(markColumn);
                subjectMark.setPercentage(subjectMarrkColumn.getPercentage());
                subjectMark.setPart(subjectMarrkColumn.getPart());

                subjectMarks.add(subjectMark);
            }
            subjectMarkRepository.saveAll(subjectMarks);

//            return true;
//        }
//        catch (Exception e){
//            return false;
//        }

    }

    @Override
    public void update(SubjectMarkColumnDTO request, Integer subjectId) {
//        try {
            Subject subject = subjectRepository.findById(subjectId).orElseThrow(()->
                    new RuntimeException("Môn học này không tồn tại"));

            subjectMapper.updateSubject(subject, request.getSubjectDTO());

            if(request.getSubjectDTO().getRequiredId() != 0){
                Subject subjectRequired = subjectRepository.findById(request.getSubjectDTO().getRequiredId()).orElseThrow(()->
                        new RuntimeException("Môn học bắt buộc này không tồn tại"));
                subject.setRequired(subjectRequired);
            }

            subject.setUpdatedAt(new Date());
            subject.setStatus(true);
            subject.setOffline(true);
            subject.setUpdatedBy(identifyUserAccessService.getAdmin().getCode());
            subjectRepository.save(subject);

            // Tìm kiếm tất cả subjectMark của subject
            List<SubjectMark> subjectMarksList = subjectMarkRepository.findAllBySubject(subject);

            // Xóa tất cả
            subjectMarkRepository.deleteAll(subjectMarksList);

            List<SubjectMark> subjectMarks = new ArrayList<>();

            for (SubjectMarkColumn2DTO subjectMarkColumn : request.getItems()) {
                SubjectMark subjectMark = new SubjectMark();

                MarkColumn markColumn = markColumnRepository.findById(subjectMarkColumn.getId()).orElseThrow(()->
                        new RuntimeException("Cột điểm này không tồn tại"));

                subjectMark.setSubject(subject);
                subjectMark.setMarkColumn(markColumn);
                subjectMark.setPercentage(subjectMarkColumn.getPercentage());
                subjectMark.setPart(subjectMarkColumn.getPart());

                subjectMarks.add(subjectMark);
            }
            subjectMarkRepository.saveAll(subjectMarks);
//            return true;
//        }
//        catch (Exception e){
//            return false;
//        }
    }

    @Override
    public void delete(Integer subjectId) {
        subjectRepository.deleteById(subjectId);
    }

    @Override
    public void importSubject(MultipartFile file) {
        try {
            List<Subject> subjectList = excelUtility.excelToSubjectList(file.getInputStream());
            subjectRepository.saveAll(subjectList);
        } catch (IOException ex) {
            throw new RuntimeException("Xuất hiện lỗi trong Excel: " + ex.getMessage());
        }
    }
}

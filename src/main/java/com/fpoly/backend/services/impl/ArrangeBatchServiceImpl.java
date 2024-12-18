package com.fpoly.backend.services.impl;

import com.fpoly.backend.dto.ArrangeBatchDTO;
import com.fpoly.backend.entities.*;
import com.fpoly.backend.exception.AppUnCheckedException;
import com.fpoly.backend.mapper.ArrangeBatchMapper;
import com.fpoly.backend.repository.ArrangeBatchRepository;
import com.fpoly.backend.repository.ClazzRepository;
import com.fpoly.backend.repository.StudentRepository;
import com.fpoly.backend.services.ArrangeBatchService;
import com.fpoly.backend.services.IdentifyUserAccessService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ArrangeBatchServiceImpl implements ArrangeBatchService {
    @Autowired
    private final IdentifyUserAccessService identifyUserAccessService;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ClazzRepository clazzRepository;
    @Autowired
    private ArrangeBatchMapper arrangeBatchMapper;
    @Autowired
    private ArrangeBatchRepository arrangeBatchRepository;

    @Override
    public ArrangeBatchDTO createArrangeBatch(ArrangeBatchDTO arrangeBatchDTO) {
        String instructorCode = identifyUserAccessService.getInstructor().getCode();
        Integer instructorId = identifyUserAccessService.getInstructor().getId();

        Integer studentId = arrangeBatchDTO.getStudentId();
        Integer clazzId = arrangeBatchDTO.getClazzId();

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new AppUnCheckedException("Student not found", HttpStatus.NOT_FOUND));
        Clazz clazz = clazzRepository.findById(clazzId)
                .orElseThrow(() -> new AppUnCheckedException("Clazz not found", HttpStatus.NOT_FOUND));

        if (!instructorId.equals(clazz.getInstructor().getId())) {
            throw new AppUnCheckedException("Bạn không có quyền sắp xếp đợt thi !", HttpStatus.FORBIDDEN);
        }

        ArrangeBatch arrangeBatch = arrangeBatchMapper.toEntity(arrangeBatchDTO);
        arrangeBatch.setStudent(student);
        arrangeBatch.setClazz(clazz);
        arrangeBatch.setCreatedBy(instructorCode);
        arrangeBatch.setBatch(arrangeBatchDTO.getBatch());

        return arrangeBatchMapper.toDTO(arrangeBatchRepository.save(arrangeBatch));
    }

    @Override
    public ArrangeBatchDTO updateArrangeBatch(Integer arrangeBatchId, ArrangeBatchDTO arrangeBatchDTO) {
        String instructorCode = identifyUserAccessService.getInstructor().getCode();

        ArrangeBatch existingArrangeBatch = arrangeBatchRepository.findById(arrangeBatchId)
                .orElseThrow(() -> new AppUnCheckedException("Arrange_batch không tồn tại", HttpStatus.NOT_FOUND));
        String currentInstructorCode = identifyUserAccessService.getInstructor().getCode();


        if (!existingArrangeBatch.getCreatedBy().equals(currentInstructorCode)) {
            throw new AppUnCheckedException("Bạn không có quyền cập nhật arrange batch này !", HttpStatus.FORBIDDEN);
        }

        existingArrangeBatch.setBatch(arrangeBatchDTO.getBatch());

        if (arrangeBatchDTO.getClazzId() != null) {
            Clazz clazz = clazzRepository.findById(arrangeBatchDTO.getClazzId())
                    .orElseThrow(() -> new AppUnCheckedException("Clazz id nay không tồn tại", HttpStatus.NOT_FOUND));
            existingArrangeBatch.setClazz(clazz);
        }
        if (arrangeBatchDTO.getStudentId() != null) {
            Student student = studentRepository.findById(arrangeBatchDTO.getStudentId())
                    .orElseThrow(() -> new AppUnCheckedException("Student id nay không tồn tại", HttpStatus.NOT_FOUND));
            existingArrangeBatch.setStudent(student);
        }
        existingArrangeBatch.setUpdatedBy(instructorCode);
        return arrangeBatchMapper.toDTO(arrangeBatchRepository.save(existingArrangeBatch));
    }

    @Transactional
    @Override
    public void deleteArrangeBatch(Integer clazzId) {
        List<ArrangeBatch> arrangeBatches = arrangeBatchRepository.findAllByClazzId(clazzId);
        if (arrangeBatches.isEmpty()) {
            throw new AppUnCheckedException("Không có đợt thi nào với clazzId: " + clazzId, HttpStatus.NOT_FOUND);
        }

        String currentInstructorCode = identifyUserAccessService.getInstructor().getCode();
        for (ArrangeBatch arrangeBatch : arrangeBatches) {
            if (!arrangeBatch.getCreatedBy().equals(currentInstructorCode)) {
                throw new AppUnCheckedException("Bạn không có quyền xóa đợt thi của lớp này!", HttpStatus.FORBIDDEN);
            }
        }
        arrangeBatchRepository.deleteByClazzId(clazzId);

    }

    @Override
    public List<ArrangeBatchDTO> doArrangeBatch(List<ArrangeBatchDTO> arrangeBatchDTOS) {

        for (ArrangeBatchDTO arrangeBatchDTO : arrangeBatchDTOS) {
            Integer batch = arrangeBatchDTO.getBatch();
            if (batch != null) {
                Integer clazzId = arrangeBatchDTO.getClazzId();
                Integer studentId = arrangeBatchDTO.getStudentId();
                Clazz clazz = clazzRepository.findById(clazzId)
                        .orElseThrow(() -> new AppUnCheckedException("Không tìm thấy lớp học!!", HttpStatus.NOT_FOUND));
                Student student = studentRepository.findById(studentId)
                        .orElseThrow(() -> new AppUnCheckedException("Không tìm thấy sinh viên!!", HttpStatus.NOT_FOUND));
                ArrangeBatch arrangeBatch = arrangeBatchRepository.findArrangeBatchByStudentIdAndClazzId(studentId, clazzId);
                if (arrangeBatch == null) {
                    arrangeBatch = new ArrangeBatch();
                }
                arrangeBatch.setStudent(student);
                arrangeBatch.setClazz(clazz);
                arrangeBatch.setBatch(batch);

                arrangeBatchRepository.save(arrangeBatch);
            }
        }

        return arrangeBatchDTOS;
    }

}

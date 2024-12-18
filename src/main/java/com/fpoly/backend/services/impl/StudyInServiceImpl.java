package com.fpoly.backend.services.impl;

import com.fpoly.backend.dto.Response;
import com.fpoly.backend.dto.StudyInDTO;
import com.fpoly.backend.entities.*;
import com.fpoly.backend.exception.AppUnCheckedException;
import com.fpoly.backend.mapper.StudyInMapper;
import com.fpoly.backend.repository.*;
import com.fpoly.backend.services.AuthenticationService;
import com.fpoly.backend.services.IdentifyUserAccessService;
import com.fpoly.backend.services.SemesterProgressService;
import com.fpoly.backend.services.StudyInService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StudyInServiceImpl implements StudyInService {
    private final SemesterProgressService semesterProgressService;
    private final BlockRepository blockRepository;
    private final SemesterRepository semesterRepository;
    private final YearRepository yearRepository;
    private final StudyInRepository studyInRepository;
    private final StudyInMapper studyInMapper;
    private final IdentifyUserAccessService identifyUserAccessService;
    private final ClazzRepository clazzRepository;
    private final StudentRepository studentRepository;
    private final SemesterProgressRepository semesterProgressRepository;
    private final WeekDayRepository weekDayRepository;
    private final SubjectRepository subjectRepository;

    @Override
    public List<StudyInDTO> findAllByBlockAndSemesterAnhYear() {
        SemesterProgress semesterProgress = semesterProgressService.findActivedProgressTrue();
        Integer blockId = semesterProgress.getBlock().getBlock();
        String semesterId = semesterProgress.getSemester().getSemester();
        Integer yearId = semesterProgress.getYear().getYear();

        Block block =  blockRepository.findById(blockId).orElseThrow(()->
                new RuntimeException("Block not found"));
        Semester semester =  semesterRepository.findById(semesterId).orElseThrow(()->
                new RuntimeException("Semester not found"));
        Year year =  yearRepository.findById(yearId).orElseThrow(()->
                new RuntimeException("Year not found"));

        return studyInRepository
                .findAllByClazzBlockAndClazzSemesterAndClazzYear(block,semester,year)
                .stream()
                .map(studyInMapper::toDTO).toList();
    }

    @Override
    public List<Map<String, Object>> getAllIdOfStudyInByBlockAndSemesterAndYearOfStudent(Integer blockId, String semesterId, Integer yearId) {
        return studyInRepository.getAllIdOfStudyInByBlockAndSemesterAndYearOfStudent(
                blockId, semesterId, yearId
        );
    }

    @Override
    public List<Map<String, Object>> getAllMarkAverageStudentsByClazzId(Integer clazzId) {

        Integer instructorId = identifyUserAccessService.getInstructor().getId();
        Clazz clazz = clazzRepository.findById(clazzId)
                .orElseThrow(() -> new AppUnCheckedException("Lớp học không tồn tại", HttpStatus.NOT_FOUND));
        if(!instructorId.equals(clazz.getInstructor().getId())) {
            throw new AppUnCheckedException("Bạn không có quyền xem danh sách này !", HttpStatus.FORBIDDEN);
        }
        return studyInRepository.getAllMarkAverageStudentsByClazzId(clazzId);
    }

    @Override
    public StudyInDTO create(Integer clazzId) {
        Clazz clazz = clazzRepository.findById(clazzId)
                .orElseThrow(() -> new AppUnCheckedException("Lớp học không tồn tại", HttpStatus.NOT_FOUND));
        Integer shift = clazz.getShift().getId();
        Student student = identifyUserAccessService.getStudent();
        Integer studentId = student.getId();

        SemesterProgress semesterProgress = semesterProgressRepository.findActivedProgress();
        Integer block = semesterProgress.getBlock().getBlock();
        String semester = semesterProgress.getSemester().getSemester();
        Integer year = semesterProgress.getYear().getYear();

        //Kiểm tra môn học trước
        Subject requiredSubject = clazz.getSubject().getRequired();
        if (requiredSubject != null) {
            Integer requiredSubjectId = requiredSubject.getId();
            List<Integer> studiedSubjectsId = subjectRepository.findStudiedSubjectByStudentId(studentId);
            boolean studiedFlag = false;
            for (Integer studiedSubjectId : studiedSubjectsId) {
                if (studiedSubjectId.equals(requiredSubjectId)) {
                    studiedFlag = true;
                    break;
                }
            }
            if (!studiedFlag) {
                throw new AppUnCheckedException("Bạn chưa học môn học trước là: "
                        + requiredSubject.getCode() + " - "
                        + requiredSubject.getName()
                        , HttpStatus.NOT_ACCEPTABLE);
            }
        }
        //Kiểm tra số lượng
        Integer amout = studyInRepository.countStudentByClazzId(clazz.getId());
        if(amout >= clazz.getQuantity()){
            throw new AppUnCheckedException("Không thể đăng ký môn học do đã đủ số lượng", HttpStatus.NOT_ACCEPTABLE);
        }


        // Kiểm tra ca trùng
        List<Integer> dulicatedShiftClazzesId =
                clazzRepository.findClazzesIdByShiftAndStudentIdAndBlockAndSemesterAndYear(
                                                shift, studentId, block, semester, year);
        List<Integer> weekDays = weekDayRepository.findWeekDayIdByClazzId(clazz.getId());


        if (!dulicatedShiftClazzesId.isEmpty()){
            for (Integer dulicatedShiftClazzId : dulicatedShiftClazzesId){
                List<Integer> duplicatedClazzWeekDays = weekDayRepository.findWeekDayIdByClazzId(dulicatedShiftClazzId);
                for (Integer duplicatedClazzWeekDay : duplicatedClazzWeekDays){
                    for (Integer weekDay : weekDays){
                        if (weekDay.equals(duplicatedClazzWeekDay)){
                            Clazz duplicatedShiftClazz = clazzRepository.findById(dulicatedShiftClazzId).get();
                            throw new AppUnCheckedException("Không thể đăng ký môn học do trùng ca với lớp: " + duplicatedShiftClazz.getCode() + "ca: "
                                                            + duplicatedShiftClazz.getShift().getId(), HttpStatus.NOT_ACCEPTABLE);
                        }
                    }
                }
            }
        }

        StudyIn studyIn = new StudyIn();
        studyIn.setStudent(student);
        studyIn.setClazz(clazz);
        studyIn.setPaid(false);
        studyInRepository.save(studyIn);

        return studyInMapper.toDTO(studyIn);
    }


    @Override
    public StudyInDTO update(Integer oldClazzId, Integer newClazzId) {
        Clazz oldClazz = clazzRepository.findById(oldClazzId)
                .orElseThrow(() -> new AppUnCheckedException("Lớp học không tồn tại", HttpStatus.NOT_FOUND));
        Clazz newClazz = clazzRepository.findById(newClazzId)
                .orElseThrow(() -> new AppUnCheckedException("Lớp học không tồn tại", HttpStatus.NOT_FOUND));

        Integer newShift = newClazz.getShift().getId();
        Student student = identifyUserAccessService.getStudent();
        Integer studentId = student.getId();

        SemesterProgress semesterProgress = semesterProgressRepository.findActivedProgress();
        Integer block = semesterProgress.getBlock().getBlock();
        String semester = semesterProgress.getSemester().getSemester();
        Integer year = semesterProgress.getYear().getYear();


        Integer amout = studyInRepository.countStudentByClazzId(newClazz.getId());
        if(amout >= newClazz.getQuantity()){
            throw new AppUnCheckedException("Không thể đổi lớp do lớp đã đủ số lượng", HttpStatus.NOT_ACCEPTABLE);
        }



        List<Integer> dulicatedShiftClazzesId =
                clazzRepository.findClazzesIdByShiftAndStudentIdAndBlockAndSemesterAndYear(
                        newShift, studentId, block, semester, year);
        List<Integer> weekDays = weekDayRepository.findWeekDayIdByClazzId(newClazz.getId());


        if (!dulicatedShiftClazzesId.isEmpty()){
            for (Integer dulicatedShiftClazzId : dulicatedShiftClazzesId){
                List<Integer> duplicatedClazzWeekDays = weekDayRepository.findWeekDayIdByClazzId(dulicatedShiftClazzId);
                for (Integer duplicatedClazzWeekDay : duplicatedClazzWeekDays){
                    for (Integer weekDay : weekDays){
                        if (weekDay.equals(duplicatedClazzWeekDay)){
                            Clazz duplicatedShiftClazz = clazzRepository.findById(dulicatedShiftClazzId).get();
                            throw new AppUnCheckedException("Không thể đổi lớp do trùng ca với lớp: " + duplicatedShiftClazz.getCode() + " - ca: "
                                    + duplicatedShiftClazz.getShift().getId(), HttpStatus.NOT_ACCEPTABLE);
                        }
                    }
                }
            }
        }

        Integer clazzId = oldClazzId;

        StudyIn studyIn = studyInRepository.findByStudentIdAndClazzId(studentId, clazzId);
        studyIn.setStudent(student);
        studyIn.setClazz(newClazz);
        studyInRepository.save(studyIn);

        return studyInMapper.toDTO(studyIn);
    }

    @Override
    public void delete(Integer clazzId) {
        Student student = identifyUserAccessService.getStudent();
        StudyIn studyIn = studyInRepository.findByStudentIdAndClazzId(student.getId(),clazzId);
        studyInRepository.delete(studyIn);
    }

    @Override
    public void updateAllStudyInIsTrueByStudent() {
        Student student = identifyUserAccessService.getStudent();
        studyInRepository.updateAllStudyInIsTrueByStudent(student);
    }

    @Override
    public List<Map<String, Object>> getAllIdOfStudyInByBlockAndSemesterAndYearOfStudent2() {
        SemesterProgress semesterProgress = semesterProgressRepository.findActivedProgress();
        Integer block = semesterProgress.getBlock().getBlock();
        String semester = semesterProgress.getSemester().getSemester();
        Integer year = semesterProgress.getYear().getYear();

        Student student = identifyUserAccessService.getStudent();

        List<Map<String,Object>> studyIns = studyInRepository.getAllIdOfStudyInByBlockAndSemesterAndYearOfStudent2(block,semester,year, student);

        for (int i = 0; i < studyIns.size(); i++){
            HashMap<String, Object> studyIn = new HashMap<>(studyIns.get(i));
            List<String> weekDays = weekDayRepository.findWeekDayByClazzId((Integer) studyIn.get("classId"));
            String studyDays = "";
            for (int j = 0; j < weekDays.size(); j++){
                if (j < weekDays.size() -1){
                    studyDays += weekDays.get(j) + ", ";
                } else {
                    studyDays += weekDays.get(j);
                }
            }
            studyIn.put("study_day", studyDays);
            studyIns.set(i,studyIn);
        }

        return studyIns;
    }

}

package com.fpoly.backend.services.impl;

import com.fpoly.backend.dto.CloudinaryResponse;
import com.fpoly.backend.dto.StudentDTO;
import com.fpoly.backend.entities.*;
import com.fpoly.backend.exception.AppUnCheckedException;
import com.fpoly.backend.mapper.StudentMapper;
import com.fpoly.backend.repository.*;
import com.fpoly.backend.services.CloudinaryService;
import com.fpoly.backend.services.IdentifyUserAccessService;
import com.fpoly.backend.services.SemesterProgressService;
import com.fpoly.backend.services.StudentService;
import com.fpoly.backend.until.FileUpload;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.poi.ss.usermodel.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class StudentServiceImpl implements StudentService {

    StudentRepository studentRepository;
    StudentMapper studentMapper;
    IdentifyUserAccessService identifyUserAccessService;
    CloudinaryService cloudinaryService;
    EducationProgramRepository educationProgramRepository;
    MajorRepository majorRepository;
    YearRepository yearRepository;
    SemesterRepository semesterRepository;
    private final SemesterProgressService semesterProgressService;
    private final ScheduleRepository scheduleRepository;
    private final ExamScheduleRepository examScheduleRepository;
    private final RetakeScheduleRepository retakeScheduleRepository;
    private final AttendanceRepository attendanceRepository;
    private final SubjectMarkRepository subjectMarkRepository;
    private final ClazzRepository clazzRepository;
    private final StudyInRepository studyInRepository;
    private final StudyResultRepository studyResultRepository;
    private final ArrangeBatchRepository arrangeBatchRepository;
    @Override
    public Student findById(Integer id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public StudentDTO getStudentInfor() {
        Student student = identifyUserAccessService.getStudent();
        return studentMapper.toDTO(student);
    }

    @Override
    public StudentDTO getStudentById(Integer studentId) {
        return studentMapper.toDTO(studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + studentId)));
    }

    @Override
    public StudentDTO createStudent(StudentDTO request, MultipartFile file) {
        SemesterProgress semesterProgress = semesterProgressService.findActivedProgressTrue();

        if (studentRepository.existsByCode(request.getCode())) {
            throw new AppUnCheckedException("Mã sinh viên này đã tồn tại", HttpStatus.CONFLICT);
        }
        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new AppUnCheckedException("Email này đã tồn tại", HttpStatus.CONFLICT);
        }

        String adminCode = identifyUserAccessService.getAdmin().getCode();
        Student student = studentMapper.toEntity(request);
        if (file != null && !file.isEmpty()) {
            FileUpload.assertAllowed(file, FileUpload.IMAGE_PATTERN);
            final String fileName = FileUpload.getFileName(file.getOriginalFilename());
            final CloudinaryResponse response = cloudinaryService.uploadFile(file, fileName);
            student.setAvatar(response.getPublicId());
        }
        student.setCreatedBy(adminCode);
        student.setEducationProgram(educationProgramRepository.findById(request.getEducationProgramId()).orElseThrow(() ->
                new AppUnCheckedException("Không tìm thấy chương trình đào tạo", HttpStatus.NOT_FOUND)
        ));

//        Semester semester = semesterRepository.findById(request.getSemester()).orElseThrow(() ->
//                new AppUnCheckedException("Không tìm thấy học kỳ", HttpStatus.NOT_FOUND)
//        );
//        student.setSemester(semester);
//
//        Year year = yearRepository.findById(request.getYear()).orElseThrow(() ->
//                new AppUnCheckedException("Không tìm thấy năm học", HttpStatus.NOT_FOUND)
//        );
//        student.setYear(year);

        Major major = majorRepository.findById(request.getMajorId()).orElseThrow(() ->
                new AppUnCheckedException("Không tìm thấy chuyên ngành", HttpStatus.NOT_FOUND)
        );
        student.setMajor(major);
        student.setId(null);
        student.setSemester(semesterProgress.getSemester());
        student.setYear(semesterProgress.getYear());

        return studentMapper.toDTO(studentRepository.save(student));
    }

    @Override
    public StudentDTO updateStudentByStudent(StudentDTO request, MultipartFile file) {
        Student student = identifyUserAccessService.getStudent();
        student.setUpdatedBy(student.getCode());
        studentMapper.updateStudent(student, request);

        if (file != null && !file.isEmpty()) {
            FileUpload.assertAllowed(file, FileUpload.IMAGE_PATTERN);
            final String fileName = FileUpload.getFileName(file.getOriginalFilename());
            final CloudinaryResponse response = cloudinaryService.uploadFile(file, fileName);
            student.setAvatar(response.getPublicId());
        }

        return studentMapper.toDTO(studentRepository.save(student));
    }


    @Override
    public StudentDTO updateStudentByAdmin(Integer studentId, StudentDTO request) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(()-> new RuntimeException("Student not found"));

        studentMapper.updateStudentByAdmin(student,request);

//        if(file!=null||!file.isEmpty()){
//            FileUpload.assertAllowed(file, FileUpload.IMAGE_PATTERN);
//            final String fileName= FileUpload.getFileName(file.getOriginalFilename());
//            final CloudinaryResponse response = cloudinaryService.uploadFile(file, fileName);
//            student.setAvatar(response.getPublicId());
//        }
        String adminCode = identifyUserAccessService.getAdmin().getCode();
        student.setUpdatedBy(adminCode);

        return studentMapper.toDTO(studentRepository.save(student));
    }


    @Override
    public StudentDTO deleteStudentById(Integer studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + studentId));

        if (student.getAvatar() != null) {
            cloudinaryService.deleteFile(student.getAvatar());
        }

        studentRepository.delete(student);
        return studentMapper.toDTO(student);
    }

    @Override
    public void uploadStudentImages(String folderPath, List<Student> students) {
    }

    @Override
    public List<StudentDTO> getAllStudentByCourseAndMajor(String course, Integer majorId) {
        List<Student> students = studentRepository.findAll();
        System.out.println("Total students found: " + students.size());

        return students.stream()
                .filter(student ->
                        (course == null || student.getCourse().equalsIgnoreCase(course)) &&
                                (majorId == null || (student.getMajor() != null && student.getMajor().getId().equals(majorId)))
                )
                .map(studentMapper::toDTO)
                .toList();
    }

    public List<StudentDTO> importExcelFile(MultipartFile file) {
        List<StudentDTO> studentsDTO = new ArrayList<>();
        List<String> errorMessages = new ArrayList<>();

        try (InputStream is = file.getInputStream(); Workbook workbook = WorkbookFactory.create(is)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    Student student = new Student();
                    StringBuilder rowErrorMessages = new StringBuilder();

                    String studentCode = null;
                    if (row.getCell(1).getCellType() == CellType.STRING) {
                        studentCode = row.getCell(1).getStringCellValue();
                    } else if (row.getCell(1).getCellType() == CellType.NUMERIC) {
                        studentCode = String.valueOf((int) row.getCell(1).getNumericCellValue());
                    } else {
                        rowErrorMessages.append("Invalid code format; ");
                    }

                    Optional<Student> existingStudent = studentRepository.findByCode(studentCode);
                    if (existingStudent.isPresent()) {
                        rowErrorMessages.append("Student already exists; ");
                        continue;
                    }
                    student.setCode(studentCode);

                    student.setFirstName(getCellStringValue(row.getCell(2)));
                    student.setLastName(getCellStringValue(row.getCell(3)));

                    if (row.getCell(4).getCellType() == CellType.NUMERIC) {
                        LocalDate birthday = row.getCell(4).getLocalDateTimeCellValue().toLocalDate();
                        if (birthday.isAfter(LocalDate.now().minusYears(17))) {
                            rowErrorMessages.append("Student must be at least 17 years old; ");
                        } else {
                            student.setBirthday(birthday);
                        }
                    } else {
                        rowErrorMessages.append("Invalid date format for birthday; ");
                    }

                    String genderStr = getCellStringValue(row.getCell(5));
                    if ("Nam".equalsIgnoreCase(genderStr)) {
                        student.setGender(true);
                    } else if ("Nữ".equalsIgnoreCase(genderStr)) {
                        student.setGender(false);
                    } else {
                        rowErrorMessages.append("Invalid gender value; ");
                    }

                    student.setAddress(getCellStringValue(row.getCell(6)));
                    student.setEmail(getCellStringValue(row.getCell(7)));
                    student.setPhone(getCellStringValue(row.getCell(8)));
                    student.setDescription(getCellStringValue(row.getCell(9)));
                    student.setAvatar(null);

                    if (!isValidEmail(student.getEmail())) {
                        rowErrorMessages.append("Invalid email format; ");
                    }

                    if (!isValidPhone(student.getPhone())) {
                        rowErrorMessages.append("Invalid phone number; ");
                    }

                    if (row.getCell(10).getCellType() == CellType.NUMERIC) {
                        student.setCourse(String.valueOf((double) row.getCell(10).getNumericCellValue()));
                    } else {
                        student.setCourse(getCellStringValue(row.getCell(10)));
                    }

                    String majorName = getCellStringValue(row.getCell(11));
                    Major major = majorRepository.findByName(majorName);
                    if (major != null) {
                        student.setMajor(major);
                    } else {
                        rowErrorMessages.append("Major not found: " + majorName + "; ");
                    }

                    String semesterValue = getCellStringValue(row.getCell(12));
                    Semester semester = semesterRepository.findById(semesterValue)
                            .orElseThrow(() -> new AppUnCheckedException("Semester not found: " + semesterValue, HttpStatus.BAD_REQUEST));
                    student.setSemester(semester);

                    if (row.getCell(13).getCellType() == CellType.NUMERIC) {
                        Integer yearValue = (int) row.getCell(13).getNumericCellValue();
                        Year year = yearRepository.findByYear(yearValue)
                                .orElseThrow(() -> new AppUnCheckedException("Year not found: " + yearValue, HttpStatus.BAD_REQUEST));
                        student.setYear(year);
                    } else {
                        rowErrorMessages.append("Invalid year format; ");
                    }

                    String educationProgramName = getCellStringValue(row.getCell(14));
                    EducationProgram educationProgram = educationProgramRepository.findByName(educationProgramName);
                    if (educationProgram != null) {
                        student.setEducationProgram(educationProgram);
                    } else {
                        rowErrorMessages.append("EducationProgram not found: " + educationProgramName + "; ");
                    }

                    if (rowErrorMessages.length() > 0) {
                        errorMessages.add("Row " + (i + 1) + ": " + rowErrorMessages.toString());
                        continue;
                    }

                    studentRepository.save(student);
                    studentsDTO.add(studentMapper.toDTO(student));
                }
            }
        } catch (Exception e) {
            throw new AppUnCheckedException("Error import Excel: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (!errorMessages.isEmpty()) {
            throw new AppUnCheckedException("Import failed with the following errors: " + String.join(", ", errorMessages), HttpStatus.BAD_REQUEST);
        }

        return studentsDTO;
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    private boolean isValidPhone(String phone) {
        return phone != null && phone.matches("^0\\d{9}$");
    }

    @Override
    public List<Map<String, Object>> getStudentsByClazzId(Integer clazzId) {
        Integer instructorId = identifyUserAccessService.getInstructor().getId();
        System.out.println(studentRepository.findStudentByClazzId(clazzId, instructorId).toString());
        return studentRepository.findStudentByClazzId(clazzId, instructorId);
    }

    @Override
    public void updateImage(Integer id, MultipartFile avatar) {
        Student student = studentRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Student not found"));

        // Nếu có file hình ảnh mới, cập nhật hình ảnh trên Cloudinary và lưu id hình
        if (avatar != null && !avatar.isEmpty()) {
            FileUpload.assertAllowed(avatar, FileUpload.IMAGE_PATTERN);
            final String fileName= FileUpload.getFileName(avatar.getOriginalFilename());
            final CloudinaryResponse response = cloudinaryService.uploadFile(avatar, fileName);
            student.setAvatar(response.getPublicId());
        }
        studentRepository.save(student);
    }

    @Override
    public List<Map<String, Object>> getAllStudentByCourse() {
        return studentRepository.getAllStudentByCourse();
    }

    @Override
    public Integer findCannotPresentStudentAmountByScheduleIdAndDateAndShift(Integer scheduleId, LocalDate date, Integer shift) {
        Integer amount = 0;
        List<Integer> students = studentRepository.findStudentsIdByScheduleId(scheduleId);
        System.out.println(students.size());
        for (Integer studentId : students){
            List<Schedule> duplicatedSchedule = scheduleRepository.findSchedulesByDateAndShiftAndStudentId(date, shift, studentId);
            List<RetakeSchedule> duplicatedRetakeSchedule = retakeScheduleRepository.findRetakeSchedulesByDateAndShiftAndStudentId(date, shift, studentId, null);
            List<ExamSchedule> duplicatedExamSchedule = examScheduleRepository.findExamSchedulesByDateAndShiftAndStudentId(date, shift, studentId);

            if(!duplicatedSchedule.isEmpty() || !duplicatedExamSchedule.isEmpty() || !duplicatedRetakeSchedule.isEmpty()){
                amount++;
            }
        }
        return amount;
    }


    private String getCellStringValue(Cell cell) {
        if (cell == null) return "";
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf((int) cell.getNumericCellValue());
        }
        return "";
    }

    @Override
    public List<Map<String, Object>> findStudentWithQualifyByClazzId(Integer clazzId) {
        List<Map<String, Object>> students = studentRepository.findStudentsWithQualifyByClazzId(clazzId);
        Clazz clazz = clazzRepository.findById(clazzId)
                .orElseThrow(() -> new AppUnCheckedException("Không tìm thấy lớp học!!", HttpStatus.NOT_FOUND));
        Integer subjectId = clazz.getSubject().getId();
        Integer scheduleCount = scheduleRepository.countScheduleByClazzId(clazzId);

        System.out.println(scheduleCount);

        for (int i = 0; i< students.size(); i++){
            Map<String, Object> student = new HashMap<String, Object>(students.get(i));
            Integer studentId = (Integer) student.get("student_id");
            StudyIn studyIn = studyInRepository.findByStudentIdAndClazzId(studentId,clazzId);
            if (studyIn == null){
                throw new AppUnCheckedException("Học sinh không học trong lớp này!!", HttpStatus.NOT_FOUND);
            }
            Integer absent = attendanceRepository.findAbsentForRetakeScheduleByStudentIdAndClazzId(studentId,clazzId)
                            + attendanceRepository.findAbsentForScheduleByStudentIdAndClazzId(studentId, clazzId) ;

            Double progressMarkPercentage = subjectMarkRepository.findProgressPercentageBySubjectId(subjectId);

            Double progressMark = studyResultRepository.findProgressMarkByStudyInId(studyIn.getId());

            if (progressMarkPercentage == null || progressMarkPercentage == 0){
                student.put("progress_mark","Môn học không có điểm quá trình");
                if (absent > (scheduleCount/5)) {
                    student.put("qualify", false);
                    student.put("batch", null);
                } else {
                    student.put("qualify", true);
                    ArrangeBatch arrangeBatch = arrangeBatchRepository.findArrangeBatchByStudentIdAndClazzId(studentId,clazzId);
                    if (arrangeBatch != null) {
                        student.put("batch", arrangeBatch.getBatch());
                    }else {
                        student.put("batch", null);
                    }
                }
                student.put("absent", absent);
            }else {
                if (absent > (scheduleCount/5) || ((progressMark / progressMarkPercentage) < 5)) {
                    student.put("batch", null);
                    student.put("qualify", false);
                    student.put("progress_mark", progressMark/progressMarkPercentage);
                    student.put("absent", absent);
                }
                else{
                    ArrangeBatch arrangeBatch = arrangeBatchRepository.findArrangeBatchByStudentIdAndClazzId(studentId,clazzId);
                    if (arrangeBatch == null) {
                        student.put("batch", null);
                    } else {
                        student.put("batch", arrangeBatch.getBatch());
                    }
                    student.put("qualify", true);
                    student.put("progress_mark", progressMark/progressMarkPercentage);
                    student.put("absent", absent);
                }
            }
            students.set(i, student);
        }
        return students;
    }

}

package com.fpoly.backend.services.impl;

import com.fpoly.backend.dto.ClazzDTO;
import com.fpoly.backend.entities.*;
import com.fpoly.backend.mapper.ClazzMapper;
import com.fpoly.backend.repository.*;
import com.fpoly.backend.services.ClazzService;
import com.fpoly.backend.services.IdentifyUserAccessService;
import com.fpoly.backend.services.SemesterProgressService;
import com.fpoly.backend.until.ExcelUtility;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class ClazzServiceImpl implements ClazzService {
    ClazzRepository clazzRepository;
    ClazzMapper clazzMapper;
    BlockRepository blockRepository;
    SemesterRepository semesterRepository;
    YearRepository yearRepository;
    SubjectRepository subjectRepository;
    InstructorRepository instructorRepository;
    IdentifyUserAccessService identifyUserAccessService;
    ShiftRepository shiftRepository;
    RoomRepository roomRepository;
    ExcelUtility excelUtility;
    SemesterProgressRepository semesterProgressRepository;
    StudyInRepository studyInRepository;
    WeekDayRepository weekDayRepository;
    ScheduleRepository scheduleRepository;
    SemesterProgressService semesterProgressService;
    StudyDayRepository studyDayRepository;

    @Override
    public ClazzDTO create(ClazzDTO request) {
        Clazz clazz =  clazzMapper.toEntity(request);

//        if(clazzRepository.existsByCode(request.getCode())){
//            throw new RuntimeException("Mã lớp học này đã tồn tại");
//        }
        // Tìm block
        Block block = blockRepository.findById(request.getBlock()).orElseThrow(() ->
                new RuntimeException("Block not found"));
        // Tìm semester
        Semester semester = semesterRepository.findById(request.getSemester()).orElseThrow(() ->
                new RuntimeException("Semester not found"));
        // Tìm year
        Year year = yearRepository.findById(request.getYear()).orElseThrow(() ->
                new RuntimeException("Year not found"));
        // Tìm subject
        Subject subject = subjectRepository.findById(request.getSubjectId()).orElseThrow(() ->
                new RuntimeException("Subject not found"));
        // Tìm instructor
        if(request.getInstructorId() != 0){
            Instructor instructor = instructorRepository.findById(request.getInstructorId()).orElseThrow(() ->
                    new RuntimeException("Giảng viên này đã tồn tại"));
            clazz.setInstructor(instructor);
        }
        // Tìm admin
        Admin admin = identifyUserAccessService.getAdmin();
        // Tìm shift
        Shift shift = shiftRepository.findById(request.getShiftId()).orElseThrow(() ->
                new RuntimeException("Shift not found"));
        // Tìm room
        if(!(request.getRoomId() == 0)){
            Room room = roomRepository.findById(request.getRoomId()).orElseThrow(() ->
                    new RuntimeException("Room not found"));
            clazz.setRoom(room);
        }

        clazz.setId(null);
        clazz.setBlock(block);
        clazz.setSemester(semester);
        clazz.setYear(year);
        clazz.setSubject(subject);
        clazz.setAdmin(admin);
        clazz.setCreatedBy(admin.getCode());
        clazz.setShift(shift);

        // Lưu clazz
        Clazz clazz1 = clazzRepository.save(clazz);

        // Chuyển đổi ngày học trong tuần
        if(request.getWeekdays().equals("2, 4, 6"))
            request.setWeekdays("1,3,5");
        else if(request.getWeekdays().equals("3, 5, 7"))
            request.setWeekdays("2,4,6");

        // Tách chuỗi thành mảng
        String[] weekdays = request.getWeekdays().split(",");

        // tạo weekdays theo clazz
        for (String weekday : weekdays) {
            WeekDay weekDay = new WeekDay();
            weekDay.setId(Integer.parseInt(weekday.trim()));

            StudyDay studyDay = new StudyDay();
            studyDay.setClazz(clazz1);
            studyDay.setWeekDay(weekDay);

            studyDayRepository.save(studyDay);
        }

        return clazzMapper.toDTO(clazz1);
    }

    @Override
    public ClazzDTO update(ClazzDTO request, Integer id) {
        Clazz clazz = clazzRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Clazz not found"));
        clazzMapper.updateClazz(clazz, request);

        // Tìm block
        Block block = blockRepository.findById(request.getBlock()).orElseThrow(() ->
                new RuntimeException("Block not found"));
        // Tìm semester
        Semester semester = semesterRepository.findById(request.getSemester()).orElseThrow(() ->
                new RuntimeException("Semester not found"));
        // Tìm year
        Year year = yearRepository.findById(request.getYear()).orElseThrow(() ->
                new RuntimeException("Year not found"));
        // Tìm subject
        Subject subject = subjectRepository.findById(request.getSubjectId()).orElseThrow(() ->
                new RuntimeException("Subject not found"));
        // Tìm instructor
        Instructor instructor = instructorRepository.findById(request.getInstructorId()).orElseThrow(() ->
                new RuntimeException("Instructor not found"));
        // Tìm admin
        Admin admin = identifyUserAccessService.getAdmin();
        // Tìm shift
        Shift shift = shiftRepository.findById(request.getShiftId()).orElseThrow(() ->
                new RuntimeException("Shift not found"));
        // Tìm room
        if(!(request.getRoomId() == 0)){
            Room room = roomRepository.findById(request.getRoomId()).orElseThrow(() ->
                    new RuntimeException("Room not found"));
            clazz.setRoom(room);
        }

        clazz.setBlock(block);
        clazz.setSemester(semester);
        clazz.setYear(year);
        clazz.setSubject(subject);
        clazz.setInstructor(instructor);
        clazz.setAdmin(admin);
        clazz.setUpdatedBy(admin.getCode());
        clazz.setShift(shift);

        // Lưu clazz
        Clazz clazz1 = clazzRepository.save(clazz);

        // Chuyển đổi ngày học trong tuần
        if(request.getWeekdays().equals("2, 4, 6"))
            request.setWeekdays("1,3,5");
        else if(request.getWeekdays().equals("3, 5, 7"))
            request.setWeekdays("2,4,6");

        // Tách chuỗi thành mảng
        String[] weekdays = request.getWeekdays().split(",");
        List<StudyDay> studyDays = studyDayRepository.findAllByClazz(clazz1);
        WeekDay weekDay = new WeekDay();

        // Ngày 1
        weekDay.setId(Integer.parseInt(weekdays[0].trim()));
        studyDays.get(0).setClazz(clazz1);
        studyDays.get(0).setWeekDay(weekDay);
        studyDayRepository.save(studyDays.get(0));

        // Ngày 2
        weekDay.setId(Integer.parseInt(weekdays[1].trim()));
        studyDays.get(1).setClazz(clazz1);
        studyDays.get(1).setWeekDay(weekDay);
        studyDayRepository.save(studyDays.get(1));

        // Ngày 3
        weekDay.setId(Integer.parseInt(weekdays[2].trim()));
        studyDays.get(2).setClazz(clazz1);
        studyDays.get(2).setWeekDay(weekDay);
        studyDayRepository.save(studyDays.get(2));

        return clazzMapper.toDTO(clazz1);
    }

    @Override
    public void delete(Integer id) {
        clazzRepository.deleteById(id);
    }

    @Override
    public ClazzDTO getOne(Integer id) {
        Clazz clazz = clazzRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Clazz not found"));

        return clazzMapper.toDTO(clazz);
    }

    @Override
    public List<ClazzDTO> getAll() {
        return clazzRepository.findAll().stream()
                .map(clazzMapper::toDTO).toList();
    }

    @Override
    public List<Map<String, Object>> findClazzByBlockAndSemesterAndYear() {
        SemesterProgress semesterProgress = semesterProgressService.findActivedProgressTrue();
        Integer block = semesterProgress.getBlock().getBlock();
        String semester = semesterProgress.getSemester().getSemester();
        Integer year = semesterProgress.getYear().getYear();

        return clazzRepository.findClazzByBlockAndSemesterAndYear(block, semester, year);
    }

    @Override
    public void importClazz(MultipartFile file) {
        try {
//            List<Clazz> clazzList = excelUtility.excelToClazzList(file.getInputStream());
            excelUtility.excelToClazzList(file.getInputStream());
//            clazzRepository.saveAll(clazzList);
        } catch (IOException ex) {
            throw new RuntimeException("Xuất hiện lỗi trong Excel: " + ex.getMessage());
        }
    }

    @Override
    public List<ClazzDTO> getClazzsByInstructorId() {
        Integer instructorId =identifyUserAccessService.getInstructor().getId();
        List<StudyIn> studyIns = studyInRepository.findByClazz_Instructor_Id(instructorId);
        return studyIns.stream()
                .map(studyIn -> clazzMapper.toDTO(studyIn.getClazz()))
                .distinct()
                .collect(Collectors.toList());
    }


    @Override
    public List<Map<String, Object>> findClazzesForRegisterByBlockAndSemesterAndYearAndStudentId() {
        SemesterProgress semesterProgress = semesterProgressRepository.findActivedProgress();
        Integer block = semesterProgress.getBlock().getBlock();
        String semester = semesterProgress.getSemester().getSemester();
        Integer year = semesterProgress.getYear().getYear();
        Student student = identifyUserAccessService.getStudent();

        //Tìm toàn bộ môn phải học trong CTDT
        List<Integer> allSubjects = subjectRepository.findSubjectsIdByEducationProgram(student.getEducationProgram().getId());

        //Tìm các môn đã pass
        List<Integer> passedSubjects = subjectRepository.findPassedSubjectsIdByStudentId(student.getId());

        //Tìm các môn đăng ký học
        List<Integer> registedSubject = subjectRepository.findRegistedSubjectsIdByStudentIdAndBlockAndSemesterAndYear(
                student.getId(),
                block,
                semester,
                year);

        //So sánh nếu pass môn thì cho môn đó là null
        for (int i = 0; i < allSubjects.size(); i++){
            for (int j = 0; j < passedSubjects.size(); j++){
                if (Objects.equals(allSubjects.get(i), passedSubjects.get(j))){
                    allSubjects.set(i, null);
                    break;
                }
            }
        }

        //So sánh nếu đã đăng ký môn này trong kỳ này thì môn đó là null
        for (int i = 0; i < allSubjects.size(); i++){
            for (int j = 0; j < registedSubject.size(); j++){
                if(Objects.equals(allSubjects.get(i), registedSubject.get(j))){
                    allSubjects.set(i, null);
                    break;
                }
            }
        }

        List<Map<String, Object>> registerClazzes = new ArrayList<Map<String, Object>>();

        //Nếu môn đó null thì không cần tải lớp có môn đó lên nữa
        for (int i = 0; i < allSubjects.size(); i++){
            if (allSubjects.get(i) != null){
                List<Map<String, Object>> clazzes = clazzRepository.findClazzesToRegistByBlockAndYearAndSemesterAndYearAndSubjectId(
                        block, semester, year, allSubjects.get(i));
                for (int j = 0; j < clazzes.size(); j++){
                    registerClazzes.add(clazzes.get(j));
                }
            }
        }

        for (int i = 0; i <registerClazzes.size(); i++){
            Map<String, Object> clazz = new HashMap<>(registerClazzes.get(i));

            Integer amout = studyInRepository.countStudentByClazzId((Integer) registerClazzes.get(i).get("id"));
            clazz.put("amout", amout);

            List<String> weekDays = weekDayRepository.findWeekDayByClazzId((Integer) clazz.get("id"));
            String studyDays = "";
            for (int j = 0; j < weekDays.size(); j++){
                if (j < weekDays.size() -1){
                    studyDays += weekDays.get(j) + ", ";
                } else {
                    studyDays += weekDays.get(j);
                }
            }
            clazz.put("study_day", studyDays);

            registerClazzes.set(i, clazz);
        }
        return registerClazzes;
    }

    @Override
    public List<Map<String, Object>> findCurrentClazzesByBlockAndSemesterAndYearAndStudentId() {
        SemesterProgress semesterProgress = semesterProgressRepository.findActivedProgress();
        Integer block = semesterProgress.getBlock().getBlock();
        String semester = semesterProgress.getSemester().getSemester();
        Integer year = semesterProgress.getYear().getYear();
        Student student = identifyUserAccessService.getStudent();
        List<Map<String,Object>> clazzes = clazzRepository.findCurrentClassesByBlockAndSemesterAndYearAndStudentId(block, semester, year, student.getId(), true);

        for (int i = 0; i < clazzes.size(); i++){
            HashMap<String, Object> clazz = new HashMap<>(clazzes.get(i));
            List<String> weekDays = weekDayRepository.findWeekDayByClazzId((Integer) clazz.get("id"));
            String studyDays = "";
            for (int j = 0; j < weekDays.size(); j++){
                if (j < weekDays.size() -1){
                    studyDays += weekDays.get(j) + ", ";
                } else {
                    studyDays += weekDays.get(j);
                }
            }
            clazz.put("study_day", studyDays);
            clazzes.set(i,clazz);
        }
        return clazzes;
    }


    @Override
    public Map<String, Object> findClazzToChangeShiftById(Integer id) {
        HashMap<String, Object> clazz = new HashMap<>(clazzRepository.findClazzToChangeByClazzId(id));
        List<String> weekDays = weekDayRepository.findWeekDayByClazzId((Integer) clazz.get("id"));
        String studyDays = "";
        for (int j = 0; j < weekDays.size(); j++){
            if (j < weekDays.size() -1){
                studyDays += weekDays.get(j) + ", ";
            } else {
                studyDays += weekDays.get(j);
            }
        }
        clazz.put("study_day", studyDays);
        return clazz;
    }

    @Override
    public List<Map<String, Object>> findClazzesBySubjectIdAndShiftAndBlockAndSemesterAndYear(Integer subjectId, Integer shift) {
        SemesterProgress semesterProgress = semesterProgressRepository.findActivedProgress();
        Integer block = semesterProgress.getBlock().getBlock();
        String semester = semesterProgress.getSemester().getSemester();
        Integer year = semesterProgress.getYear().getYear();
        Student student = identifyUserAccessService.getStudent();

        Integer oldClazzId = clazzRepository.findClazzByStudentIdAndBlockAndSemesterAndYearAndSubjectId(student.getId(),block, semester, year, subjectId);

        List<Map<String,Object>> clazzes = clazzRepository
                .findClazzesBySubjectIdAndShiftAndBlockAndSemesterAndYearAndOldClazzId(subjectId, shift,block,semester,year, oldClazzId);

        for (int i = 0; i <clazzes.size(); i++){
            Map<String, Object> clazz = new HashMap<>(clazzes.get(i));

            Integer amout = studyInRepository.countStudentByClazzId((Integer) clazz.get("clazz_id"));
            clazz.put("amout", amout);

            List<String> weekDays = weekDayRepository.findWeekDayByClazzId((Integer) clazz.get("clazz_id"));
            String studyDays = "";
            for (int j = 0; j < weekDays.size(); j++){
                if (j < weekDays.size() -1){
                    studyDays += weekDays.get(j) + ", ";
                } else {
                    studyDays += weekDays.get(j);
                }
            }
            clazz.put("study_day", studyDays);

            List<LocalDate> dates = scheduleRepository.findDateByClazzId((Integer) clazz.get("clazz_id"));
            if (!dates.isEmpty()) {
                clazz.put("start_date", dates.get(0));
            }


            clazzes.set(i, clazz);
        }


        return clazzes;
    }

    @Override
    public List<Map<String, Object>> findClazzesByInstructorIdAndBlockAndSemesterAndYear(Integer block, String semester, Integer year) {
        Instructor instructor = identifyUserAccessService.getInstructor();
        List<Map<String,Object>> clazzes = clazzRepository.findClazzesByInstructorIdAndBlockAndSemesterAndYear(
                instructor.getId(),
                block,
                semester,
                year);
        for (int i = 0; i < clazzes.size(); i++){
            HashMap<String, Object> clazz = new HashMap<>(clazzes.get(i));
            List<String> weekDays = weekDayRepository.findWeekDayByClazzId((Integer) clazz.get("id"));
            String studyDays = "";
            for (int j = 0; j < weekDays.size(); j++){
                if (j < weekDays.size() -1){
                    studyDays += weekDays.get(j) + ", ";
                } else {
                    studyDays += weekDays.get(j);
                }
            }
            clazz.put("study_day", studyDays);
            clazzes.set(i,clazz);
        }

        return clazzes;
    }

//    @Override
//    public List<Map<String, Object>> findAllClazzsByBlockAndSemesterAndYear(Integer block, String semester, Integer year) {
//        return clazzRepository.findAllClazzsByBlockAndSemesterAndYear(block,semester,year);
//    }

@Override
    public List<Map<String, Object>> findAllClazzsByBlockAndSemesterAndYear(
            Integer block, String semester, Integer year) {
    // Ánh xạ các ngày trong tuần sang số
    Map<String, Integer> dayToNumber = Map.of(
            "Monday", 2,
            "Tuesday", 3,
            "Wednesday", 4,
            "Thursday", 5,
            "Friday", 6,
            "Saturday", 7,
            "Sunday", 8
    );
    List<Map<String, Object>> rawResults = clazzRepository.findAllClazzsByBlockAndSemesterAndYear(block, semester, year);

    Map<String, Map<String, Object>> clazzMap = new HashMap<>();

    for (Map<String, Object> row : rawResults) {
        String clazzId = row.get("id").toString(); // Lấy id của lớp
        String weekday = row.get("weekday").toString(); // Lấy ngày trong tuần

        // Ánh xạ weekday sang số
        Integer weekdayNumber = dayToNumber.get(weekday);

        if (!clazzMap.containsKey(clazzId)) {
            // Tạo bản ghi mới nếu chưa tồn tại trong map
            Map<String, Object> clazzData = new HashMap<>(row); // Sao chép dữ liệu ban đầu
            clazzData.put("weekdays", new ArrayList<>()); // Tạo danh sách ngày trong tuần
            clazzMap.put(clazzId, clazzData);
        }

        // Thêm ngày đã được chuyển đổi thành số vào danh sách weekdays
        ((List<Integer>) clazzMap.get(clazzId).get("weekdays")).add(weekdayNumber);

    }

    // Biến danh sách ngày thành chuỗi phân cách bởi dấu phẩy
    return clazzMap.values().stream().map(clazz -> {
        List<Integer> weekdays = (List<Integer>) clazz.get("weekdays");
        clazz.put("weekdays", weekdays.stream()
                .sorted() // Sắp xếp tăng dần
                .map(String::valueOf) // Chuyển Integer sang String
                .collect(Collectors.joining(", "))); // Ghép thành chuỗi
        return clazz;
    }).collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> findRegistedClazzesByBlockAndSemesterAndYearAndStudentId() {
        SemesterProgress semesterProgress = semesterProgressRepository.findActivedProgress();
        Integer block = semesterProgress.getBlock().getBlock();
        String semester = semesterProgress.getSemester().getSemester();
        Integer year = semesterProgress.getYear().getYear();
        Student student = identifyUserAccessService.getStudent();
        List<Map<String,Object>> clazzes = clazzRepository.findCurrentClassesByBlockAndSemesterAndYearAndStudentId(block, semester, year, student.getId(), false);

        for (int i = 0; i < clazzes.size(); i++){
            HashMap<String, Object> clazz = new HashMap<>(clazzes.get(i));
            List<String> weekDays = weekDayRepository.findWeekDayByClazzId((Integer) clazz.get("id"));
            String studyDays = "";
            for (int j = 0; j < weekDays.size(); j++){
                if (j < weekDays.size() -1){
                    studyDays += weekDays.get(j) + ", ";
                } else {
                    studyDays += weekDays.get(j);
                }
            }
            clazz.put("study_day", studyDays);
            clazzes.set(i,clazz);
        }
        return clazzes;
    }

}

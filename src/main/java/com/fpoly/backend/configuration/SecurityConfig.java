package com.fpoly.backend.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class SecurityConfig {
    @Autowired
    private CustomJwtDecoder customJwtDecoder;
    private final String[] PUBLIC_ENDPOINTS = {
            "/auth/token",
            "/auth/introspect",
            "/auth/logout",
            "/auth/refresh",
            "api/sendemail",
            "/api/subjects/getAllSubject",
            "/api/subjects/getAllSubjectBySpecializationId",
            "/api/subjects/getSubjectDetail",
            "api/events",

    };
    private final String[] STUDENT_ENDPOINTS={
            "/api/students/studentInfor",
            "/api/students/updateStudentByStudent",
            "/api/clazzs/getAllClazzByStudent/*",
            "/api/clazzs/registerClazz/*",
            "/api/studyResult/learningProgressByStudent",
            "/api/notes/countNoteByMonth",
            "/api/notes/getNoteByMonth",
            "/api/notes/getNoteByDay",
            "/api/notes/createNote",
            "/api/notes/updateNote",
            "/api/notes/deleteNote",
            "/api/schedules/getSchedules",
            "/api/examSchedules/getExamSchedules"
    };

//    private final String[] STUDENT_INSTRUCTOR_ENDPOINTS={
//            "api/events",
//    };
    private final String[] INSTRUCTOR_ENDPOINTS={
        "/api/shifts",
            "/api/instructors/getAllTeachingSchedule",
            "/api/shifts/getAllShiftAvailable",
            "/api/retakeschedules/getRetakeScheduleByInstructor",
            "/api/schedules/getSchedules",
            "/api/schedules/getscheduleStatusFalse",
            "/api/rooms/getAllRoomAvailable",
            "/api/retakeschedules/createRetakeSchedule",
            "/api/students/getStudentsByInstructorIdAndClazzId",
            "api/clazzs/getClazzsByInstructorId",
            "/api/attendances/attended",
            "/api/attendances/attendedByClazzId",
            "/api/attendances/putAttended",
            "/api/examschedules/getExamBatch",
            "/api/arrangebatchs/create",
            "/api/arrangebatchs/update",
            "/api/arrangebatchs/delete",
            "/api/studyResult/markColumn",
            "/api/studyResult/create",
            "/api/studyResult/update",
            "/api/studyins/getAllAverage",
            "/api/studyResult/getMarkByClazzId"

    };
    private final String[] ADMIN_ENDPOINTS={
            "/api/students/updateStudentByAdmin",
            "/api/students/updateStudentByAdmin",
            "/api/students/studentByID/*",
            "/api/students/updateStudentByAdmin/*",
            "/api/students/deleteStudent/*",
            "/api/students/getAllStudentByCourseAndMajor",
            "/api/students/createStudent",
            "/api/students/import",
            "/api/semesterprogresses/createSP",
            "/api/semesterprogresses/updateSP",
            "/api/semesterprogresses/deleteSP",
            "/api/examschedules/admin",
            "/api/semesterprogresses/getAllSP",
            "/api/schedules/admin",
            "/api/schedules/getByBlockSemesterYear/admin",


    };
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers("/login").permitAll()
                                .requestMatchers(STUDENT_ENDPOINTS).hasRole("STUDENT")
                                .requestMatchers(INSTRUCTOR_ENDPOINTS).hasRole("INSTRUCTOR")
                                .requestMatchers(ADMIN_ENDPOINTS).hasRole("ADMIN")
//                                .requestMatchers(STUDENT_INSTRUCTOR_ENDPOINTS).hasAnyRole("INSTRUCTOR","STUDENT","ADMIN")
                                .anyRequest().authenticated()

//                                .anyRequest().permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login") // Trang đăng nhập
                )
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(customJwtDecoder)
                                .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                )
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));
        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
}

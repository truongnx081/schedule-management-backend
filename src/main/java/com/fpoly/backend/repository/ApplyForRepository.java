package com.fpoly.backend.repository;

import com.fpoly.backend.entities.ApplyFor;
import com.fpoly.backend.entities.EducationProgram;
import com.fpoly.backend.entities.Subject;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ApplyForRepository extends JpaRepository<ApplyFor,Integer> {

    @Query("SELECT COUNT(DISTINCT ap.subject.id) " +
            "FROM Student st " +
            "JOIN st.educationProgram ed " +
            "JOIN ed.applyFors ap " +
            "WHERE st.id =:studentId")
    Integer countSubjectByStudent(@Param("studentId") Integer studentId);

    List<ApplyFor> findAllByEducationProgram(EducationProgram educationProgram);
}

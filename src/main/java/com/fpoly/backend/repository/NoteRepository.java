package com.fpoly.backend.repository;

import com.fpoly.backend.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface NoteRepository extends JpaRepository<Note,Integer> {

    @Query("SELECT nt.name AS noteType, COUNT(n.id) AS SL " +
            "FROM NoteType nt " +
            "LEFT JOIN nt.notes n " +
            "ON n.student.id = :studentId " +
            "AND FUNCTION('MONTH', n.date) = :month " +
            "AND FUNCTION('YEAR', n.date) = :year " +
            "GROUP BY nt.name")
    List<Map<String, Object>> countNotesByStudentIdAndMonthAndYear(@Param("studentId") Integer studentId,
                                                                   @Param("month") int month, @Param("year") int year);

    @Query("SELECT n.date as date_note, n.content as content_note, " +
            "n.location as location_note, n.noteTime as noteTime_note, n.noteType.id as noteType_note " +
            "FROM Note n JOIN n.noteType nt " +
            "WHERE n.student.id = :studentId  " +
            "AND FUNCTION('MONTH', n.date) = :month " +
            "AND FUNCTION('YEAR', n.date) = :year")
    List<Map<String, Object>> getAllNoteByMonthYear(@Param("studentId") Integer studentId,
                                                    @Param("month") int month, @Param("year") int year);
    @Query("SELECT n.id as note_id, n.date as date_note, n.content as content_note, " +
            "n.location as location_note, n.noteTime as time_note, n.noteType.name as name_note_type, n.noteType.id as note_type_id " +
            "FROM Note n JOIN n.noteType nt " +
            "WHERE n.student.id = :studentId " +
            "AND FUNCTION('DAY', n.date) = :day " +
            "AND FUNCTION('MONTH', n.date) = :month " +
            "AND FUNCTION('YEAR', n.date) = :year")
    List<Map<String, Object>> getAllNoteByDayMonthYear(@Param("studentId") Integer studentId, @Param("day") int day,
                                                       @Param("month") int month, @Param("year") int year);
    @Query("SELECT n.date as date_note, n.content as content_note, " +
            "n.location as location_note, n.noteTime as noteTime_note, n.noteType.id as noteType_note " +
            "FROM Note n JOIN n.noteType nt " +
            "WHERE n.student.id = :studentId  " )
    List<Map<String, Object>> getAllNote(@Param("studentId") Integer studentId);
}

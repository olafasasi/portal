package com.student.portal.dao.repository;

import com.student.portal.dao.entities.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    @Query("SELECT COUNT(e) > 0 FROM Enrollment e WHERE e.student.id = ?1 AND e.course.id = ?2")
    public boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    public List<Enrollment> findByStudentId(Long studentId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Enrollment e WHERE e.student.id = :studentId")
    public void deleteAllByStudentId(@Param("studentId") Long studentId);

}

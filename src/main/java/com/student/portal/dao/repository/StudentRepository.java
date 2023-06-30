package com.student.portal.dao.repository;

import com.student.portal.dao.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    public Student getStudentById(Long studentId);

    public Student findByEmail(String username);
}

package com.student.portal.service;

import com.student.portal.dao.dto.StudentDto;
import com.student.portal.dao.entities.Student;

public interface StudentService {

    public StudentDto findByEmail(String email);

    public StudentDto saveStudent(StudentDto studentDto);

    public StudentDto updateStudent(StudentDto studentDto);

}

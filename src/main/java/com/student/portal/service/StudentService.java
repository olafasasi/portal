package com.student.portal.service;

import com.student.portal.dao.dto.StudentDto;

public interface StudentService {

    public StudentDto findByEmail(String email);

    public void saveStudent(StudentDto studentDto);

    public void updateStudent(StudentDto studentDto);

}

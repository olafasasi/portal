package com.student.portal.service.impl;

import com.student.portal.dao.dto.StudentDto;
import com.student.portal.dao.entities.Student;
import com.student.portal.dao.repository.StudentRepository;
import com.student.portal.service.FinanceService;
import com.student.portal.service.LibraryService;
import com.student.portal.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class StudentServiceImpl implements StudentService {

    private Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private FinanceService financeService;
    @Autowired
    private LibraryService libraryService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public StudentDto findByEmail(String email){
        return this.studentDtoMapper(this.studentRepository.findByEmail(email));
    }

    @Override
    public StudentDto saveStudent(StudentDto studentDto) {
        Student student = this.studentMapper(studentDto);
        student.setRole("ROLE_STUDENT");
        student.setPasswordHash(this.passwordEncoder.encode(studentDto.getPasswordHash()));
        student = this.studentRepository.save(student);
        studentDto.setId(student.getId());
        // send the request to fanice service
        this.financeService.createNewFinanceAccountForStudent(student);
        this.libraryService.createNewLibraryAccountForStudent(student);
        return studentDto;
    }

    @Override
    public StudentDto updateStudent(StudentDto studentDto) {
        Student currentStudent = this.studentRepository.findByEmail(studentDto.getEmail());
        currentStudent.setFirstName(studentDto.getFirstName());
        currentStudent.setLastName(studentDto.getLastName());
        this.studentRepository.save(currentStudent);
        return studentDto;
    }

    /**
     * studentDtoMapper method use to map the basic info to dto
     * @param student
     * @return StudentDto
     * */
    private StudentDto studentDtoMapper(Student student) {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(student.getId());
        studentDto.setFirstName(student.getFirstName());
        studentDto.setLastName(student.getLastName());
        studentDto.setEmail(student.getEmail());
        studentDto.setRole(student.getRole());
        return studentDto;
    }

    /**
     * studentDtoMapper method use to map the basic info to dto
     * @param studentDto
     * @return Student
     * */
    private Student studentMapper(StudentDto studentDto) {
        Student student = new Student();
//        student.setId(studentDto.getId());
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setEmail(studentDto.getEmail());
        student.setRole(studentDto.getRole());
        return student;
    }
}

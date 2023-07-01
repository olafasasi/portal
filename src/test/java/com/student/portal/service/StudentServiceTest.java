package com.student.portal.service;

import com.student.portal.dao.dto.StudentDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Test
    public void saveStudent() {
        StudentDto studentDto = new StudentDto();
        studentDto.setFirstName("ALFA");
        studentDto.setLastName("BETA");
        studentDto.setEmail("abc@gmail.com");
        studentDto.setPasswordHash("abc@gmail.com");
        studentDto.setRole("ROLE_STUDENT");
        this.studentService.saveStudent(studentDto);
        Assertions.assertThat(studentDto.getId() != null);
    }

    @Test
    public void findByEmail() {
        StudentDto studentDto = this.studentService.findByEmail("abc@gmail.com");
        Assertions.assertThat(studentDto.getRole().equals("ROLE_STUDENT"));
        Assertions.assertThat(studentDto.getFirstName().equals("ALFA"));
    }

    @Test
    public void updateStudent() {
        StudentDto studentDto = this.studentService.findByEmail("abc@gmail.com");
        studentDto.setFirstName("BETA");
        studentDto.setFirstName("ALFA");
        this.studentService.updateStudent(studentDto);
        studentDto = this.studentService.findByEmail("abc@gmail.com");
        Assertions.assertThat(studentDto.getFirstName().equals("BETA"));
        Assertions.assertThat(studentDto.getLastName().equals("ALFA"));
    }

}

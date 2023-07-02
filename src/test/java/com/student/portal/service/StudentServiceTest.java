package com.student.portal.service;

import com.student.portal.dao.dto.StudentDto;
import com.student.portal.dao.entities.Student;
import com.student.portal.dao.repository.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class StudentServiceTest {

    private Student student;
    private StudentDto studentDto;
    private final String email = "test@email.com";
    private final String role = "ROLE_STUDENT";
    private final Long id = 1L;

    @MockBean
    private StudentRepository studentRepository;
    @Autowired
    private StudentService studentService;

    @BeforeEach
    public void setUp() {
        this.studentDto = new StudentDto();
        this.studentDto.setId(this.id);
        this.studentDto.setFirstName("ALFA");
        this.studentDto.setLastName("BETA");
        this.studentDto.setEmail(this.email);
        this.studentDto.setPasswordHash("admin");
        this.studentDto.setRole(this.role);

        this.student = new Student();
        this.student.setFirstName("ALFA");
        this.student.setLastName("BETA");
        this.student.setEmail(this.email);
        this.student.setPasswordHash("admin");
        this.student.setRole(this.role);

        Mockito.when(this.studentRepository.findByEmail(this.student.getEmail())).thenReturn(this.student);
        Mockito.when(this.studentRepository.save(this.student)).thenReturn(this.student);
    }

    @Test
    public void testGetStudentByEmailWithValidEmailReturnsExistingStudent() {
        StudentDto studentDto = this.studentService.findByEmail(email);
        Assertions.assertThat(studentDto.getEmail().equals(email));
        Assertions.assertThat(studentDto.getRole().equals(role));
    }

    @Test
    void testGetStudentByEmailWithInValidEmailThrowsException() {
        assertThrows(Exception.class, () -> this.studentService.findByEmail("Dummy"),
          "Exception was not thrown.");
    }

    @Test
    void testGetStudentByEmailWithEmptyEmailThrowsException() {
        assertThrows(Exception.class, () -> this.studentService.findByEmail(""),
          "Exception was not thrown.");
    }

    @Test
    void testGetStudentByEmailWithInNullEmail_throwsException() {
        assertThrows(Exception.class, () -> this.studentService.findByEmail(null),
        "Exception was not thrown.");
    }

    @Test
    public void saveStudent() {
        Student entity = this.studentRepository.save(student);
        Assertions.assertThat(this.studentDto.getEmail().equals(entity.getEmail()));
        Assertions.assertThat(this.studentDto.getRole().equals(entity.getRole()));
    }

}

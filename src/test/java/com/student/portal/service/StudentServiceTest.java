package com.student.portal.service;

import com.student.portal.dao.dto.StudentDto;
import com.student.portal.dao.entities.Student;
import com.student.portal.dao.repository.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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

      studentDto = new StudentDto();
      studentDto.setId(id);
      studentDto.setFirstName("ALFA");
      studentDto.setLastName("BETA");
      studentDto.setEmail(email);
      studentDto.setPasswordHash("admin");
      studentDto.setRole(role);

      student = new Student();
      student.setFirstName("ALFA");
      student.setLastName("BETA");
      student.setEmail(email);
      student.setPasswordHash("admin");
      student.setRole(role);

      Mockito.when(studentRepository.findByEmail(student.getEmail()))
          .thenReturn(student);
      Mockito.when(studentRepository.save(student))
          .thenReturn(student);
    }

    @Test
    public void testGetStudentByEmail_withValidEmail_ReturnsExistingStudent() {
      StudentDto studentDto = this.studentService.findByEmail(email);
      Assertions.assertThat(studentDto.getEmail().equals(email));
      Assertions.assertThat(studentDto.getRole().equals(role));
    }

    @Test
    void testGetStudentByEmail_withInValidEmail_throwsException() {
      assertThrows(Exception.class, () -> studentService.findByEmail("Dummy"),
          "Exception was not thrown.");
    }

    @Test
    void testGetStudentByEmail_withEmptyEmail_throwsException() {
      assertThrows(Exception.class, () -> studentService.findByEmail(""),
          "Exception was not thrown.");
    }

    @Test
    void testGetStudentByEmail_withInNullEmail_throwsException() {
      assertThrows(Exception.class, () -> studentService.findByEmail(null),
          "Exception was not thrown.");
    }

    @Test
    public void saveStudent() {
        Student entity = this.studentRepository.save(student);
        Assertions.assertThat(this.studentDto.getEmail().equals(entity.getEmail()));
        Assertions.assertThat(this.studentDto.getRole().equals(entity.getRole()));
    }

}

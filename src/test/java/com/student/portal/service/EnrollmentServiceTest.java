package com.student.portal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.student.portal.dao.entities.Course;
import com.student.portal.dao.entities.Enrollment;
import com.student.portal.dao.entities.Student;
import com.student.portal.dao.repository.EnrollmentRepository;
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
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;


@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class EnrollmentServiceTest {

    private Course course = new Course(1l, "BE/B.Tech- Bachelor of Technology.", "BE/B.Tech- Bachelor of Technology.", 1000.0);
    private Student student = new Student(1l, "Nabeel", "Ahmed", "apple@gmail.com",
        "$2a$10$OtjiCN27cKN9CY6zvV7w7OHKDULtiJxgQFo3.zKC8j0xFCnWLwsfq", Set.of(course), "ROLE_STUDENT");
    private Enrollment enrollment;

    @MockBean
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private EnrollmentService enrollmentService;

    @BeforeEach
    public void setUp() {
        this.enrollment = new Enrollment(2l, student, course, LocalDate.parse("2020-04-04"));
        Mockito.when(this.enrollmentRepository.findById(this.enrollment.getId())).thenReturn(Optional.of(enrollment));
        Mockito.when(this.enrollmentRepository.findAll()).thenReturn(Arrays.asList(enrollment));
        Mockito.when(this.enrollmentRepository.save(enrollment)).thenReturn(enrollment);
        Mockito.doNothing().when(this.enrollmentRepository).delete(enrollment);
    }

    @Test
    public void enrollStudent() throws JsonProcessingException {
        Enrollment enrollment = this.enrollmentService.enrollStudent(this.course.getId(), this.student.getEmail());
        Assertions.assertThat(enrollment.getId());
        Assertions.assertThat(enrollment.getEnrollmentDate().equals(LocalDate.parse("2020-04-04")));
    }

    @Test
    public void fetchAllEnrolledCoursesByStudent() {
        Assertions.assertThat(this.enrollmentService.fetchAllEnrolledCoursesByStudent(this.student.getEmail()).size() > 1);
    }

    @Test
    public void deleteAllByStudentId() {
        Assertions.assertThat(this.enrollmentService.deleteAllByStudentId(this.enrollment.getStudent().getId()) == 1);
    }

}

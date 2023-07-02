package com.student.portal.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.student.portal.dao.dto.CourseDto;
import com.student.portal.dao.dto.EnrollmentDto;
import com.student.portal.dao.entities.Course;
import com.student.portal.dao.entities.Enrollment;
import com.student.portal.dao.entities.Student;
import com.student.portal.dao.repository.CourseRepository;
import com.student.portal.dao.repository.EnrollmentRepository;
import com.student.portal.dao.repository.StudentRepository;
import com.student.portal.service.EnrollmentService;
import com.student.portal.service.FinanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private Logger logger = LoggerFactory.getLogger(EnrollmentServiceImpl.class);

    @Autowired
    private FinanceService financeService;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public Enrollment enrollStudent(Long courseId, String email) throws JsonProcessingException {
        Enrollment enrollment = null;
        Student student = this.studentRepository.findByEmail(email);
        Course course = this.courseRepository.getCourseById(courseId);
        Boolean isEnrollmentAlreadyExist = this.enrollmentRepository.existsByStudentIdAndCourseId(
            student.getId(), course.getId());
        if(!isEnrollmentAlreadyExist){
          this.financeService.createNewFinanceInvoiceForStudentCourse(student, course);
            enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollment.setEnrollmentDate(LocalDate.now());
            enrollment = this.enrollmentRepository.save(enrollment);
            return enrollment;
        }
        return enrollment;
    }

    public List<EnrollmentDto> fetchAllEnrolledCoursesByStudent(String email) {
        Student student = this.studentRepository.findByEmail(email);
        return this.enrollmentRepository.findByStudentId(student.getId())
        .stream().map(enrollment -> {
            EnrollmentDto enrollmentDto = new EnrollmentDto();
            enrollmentDto.setId(enrollment.getId());
            CourseDto courseDto = new CourseDto();
            if (enrollment.getCourse() != null) {
                courseDto.setName(enrollment.getCourse().getName());
            }
            enrollmentDto.setCourse(courseDto);
            return enrollmentDto;
        }).collect(Collectors.toList());
    }

    @Override
    public int deleteAllByStudentId(Long studentId) {
        this.enrollmentRepository.deleteAllByStudentId(studentId);
        return 1;
    }
}

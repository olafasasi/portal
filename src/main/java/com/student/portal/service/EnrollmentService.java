package com.student.portal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.student.portal.dao.dto.EnrollmentDto;
import com.student.portal.dao.entities.Enrollment;
import java.util.List;

public interface EnrollmentService {

    public Enrollment enrollStudent(Long courseId, String email) throws JsonProcessingException;

    public List<EnrollmentDto> fetchAllEnrolledCoursesByStudent(String email);

    public int deleteAllByStudentId(Long studentId);
}

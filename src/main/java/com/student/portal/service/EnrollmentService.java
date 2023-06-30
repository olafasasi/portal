package com.student.portal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.student.portal.dao.dto.EnrollmentDto;
import java.util.List;

public interface EnrollmentService {

    public void enrollStudent(Long id, String email) throws JsonProcessingException;

    public List<EnrollmentDto> fetchAllEnrolledCoursesByStudent(String email);

    public void deleteAllByStudentId(Long studentId);
}

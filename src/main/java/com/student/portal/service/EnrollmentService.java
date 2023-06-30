package com.student.portal.service;

import com.student.portal.dao.dto.EnrollmentDto;
import java.util.List;

public interface EnrollmentService {

    public void enrollStudent(Long id, String email);

    public List<EnrollmentDto> fetchAllEnrolledCoursesByStudent(String email);
}

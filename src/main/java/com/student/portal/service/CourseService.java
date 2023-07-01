package com.student.portal.service;

import com.student.portal.dao.dto.CourseDto;
import java.util.List;

public interface CourseService {

    public List<CourseDto> fetchAllCourse();

}

package com.student.portal.service.impl;

import com.student.portal.dao.dto.CourseDto;
import com.student.portal.dao.repository.CourseRepository;
import com.student.portal.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<CourseDto> fetchAllCourse() {
        return this.courseRepository.findAll()
        .stream().map(course -> {
            CourseDto courseDto = new CourseDto();
            courseDto.setId(course.getId());
            courseDto.setName(course.getName());
            courseDto.setDescription(course.getDescription());
            courseDto.setCost(course.getCost());
            return courseDto;
        }).collect(Collectors.toList());
    }




}

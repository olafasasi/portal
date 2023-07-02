package com.student.portal.service;

import com.student.portal.dao.dto.CourseDto;
import com.student.portal.dao.entities.Course;
import com.student.portal.dao.repository.CourseRepository;
import java.util.Arrays;
import java.util.Optional;
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


@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class CourseServiceTest {

    private Course course;
    @MockBean
    private CourseRepository courseRepository;
    @Autowired
    private CourseService courseService;

    @BeforeEach
    public void setUp() {
        this.course = new Course(1l, "BE/B.Tech- Bachelor of Technology.",
            "BE/B.Tech- Bachelor of Technology.", 1000.0);
        Mockito.when(this.courseRepository.findById(this.course.getId())).thenReturn(Optional.of(this.course));
        Mockito.when(this.courseRepository.findAll()).thenReturn(Arrays.asList(this.course));
        Mockito.when(this.courseRepository.save(this.course)).thenReturn(this.course);
        Mockito.doNothing().when(this.courseRepository).delete(this.course);
    }

    @Test
    void checkCourseExist() {
        Optional<CourseDto> targetCourse = this.courseService.fetchAllCourse().stream().findFirst();
        Assertions.assertThat(this.course.getId().equals(targetCourse.get().getId()));
        Assertions.assertThat(this.course.getName().equals("BE/B.Tech- Bachelor of Technology."));
        Assertions.assertThat(this.course.getCost().equals(1000.0));
    }

    @Test
    void checkCourseListExist() {
        Integer size = this.courseService.fetchAllCourse().size();
        Assertions.assertThat(size > 0);
    }

}

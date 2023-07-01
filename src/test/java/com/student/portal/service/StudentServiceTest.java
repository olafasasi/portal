package com.student.portal.service;

import com.student.portal.dao.dto.StudentDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Test
    public void findByEmail() {}

    @Test
    public void saveStudent() {}

    @Test
    public void updateStudent() {}

}

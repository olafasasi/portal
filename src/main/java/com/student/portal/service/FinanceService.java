package com.student.portal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.student.portal.dao.entities.Course;
import com.student.portal.dao.entities.Student;

public interface FinanceService {

    public void createNewFinanceAccountForStudent(Student student);

    public void createNewFinanceInvoiceForStudentCourse(Student student, Course course) throws JsonProcessingException;

}

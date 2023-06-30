package com.student.portal.controller;

import com.student.portal.service.EnrollmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/enrollment")
public class EnrollmentController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(EnrollmentController.class);

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/enrollCourse/{id}")
    public String enrollCourse(@PathVariable Long id) {
        this.enrollmentService.enrollStudent(id, this.getCurrentSessionUserEmail());
        return "redirect:/student/studentIndex";
    }

    @GetMapping("/enrolled-courses")
    public ModelAndView viewEnrolledCourses(Model model) {
        ModelAndView mav = new ModelAndView("viewEnrolledCourses");
        model.addAttribute("enrollments", this.enrollmentService.fetchAllEnrolledCoursesByStudent(
            this.getCurrentSessionUserEmail()));
        return mav;
    }


}

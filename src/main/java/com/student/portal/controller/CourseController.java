package com.student.portal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.student.portal.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/course")
public class CourseController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private CourseService courseService;

    /**
     * Working
     * controller use to fetch the all couse
     * @param model
     * @return ModelAndView
     * */
    @GetMapping("/allCourses")
    public ModelAndView viewAllCourses(Model model) {
        model.addAttribute("courses", this.courseService.fetchAllCourse());
        ModelAndView mav = new ModelAndView("viewAllCourses");
        return mav;
    }
}

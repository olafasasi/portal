package com.student.portal.controller;

import com.student.portal.dao.dto.StudentDto;
import com.student.portal.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HomeController {

    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private StudentService studentService;

    /**
     * Working
     * */
    @GetMapping("/")
    public ModelAndView home(Model model) {
        return new ModelAndView("index");
    }

    /**
     * Working
     * */
    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("student", new StudentDto());
        return "register";
    }

    /**
     * Working
     * */
    @PostMapping("/register")
    public ModelAndView registerSuccess(@ModelAttribute("student")  StudentDto student) {
    this.studentService.saveStudent(student);
        ModelAndView mav = new ModelAndView("redirect:/login");
        return mav;
    }

    /**
     * Working
     * */
    @GetMapping("/login")
    public String login(Model model){
        return "login";
    }

}

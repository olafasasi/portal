package com.student.portal.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.student.portal.dao.dto.StudentDto;
import com.student.portal.dao.entities.Invoice;
import com.student.portal.dao.repository.CourseRepository;
import com.student.portal.dao.repository.EnrollmentRepository;
import com.student.portal.dao.repository.InvoiceRepository;
import com.student.portal.service.EnrollmentService;
import com.student.portal.service.StudentService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;


@Controller
@RequestMapping("/student")
public class StudentController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private StudentService studentService;

    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;


    /**
     * Working
     * */
    @GetMapping("/studentIndex")
    public ModelAndView studentIndex(Model model) {
        model.addAttribute("student", this.studentService.findByEmail(
            this.getCurrentSessionUserEmail()));
        ModelAndView mav = new ModelAndView("studentIndex");
        return mav;
    }

    /**
     * Working
     * */
    @GetMapping("/profile")
    public ModelAndView viewProfile() {
        ModelAndView mav = new ModelAndView("myProfile");
        mav.addObject("student", this.studentService.findByEmail(
            this.getCurrentSessionUserEmail()));
        return mav;
    }

    @GetMapping("/profile/update")
    public String updateProfile(Model model){
        model.addAttribute("student", this.studentService.findByEmail(
            this.getCurrentSessionUserEmail()));
        return "updateStudentProfile";
    }

    @PostMapping("/profile/update")
    public String updateUserProfile(@ModelAttribute("student") StudentDto updatedStudent) {
        this.studentService.updateStudent(updatedStudent);
        return "redirect:/student/profile";
    }

    // remaining
    @GetMapping("/graduation-eligibility")
    public ModelAndView checkGraduationEligibility(Model model) throws JsonProcessingException {
        StudentDto student = this.studentService.findByEmail(this.getCurrentSessionUserEmail());
        boolean hasOutstandingBalance = false;
        // Create a RestTemplate instance
        try {
            String financeUrl = "http://localhost:8081/accounts/student/" + student.getId();
            ResponseEntity<String> response = restTemplate.getForEntity(financeUrl, String.class);
           

            if (response.getStatusCode() == HttpStatus.OK) {
                JSONObject responseObject = new JSONObject(response.getBody());
                hasOutstandingBalance = responseObject.getBoolean("hasOutstandingBalance");
                // use the hasOutstandingBalance variable as needed
            }
        } catch (HttpClientErrorException ex) {
            // handle HttpClientErrorException
        }


        if (hasOutstandingBalance) {
            // do something
            System.out.println("True");
            List<Invoice> invoiceList = invoiceRepository.getInvoiceByStudentId(student.getId());
            double totalAmount = 0.0;
            for (Invoice invoice : invoiceList) {
                totalAmount += invoice.getAmount();
            }
            model.addAttribute("invoices", invoiceList);
            model.addAttribute("totalAmount", totalAmount);            ModelAndView mav = new ModelAndView("checkGraduation");
            return mav;

        } else {
            // do something else
            System.out.println("False");
        }
        List<Invoice> invoiceList = invoiceRepository.getInvoiceByStudentId(student.getId());
        double totalAmount = 0.0;
        for (Invoice invoice : invoiceList) {
            totalAmount += invoice.getAmount();
        }
        model.addAttribute("invoices", invoiceList);
        model.addAttribute("totalAmount", totalAmount);

        ModelAndView mav = new ModelAndView("checkGraduationNotNow");
        return mav;
    }
    @PostMapping("/pay-invoice/{id}")
    public String payInvoice(@PathVariable Long id) throws JsonProcessingException {
        Invoice invoice = invoiceRepository.findById(id).get();
        String currentReference = invoice.getReference();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String invoiceId = currentReference;
        String url = String.format("http://localhost:8081/invoices/%s/pay", invoiceId);

        HttpEntity<String> entity = new HttpEntity<String>("{}", headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String email = userDetails.getUsername();
        StudentDto student = this.studentService.findByEmail(email);
        invoiceRepository.deleteById(id);
        List<Invoice> list = invoiceRepository.getInvoiceByStudentId(student.getId());
        if(list.size() == 0){
            enrollmentRepository.deleteAllByStudentId(student.getId());
        }
        return "redirect:/student/graduation-eligibility";

    }

}

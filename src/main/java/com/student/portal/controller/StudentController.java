package com.student.portal.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.student.portal.dao.dto.InvoiceDto;
import com.student.portal.dao.dto.StudentDto;
import com.student.portal.service.EnrollmentService;
import com.student.portal.service.FinanceService;
import com.student.portal.service.InvoiceService;
import com.student.portal.service.StudentService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;


@Controller
@RequestMapping("/student")
public class StudentController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Value("${invoice.api}")
    private String invoiceApi;
    @Value("${account.api}")
    private String accountApi;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private FinanceService financeService;

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

    @GetMapping("/graduation-eligibility")
    public ModelAndView checkGraduationEligibility(Model model) throws JsonProcessingException {
        StudentDto student = this.studentService.findByEmail(this.getCurrentSessionUserEmail());
        boolean hasOutstandingBalance = false;
        // Create a RestTemplate instance
        String financeUrl = this.accountApi+"/student/" + student.getId();
        ResponseEntity<String> response = restTemplate.getForEntity(financeUrl, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject responseObject = new JSONObject(response.getBody());
            hasOutstandingBalance = responseObject.getBoolean("hasOutstandingBalance");
        }
        if (hasOutstandingBalance) {
            List<InvoiceDto> invoiceList = this.invoiceService.getOutstadingInvoiceByStudentId(student.getId());
            double totalAmount = 0.0;
            for (InvoiceDto invoice : invoiceList) {
                totalAmount += invoice.getAmount();
            }
            model.addAttribute("invoices", invoiceList);
            model.addAttribute("totalAmount", totalAmount);
            ModelAndView mav = new ModelAndView("checkGraduation");
            return mav;
        } else {
            List<InvoiceDto> invoiceList = this.invoiceService.getInvoiceByStudentId(student.getId());
            double totalAmount = 0.0;
            for (InvoiceDto invoice : invoiceList) {
                totalAmount += invoice.getAmount();
            }
            model.addAttribute("invoices", invoiceList);
            model.addAttribute("totalAmount", totalAmount);
            ModelAndView mav = new ModelAndView("checkGraduationNotNow");
            return mav;
        }
    }

    @PostMapping("/pay-invoice/{id}")
    public String payInvoice(@PathVariable Long id) throws JsonProcessingException {
        InvoiceDto invoice = this.invoiceService.getInvoiceById(id);
        String currentReference = invoice.getReference();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String invoiceId = currentReference;
        String finalUrl = String.format(this.invoiceApi+"/%s/pay", invoiceId);
        HttpEntity<String> entity = new HttpEntity<String>("{}", headers);
        this.restTemplate.exchange(finalUrl, HttpMethod.PUT, entity, String.class);
        this.invoiceService.updateInvoice(id);
        StudentDto student = this.studentService.findByEmail(this.getCurrentSessionUserEmail());
        List<InvoiceDto> list = this.invoiceService.getInvoiceByStudentId(student.getId());
        if(list.size() == 0){
            this.enrollmentService.deleteAllByStudentId(student.getId());
        }
        return "redirect:/student/graduation-eligibility";
    }

}

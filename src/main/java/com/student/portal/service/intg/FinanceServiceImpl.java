package com.student.portal.service.intg;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.portal.dao.entities.Course;
import com.student.portal.dao.entities.Invoice;
import com.student.portal.dao.entities.Student;
import com.student.portal.dao.repository.CourseRepository;
import com.student.portal.dao.repository.InvoiceRepository;
import com.student.portal.dao.repository.StudentRepository;
import com.student.portal.service.FinanceService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;


@Service
public class FinanceServiceImpl implements FinanceService {

    @Value("account.api")
    private String accountApi;
    @Value("invoice.ap")
    private String invoice;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private InvoiceRepository invoiceRepository;

    public void createNewFinanceAccountForStudent(Student student){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails userDetails = (UserDetails) auth.getPrincipal();
//        String email = userDetails.getUsername();
//        Student student = studentRepository.findByEmail(email);
//        Long id = student.getId();
//        String studentId = String.valueOf(id);
//
//        try {
//            String financeUrl = "http://localhost:8081/accounts/student/" + studentId;
//            ResponseEntity<String> response = restTemplate.getForEntity(financeUrl, String.class);
//
//        }catch (HttpClientErrorException ex){
//            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
//                JSONObject requestBody = new JSONObject();
//                requestBody.put("studentId", studentId);
//
//                // set request headers
//                HttpHeaders headers = new HttpHeaders();
//                headers.setContentType(MediaType.APPLICATION_JSON);
//
//                // create request entity with body and headers
//                HttpEntity<String> requestEntity = new HttpEntity<>(requestBody.toString(), headers);
//
//                // make POST request to create account
//                String url = "http://localhost:8081/accounts/";
//                RestTemplate restTemplate = new RestTemplate();
//                restTemplate.postForObject(url, requestEntity, String.class);
//            }
//        }
    }
    public void createNewFinanceInvoiceForStudentCourse(Student student, Course course) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails userDetails = (UserDetails) auth.getPrincipal();
//        String email = userDetails.getUsername();
//        Student student = studentRepository.findByEmail(email);
//        Course course = courseRepository.getCourseById(id);
//
//        RestTemplate restTemplate = new RestTemplate();
//        String url = "http://localhost:8081/invoices/";
//
//        // set request headers
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        // set request body
//        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("amount", course.getCost());
//        requestBody.put("dueDate", "2021-11-06");
//        requestBody.put("type", "LIBRARY_FINE");
//
//        Map<String, String> account = new HashMap<>();
//        String stringId = String.valueOf(student.getId());
//        account.put("studentId", stringId);
//        requestBody.put("account", account);
//
//        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
//
//        // make the request
//        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
//
//        String responseBody = response.getBody();
//
//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode rootNode = mapper.readTree(responseBody);
//        String reference = rootNode.get("reference").asText();
//        int invoiceId = rootNode.get("id").asInt();
//        System.out.println("Invoice created with reference: " + reference + " and id: " + invoiceId);
//        Invoice invoice = new Invoice();
//        invoice.setReference(reference);
//        invoice.setInvoiceId(invoiceId);
//        invoice.setStudentId(student.getId());
//        invoice.setAmount(course.getCost());
//        invoiceRepository.save(invoice);
    }
}

package com.student.portal.service.intg;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.portal.dao.dto.Status;
import com.student.portal.dao.entities.Course;
import com.student.portal.dao.entities.Invoice;
import com.student.portal.dao.entities.Student;
import com.student.portal.dao.repository.InvoiceRepository;
import com.student.portal.service.FinanceService;
import com.student.portal.service.impl.EnrollmentServiceImpl;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;


@Service
public class FinanceServiceImpl implements FinanceService {

    private Logger logger = LoggerFactory.getLogger(EnrollmentServiceImpl.class);

    @Value("${account.api}")
    private String accountApi;
    @Value("${invoice.api}")
    private String invoiceApi;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private InvoiceRepository invoiceRepository;

    /**
     * Method need to check test
     * */
    public void createNewFinanceAccountForStudent(Student student){
        try {
            String financeUrl = this.accountApi+"/student/" + student.getId();
            this.restTemplate.getForEntity(financeUrl, String.class);
        }catch (HttpClientErrorException ex){
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                JSONObject requestBody = new JSONObject();
                requestBody.put("studentId", student.getId());
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                // create request entity with body and headers
                HttpEntity<String> requestEntity = new HttpEntity<>(requestBody.toString(), headers);
                // make POST request to create account
                this.restTemplate.postForObject(this.accountApi, requestEntity, String.class);
            }
        }
    }

    /**
     * Method need to check test
     * */
    public void createNewFinanceInvoiceForStudentCourse(Student student,
        Course course) throws JsonProcessingException {
        // set request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("amount", course.getCost());
        requestBody.put("dueDate", "2021-11-06");
        requestBody.put("type", "LIBRARY_FINE");
        Map<String, String> account = new HashMap<>();
        account.put("studentId", String.valueOf(student.getId()));
        requestBody.put("account", account);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
        this.saveInvoice(this.restTemplate.postForEntity(invoiceApi, requestEntity, String.class), student, course);
    }

    /**
     * saveInvoice method use to create invoice
     * @param response
     * @param student
     * @param course
     * */
    private void saveInvoice (ResponseEntity<String> response,
        Student student, Course course) throws JsonProcessingException {
        String responseBody = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(responseBody);
        String reference = rootNode.get("reference").asText();
        int invoiceId = rootNode.get("id").asInt();
        logger.info("Invoice created with reference: " + reference + " and id: " + invoiceId);
        Invoice invoice = new Invoice();
        invoice.setReference(reference);
        invoice.setInvoiceId(invoiceId);
        invoice.setStudentId(student.getId());
        invoice.setAmount(course.getCost());
        invoice.setStatus(Status.OUTSTANDING);
        this.invoiceRepository.save(invoice);
    }
}

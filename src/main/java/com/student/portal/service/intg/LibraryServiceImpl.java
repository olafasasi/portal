package com.student.portal.service.intg;


import com.student.portal.dao.entities.Student;
import com.student.portal.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class LibraryServiceImpl implements LibraryService {

    @Value("library.api")
    private String libraryApi;

    @Autowired
    private RestTemplate restTemplate;

    public String createNewLibraryAccountForStudent(Student student) {
        String studentId = String.valueOf(student.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = "{\"studentId\": \"" + studentId + "\"}";
        HttpEntity<String> entity = new HttpEntity<String>(requestBody, headers);
        return this.restTemplate.postForObject(libraryApi, entity, String.class);
    }
}

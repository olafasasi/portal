package com.student.portal.controller;

import com.student.portal.dao.dto.StudentDto;
import com.student.portal.dao.entities.Student;
import com.student.portal.dao.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class HomeControllerTest {

    private Student student;
    private final String email = "test@email.com";
    private final String role = "ROLE_STUDENT";
    private final Long id = 1L;

    private static final String HOME_PATH = "/";
    private static final String REGISTER = "/register";
    private static final String LOGOUT_PATH = "/login";
    private static final String LOGOUT_PATH_REDIRECT = "/login?logout";
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private FilterChainProxy filterChain;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentRepository studentRepository;

    @BeforeEach
    public void setUp() {
        this.student = new Student();
        this.student.setId(this.id);
        this.student.setFirstName("ALFA");
        this.student.setLastName("BETA");
        this.student.setEmail(this.email);
        this.student.setPasswordHash("$2a$10$OtjiCN27cKN9CY6zvV7w7OHKDULtiJxgQFo3.zKC8j0xFCnWLwsfq");
        this.student.setRole(this.role);
        Mockito.when(this.studentRepository.findByEmail(this.student.getEmail())).thenReturn(this.student);
        Mockito.when(this.studentRepository.save(this.student)).thenReturn(this.student);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(filterChain).build();
    }

    @Test
    public void mainPageTest() throws Exception {
        this.mockMvc.perform(get(HOME_PATH)
        .contentType(MediaType.TEXT_HTML))
        .andExpect(status().isOk())
        .andExpect(content()
        .contentTypeCompatibleWith(MediaType.TEXT_HTML))
        .andExpect(content().string(containsString("Welcome to Student Portal")));
    }

    @Test
    public void registerPageTest() throws Exception {
        this.mockMvc.perform(get(REGISTER)
        .contentType(MediaType.TEXT_HTML))
        .andExpect(status().isOk())
        .andExpect(content()
        .contentTypeCompatibleWith(MediaType.TEXT_HTML))
        .andExpect(content().string(containsString("Register")))
        .andExpect(content().string(containsString("First Name")))
        .andExpect(content().string(containsString("Last Name")))
        .andExpect(content().string(containsString("Email")))
        .andExpect(content().string(containsString("Password")));
    }

    @Test
    public void registerUserTest() throws Exception {
        StudentDto registerUser = new StudentDto();
        registerUser.setFirstName("beta");
        registerUser.setLastName("alfa");
        registerUser.setEmail("alfa93@gmail.com");
        registerUser.setPasswordHash("alfa93@gmail.com");
        this.mockMvc.perform(post(REGISTER)
        .contentType(MediaType.APPLICATION_JSON)
        .flashAttr("student", registerUser))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl(LOGOUT_PATH));
    }

    @Test
    public void loginPageTest() throws Exception {
        this.mockMvc.perform(get(LOGOUT_PATH)
        .contentType(MediaType.TEXT_HTML))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
        .andExpect(content().string(containsString("Login")))
        .andExpect(content().string(containsString("username")))
        .andExpect(content().string(containsString("password")));
    }

    @Test
    public void loginWrongRequestTest() throws Exception {
        this.mockMvc.perform(formLogin(LOGOUT_PATH)
        .user("username", "test@email.com").password("password","B@234"))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/login?error"));
    }
}

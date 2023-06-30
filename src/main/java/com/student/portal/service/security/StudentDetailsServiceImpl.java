package com.student.portal.service.security;

import com.student.portal.dao.entities.Student;
import com.student.portal.dao.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class StudentDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private StudentRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = userRepository.findByEmail(username);
        if(student == null){
            throw new UsernameNotFoundException("Users not found");
        }
        CustomStudentDetails customStudentDetails = new CustomStudentDetails(student);
        return customStudentDetails;
    }

}

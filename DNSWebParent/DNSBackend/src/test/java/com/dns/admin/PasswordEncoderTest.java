//package com.dns.admin;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import static org.assertj.core.api.Assertions.assertThat;
//
///**
// * @author valeriali on {06.09.2023}
// * @project TechnoShopProject
// */
//public class PasswordEncoderTest {
//
//    @Test
//    public void testEncodePassword() {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String rawPassword = "admin";
//        String encodedPassword = passwordEncoder.encode(rawPassword);
//
//        System.out.println(encodedPassword);
//
//        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
//
//        assertThat(matches).isTrue();
//    }
//
//}

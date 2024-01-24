package com.dns.admin.security;

import com.dns.admin.user.UserNotFoundException;
import com.dns.admin.user.UserRepository;
import com.dns.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author valeriali on {15.12.2023}
 * @project TechnoShopProject
 */
public class DnsUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        if (user != null) {
            return new DnsUserDetails(user);
        }
        throw new UsernameNotFoundException("Пользователь с email " + email + " не найден");
    }
}

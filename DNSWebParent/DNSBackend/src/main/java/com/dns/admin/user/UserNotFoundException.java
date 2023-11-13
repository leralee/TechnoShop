package com.dns.admin.user;

/**
 * @author valeriali on {11.09.2023}
 * @project TechnoShopProject
 */
public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }
}

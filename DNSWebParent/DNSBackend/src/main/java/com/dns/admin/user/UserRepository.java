package com.dns.admin.user;

import com.dns.common.entity.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @author valeriali on {02.09.2023}
 * @project TechnoShopProject
 */
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByEmail(String email);
}

package com.dns.admin.user;

import com.dns.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author valeriali on {02.09.2023}
 * @project TechnoShopProject
 */

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    Long countById(Integer id);

    @Query("Update User u Set u.enabled = ?2 where u.id=?1")
    @Modifying
    public void updateEnabledStatus(Integer id, boolean enabled);
}

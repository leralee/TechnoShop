package com.dns.admin.user;

import com.dns.common.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    Long countById(Integer id);

    @Query("SELECT u FROM User u WHERE CONCAT(u.id, ' ', u.email, ' ', u.firstName, ' ', u.lastName) " +
            "LIKE %:keyword%")
    Page<User> findAll(String keyword, Pageable pageable);

    @Query("Update User u Set u.enabled = (:enabled) where u.id=(:id)")
    @Modifying
    void updateEnabledStatus(Integer id, boolean enabled);
}

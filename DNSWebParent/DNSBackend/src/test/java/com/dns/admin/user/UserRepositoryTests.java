package com.dns.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.dns.common.entity.Role;
import com.dns.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTests {

    @Autowired
    private UserRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUserWithOneRole() {
        Role roleAdmin = entityManager.find(Role.class, 1);

        User me = new User("leralee", "1", "Валерия", "Ли");
        me.addRole(roleAdmin);

        User savedUser = repo.save(me);
        assertThat(savedUser.getId()).isGreaterThan(0);

    }

    @Test
    public void testCreateUserWithTwoRoles() {
        User newUser = new User("david_k@gmail.com", "1", "Давид", "Колесников");
        Role roleEditor = new Role(3);
        Role roleAssistant = new Role(5);

        newUser.addRole(roleAssistant);
        newUser.addRole(roleEditor);

        User savedUser = repo.save(newUser);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers() {
        Iterable<User> listUsers  = repo.findAll();
        listUsers.forEach(System.out::println);
    }

    @Test
    public void testGetUserById() {
        Optional<User> me = repo.findById(1);
        assertThat(me).isNotEmpty();
        System.out.println(me.get());
    }

    @Test
    public void testUpdateUserDetails() {
        Optional<User> me = repo.findById(1);
        assertThat(me).isNotEmpty();
        me.get().setEnabled(true);
        repo.save(me.get());
    }

    @Test
    public void testUpdateUserRoles() {
        Optional<User> user = repo.findById(2);
        Role roleEditor = new Role(3);
        Role roleSalesPerson = new Role(2);
        if (user.isPresent()) {
            user.get().getRoles().remove(roleEditor);
            user.get().addRole(roleSalesPerson);
            repo.save(user.get());
        }
    }

    @Test
    public void testDeleteUser() {
        Integer userId = 2;
        repo.deleteById(userId);
    }

    @Test
    public void testGetUserByEmail() {
        String email = "anna14@gmail.com";
        User user = repo.findByEmail(email);

        assertThat(user).isNotNull();
    }

    @Test
    public void testDisableUser() {
        Integer id = 1;
        repo.updateEnabledStatus(id, false);
    }

    @Test
    public void testEnableUser() {
        Integer id = 3;
        repo.updateEnabledStatus(id, true);
    }

    @Test
    public void testListFirstPage() {
        int pageNumber = 0;
        int pageSize = 4;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = repo.findAll(pageable);

        List<User> listUsers = page.getContent();
        listUsers.forEach((System.out::println));

        assertThat(listUsers.size()).isEqualTo(pageSize);
    }

    @Test
    public void testSearchUsers() {
        String keyword = "Tom";

        int pageNumber = 0;
        int pageSize = 4;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = repo.findAll(keyword, pageable);

        List<User> listUsers = page.getContent();
        listUsers.forEach(System.out::println);

        assertThat(listUsers.size()).isGreaterThan(0);

    }
}

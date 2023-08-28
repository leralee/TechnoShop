//package com.example.techno_shop;
//
//
//import com.example.techno_shop.model.Role;
//import com.example.techno_shop.model.User;
//import com.example.techno_shop.repository.UserRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.test.annotation.Rollback;
//
//import java.util.NoSuchElementException;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Rollback(value = false)
//public class UserRepositoryTests {
//    @Autowired
//    private UserRepository repo;
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Test
//    public void testCreateNewUserWithOneRole(){
//        Role roleAdmin = entityManager.find(Role.class,1);
//        User me = new User("kenobi", "123", "Valeriya", "Lee");
//        me.addRole(roleAdmin);
//        User savedUser = repo.save(me);
//        assertThat(savedUser.getId()).isGreaterThan(0);
//    }
//
//    @Test
//    public void testCreateNewUserWithTwoRoles(){
//        User user2 = new User("freshovich", "123", "Nikita", "Budnik");
//        Role roleEditor = new Role(3);
//        Role roleAssitant = new Role(5);
//        user2.addRole(roleEditor);
//        user2.addRole(roleAssitant);
//
//        User savedUser = repo.save(user2);
//
//        assertThat(savedUser.getId()).isGreaterThan(0);
//    }
//
//    @Test
//    public void testListAllUsers() {
//        Iterable<User> listUsers = repo.findAll();
//        listUsers.forEach(System.out::println);
//    }
//
//    @Test
//    public void testGetUserById() {
//        Optional<User> user = repo.findById(1);
//        if (user.isPresent()){
//            System.out.println(user.get());
//        }else {
//            throw new NoSuchElementException();
//        }
//        assertThat(user).isNotNull();
//    }
//
//    @Test
//    public void testUpdateUserRoles() {
//        User user = repo.findById(1).get();
//        Role roleEditor = new Role(3);
//        Role roleSalesperson = new Role(2);
//        user.getRoles().remove(roleEditor);
//        user.addRole(roleSalesperson);
//        repo.save(user);
//    }
//
//    @Test
//    public void testDeleteUser() {
//        Integer userId = 3;
//        repo.deleteById(userId);
//    }
//}

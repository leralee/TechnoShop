//package com.example.techno_shop;
//
//import com.example.techno_shop.model.Role;
//import com.example.techno_shop.repository.RoleRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
////import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Rollback(value = false)
//public class RoleRepositoryTests {
//
//    @Autowired
//    private RoleRepository repo;
//
//    @Test
//    public void testCreateFirstRole(){
//        Role roleAdmin = new Role("Админ", "контролирует все");
//
//        Role savedRole = repo.save(roleAdmin);
//        assertThat(savedRole.getId()).isGreaterThan(0);
//    }
//
//    @Test
//    public void testCreateRestRoles() {
//        Role roleSalesperson = new Role("Менеджер по продажам", "контролирует цены "+
//                "клиентов, доставки, заказы");
//        Role roleEditor = new Role("Редактор", "контролирует категории " +
//                "бренды, продукты");
//        Role roleShipper = new Role("Логист", "может обновлять статус заказа");
//        Role roleAssistant = new Role("Ассистент", "оказывает поддержку, отвечает на вопросы и отзывы");
//        repo.saveAll(List.of(roleSalesperson, roleEditor, roleShipper, roleAssistant));
//
//    }
//}

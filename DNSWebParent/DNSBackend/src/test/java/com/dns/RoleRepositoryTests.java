package com.dns;

import static org.assertj.core.api.Assertions.assertThat;

import com.dns.admin.user.RoleRepository;
import com.dns.common.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(value = false)
public class RoleRepositoryTests {

	@Autowired
	private RoleRepository repo;

	@Test
	public void testCreateFirstRole() {
		Role roleAdmin = new Role("Админ", "управляет всем");
		Role savedRole = repo.save(roleAdmin);
		assertThat(savedRole.getId()).isGreaterThan(0);

	}

	@Test
	public void testCreateRestRoles() {
		Role roleSalesPerson = new Role("Менеджер по продажам", "управляет ценами, клиентами, доставками" +
				"заказами и отчетами о продажах");

		Role roleEditor = new Role("Редактор", "управляет категориями, брендами, продуктами" +
				"статьями и меню");

		Role roleShipper = new Role("Логистика", "Просматривает товары, заказы и обновляет статус заказов");

		Role roleAssistant = new Role("Ассистент", "Управляет вопросами и отзывами");

		repo.saveAll(List.of(roleSalesPerson, roleEditor, roleShipper, roleAssistant));

	}

}

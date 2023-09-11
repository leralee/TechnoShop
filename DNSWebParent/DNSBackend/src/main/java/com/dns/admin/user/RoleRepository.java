package com.dns.admin.user;

import com.dns.common.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author valeriali on {29.08.2023}
 * @project TechnoShopProject
 */

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

}

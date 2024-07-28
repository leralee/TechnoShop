package com.dns.admin.setting;

import com.dns.common.entity.Setting;
import org.springframework.data.repository.CrudRepository;

/**
 * @author valeriali
 * @project TechnoShopProject
 */
public interface SettingRepository extends CrudRepository<Setting, String> {
}

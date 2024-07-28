package com.dns.common.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author valeriali
 * @project TechnoShopProject
 */

@Entity
@Table(name="settings")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Setting {

    @Id
    @Column(name = "`key`", nullable = false, length = 128)
    private String key;

    @Column(nullable = false, length = 1024)
    private String value;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 45)
    private SettingCategory category;

}
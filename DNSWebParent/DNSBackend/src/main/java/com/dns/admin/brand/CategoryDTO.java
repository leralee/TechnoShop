package com.dns.admin.brand;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {
    private Integer id;
    private String name;

    public CategoryDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}

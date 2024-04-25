package com.dns.common.entity;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Nonnull
    private String name;

    @Nonnull
    private String value;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    public ProductDetail(@Nonnull String name, @Nonnull String value, Product product) {
        this.name = name;
        this.value = value;
        this.product = product;
    }
}

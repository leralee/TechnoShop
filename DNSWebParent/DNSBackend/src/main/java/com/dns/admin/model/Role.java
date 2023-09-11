//package com.example.admin.model;
//
//
//import lombok.*;
//import javax.persistence.*;
//import java.util.Objects;
//
//
//@Entity
//@Table(name="roles")
//@NoArgsConstructor
//@Getter
//@Setter
//
//public class Role {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @Column(name = "name", length = 40, nullable = false, unique = true)
//    private String name;
//
//    @Column(nullable = false)
//    private String description;
//
//    public Role(String name) {
//        this.name = name;
//    }
//
//    public Role(Integer id) {
//        this.id = id;
//    }
//
//
//
//    public Role(String name, String description) {
//        this.name = name;
//        this.description = description;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Role role = (Role) o;
//        return Objects.equals(id, role.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }
//
//    @Override
//    public String toString() {
//        return this.name;
//    }
//
//
//}

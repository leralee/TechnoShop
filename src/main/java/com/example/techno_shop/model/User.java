//package com.example.admin.model;
//
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//
//import javax.persistence.*;
//import java.util.HashSet;
//import java.util.Set;
////
//@Entity
//@Table(name="users")
//@NoArgsConstructor
//@Data
//public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @Column(nullable = false, unique = true)
//    private String username;
//
//    @Column(length = 64, nullable = false)
//    private String password;
//
//    @Column(name = "first_name")
//    private String firstName;
//
//    @Column(name = "last_name")
//    private String lastName;
//
//    @Column(length = 64)
//    private String photos;
//
//    private boolean enabled;
//
//
//    // так как ролей может быть несколько объявляем set
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name="users_roles",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name="role_id")
//    )
//    private Set<Role> roles = new HashSet<>();
//
//
//    public void addRole(Role role) {
//        this.roles.add(role);
//    }
//
//
//    public User(String username, String password, String firstName, String lastName) {
//        this.username = username;
//        this.password = password;
//        this.firstName = firstName;
//        this.lastName = lastName;
//    }
//
//    public User(String username, String password) {
//        this.username = username;
//        this.password = password;
//    }
//
//    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", username='" + username + '\'' +
//                ", firstName='" + firstName + '\'' +
//                ", lastName='" + lastName + '\'' +
//                ", roles=" + roles +
//                '}';
//    }
//}

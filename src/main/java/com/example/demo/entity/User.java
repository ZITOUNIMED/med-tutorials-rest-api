package com.example.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name="APP_USER")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="USER_ID")
    private Long id;

    private String firstname;

    private String lastname;

    @Column(unique=true)
    private String username;

    private String password;

    private boolean enable;

    private String email;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="USERS_ROLES",
            joinColumns={@JoinColumn(name="USER_ID")},
            inverseJoinColumns={@JoinColumn(name="ROLE_ID")})
    private List<Role> roles;
}
package com.whitespace.bankapi.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    // Encoded password
    @Column(nullable = false)
    private String password;

    // Flag to denote a super user
    private boolean superUser;

    // Global roles (e.g., ROLE_SUPERUSER, ROLE_MANAGER)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles = new HashSet<>();

    // Constructors, getters and setters

    public Employee() { }

    public Employee(String username, String password, boolean superUser) {
        this.username = username;
        this.password = password;
        this.superUser = superUser;
    }

    // Getters and setters ...
    public Long getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isSuperUser() {
        return superUser;
    }
    public void setSuperUser(boolean superUser) {
        this.superUser = superUser;
    }
    public Set<String> getRoles() {
        return roles;
    }
    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}

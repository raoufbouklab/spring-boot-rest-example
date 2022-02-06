package com.example.bookstore.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "username", unique = true, nullable = false, length = 30)
    private String username;
    @Column(name = "firstname", length = 30)
    private String firstName;
    @Column(name = "lastname", nullable = false, length = 30)
    private String lastName;
    @Column(name = "email", unique = true, nullable = false, length = 50)
    private String email;
    @Column(name = "password", nullable = false, length = 30)
    private String password;
    @Column(name = "active")
    private boolean active;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private Set<Role> roles;

    public User(String username, String firstName, String lastName, String email, String password, boolean active, Set<Role> roles) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.active = active;
        this.roles = roles;
    }
}

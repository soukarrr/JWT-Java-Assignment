package com.example.ExampleJWT.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Basic
    @Column(name = "username", nullable = false, length = 150)
    private String username;


    @Basic
    @Column(name = "email", nullable = false, length = 250)
    private String email;


    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
}

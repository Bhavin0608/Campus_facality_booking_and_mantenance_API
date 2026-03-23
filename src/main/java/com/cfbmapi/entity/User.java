package com.cfbmapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)// no setter method for this field
    private int id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING) // in Database the String is store instead of enum number like 0 - ADMIN... etc
    @Column(nullable = false)
    private UserRole role;

    @Column
    private boolean active = true;

    @Column
    @Setter(AccessLevel.NONE) // No setter method for this field
    private LocalDateTime createdAt;

    @PrePersist // Set time before saving/persist on database...
    public void setCreatedAt(){
        this.createdAt = LocalDateTime.now();
    }
}

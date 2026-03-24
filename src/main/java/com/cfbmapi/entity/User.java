package com.cfbmapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
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

    // RELATION-SHIPS
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) // Here one user have many booking
    private List<Booking> bookings;

    @OneToMany(mappedBy = "reportedByUser", cascade = CascadeType.ALL) // One User can have many maintenance tickets
    private List<MaintenanceTicket> reportedTickets;

    @OneToMany(mappedBy = "assignedToUser", cascade = CascadeType.ALL) // One staff can be assigned many tickets
    private List<MaintenanceTicket> assignedTickets;

}

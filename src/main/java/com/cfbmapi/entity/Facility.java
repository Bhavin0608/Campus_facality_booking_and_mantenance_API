package com.cfbmapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "facilities")
@Data
public class Facility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private int id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FacilityType type;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FacilityStatus status = FacilityStatus.AVAILABLE;

    //RELATION-SHIPS
    @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL) // One facility have many bookings
    @JsonIgnore
    private List<Booking> bookings;

    @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL) // One User can have many maintenance tickets
    @JsonIgnore
    private List<MaintenanceTicket> tickets;

    //to String
    @Override
    public String toString(){
        return "Id : " +getId()+
                "\nName : " +getName()+
                "\nType : " +getType()+
                "\nCapacity : " +getCapacity()+
                "\nLocation : " +getLocation()+
                "\nStatus : " +getStatus();
    }
}

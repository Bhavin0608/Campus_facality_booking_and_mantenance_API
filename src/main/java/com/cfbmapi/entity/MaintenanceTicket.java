package com.cfbmapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "maintenance_ticket")
@Data
public class MaintenanceTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

//    @Lob
    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketPriority priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status = TicketStatus.OPEN;

    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdAt;

    @PrePersist // Set time before saving/persist on database...
    public void setCreatedAt(){
        this.createdAt = LocalDateTime.now();
    }

    @Column(nullable = true) // if work is not done then it can be null
    private LocalDateTime resolvedAt;

    //RELATION-SHIPS
    @ManyToOne
    @JoinColumn(nullable = false)
    private Facility facility;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User reportedByUser;

    @ManyToOne
    private User assignedToUser;

    //toString
    @Override
    public String toString(){
        return "Id : " +getId()+
                "\nTitle : " +getTitle()+
                "\nDescription : " +getDescription()+
                "\nPriority : " +getPriority()+
                "\nStatus : " +getStatus()+
                "\nResolved At : " +getResolvedAt()+
                "\nCreated At : "+getCreatedAt();
    }
}

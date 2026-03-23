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

    @Lob
    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketPriority priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status = TicketStatus.OPEN;

    @Column(nullable = false, name = "facility_id")
    private int facilityId;

    @Column(nullable = false, name = "reported_by")
    private int reportedBy;

    @Column(nullable = false, name = "assigned_to")
    private int assignedTo;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = true) // if work is not done then it can be null
    private LocalDateTime resolvedAt;
}

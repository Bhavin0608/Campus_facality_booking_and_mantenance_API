package com.cfbmapi.controller;

import com.cfbmapi.dto.TicketCreateRequest;
import com.cfbmapi.entity.MaintenanceTicket;
import com.cfbmapi.entity.TicketPriority;
import com.cfbmapi.entity.TicketStatus;
import com.cfbmapi.entity.UserRole;
import com.cfbmapi.service.MaintenanceTicketService;
import com.cfbmapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class MaintenanceTicketController {
    private final MaintenanceTicketService maintenanceTicketService;
    private final UserService userService;

    //--------------------------------- Create ticket -------------------------------------
    @PostMapping
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<?> createTicket(@RequestBody TicketCreateRequest request) {
        try {
            MaintenanceTicket ticket = maintenanceTicketService.createTicket(
                    request.getTitle(),
                    request.getDescription(),
                    request.getPriority(),
                    request.getFacilityId(),
                    request.getReportedByUserId()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(ticket);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error: " + e.getMessage());
        }
    }

    //------------------------------- Update ticket status --------------------------------------------
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> updateTicketStatus(@PathVariable int id,
                                                @RequestParam TicketStatus status) {
        try {
            MaintenanceTicket ticket = maintenanceTicketService.updateTicketStatus(id, status);
            return ResponseEntity.ok(ticket);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        }
    }

    // Update ticket
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<?> updateTicket(@PathVariable int id,
                                          @RequestBody TicketCreateRequest request) {
        try {
            if(maintenanceTicketService.getTicketById(id).getStatus() == TicketStatus.CLOSED ||
                    maintenanceTicketService.getTicketById(id).getStatus() == TicketStatus.RESOLVED)
                return ResponseEntity.badRequest().body("Ticket is not updatable because it is closed or resolved");
            MaintenanceTicket ticket = new MaintenanceTicket();
            ticket.setTitle(request.getTitle());
            ticket.setDescription(request.getDescription());
            ticket.setPriority(request.getPriority());

            MaintenanceTicket updatedTicket = maintenanceTicketService.updateTicket(id, ticket);
            return ResponseEntity.ok(updatedTicket);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        }
    }

    //------------------------------------- Delete ticket ----------------------------------------
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> deleteTicket(@PathVariable int id) {
        try {
            maintenanceTicketService.deleteTicket(id);
            return ResponseEntity.ok("Ticket deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        }
    }

    //------------------------------- Get all tickets and its filters -------------------------------------
    @GetMapping
    @PreAuthorize("hasAnyRole('STAFF','ADMIN')")
    public ResponseEntity<?> getAllTickets() {
        try {
            List<MaintenanceTicket> tickets = maintenanceTicketService.getAllTickets();
            return ResponseEntity.ok(tickets);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error: " + e.getMessage());
        }
    }

    // Get ticket by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getTicketById(@PathVariable int id) {
        try {
            MaintenanceTicket ticket = maintenanceTicketService.getTicketById(id);
            return ResponseEntity.ok(ticket);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        }
    }

    // Get tickets by facility
    @GetMapping("/facility/{facilityId}")
    @PreAuthorize("hasAnyRole('STAFF','ADMIN','STUDENT')")
    public ResponseEntity<?> getTicketsByFacility(@PathVariable int facilityId) {
        try {
            List<MaintenanceTicket> tickets = maintenanceTicketService.getTicketsByFacility(facilityId);
            return ResponseEntity.ok(tickets);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error: " + e.getMessage());
        }
    }

    // Get tickets by status
    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('STAFF','ADMIN','STUDENT')")
    public ResponseEntity<?> getTicketsByStatus(@PathVariable TicketStatus status) {
        try {
            List<MaintenanceTicket> tickets = maintenanceTicketService.getTicketsByStatus(status);
            return ResponseEntity.ok(tickets);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error: " + e.getMessage());
        }
    }

    // Get tickets by priority
    @GetMapping("/priority/{priority}")
    @PreAuthorize("hasAnyRole('STAFF','ADMIN','STUDENT')")
    public ResponseEntity<?> getTicketsByPriority(@PathVariable TicketPriority priority) {
        try {
            List<MaintenanceTicket> tickets = maintenanceTicketService.getTicketsByPriority(priority);
            return ResponseEntity.ok(tickets);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error: " + e.getMessage());
        }
    }

    // Get tickets assigned to user
    @GetMapping("/assigned/{userId}")
    public ResponseEntity<?> getTicketsAssignedToUser(@PathVariable int userId) {
        try {
            if(userService.getUserById(userId).getRole() != UserRole.STAFF)
                return ResponseEntity.badRequest().body("User is not a staff");
            List<MaintenanceTicket> tickets = maintenanceTicketService.getTicketsAssignedToUser(userId);
            return ResponseEntity.ok(tickets);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error: " + e.getMessage());
        }
    }

    // Get tickets reported by user
    @GetMapping("/reported/{userId}")
    public ResponseEntity<?> getTicketsReportedByUser(@PathVariable int userId) {
        try {
            List<MaintenanceTicket> tickets = maintenanceTicketService.getTicketsReportedByUser(userId);
            return ResponseEntity.ok(tickets);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error: " + e.getMessage());
        }
    }

    //------------------------------------ Assign ticket to staff and Resolve -------------------------------------
    @PutMapping("/{id}/assign")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> assignTicket(@PathVariable int id,
                                          @RequestParam int staffUserId) {
        try {
            if(userService.getUserById(staffUserId).getRole() != UserRole.STAFF)
                return ResponseEntity.badRequest().body("User is not a staff");
            MaintenanceTicket ticket = maintenanceTicketService.assignTicket(id, staffUserId);
            return ResponseEntity.ok(ticket);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        }
    }

    // Resolve ticket
    @PutMapping("/{id}/resolve")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> resolveTicket(@PathVariable int id) {
        try {
            MaintenanceTicket ticket = maintenanceTicketService.resolveTicket(id);
            return ResponseEntity.ok(ticket);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        }
    }
}
package com.cfbmapi.service;

import com.cfbmapi.entity.MaintenanceTicket;
import com.cfbmapi.entity.TicketPriority;
import com.cfbmapi.entity.TicketStatus;
import com.cfbmapi.entity.Facility;
import com.cfbmapi.entity.User;
import com.cfbmapi.repository.MaintenanceTicketRepository;
import com.cfbmapi.repository.FacilityRepository;
import com.cfbmapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceTicketService {
    private final MaintenanceTicketRepository maintenanceTicketRepository;
    private final FacilityRepository facilityRepository;
    private final UserRepository userRepository;

    // Create maintenance ticket
    @Transactional
    public MaintenanceTicket createTicket(String title, String description, TicketPriority priority,
                                          int facilityId, int reportedByUserId) {
        try {
            // Check if facility exists
            Facility facility = facilityRepository.findById(facilityId)
                    .orElseThrow(() -> new RuntimeException("Facility not found"));

            // Check if user exists
            User reportedByUser = userRepository.findById(reportedByUserId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Create ticket
            MaintenanceTicket ticket = new MaintenanceTicket();
            ticket.setTitle(title);
            ticket.setDescription(description);
            ticket.setPriority(priority);
            ticket.setStatus(TicketStatus.OPEN);
            ticket.setFacility(facility);
            ticket.setReportedByUser(reportedByUser);
            ticket.setAssignedToUser(null); // Not assigned yet

            return maintenanceTicketRepository.save(ticket);
        } catch (Exception e) {
            throw new RuntimeException("Error creating ticket: " + e.getMessage());
        }
    }

    // Get ticket by ID
    public MaintenanceTicket getTicketById(int id) {
        return maintenanceTicketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    // Get all tickets
    public List<MaintenanceTicket> getAllTickets() {
        return maintenanceTicketRepository.findAll();
    }

    // Get all tickets for a facility
    public List<MaintenanceTicket> getTicketsByFacility(int facilityId) {
        return maintenanceTicketRepository.findAllByFacility_Id(facilityId);
    }

    // Get tickets by status
    public List<MaintenanceTicket> getTicketsByStatus(TicketStatus status) {
        return maintenanceTicketRepository.findAllByStatus(status);
    }

    // Get tickets by priority
    public List<MaintenanceTicket> getTicketsByPriority(TicketPriority priority) {
        return maintenanceTicketRepository.findAllByPriority(priority);
    }

    // Get tickets assigned to a user
    public List<MaintenanceTicket> getTicketsAssignedToUser(int userId) {
        return maintenanceTicketRepository.findByAssignedToUser_Id(userId);
    }

    // Get tickets reported by a user
    public List<MaintenanceTicket> getTicketsReportedByUser(int userId) {
        return maintenanceTicketRepository.findByReportedByUser_Id(userId);
    }

    // Assign ticket to staff member
    @Transactional
    public MaintenanceTicket assignTicket(int ticketId, int staffUserId) {
        try {
            MaintenanceTicket ticket = getTicketById(ticketId);

            User staffUser = userRepository.findById(staffUserId)
                    .orElseThrow(() -> new RuntimeException("Staff user not found"));

            ticket.setAssignedToUser(staffUser);
            ticket.setStatus(TicketStatus.IN_PROGRESS);

            return maintenanceTicketRepository.save(ticket);
        } catch (Exception e) {
            throw new RuntimeException("Error assigning ticket: " + e.getMessage());
        }
    }

    // Update ticket status
    @Transactional
    public MaintenanceTicket updateTicketStatus(int id, TicketStatus status) {
        try {
            MaintenanceTicket ticket = getTicketById(id);
            ticket.setStatus(status);

            if (status == TicketStatus.RESOLVED) {
                ticket.setResolvedAt(LocalDateTime.now());
            }

            return maintenanceTicketRepository.save(ticket);
        } catch (Exception e) {
            throw new RuntimeException("Error updating ticket status: " + e.getMessage());
        }
    }

    // Resolve ticket (mark as resolved and set resolved time)
    @Transactional
    public MaintenanceTicket resolveTicket(int id) {
        try {
            MaintenanceTicket ticket = getTicketById(id);
            ticket.setStatus(TicketStatus.RESOLVED);
            ticket.setResolvedAt(LocalDateTime.now());

            return maintenanceTicketRepository.save(ticket);
        } catch (Exception e) {
            throw new RuntimeException("Error resolving ticket: " + e.getMessage());
        }
    }

    // Update ticket details
    @Transactional
    public MaintenanceTicket updateTicket(int id, MaintenanceTicket ticketDetails) {
        try {
            MaintenanceTicket ticket = getTicketById(id);

            if (ticketDetails.getTitle() != null) {
                ticket.setTitle(ticketDetails.getTitle());
            }
            if (ticketDetails.getDescription() != null) {
                ticket.setDescription(ticketDetails.getDescription());
            }
            if (ticketDetails.getPriority() != null) {
                ticket.setPriority(ticketDetails.getPriority());
            }

            return maintenanceTicketRepository.save(ticket);
        } catch (Exception e) {
            throw new RuntimeException("Error updating ticket: " + e.getMessage());
        }
    }

    // Delete ticket
    @Transactional
    public void deleteTicket(int id) {
        try {
            maintenanceTicketRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting ticket: " + e.getMessage());
        }
    }
}
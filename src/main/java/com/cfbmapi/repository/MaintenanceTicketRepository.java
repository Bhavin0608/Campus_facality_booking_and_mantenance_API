package com.cfbmapi.repository;

import com.cfbmapi.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MaintenanceTicketRepository extends JpaRepository<MaintenanceTicket,Integer> {

    List<MaintenanceTicket> findAllByFacility(Facility facility);
    List<MaintenanceTicket> findAllByStatus(TicketStatus status);
    List<MaintenanceTicket> findAllByAssignedToUser(User assignedToUser);
    List<MaintenanceTicket> findAllByReportedByUser(User reportedByUser);
    List<MaintenanceTicket> findAllByPriority(TicketPriority priority);

}

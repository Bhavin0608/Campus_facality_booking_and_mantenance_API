package com.cfbmapi.repository;

import com.cfbmapi.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceTicketRepository extends JpaRepository<MaintenanceTicket,Integer> {

    List<MaintenanceTicket> findAllByFacility_Id(int facilityId);
    List<MaintenanceTicket> findAllByStatus(TicketStatus status);
    List<MaintenanceTicket> findByAssignedToUser_Id(int userId);
    List<MaintenanceTicket> findByReportedByUser_Id(int UserId);
    List<MaintenanceTicket> findAllByPriority(TicketPriority priority);

}

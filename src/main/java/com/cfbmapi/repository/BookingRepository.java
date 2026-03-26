package com.cfbmapi.repository;

import com.cfbmapi.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Integer> {

    List<Booking> findAllByUser(User user);
    List<Booking> findAllByFacility(Facility facility);
    List<Booking> findAllByStatus(BookingStatus status);
    List<Booking> findByUserAndFacilityAndBookingDate(User user, Facility facility, LocalDate bookingDate);
    List<Booking> findByBookingDateBetween(LocalDate bookingDateAfter, LocalDate bookingDateBefore);
    List<Booking> findAllByUser_Id(int userId);
    List<Booking> findAllByFacility_Id(int facilityId);
    List<Booking> findByFacility_IdAndBookingDate(int facilityId, LocalDate bookingDate);

}

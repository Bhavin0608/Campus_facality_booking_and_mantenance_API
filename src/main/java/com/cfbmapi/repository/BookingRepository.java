package com.cfbmapi.repository;

import com.cfbmapi.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Integer> {

    List<Booking> findAllByUser(User user);
    List<Booking> findAllByFacility(Facility facility);
    List<Booking> findAllByStatus(BookingStatus status);
    List<Booking> findByUserAndFacilityAndBookingDate(User user, Facility facility, LocalDate bookingDate);
    List<Booking> findByBookingDateBetween(LocalDate bookingDateAfter, LocalDate bookingDateBefore);

}

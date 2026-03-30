package com.cfbmapi.service;

import com.cfbmapi.dto.BookingUpdateRequest;
import com.cfbmapi.entity.Booking;
import com.cfbmapi.entity.BookingStatus;
import com.cfbmapi.entity.Facility;
import com.cfbmapi.entity.User;
import com.cfbmapi.repository.BookingRepository;
import com.cfbmapi.repository.FacilityRepository;
import com.cfbmapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final FacilityRepository facilityRepository;
    private final UserRepository userRepository;

    //------------------- Create booking with conflict check -------------------------
    @Transactional
    public Booking createBooking(int userId, int facilityId, LocalDate date,
                                 LocalTime startTime, LocalTime endTime, String purpose) {
        try {
            // Check if time slot is available
            if (!isTimeSlotAvailable(facilityId, date, startTime, endTime)) {
                throw new RuntimeException("Time slot not available for this facility");
            }

            // Check if user exists
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Check if facility exists
            Facility facility = facilityRepository.findById(facilityId)
                    .orElseThrow(() -> new RuntimeException("Facility not found"));

            // Create booking
            Booking booking = new Booking();
            booking.setUser(user);
            booking.setFacility(facility);
            booking.setBookingDate(date);
            booking.setStartTime(startTime);
            booking.setEndTime(endTime);
            booking.setPurpose(purpose);
            booking.setStatus(BookingStatus.PENDING);

            return bookingRepository.save(booking);
        } catch (Exception e) {
            throw new RuntimeException("Error creating booking: " + e.getMessage());
        }
    }

    //------------------------------ Update booking --------------------------------
    @Transactional
    public Booking updateBooking(int id, BookingUpdateRequest bookingDetails) {
        try {
            Booking booking = getBookingById(id);

            if (bookingDetails.getDate() != null) {
                booking.setBookingDate(bookingDetails.getDate());
            }
            if (bookingDetails.getStartTime() != null) {
                booking.setStartTime(bookingDetails.getStartTime());
            }
            if (bookingDetails.getEndTime() != null) {
                booking.setEndTime(bookingDetails.getEndTime());
            }
            if (bookingDetails.getPurpose() != null) {
                booking.setPurpose(bookingDetails.getPurpose());
            }

            return bookingRepository.save(booking);
        } catch (Exception e) {
            throw new RuntimeException("Error updating booking: " + e.getMessage());
        }
    }

    //--------------------- Approve booking (Admin only) --------------------------
    @Transactional
    public Booking approveBooking(int id) {
        try {
            Booking booking = getBookingById(id);
            booking.setStatus(BookingStatus.APPROVED);
            return bookingRepository.save(booking);
        } catch (Exception e) {
            throw new RuntimeException("Error approving booking: " + e.getMessage());
        }
    }

    // Reject booking (Admin only)
    @Transactional
    public Booking rejectBooking(int id) {
        try {
            Booking booking = getBookingById(id);
            booking.setStatus(BookingStatus.REJECTED);
            return bookingRepository.save(booking);
        } catch (Exception e) {
            throw new RuntimeException("Error rejecting booking: " + e.getMessage());
        }
    }

    // Cancel booking (User)
    @Transactional
    public Booking cancelBooking(int id) {
        try {
            Booking booking = getBookingById(id);
            booking.setStatus(BookingStatus.CANCELLED);
            return bookingRepository.save(booking);
        } catch (Exception e) {
            throw new RuntimeException("Error cancelling booking: " + e.getMessage());
        }
    }

    //------------------------- Get booking by ID --------------------------------
    public Booking getBookingById(int id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    // Get all bookings
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Get all bookings for a user
    public List<Booking> getUserBookings(int userId) {
        return bookingRepository.findAllByUser_Id(userId);
    }

    // Get all bookings for a facility
    public List<Booking> getFacilityBookings(int facilityId) {
        return bookingRepository.findAllByFacility_Id(facilityId);
    }

    // Get bookings by status
    public List<Booking> getBookingsByStatus(BookingStatus status) {
        return bookingRepository.findAllByStatus(status);
    }

    //------- Check if time slot is available (important for conflict checking) --------
    public boolean isTimeSlotAvailable(int facilityId, LocalDate date,
                                       LocalTime startTime, LocalTime endTime) {
        try {
            List<Booking> bookings = bookingRepository.findByFacility_IdAndBookingDate(facilityId, date);

            for (Booking booking : bookings) {
                if (booking.getStatus() == BookingStatus.APPROVED || booking.getStatus() == BookingStatus.PENDING) {// Only check approved bookings
                    if (!(endTime.isBefore(booking.getStartTime()) && startTime.isAfter(booking.getEndTime()))) {// Check for time overlap
                        return false; // Conflict found
                    }
                }
            }
            return true; // No conflicts
        } catch (Exception e) {
            throw new RuntimeException("Error checking availability: " + e.getMessage());
        }
    }
}
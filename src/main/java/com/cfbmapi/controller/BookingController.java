package com.cfbmapi.controller;

import com.cfbmapi.dto.BookingCreateRequest;
import com.cfbmapi.dto.BookingUpdateRequest;
import com.cfbmapi.entity.Booking;
import com.cfbmapi.entity.BookingStatus;
import com.cfbmapi.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    //----------------------- Create Booking ---------------------------
    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingCreateRequest request){
        try{
            Booking booking = bookingService.createBooking(
                    request.getUserId(),
                    request.getFacilityId(),
                    request.getDate(),
                    request.getStartTime(),
                    request.getEndTime(),
                    request.getPurpose()
            );
            return ResponseEntity.ok(booking);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error : "+e.getMessage());
        }
    }

    //--------------------------- Update Booking ----------------------------
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBooking(@PathVariable int id, @RequestBody BookingUpdateRequest request){
        try{
            Booking booking = bookingService.updateBooking(id,request);
            return ResponseEntity.ok(booking);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error : "+e.getMessage());
        }
    }

    // ------------------- Approve Reject and Cancel Booking -----------------
    @PutMapping("/{id}/approve")
    public ResponseEntity<?> approveBooking(@PathVariable int id){
        try{
            Booking booking = bookingService.approveBooking(id);
            return ResponseEntity.ok("Approved :\n" +booking);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error : "+e.getMessage());
        }
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<?> rejectBooking(@PathVariable int id){
        try{
            Booking booking = bookingService.rejectBooking(id);
            return ResponseEntity.ok("Rejected :\n" +booking);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error : "+e.getMessage());
        }
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelBooking(@PathVariable int id){
        try{
            Booking booking = bookingService.cancelBooking(id);
            return ResponseEntity.ok("Cancelled :\n" +booking);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error : "+e.getMessage());
        }
    }

    //--------------------- Get booking Using Various filters ----------------------
    @GetMapping
    public ResponseEntity<?> getAllBookings(){
        try{
            List<Booking> bookings = bookingService.getAllBookings();
            return ResponseEntity.ok(bookings);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error : "+e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getBooking(@PathVariable int id){
        try{
            Booking booking = bookingService.getBookingById(id);
            return ResponseEntity.ok(booking);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error : "+e.getMessage());
        }
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getUserBookings(@PathVariable int userId){
        try{
            List<Booking> bookings = bookingService.getUserBookings(userId);
            return ResponseEntity.ok(bookings);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error : "+e.getMessage());
        }
    }

    @GetMapping("/facility/{facilityId}")
    public ResponseEntity<?> getAllFacilityBookings(@PathVariable int facilityId){
        try{
            List<Booking> bookings = bookingService.getFacilityBookings(facilityId);
            return ResponseEntity.ok(bookings);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error : "+e.getMessage());
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getAllBookingsByStatus(@PathVariable BookingStatus status){
        try{
            List<Booking> bookings = bookingService.getBookingsByStatus(status);
            return ResponseEntity.ok(bookings);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error : "+e.getMessage());
        }
    }
}

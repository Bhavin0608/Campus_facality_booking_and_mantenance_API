package com.cfbmapi.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class BookingCreateRequest {
    private int userId; // Which user make the booking
    private int facilityId; // For which facility that user make the booking
    private LocalDate date; // For which date
    private LocalTime startTime;
    private LocalTime endTime;
    private String purpose;
}
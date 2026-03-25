package com.cfbmapi.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class BookingUpdateRequest{
    private LocalDate date; // For which date
    private LocalTime startTime;
    private LocalTime endTime;
    private String purpose;
}
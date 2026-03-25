package com.cfbmapi.dto;

import com.cfbmapi.entity.TicketPriority;
import lombok.Data;

@Data
public class TicketCreateRequest {
    private String title;
    private String description;
    private TicketPriority priority;
    private int facilityId;
    private int reportedByUserId;
}

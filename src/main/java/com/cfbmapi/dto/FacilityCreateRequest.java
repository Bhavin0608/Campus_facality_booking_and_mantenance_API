package com.cfbmapi.dto;

import com.cfbmapi.entity.FacilityType;
import lombok.Data;

@Data
public class FacilityCreateRequest {
    private String name;
    private FacilityType type;
    private int capacity;
    private String location;
}

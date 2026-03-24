package com.cfbmapi.dto;

import com.cfbmapi.entity.FacilityStatus;
import lombok.Data;

@Data
public class FacilityStatusRequest {
    private FacilityStatus status;
}

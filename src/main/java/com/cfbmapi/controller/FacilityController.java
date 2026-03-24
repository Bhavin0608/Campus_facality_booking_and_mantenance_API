package com.cfbmapi.controller;

import com.cfbmapi.dto.FacilityCreateRequest;
import com.cfbmapi.dto.FacilityStatusRequest;
import com.cfbmapi.entity.Facility;
import com.cfbmapi.entity.FacilityStatus;
import com.cfbmapi.entity.FacilityType;
import com.cfbmapi.service.FacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facilities")
@RequiredArgsConstructor
public class FacilityController {
    private final FacilityService facilityService;

    //--------------------------- Create Facility ------------------------------------
    @PostMapping
    public ResponseEntity<?> createFacility(@RequestBody FacilityCreateRequest request){
        try{
            Facility facility = facilityService.createFacility(request);
            return ResponseEntity.ok(facility);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error : " +e.getMessage());
        }
    }

    //---------------------------- Update Facility -----------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<?> updateFacility(@PathVariable int id, @RequestBody FacilityCreateRequest request){
        try{
            facilityService.updateFacility(id,request);
            return ResponseEntity.ok("Data Updated...");
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error : " +e.getMessage());
        }
    }

    // Update Facility Status
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateFacilityStatus(@PathVariable int id, @RequestBody FacilityStatusRequest status){
        try{
            facilityService.updateFacilityStatus(id,status.getStatus());
            return ResponseEntity.ok("Status is updated to : "+status.getStatus());
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error : " +e.getMessage());
        }
    }

    //--------------------------- Delete Facility ------------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFacility(@PathVariable int id){
        try{
            facilityService.deleteFacility(id);
            return ResponseEntity.ok("Record deleted");
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error : " +e.getMessage());
        }
    }

    //---------------------- Get Facility and Various Filters ------------------------
    @GetMapping
    public ResponseEntity<?> getAllFacilities(){
        try{
            List<Facility> facilities = facilityService.getAllFacilities();
            return ResponseEntity.ok(facilities);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error : " +e.getMessage());
        }
    }

    // Get facility by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getFacilityById(@PathVariable int id){
        try{
            Facility facility = facilityService.getFacilityById(id);
            return ResponseEntity.ok(facility);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error : " +e.getMessage());
        }
    }

    // Get Facilities by type
    @GetMapping("/type/{type}")
    public ResponseEntity<?> getAllFacilities(@PathVariable FacilityType type){
        try{
            List<Facility> facilities = facilityService.getFacilitiesByType(type);
            return  ResponseEntity.ok(facilities);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error : " +e.getMessage());
        }
    }

    // Get only Available Facilities
    @GetMapping("/available")
    public ResponseEntity<?> getAllAvailableFacilities(){
        try{
            List<Facility> facilities = facilityService.getAvailableFacilities();
            return  ResponseEntity.ok(facilities);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error : " +e.getMessage());
        }
    }
}

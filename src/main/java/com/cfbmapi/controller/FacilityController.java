package com.cfbmapi.controller;

import com.cfbmapi.dto.FacilityCreateRequest;
import com.cfbmapi.entity.Facility;
import com.cfbmapi.entity.FacilityStatus;
import com.cfbmapi.entity.FacilityType;
import com.cfbmapi.service.FacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facilities")
@RequiredArgsConstructor
public class FacilityController {
    private final FacilityService facilityService;

    //--------------------------- Create Facility ------------------------------------
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('STAFF')") // when they look after the maintenance tickets
    public ResponseEntity<?> updateFacilityStatus(@PathVariable int id, @RequestParam FacilityStatus status){
        try{
            facilityService.updateFacilityStatus(id,status);
            return ResponseEntity.ok("Status is updated to : "+status);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error : " +e.getMessage());
        }
    }

    //--------------------------- Delete Facility ------------------------------------
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasAnyRole('ADMIN','STAFF','STUDENT')")
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
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasAnyRole('ADMIN','STAFF','STUDENT')") // Filter Option
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
    @PreAuthorize("hasAnyRole('ADMIN','STAFF','STUDENT')") // Filter option
    public ResponseEntity<?> getAllAvailableFacilities(){
        try{
            List<Facility> facilities = facilityService.getAvailableFacilities();
            return  ResponseEntity.ok(facilities);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error : " +e.getMessage());
        }
    }
}
